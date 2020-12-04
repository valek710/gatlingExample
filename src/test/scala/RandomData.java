import java.math.BigInteger;
import java.util.Random;

public class RandomData {

    public String randomDigit(long min, long max) {
        return String.valueOf(min + (long) (Math.random() * (max - min) + 1));
    }

    public BigInteger iban() {
        BigInteger maxLimit = new BigInteger("999999999999999999999999999");

        Random randNum = new Random();
        int len = maxLimit.bitLength();
        BigInteger res = new BigInteger(len, randNum);
        if (res.toString().length() > 27) {
            String resS = res.toString();
            int diff = resS.length() - 27;
            for (int i = 0; i < diff; i++) {
                resS = resS.substring(0, resS.length() - 1);
            }
            res = new BigInteger(resS);
        }
        else if (res.toString().length() < 27) {
            StringBuilder resS = new StringBuilder(res.toString());
            int diff = 27 - res.toString().length();
            for (int i = 0; i < diff; i++) {
                String rand = String.valueOf((int) (Math.random() * 9 + 1));
                resS.append(rand);
            }
            res = new BigInteger(resS.toString());
        }

        return res;
    }
}
