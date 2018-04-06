# 关于springboot的配置文件
采用yml格式的配置文件
配置文件分2类，主配置文件（application.yml）和环境配置文件（application-***.yml）
可在主配置文件中指定环境配置文件（通过spring.profiles.active）
环境配置文件有三个，后缀为-dev -test -prod，分别对应开发环境，测试环境，生产环境，环境配置文件将覆盖主配置文件的对应参数的值
配置文件中的属性覆盖遵循优先级：命令行参数 > 环境变量 > 环境配置文件(application-***.yml) > application.yml
命令行参数传入并覆盖配置的方式：java -jar ***.jar --xxx.xxx.xxx=yyy

# 关于消息转换器
使用JSON格式的消息，集成FastJson工具包，通过CustomMessageConverters类完成JSON消息转换的配置

# 关于跨域访问
采用SpringMVC4.2以后提供的CORS机制
通过CustomCorsMapping类，重写WebMvcConfigurerAdapter的addCorsMappings
若想做到更细粒度的控制，也可以在controller类中使用@CrossOrigin注解

# 关于springboot的banner生成
通过 http://patorjk.com/software/taag 网站生成自己想要的字符
在resources文件夹下新建banner.txt，并填写想要展示的字符
也可以通过设置SpringBoot关闭banner显示：
SpringApplication app = new SpringApplication(ApiGatewayBootstrap.class);
app.setBannerMode(Banner.Mode.OFF);
app.run(args);

# RpcClient配置步骤
1、引入maven依赖（kitchen-rpc-client）
2、springboot入口处增加@ComponentScan(basePackages = {"com.restaurant.dinner", RpcConstant.SCAN_PATH})扫描注解（除了Rpc包，还需包含当前工程的扫描包）

# RpcServer配置步骤
1、引入maven依赖（kitchen-rpc-server）
2、springboot入口处增加@ComponentScan(basePackages = {"com.restaurant.dinner", RpcConstant.SCAN_PATH})扫描注解（除了Rpc包，还需包含当前工程的扫描包）
3、在springboot入口处，SpringApplication.run()后加上：RpcServer.start(); 显示启动RPC服务端程序，此时主线程将进入等待状态

# 关于HTTPS
https://www.cnblogs.com/garyyan/p/7600322.html