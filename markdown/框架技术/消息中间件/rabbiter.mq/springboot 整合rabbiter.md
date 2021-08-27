1. 使用的jar
    ```aidl
         <dependency>
                   <groupId>org.springframework.boot</groupId>
                   <artifactId>spring-boot-starter-amqp</artifactId>
         </dependency>
    ```
2.  消息消费方   
    ```aidl
    
    import com.alibaba.fastjson.JSON;
    import com.ccdt.amos.cirs.util.RedisUtil;
    import com.rabbitmq.client.Channel;
    import com.rabbitmq.client.QueueingConsumer;
    import org.slf4j.Logger;
    import org.slf4j.LoggerFactory;
    import org.springframework.amqp.core.*;
    import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
    import org.springframework.amqp.rabbit.connection.ConnectionFactory;
    import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
    import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
    import org.springframework.beans.factory.annotation.Value;
    import org.springframework.context.annotation.Bean;
    import org.springframework.context.annotation.Configuration;
    
    /**
     * @description: RabbitMQ 配置类
     * @author: Zhang
     **/
    @Configuration
    public class RabbitConfig {
        @Value("${mqExchange}")
        String exchangeName;
        @Value("${mqQueue}")
        String queueName;
        @Value("${mqKey}")
        String routingKey;
    
        @Value("${spring.rabbitmq.username}")
        String username;
    
        @Value("${spring.rabbitmq.password}")
        String password;
    
        @Value("${spring.rabbitmq.virtual-host}")
        String mqVm;
    
        @Value("${spring.rabbitmq.host}")
        String host;
    
        @Value("${spring.rabbitmq.port}")
        int port;
    
        private EsIndex esIndex = new EsIndex();
        private static final Logger logger = LoggerFactory.getLogger(RedisUtil.class);
        /**
         * 配置链接信息
         * @return
         */
        @Bean
        public ConnectionFactory connectionFactory() {
            CachingConnectionFactory connectionFactory = new CachingConnectionFactory(host,port);
            connectionFactory.setUsername(username);
            connectionFactory.setPassword(password);
            connectionFactory.setVirtualHost(mqVm);
            // 必须要设置
            connectionFactory.setPublisherConfirms(true);
            return connectionFactory;
        }
        /**
         * 针对消费者配置
         * @return
         */
        @Bean
        public Queue queue() {
            return new Queue(queueName, true);
        }
    
        /**
         * 配置消息交换机
         * FanoutExchange: 将消息分发到所有的绑定队列，无routingkey的概念
         * HeadersExchange ：通过添加属性key-value匹配
         * DirectExchange:按照routingkey分发到指定队列
         * TopicExchange:多关键字匹配
         * @return
         */
        @Bean
        public DirectExchange defaultExchange() {
            return new DirectExchange(exchangeName, true, false);
        }
    
        /**
         * 将消息队列与交换机绑定
         * @return
         */
        @Bean
        public Binding binding() {
            return BindingBuilder.bind(queue()).to(defaultExchange()).with(routingKey);
        }
    
        /**
         * 接受消息的监听，这个监听会接受消息队列的消息
         * 针对消费者配置
         * @return
         */
        @Bean
        public SimpleMessageListenerContainer messageContainer() {
            SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory());
            container.setQueues(queue());
            container.setExposeListenerChannel(true);
            container.setMaxConcurrentConsumers(1);
            container.setConcurrentConsumers(1);
            //设置确认模式手工确认
            container.setAcknowledgeMode(AcknowledgeMode.MANUAL);
            container.setMessageListener(new ChannelAwareMessageListener() {
                @Override
                public void onMessage(Message message, Channel channel) throws Exception {
                    byte[] body = message.getBody();
                    String key = new QueueingConsumer(channel).nextDelivery().getEnvelope().getRoutingKey();
                    esIndex.incrementIndex(new String(body), key);
                    System.out.println("收到消息 : " + new String(body));
                    //确认消息成功消费.
                    try {
                        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    } catch (Exception e) {
                        logger.error("MQ消息处理异常，消息ID：{}，消息体:{}", message.getMessageProperties().getCorrelationIdString(), JSON.toJSONString(message), e);
                        try {
                            // 确认消息已经消费成功
                            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                        } catch (Exception e1) {
                            logger.error("保存异常MQ消息到数据库异常，放到死性队列，消息ID：{}", message.getMessageProperties().getCorrelationIdString());
                            // 确认消息将消息放到死信队列
                            channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
                        }
                    }
                }
    
            });
            return container;
        }
    
    
    }


    ```
