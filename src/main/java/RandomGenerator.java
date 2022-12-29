import java.util.Random;

public class RandomGenerator {

    public static String randomGenerator(){
        Random rnd = new Random();
        return String.format("%06d", rnd.nextInt(999999));
    }
}
