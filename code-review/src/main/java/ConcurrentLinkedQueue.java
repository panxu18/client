import lombok.AllArgsConstructor;

import java.util.Objects;

public class ConcurrentLinkedQueue<T> {
    private volatile Node<T> head;
    private volatile Node<T> tail;

    public ConcurrentLinkedQueue() {
        head = tail = new Node<T>(null);
    }

    /**
     * 先连接到链上，然后再移动tail指针，
     * @param e
     */
    public boolean add(T e) {
        Objects.nonNull(e);
        Node<T> newNode = new Node<>(e);
        Node<T> oldTail = tail;
        for (;;) {
            Node<T> p = tail.next;
            if (p == null) {
                if (oldTail.casNext(null, newNode)) {
                    if (newNode != oldTail) {
                        casSetTail(newNode);
                    }
                    return true;
                }
            } else if (oldTail == p) {
                oldTail = tail;
            } else {
                oldTail = p;
            }
        }
    }

    boolean casSetTail(Node<T> val) {
        return UNSAFE.compareAndSwapObject(tail, tailOffset, tail, val);
    }




    private static class Node<T> {
        volatile T value;
        volatile Node<T> next;

        Node(T value) {
            this.value = value;
        }

        boolean casNext(Node<T> cmp, Node<T> val) {
            return UNSAFE.compareAndSwapObject(this, nextOffset, cmp, val);
        }

        private static final sun.misc.Unsafe UNSAFE;
        private static final long valueOffset;
        private static final long nextOffset;

        static {
            try {
                UNSAFE = sun.misc.Unsafe.getUnsafe();
                Class<?> k = Node.class;
                valueOffset = UNSAFE.objectFieldOffset
                        (k.getDeclaredField("value"));
                nextOffset = UNSAFE.objectFieldOffset
                        (k.getDeclaredField("next"));
            } catch (Exception e) {
                throw new Error(e);
            }
        }
    }

    private static final sun.misc.Unsafe UNSAFE;
    private static final long headOffset;
    private static final long tailOffset;
    static {
        try {
            UNSAFE = sun.misc.Unsafe.getUnsafe();
            Class<?> k = ConcurrentLinkedQueue.class;
            headOffset = UNSAFE.objectFieldOffset
                    (k.getDeclaredField("head"));
            tailOffset = UNSAFE.objectFieldOffset
                    (k.getDeclaredField("tail"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }
}
