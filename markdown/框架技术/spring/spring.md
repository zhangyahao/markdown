## spring中用到的设计模式
  1. 工厂模式
  2. 代理模式
  3. 策略模式
  4. 单例模式

## spring中的ioc和aop
IoC的核心是依赖反转，将创建对象和对象之间的依赖管理交给IoC容器来做，完成对象之间的解耦。

AOP主要是利用代理模式，把许多接口都要用的又和接口本身主要的业务逻辑无关的部分抽出来，写成一个切面，单独维护

##aop的应用场景
权限，日志，处理异常，事务等等，个人理解就是把许多接口都要用的又和接口本身主要的业务逻辑无关的部分抽出来，
写成一个切面，单独维护

##Spring有哪几种配置方式
   1. 基于xml的配置
   2. 给予注解的配置
   3. 基于java的配置
   
## Spring Bean的生命周期
Spring Bean的生命周期简单易懂。在一个bean实例被初始化时，需要执行一系列的初始化操作以达到可用的状态。同样的，
当一个bean不在被调用时需要进行相关的析构操作，并从bean容器中移除。

Spring bean factory 负责管理在spring容器中被创建的bean的生命周期。Bean的生命周期由两组回调（call back）方法组成。
1. 初始化之后调用的回调方法。

2. 销毁之前调用的回调方法。

## 声明事务管理
   声明式事务管理建立在AOP之上的。其本质是对方法前后进行拦截，然后在目标方法开始之前创建或者加入一个事务，
   在执行完目标方法之后根据执行情况提交或者回滚事务。声明式事务最大的优点就是不需要通过编程的方式管理事务，
   这样就不需要在业务逻辑代码中掺杂事务管理的代码，只需在配置文件中做相关的事务规则声明(或通过基于@Transactional注解
   的方式)，便可以将事务规则应用到业务逻辑中。

##核心容器(应用上下文)模块
  这是Spring的基本模块，它提供了Spring框架的基本功能。BeanFactory 是所有Spring应用的核心。Spring框
  架是建立在这个模块之上的，这也使得Spring成为一个容器。
  
##Spring应用程序看起来像什么
1. 一个定义功能的接口
2. 实现包括属性，setter和getter方法，功能等
3. Spring AOP
4. Spring的XML配置文件
5. 使用该功能的客户端编程

