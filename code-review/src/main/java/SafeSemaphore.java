import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SafeSemaphore extends Semaphore {

    private static final Object object = new Object();
    private final ConcurrentHashMap<Thread, Object> threadSet;

    public SafeSemaphore(int permits) {
        super(permits);
        threadSet = new ConcurrentHashMap<>(permits);
    }

    public SafeSemaphore(int permits, boolean fair) {
        super(permits, fair);
        threadSet = new ConcurrentHashMap<>(permits);
    }

    @Override
    public void acquire() throws InterruptedException {
        super.acquire();
        threadSet.put(Thread.currentThread(), object);
    }

    @Override
    public void release() {
        final Thread thread = Thread.currentThread();
        if (threadSet.containsKey(thread)) {
            super.release();
            threadSet.remove(thread);
        }
    }

    @Override
    public boolean tryAcquire(long timeout, TimeUnit unit) throws InterruptedException {
        if (super.tryAcquire(timeout, unit)) {
            threadSet.put(Thread.currentThread(), object);
            return true;
        }
        return false;
    }
}
