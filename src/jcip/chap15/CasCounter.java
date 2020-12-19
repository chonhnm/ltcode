package jcip.chap15;

import java.util.ArrayList;
import java.util.List;

public class CasCounter {

    private SimulatedCAS value;

    private SimulatedCAS conflictCount;

    public CasCounter(int value) {
        this.value = new SimulatedCAS(value);
        this.conflictCount = new SimulatedCAS(0);
    }

    public int getValue() {
        return value.get();
    }

    private int getConflictCount() {
        return conflictCount.get();
    }

    public int incrementAndGet() {
        int v;
        for (; ; ) {
            v = value.get();
            if (v == value.compareAndSwap(v, v + 1)) {
                break;
            }
            for (;;) {
                int conf = conflictCount.get();
                if (conf == conflictCount.compareAndSwap(conf, conf + 1)) {
                    System.out.println(Thread.currentThread().getId() + ":tryAgain");
                    break;
                }
            }
        }
        return v + 1;
    }

    public static void main(String[] args) throws InterruptedException {
        CasCounter counter = new CasCounter(0);
        int threadCnt = 100;
        int cntPerThread = 1000;
        List<Thread> threads = new ArrayList<>();
        for (int i = 0; i < threadCnt; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < cntPerThread; j++) {
                    counter.incrementAndGet();
                }
            });
            threads.add(thread);
        }
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            thread.join();
        }
        System.out.println(counter.getValue());
        System.out.println(counter.getConflictCount());
    }
}
