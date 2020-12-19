package jcip.chap15;

import jcip.Immutable;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicReference;

public class CasNumberRange {

    @Immutable
    private static class IntPair {
        private final int lower;
        private final int upper;

        // invariant: lower <= upper
        public IntPair(int lower, int upper) {
            this.lower = lower;
            this.upper = upper;
        }
    }

    private final AtomicReference<IntPair> values;

    public CasNumberRange(int lower, int upper) {
        if (lower > upper) {
            throw new IllegalArgumentException(String.format("lower is larger than upper: %d %d", lower, upper));
        }
        this.values = new AtomicReference<>(new IntPair(lower, upper));
    }

    public void check() {
        IntPair intPair = values.get();
        if (intPair.lower > intPair.upper) {
            throw new IllegalStateException(String.format("lower is larger than upper: %d %d", intPair.lower, intPair.upper));
        }
    }

    public boolean setLower(int i) {
        while (true) {
            IntPair oldV = values.get();
            if (i > oldV.upper) {
                return false;
            }
            IntPair newV = new IntPair(i, oldV.upper);
            if (values.compareAndSet(oldV, newV)) {
                return true;
            }
        }
    }

    public boolean setUpper(int i) {
        while (true) {
            IntPair oldV = values.get();
            if (i < oldV.lower) {
                return false;
            }
            IntPair newV = new IntPair(oldV.lower, i);
            if (values.compareAndSet(oldV, newV)) {
                return true;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CasNumberRange range = new CasNumberRange(0, 0);
        Random random = new Random(10000);
        int threadCnt = 1000;
        ExecutorService service = Executors.newFixedThreadPool(threadCnt);
        for (int i = 0; i < threadCnt; i++) {
            Future<?> submit = service.submit(() -> {
                range.setLower(random.nextInt());
                range.setUpper(random.nextInt());
                range.check();
            });
        }
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        System.out.println(range.values.get().lower);
        System.out.println(range.values.get().upper);
    }
}
