package jcip.chap14;

public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    private static final long SLEEP_IN_MILLIS = 10;

    public SleepyBoundedBuffer(int size) {
        super(size);
    }

    public void put(V v) throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isFull()) {
                    doPut(v);
                    return;
                }
            }
            Thread.sleep(SLEEP_IN_MILLIS);
        }
    }

    public V take() throws InterruptedException {
        while (true) {
            synchronized (this) {
                if (!isEmpty()) {
                    return doTake();
                }
            }
            Thread.sleep(SLEEP_IN_MILLIS);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SleepyBoundedBuffer<Integer> buf = new SleepyBoundedBuffer<>(10);
        buf.put(10);
        Integer take = buf.take();
        System.out.println(take);
    }
}
