ssh总结
  
 mysql  查询表约束  
     show full columns from 表名；
    
    
 redis 
 compile group: 'redis.clients', name: 'jedis', version: '2.9.0'

ssh 基本的jar
    compile 'javax:javaee-api:7.0'
    compile'jstl:jstl:1.2'
    compile group: 'org.apache.logging.log4j', name: 'log4j-core', version: '2.9.1'
    compile'mysql:mysql-connector-java:5.1.42'
    compile group: 'com.alibaba', name: 'druid', version: '1.1.4'
//  插件所用的库
	  compile 'com.alibaba.p3c.idea:p3c-common:1.0.0'
//  beanutil  所用的库
    compile group: 'commons-beanutils', name: 'commons-beanutils', version: '1.9.3'
//  有邮件时 mailutil 所用的库
    compile group: 'javax.mail', name: 'javax.mail-api', version: '1.5.0-b01'
//spring  库
    compile group: 'org.springframework', name: 'spring-context', version: '5.0.0.RELEASE'
    compile group: 'org.springframework', name: 'spring-orm', version: '5.0.0.RC4'
    compile group: 'org.springframework', name: 'spring-context-support', version: '5.0.0.RELEASE'
    compile group: 'org.hibernate', name: 'hibernate-core', version: '5.2.11.Final'
//struts 
    compile 'org.apache.struts:struts2-osgi-bundles:2.5.13'
    compile group: 'org.apache.struts', name: 'struts2-convention-plugin', version: '2.5.13'
    compile group: 'org.apache.struts', name: 'struts2-spring-plugin', version: '2.5.13'
// hibernate 
    compile group: 'org.hibernate', name: 'hibernate-ehcache', version: '5.2.11.Final'
    compile group: 'org.hibernate', name: 'hibernate-core', version: '5.2.11.Final'
//  json 库
    compile group: 'com.fasterxml.jackson.core', name: 'jackson-databind', version: '2.9.2'


**_ssh各种问题_**
hibernate 可以针对某个类  或包  启用二级缓存

jquery  遍历对象  $.each（xx.list,function(){}）;

在action中如果涉及多次修改 必须在service里添加功能  只有在service中才有事务
如果修改失败能够回滚


多对多关系两边都用manytomany   如果用mappedby 代码的灵活性就会减少  当需要级联修改时  会出现修改失败

在实体类中 加   @Transient 注解   可以讲此条属性不同步加入数据库


web xml文件中的配置高于注解  过滤器注解后 如果在xml文件中被优先的struts2拦截器拦截后 注解的拦截器将不起作用
_**懒加载的属性  用才会查询  如果只是查询  则不操作_**

_在entity中配置的多对多关系 存入session 后 需要调用时 因为互相都存在对方的list<entity>
因此会导致栈溢出 简单的解决办法就是将entity中所需要的属性在new entity 销毁前直接
将此属性存入session   或者jackson可以注解忽略掉某些属性  具体为
@JsonIgnoreProperties({""})_
  
  
对某个实体类启用二级缓存  @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)

system.arraycopy()  复制数组

只有在service层才有事务

**_hibernate_**

注解忽略映射   不被数据库同步  @Transient
当表中存在多对多关系 json忽略后使用  将需要的属性创建然后在json中发送使用

_**servlet**_
所有web项目  直接装监听器  避免问题发生  中文乱码问题  在jsp页面中更改有可能不起作用 ssh配置
也有可能不起作用   直接全局监听  安全起见 在ssh中也一起配上监听器
servlet监听器的的全局注解@WebFilter(urlPatterns = "/*")


_**java8 新特性**_
非常棒的一种表达式  
仅支持java8及以上版本
// Java 8之前：
new Thread(new Runnable() {
    @Override
    public void run() {
    System.out.println("Before Java8, too much code for too little to do");
    }
}).start();

//Java 8方式：
new Thread( () -> System.out.println("In Java8, Lambda expression rocks !!") ).start();

// Java 8之前：
List features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
for (String feature : features) {
    System.out.println(feature);
}
// Java 8之后：
features.forEach(n->system.out.println(n):
//第二种
features.forEach(system.out::println);

**这只是最常用的方式其他的可以搜**






 Serializable  八种基本数据类型都实现了 Serializable接口 和序列化 
 无关  当方法的参数需要时 可以用这个来接


获取客户端发送回来的数组         request.getParameterValues("");

