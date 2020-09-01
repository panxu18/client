import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Random;

public class ServiceB {

    public int doB() {
        System.out.println("serviceB doB()");
        Random random = new Random();
        return random.nextInt(100) / 2;
    }
}
