1.  介绍  
    Seata是Alibaba开源的一款分布式事务解决方案，致力于提供高性能和简单易用的分布式事务服务，Seata 将为用户提供了 AT、TCC、SAGA 和 XA 事务模式，为用户打造一站式的分布式解决方案。
    
2.  Seata原理和设计  
    1.  协议分布式事务处理过程的三个组件  
        * Transaction Coordinator (TC)： 事务协调器，维护全局事务的运行状态，负责协调并驱动全局事务的提交或回滚；  
        * Transaction Manager (TM)： 控制全局事务的边界，负责开启一个全局事务，并最终发起全局提交或全局回滚的决议；  
        * Resource Manager (RM)： 控制分支事务，负责分支注册、状态汇报，并接收事务协调器的指令，驱动分支（本地）事务的提交和回滚。
    2.  分布式事务过程  
        *  TM 向 TC 申请开启一个全局事务，全局事务创建成功并生成一个全局唯一的 XID；
        *  XID 在微服务调用链路的上下文中传播；
        *  RM 向 TC 注册分支事务，将其纳入 XID 对应全局事务的管辖；
        *  TM 向 TC 发起针对 XID 的全局提交或回滚决议；
        *  TC 调度 XID 下管辖的全部分支事务完成提交或回滚请求。
    
3. 使用  
    * 配置文件修改
        ```yml
          spring:
            cloud:
                alibaba:
                    seata:tx-service-group: fsp_tx_group #自定义事务组名称需要与seata-server中的对应
        ```
      
    *  在启动类中取消数据源的自动创建：  
        ``` 
          @SpringBootApplication(exclude = DataSourceAutoConfiguration.class) 
        ```
    *  使用 `GlobalTransactional`替换`Transactional`使用  
    
