import factory.ConfigReader.ConfigReader;
import org.junit.Assert;
import org.junit.Test;

public class ConfigTest {

    @Test
    public void configTest() throws IllegalAccessException {
        ConfigReader reader = new ConfigReader();
        int val = reader.get(ConfigReader.Settings.logSale);
        Assert.assertEquals(val, 1);
    }

}
