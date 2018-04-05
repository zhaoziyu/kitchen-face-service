# 参考
- 【spring boot+mybatis】注解使用方式（无xml配置）设置自动驼峰明明转换（mapUnderscoreToCamelCase），IDEA中xxDao报错could not autowire的解决方法
https://www.cnblogs.com/zhangdong92/p/6986653.html
- Mybatis Generator最完整配置详解
https://www.cnblogs.com/ygjlch/p/6471924.html
- 增强MyBatis注解
https://www.jianshu.com/p/03642b807688
- springboot(六)：如何优雅的使用mybatis
http://www.ityouknow.com/springboot/2016/11/06/springboot(六)-如何优雅的使用mybatis.html
- 我为什么放弃使用MyBatis3的Mapper注解
https://www.cnblogs.com/daniaoge/p/6161736.html
- MyBatis3-基于注解的示例
https://www.cnblogs.com/EasonJim/p/7070820.html
- mybatis @Select注解中如何拼写动态sql
http://blog.csdn.net/qq_32786873/article/details/78297551
- MyBatis注解应用之动态SQL语句
http://blog.csdn.net/owen_william/article/details/51815506
- SpringBoot集成Mybatis（mapper文件配置）
https://my.oschina.net/wubiaowpBlogShare/blog/1611537
- springboot中使用Mybatis注解配置详解
http://blog.csdn.net/winter_chen001/article/details/78623700
- Spring boot Mybatis 整合（注解版）
http://blog.csdn.net/Winter_chen001/article/details/78622141
- Spring boot Mybatis 整合（完整版）
http://blog.csdn.net/winter_chen001/article/details/77249029
- spring boot+mybatis整合
https://www.cnblogs.com/peterxiao/p/7779188.html
- 记录SpringBoot使用Druid和Mybatis配置
http://blog.csdn.net/damionew/article/details/78596329

# 集成MyBatis的相关依赖
```
        <!--数据库相关-->
        <dependency><!--连接池-->
            <groupId>com.alibaba</groupId>
            <artifactId>druid-spring-boot-starter</artifactId>
            <version>${druid.version}</version>
        </dependency>
        <dependency><!--MySQL JDBC-->
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>5.1.45</version>
        </dependency>
        <dependency><!--MyBatis-->
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>${mybatis-starter.version}</version>
        </dependency>
        <dependency><!--MyBatis分页插件-->
            <groupId>com.github.pagehelper</groupId>
            <artifactId>pagehelper-spring-boot-starter</artifactId>
            <version>${pagehelper-starter.version}</version>
        </dependency>
```

# 集成MyBatis有两种方式

## 1.使用全注解方式（推荐）
无需在配置文件和启动类中配置dao层相关路径
若使用mybatis默认配置，则在application.yml中无需任何MyBatis相关配置
无mapper.xml文件，不需要关心xml文件打包
只需要在mapper.java类中使用注解完成dao层的SQL语句开发

相关注解：
mapper.java类上使用@Mapper注解（添加了@Mapper注解之后这个接口在编译时会生成相应的实现类）
mapper.java类中的方法使用MyBatis提供的@Select、@SelectProvider、@Param等注解，完成SQL开发

注意：
1、若在SQL语句中需要使用MyBatis的表达式（<foreach>、<if>），需在语句的最外层加上<script>
2、Mapper接口中不可以定义同名的方法，因为会在编译生成Mapper实现类是生成相同的id，也就是说这个接口是不支持重载的。
3、关于MyBatis3，官方说明：很不幸的是，java注解在表现和灵活性上存在限制。虽然在调研、设计和测试上花费了很多时间，但是最强大的MyBatis映射功能却无法用注解实现。这没有什么可笑的。举例来说，C#的特性就没有这个限制，所以MyBatis.NET 能拥有一个功能丰富的多的XML替代方案。所以，Java基于注解的配置是依赖于其自身特性的。
    可以使用混合方式（MIXEDMAPPER）解决，少数复杂Mapper使用xml的方式
4、若使用IDEA开发工具，*mapper.java类中仅加入@Mapper注解时，使用@Autowire自动注入Mapper类时会提示报错，但实际运行不影响注入，MyBatis会将扫描到的带有@Mapper注解的类，注册到Spring上下文中。
    解决办法：
    - 方式一：在Mapper类中加@Mapper注解的同时，增加@Repository注解，暂未发现使用两个注解对Spring注入的影响。
    - 方式二：（推荐）修改IDEA的首选项（Editor->Inspections->Spring->Spring Core），将Spring的自动注入相关检查关掉或降低等级。
    - 方式三：在使用@Autowire注入时，增加@SuppressWarnings("SpringJavaAutowiringInspection")注解
    - 方式四：安装IDEA的MyBatis Plugin（收费）

## 2.使用XML配置SQL
- 可以使用Maven的mybatis-generator插件，直接生成相关类（*Mapper.java、*Mapper.xml、PO类）
`generatorConfig中<javaClientGenerator>的type属性值为：XMLMAPPER`
Mapper.java类需加@Repository注解或@Mapper注解，使用@Mapper注解，则无需在启动类中添加@MapperScan注解
- 需要在配置文件和启动类中指明dao层相关类的包名，包名更换时，需注意替换相关配置
application.yml配置：
mybatis:
  mapper-locations: classpath:mappers/**.xml（*mapper.xml所在包）
  configuration:
    cache-enabled: true
  type-aliases-package: PO类所在包
  
启动类增加注解：
@MapperScan("mapper.java类的路径")