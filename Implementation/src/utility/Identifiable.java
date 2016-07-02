package utility;

import java.util.Random;

abstract public class Identifiable {
    final public int ID_LENGTH = 6;

    public String generateNDigitID(int n) {
        Random random = new Random();
        return Integer.toString((int) (Math.pow(10, n - 1) + random.nextFloat()
                * 9 * Math.pow(10, n - 1)));
    }
}
