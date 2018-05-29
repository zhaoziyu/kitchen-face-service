搭建企业应用开发框架

——————————————————执行步骤————————————————
（√）加入Maven管理
（√）添加Tomcat
（√）跑通index.jsp
（√）搭建MVC
（√）做一个Controller
（√）使用jackson框架，Json对象作为返回值
（√）乱码&返回参数的属性顺序
（√）Maven的自动依赖
（√）配置Maven的打包，打包成war
（√）跑通Controller————上传Git

（√）创建数据库
（√）搭建M层（M层考虑用Spring原生的还是MyBatis）
（√）串联MC层
（√）尝试把mapper.xml放倒java同一个目录下如何配置
（√）框架中的property文件配置和使用（支持本地数据库和远程数据库配置文件的切换）
（√）Maven打包打不进去配置文件
（√）增加可以在程序内使用的属性配置文件 配置文件加Spring注入到静态类中？
（√）所有xml配置中涉及到包名的地方放到属性配置文件中，将属性配置加载器放到Spring的加载配置的前面 可以容纳多个属性文件
（√）错误页面（404、500等）机制
（√）登录机制（页面、MainController）
（√）Security过滤器
（√）兼容接口开发（配置的时候）
（√）Interceptor配置和文件夹
（√）研究、配置sitemesh
（√）登录页面的样式
（√）登录页面逻辑
（√）main接口和login页面系统名称的动态维护（可以通过注入配置的方式）
（√）配置通用的弹出提示框
（√）Quartz配置和代码结构
（√）搭建视图层（Freemarker）
（√）Spring事务管理使用和配置（保证原子性），测试通过后打钩
（√）登陆改为异步的 不同的登陆方式跳转到不同的方法中
（√）找个tips提示框架（layer的可行性）
（√）搭建后台管理主页框架
（√）思考Freemarker的必要性，或者找到使用Freemarker如何能提高开发效率或灵活性，感觉现在使用Freemarker会增加很多框架逻辑（跳转、页面调试等），要不要就直接使用html，富客户端，与移动端的开发方式一致，模板化完全可以使用前端的框架或sitemesh来完成。将前端后端的耦合分开以后，可以方便前端和后端分开开发，人员要求更单一，专注点更集中，如果需要Freemarker的话，可以找个前端的框架来完成Freemarker的功能（模板功能）
（√）解决所有后缀都被mvc拦截并且被同一个控制器执行 尝试在控制器中对后缀进行区分 或者在进入控制器之前的过滤器中
（√）前端框架公共js文件中加服务器地址/方便以后拆分前后端
（×） 如何支持REST（Spring MVC的@RequestMapping注解即可支持）
——————————————————其它——————————————————
（√）如何添加非Maven管理的jar，比如各类商家提供的开放接口
（√）处理多数据源
（√）解决favicon.ico的不显示问题
（√）GET请求方式中文乱码问题（即在URL中请求，看shop工程好像是在freemarker中解决的）
（√）细化数据库连接池的配置（使用c3p0或其它Spring支持的数据库连接池）
（×）研究接口配置化开发的实用性（参考核心接口系统中使用的方式）
（√）登录页面应该支持普通方式登录、手机验证码登录等方式
（√）配置MyBatis的Generator（自定义输出文件的内容）
（√）增加业务实现说明文档，描述业务实现时的通用建议 例如：删除时通过“删除标记”或“状态”来删除
（√）升级为Java8
（√）升级成分布式服务框架(RPC)，Netty？Dubbo？可以采用单一服务集成，也可选择地支持分布式服务开发和管理（ http://dubbo.io/ 可参考：https://www.oschina.net/p/castle-platform），如何支持分布式部署 部署时：看Docker部署方式是否适用
（√）研究多线程机制（同一个方法内的多线程如何支持）

