import java.util.Random;

public class CommonUtil {

    public static int doC() {
        System.out.println("commonutil doStatic");
        Random random = new Random();
        return random.nextInt(100) / 2;
    }
}
