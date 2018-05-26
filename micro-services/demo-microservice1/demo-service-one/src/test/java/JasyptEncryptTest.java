import com.restaurant.dinner.demo.service.one.ProviderBootstrap;
import org.jasypt.encryption.StringEncryptor;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/3/12
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = ProviderBootstrap.class)
public class JasyptEncryptTest {
    private static final Logger logger = LoggerFactory.getLogger(JasyptEncryptTest.class);
    @Autowired
    private StringEncryptor stringEncryptor;

    @Test
    public void checkConfigIsEncrypt() {
        String info = "\n\n";
        info += "检查配置文件敏感信息是否加密\n";
        info += "<属性名>  加密结果：ENC(" + stringEncryptor.encrypt("通过@Value注入的需要加密的属性值") + ")\n";

        logger.warn(info);
    }
}