3. 消息发送方
    ```aidl
       import cn.hutool.core.date.DateTime;
       import cn.hutool.core.lang.Console;
       import org.springframework.amqp.rabbit.core.RabbitTemplate;
       import org.springframework.beans.factory.annotation.Autowired;
       import org.springframework.web.bind.annotation.GetMapping;
       import org.springframework.web.bind.annotation.RestController;
       
       @RestController
       public class ProducerController {
       
           @Autowired
           private RabbitTemplate rabbitTemplate;
       
           @GetMapping("/sendMessage")
           public Object sendMessage(String  value) {
              Console.log("send message {}", value);
              rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, RabbitMQConfig.ROUTING_KEY, value);
               return "ok";
           }
       
       }

    ```  
    带回调的
       
    ```
        import java.util.UUID;
        import org.springframework.amqp.rabbit.core.RabbitTemplate;
        import org.springframework.amqp.rabbit.support.CorrelationData;
        import org.springframework.web.bind.annotation.RequestMapping;
        import org.springframework.web.bind.annotation.RestController;
        
        /**
         * 测试RabbitMQ发送消息的Controller
         *
         */
        @RestController
        public class SendController implements RabbitTemplate.ConfirmCallback{
        	private RabbitTemplate rabbitTemplate;
        	/**
        	 * 配置发送消息的rabbitTemplate，因为是构造方法，所以不用注解Spring也会自动注入（应该是新版本的特性）
        	 * @param rabbitTemplate
        	 */
        	public SendController(RabbitTemplate rabbitTemplate){
        		this.rabbitTemplate = rabbitTemplate;
        		//设置消费回调
        		this.rabbitTemplate.setConfirmCallback(this);
        	}
        	/**
        	 * 向消息队列1中发送消息
        	 * @param msg
        	 * @return
        	 */
        	@RequestMapping("send1")
        	public String send1(String msg){
        		String uuid = UUID.randomUUID().toString();
        		CorrelationData correlationId = new CorrelationData(uuid);
        		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTINGKEY1, msg,
        				correlationId);
        		return null;
        	}
        	/**
        	 * 向消息队列2中发送消息
        	 * @param msg
        	 * @return
        	 */
        	@RequestMapping("send2")
        	public String send2(String msg){
        		String uuid = UUID.randomUUID().toString();
        		CorrelationData correlationId = new CorrelationData(uuid);
        		rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, RabbitMQConfig.ROUTINGKEY2, msg,
        				correlationId);
        		return null;
        	}
        	/**
        	 * 消息的回调，主要是实现RabbitTemplate.ConfirmCallback接口
        	 * 注意，消息回调只能代表消息发送成功，不能代表消息被成功处理
        	 */
        	public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        		System.out.println(" 回调id:" + correlationData);
        		if (ack) {
        			System.out.println("消息成功消费");
        		} else {
        			System.out.println("消息消费失败:" + cause+"\n重新发送");
        			
        		}
        	}
        }
    ```
4.  详细配置信息 
     ```yaml
      spring:
       rabbitmq:
         host: 127.0.0.1 #ip
         port: 5672      #端口
         username: guest #账号
         password: guest #密码
         virtualHost:    #链接的虚拟主机
         addresses: 127.0.0.1:5672     #多个以逗号分隔，与host功能一样。
         requestedHeartbeat: 60 #指定心跳超时，单位秒，0为不指定；默认60s
         publisherConfirms: true  #发布确认机制是否启用
         publisherReturns: #发布返回是否启用
         connectionTimeout: #链接超时。单位ms。0表示无穷大不超时
         ### ssl相关
         ssl:
           enabled: #是否支持ssl
           keyStore: #指定持有SSL certificate的key store的路径
           keyStoreType: #key store类型 默认PKCS12
           keyStorePassword: #指定访问key store的密码
           trustStore: #指定持有SSL certificates的Trust store
           trustStoreType: #默认JKS
           trustStorePassword: #访问密码
           algorithm: #ssl使用的算法，例如，TLSv1.1
           verifyHostname: #是否开启hostname验证
         ### cache相关
         cache:
          channel:
            size: #缓存中保持的channel数量
            checkoutTimeout: #当缓存数量被设置时，从缓存中获取一个channel的超时时间，单位毫秒；如果为0，则总是创建一个新channel
          connection:
            mode: #连接工厂缓存模式：CHANNEL 和 CONNECTION
            size: #缓存的连接数，只有是CONNECTION模式时生效
         ### listener
         listener:
            type: #两种类型，SIMPLE，DIRECT
         ## simple类型
            simple:
              concurrency: #最小消费者数量
              maxConcurrency: #最大的消费者数量
              transactionSize: #指定一个事务处理的消息数量，最好是小于等于prefetch的数量
              missingQueuesFatal: #是否停止容器当容器中的队列不可用
              ## 与direct相同配置部分
              autoStartup: #是否自动启动容器
              acknowledgeMode: #表示消息确认方式，其有三种配置方式，分别是none、manual和auto；默认auto
              prefetch: #指定一个请求能处理多少个消息，如果有事务的话，必须大于等于transaction数量
              defaultRequeueRejected: #决定被拒绝的消息是否重新入队；默认是true（与参数acknowledge-mode有关系）
              idleEventInterval: #container events发布频率，单位ms
              ##重试机制
              retry:
                stateless: #有无状态
                enabled:  #是否开启
                maxAttempts: #最大重试次数,默认3
                initialInterval: #重试间隔
                multiplier: #对于上一次重试的乘数
                maxInterval: #最大重试时间间隔
         direct:
           consumersPerQueue: #每个队列消费者数量
           missingQueuesFatal:
       #...其余配置看上方公共配置
       ## template相关
       template:
         mandatory: #是否启用强制信息；默认false
         receiveTimeout: #`receive()`接收方法超时时间
         replyTimeout: #`sendAndReceive()`超时时间
         exchange: #默认的交换机
         routingKey: #默认的路由
         defaultReceiveQueue: #默认的接收队列
         ## retry重试相关
         retry:
            enabled: #是否开启
            maxAttempts: #最大重试次数
            initialInterval: #重试间隔
            multiplier: #失败间隔乘数
            maxInterval: #最大间隔    
     ```
      