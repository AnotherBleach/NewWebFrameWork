import cn.edu.bupt.bean.ConfigBean;
import cn.edu.bupt.util.XMLParser;
import org.junit.Assert;
import org.junit.Test;

public class XMLParserTest {


    @Test
    public void test()
    {
        ConfigBean bean = XMLParser.getConfigBean("another.xml");
        Assert.assertNotNull(bean);
        System.out.println(bean.getBasepackage());
        System.out.println(bean.getViewAssetPath());
        System.out.println(bean.getViewPagePath());

    }



}
