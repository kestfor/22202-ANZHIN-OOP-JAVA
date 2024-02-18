import org.junit.Assert;
import org.junit.Test;

public class ValidatorTest {
    @Test
    public void testValidateOk() {
        Assert.assertEquals(Validator.responseCode.ok, Validator.validate(1234, 1000, 9999));
    }

    @Test
    public void testValidateOutOfRange() {
        Assert.assertEquals(Validator.responseCode.outOfRange, Validator.validate(0, 1000, 9999));
        Assert.assertEquals(Validator.responseCode.outOfRange, Validator.validate(10000, 1000, 9999));
    }


    @Test
    public void testValidateRepeatedDigits() {
        Assert.assertEquals(Validator.responseCode.repeatedDigits, Validator.validate(1123, 1000, 9999));
        Assert.assertEquals(Validator.responseCode.repeatedDigits, Validator.validate(1221, 1000, 9999));
        Assert.assertEquals(Validator.responseCode.repeatedDigits, Validator.validate(1111, 1000, 9999));
    }

    @Test
    public void cowsTest() {
        Riddler riddler = new Riddler(1234);
        Assert.assertEquals(riddler.getAmountOfCows(1098), 1);
        Assert.assertEquals(riddler.getAmountOfCows(5198), 1);
        Assert.assertEquals(riddler.getAmountOfCows(9821), 2);
        Assert.assertEquals(riddler.getAmountOfCows(4321), 4);
    }

    @Test
    public void bullsTest() {
        Riddler riddler = new Riddler(1234);
        Assert.assertEquals(riddler.getAmountOufBulls(1098), 1);
        Assert.assertEquals(riddler.getAmountOufBulls(4321), 0);
        Assert.assertEquals(riddler.getAmountOufBulls(1234), 4);
    }

}
