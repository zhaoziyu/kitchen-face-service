- Dubbo服务的同步和异步注意事项：
在同一个上下文中，多处引用同一个Dubbo Service（@Reference），但想该服务支持同步和异步时，若async属性设置的不同，则会以async=true为准（即异步），该服务的同步调用请求返回结果将为NULL。
即属性配置会交叉重合，并以最大化配置为准。
对于Dubbo服务的异步和同步场景，在同一个Dubbo上下文中，要么都是同步的，要么都是异步的。
建议引用的服务均为同步（不用设置async），当需要异步调用时，使用自己的线程池，处理异步调用方式。

- @Reference和@Service注解中的retries：
官方说明：远程服务调用重试次数，不包括第一次调用，不需要重试请设为0
实际测试发现，若设置为0，当被请求服务方法超时，则会执行默认的重试逻辑，即重试2次（总共调用3次）。
https://www.cnblogs.com/saaav/p/6374237.html
解决：将retries设置为-1，则会取消调用重试。
当同一个Dubbo服务的@Reference和@Service中均设置了retries，则以@Service设置为准（猜测@Reference中的retries应当低于@Service中的retries）。
注意：带有写操作的服务，应将retries设置为-1，防止重复写操作。