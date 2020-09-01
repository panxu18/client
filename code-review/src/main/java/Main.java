import java.util.Objects;
import java.util.concurrent.Semaphore;

public class Main {
    static Semaphore semaphore = new Semaphore(1);

    public static void doBusinessMethod(String str) {
        try {
            semaphore.acquire(1);
        } catch (InterruptedException e) {
            System.out.println("get semaphore interrupted.");
            return;
        }
        try {
            businessMethod(str);
        } catch (Exception e) {
            System.out.println("do business exception.");
        } finally {
            semaphore.release(1);
        }
    }

    public static void businessMethod(String str) throws InterruptedException {
        Objects.requireNonNull(str);
        System.out.printf("do businessMethod %s.", str);
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(()->doBusinessMethod(null)).start();
        Thread.sleep(1000);
        new Thread(()->doBusinessMethod("Thread2")).start();
    }

}