——————————————————2016-12-29前——————————————————
（√）找一个模板，在web-business中，做一个产品介绍的官网
（√）去除api-nomarl，并将其负责范围迁移到api-portal中（具备本地服务“core-business”和分布式服务“api-provider”两种消费服务的方式），api-portal采用Spring MVC框架，负责控制层，依赖core-business，core-business依赖core-interface
（√）梳理kitchen框架各个模块的依赖关系（同时把相关文档画出来）
（√）@迁移best_enterprise_webapp工程的代码至web-manage和api-portal中
best_enterprise_webapp迁移备忘录
（√）api-portal处理404和500的错误的处理，通过JsonVo返回结果
（√）@api-provider引用core-business的Service实现业务逻辑
（√）@api-portal，Spring关闭时，有资源未清理干净
（×）如何能通过RpcClientProxy同时支持Rpc调用和Spring注入Service的方式调用（无法支持）
（√）优化Maven构建，目前运行api-protal必须运行kitchen的clean和install
（√）@mybatis generate生成的PO使用模板生成cloneSelf()，并优化mapper中生成的方法，增加update等
（×）尝试在框架级支持乐观锁（update_time）的处理——暂时只能手写SQL，在where条件中加入更新时间进行比较
（√）支持异步发送请求（可以通过回调接口接收返回结果）
（×）服务提供者和消费者之间调用的安全性（防止黑客访问服务提供者）——服务提供者不开放外网端口
（√）把之前工程的Util中有用的独立功能模块梳理到core-market中（概率算法、添加服务器域名）
（√）Kitchen框架中各个工程模块的关系和作用图
（√）Kitchen框架部署示例图（多provider、多api-portal、通过Negix处理请求“api-portal”的负载均衡）

——————————————————2016-12-29至？？？？——————————————————
（√）文件上传，在core-market中增加文件上传工具，web-manage和api-protal增加文件上传的相关配置文件，并分别做一个通用的文件上传接口
（×）业务逻辑层返回结果支持错误码机制（通过properties文件注入错误码及错误信息）——会增加编码成本，降低效率，仅仅是为了使错误说明可以不用编译进行修改，得不偿失，后续再观察是否需要加。
（√）使用Redis（http://www.redis.cn/）进行数据缓存管理
（×）属性配置文件中的内容如何加密（http://blog.csdn.net/yaerfeng/article/details/26561791）——脱了裤子放屁，如果黑客破解了服务器密码，还会在乎属性文件嘛
（×）并发访问的控制，可参考shop工程的静态Map方式（优化使用方式），只在最顶级控制，api-portal和nginx都有可能为顶级，分发（负载）后控制则没有意义——使用synchronized()控制相关资源（用户id，订单id）即可
（√）前端（js或html）模板引擎，或者就用sitemesh完事儿了！
https://www.zhihu.com/question/32524504
https://www.nodetpl.com/cn/
http://juicer.name/docs/docs_zh_cn.html
https://www.oschina.net/search?scope=project&q=Javascript%E6%A8%A1%E6%9D%BF%E5%BC%95%E6%93%8E
https://github.com/shepherdwind/velocity.js/blob/master/README-cn.md
（√）内嵌Jetty，方便项目快速演示、汇报
（×）管理后台框架改版：通过Element + Layui构建
（×）iframe内session过期如何可以不在iframe中登录，在主框架中登录，且弹出登录框，而不是刷新整个页面（可以在登录页面中判断是否是在iframe中打开的，如果是，则调用外层框架的登录框，其它办法详见LoginInterceptor
） 如何无刷新的登录？
（√）用户选择保持登录状态
（√）Kitchen框架的Redis安全
（√）点击iframe也可以关闭框架中打开的菜单（关闭选项卡等）
可行方案：在iframe中的页面里添加点击事件，点击后触发父框架的点击
可行方案：鼠标离开时计时，到时关闭，鼠标离开再回来，则计时取消；
（√）iframe区域滚动条，从内页加（但iframe内页加上main.css后布局就乱了）
（√）core-business中增加MyBatis分页工具（参考mybatis-config.xml中的配置和使用）
（×）日志：服务的调用日志，接口的调用日志（可配置是否写入数据库，还是写入日志文件）——业务系统需要的时候再开发即可
（√）web-manage支持HTML页面（处理乱码）
（√）检查Kitchen的负载均衡，参考文章实现多种方式的负载均衡https://www.oschina.net/news/81750/variety-pf-load-balancing-algorithm-and-its-java-code?from=20170212
（√）支持“源地址哈希（Hash）法”
（√）优化@RpcService注解：无需指定接口，直接访问该类继承的接口是否可行，若不可行，把注解中的value字段改成更易懂的单词；若有相同的RpcService注解（接口、版本相同），则框架给出提示，防止重复的注入导致的版本不一致现象
（√）三种调用方式在RpcClientHandler中处理时，统一控制一下，不要有的在RpcClientFuture，有的在RpcClientHandler