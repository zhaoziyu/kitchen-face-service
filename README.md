# kitchen-face
应用kitchen进行微服务开发的示例，也是产品研发时搭建架构的模板

# 自定义启动Banner
http://patorjk.com/software/taag

# 属性配置文件（.yml）的敏感信息加密
应用Jasypt（http://www.jasypt.org）完成属性文件的加密
1、引入Maven依赖 - pom.xml
```
<!--配置文件属性加密支持-->
<dependency>
    <groupId>com.github.ulisesbocchio</groupId>
    <artifactId>jasypt-spring-boot-starter</artifactId>
    <version>1.18</version>
</dependency>
```
2、配置根密钥 - application.yml
```
jasypt:
  encryptor:
    # 配置文件属性加密的根密码
    password: ESXlHsVk2YM7mGcHy2ccGg
```
3、编写测试类，加密所需内容
```
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = AppBootstrap.class)
public class JasyptEncryptTest {
    private static final Logger logger = LoggerFactory.getLogger(JasyptEncryptTest.class);
    @Autowired
    private StringEncryptor stringEncryptor;

    @Value("${ftp.username}")
    private String ftpUsername;

    @Test
    public void checkConfigIsEncrypt() {
        String info = "\n\n";
        info += "检查配置文件敏感信息是否加密\n";
        info += "project.ftp.username                 加密结果：ENC(" + stringEncryptor.encrypt(ftpUsername) + ")\n";
        
        logger.warn(info);
    }
}
```
4、将加密结果填入配置文件的属性值
```
ftp:
  username: ENC(SKEndksle322D32==)
```