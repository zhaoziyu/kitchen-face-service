import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Mac版IDEA单元测试生成方式：
 * 1、选中需要创建单元测试的类名
 * 2、option+回车，弹出菜单
 * 3、在菜单中选择"Create Test"
 * 4、按引导窗口创建即可
 *
 * @author 赵梓彧 - kitchen_dev@163.com
 * @date 2018/1/30
 */
@Ignore
public class DemoNormalTest {
    @Before
    public void setUp() throws Exception {
        System.out.println("setUp");
    }

    @After
    public void tearDown() throws Exception {
        System.out.println("tearDown");
    }

    @Test
    public void testBiz() throws Exception {
        System.out.println("testBiz");
    }

}