
public class Validator {
    public enum responseCode{

        ok,
        outOfRange,
        repeatedDigits,
    }
    public static responseCode validate(int value, int lowerLimit, int upperLimit) {
        if (value < lowerLimit || value > upperLimit) {
            return responseCode.outOfRange;
        }
        String val = Integer.toString(value);
        for (int i = 0; i < val.length(); i++) {
            if (val.indexOf(val.charAt(i)) != val.lastIndexOf(val.charAt(i))) {
                return responseCode.repeatedDigits;
            }
        }
        return responseCode.ok;
    }
}
