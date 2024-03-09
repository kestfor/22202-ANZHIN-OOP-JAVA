public class Riddler {
    private final String hiddenNumber;

    Riddler(int number) {
        this.hiddenNumber = Integer.toString(number);
    }

    public int getAmountOfCows(int guess) {
        String numberToCheck = Integer.toString(guess);
        int res = 0;
        for (int i = 0; i < numberToCheck.length(); i++) {
            if (hiddenNumber.indexOf(numberToCheck.charAt(i)) != -1) {
                res++;
            }
        }
        return res;
    }

    public int getAmountOufBulls(int guess) {
        String numberToCheck = Integer.toString(guess);
        int res = 0;
        for (int i = 0; i < numberToCheck.length(); i++) {
            if (numberToCheck.charAt(i) == hiddenNumber.charAt(i)) {
                res++;
            }
        }
        return res;
    }

}
