每个微服务至少包含两个模块
[***-service]       服务业务的实现，或者说对[***-service-api]服务接口的实现。对应kitchen1.0中的provider+business模块功能。发布为可部署执行的jar包。
[***-service-api]   对外提供的服务接口及pojo对象。通常会被【api-portal】及对应的【***-service】或【其它微服务】引用、调用。