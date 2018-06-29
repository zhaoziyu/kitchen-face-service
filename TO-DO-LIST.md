#Kitchen

@支持消息队列机制（gateway、microservice、cms都可用） http://blog.csdn.net/forezp/article/details/71023692 | http://blog.csdn.net/forezp/article/details/71023652
@考虑CMS系统是统一调用api-portal还是统一调用RPC服务完成业务操作（避免在CMS中直连数据库，达到前后端分离）
@支持服务的熔断、隔离、限流和降级
@微服务监控系统（https://github.com/imdada/overwatch），另外可见“下载”文件夹中的《Docker容器的监控及日志采集》https://mp.weixin.qq.com/s?__biz=MzIwMzg1ODcwMw==&mid=2247487253&idx=1&sn=67e38efdac28db48ebaabbb959c36380&chksm=96c9b975a1be3063382d2fd535e3eb37082eaafc7a1e4ad7b327d7ef21da49002a8e1670b227&mpshare=1&scene=23&srcid=0118iIwlCUuB1US5HePJy9PA%23rd
@关注Elasticsearch、Kafaka
@考虑把kitchen-cache换为框架支持的缓存库，并应用于存储Token的业务场景，支持本地和远程Token的过期时间，同时支持一二级缓存（本地和远程缓存）；


✅做一个DevOps的总结图
✅统一的异常处理机制
✅配置logback，并在异常处理中增加日志记录
✅支持文件上传
✅集成原工程中的线程池（将配置参数暴露出来）——增加关闭监听，清除线程池
✅集成Swagger2：http://blog.csdn.net/forezp/article/details/71023536
✅支持签名校验（附加相关测试代码）
✅支持静态资源https://segmentfault.com/a/1190000004319673
✅集成spring-cache：http://blog.csdn.net/forezp/article/details/71023614
✅标准的测试用例(@RunWith( SpringJUnit4ClassRunner.class ))，说明如何使用IDEA快速创建
✅开启定时任务
✅完成Docker适配（Dockerfile、docker-compose）
✅日志适配Docker（profile active增加docker方式，日志增加docker输出，prod增加输出到控制台）
✅完成kitchen-face工程的docker-compose.yml，解决provider端口绑定的问题，相同服务有多个副本时，端口如何绑定？
❎画一个kitchen的典型应用架构图 包含可能的所有组件 MQ ZK ngix redis等
❎出一份微服务架构的指导
✅考虑将Kitchen总结的经验应用到Dubbo中，将Kitchen的研究方向切换为Netty
✅升级到SpringBoot2.0，Spring Framework5

#补充文档（先出一个文档结构、脑图之类的）

✅将此配置做成手册.md——https://segmentfault.com/a/1190000004315890
@关于.yml配置文件的多环境说明
@关于Swagger，各种注解说明，在何种环境使用，相关配置如何、访问地址
@关于跨域请求
@关于异常处理机制，如何处理自定义异常
@系统的生命周期（com.**.lifecycle）
@线程池的使用
@JSON序列化的说明，命名策略的适配条件
@关于国际化
@关于签名、Token、时间戳，机制、使用说明、配置说明。描述一个典型的签名场景（获取Token、非用户相关接口使用签名方式、用户登录后可用接口使用Token方式）与签名Demo相互呼应。描述APPsecret在终端中的安全保存（动态获取+HTTPS）。说明提供两种方式的签名，一个是RSA，另一个是MD5（IDM用的方式）
@关于文件上传下载、本地方式和FTP方式
@关于日志，配置说明、使用说明、生产环境建议
@关于缓存的使用（Caffeine、Guava、Ehcache、Simple、Kitchen-Cache）
@梳理Guava中提供的工具