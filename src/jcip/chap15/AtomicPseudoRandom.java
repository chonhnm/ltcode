package jcip.chap15;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicPseudoRandom extends PseudoRandom {
    private AtomicInteger seed;

    public AtomicPseudoRandom(int seed) {
        this.seed = new AtomicInteger(seed);
    }

    @Override
    public int nextInt(int n) {
        while (true) {
            int s = seed.get();
            int next = calculateNext(s);
            if (seed.compareAndSet(s, next)) {
                int remainder = s % n;
                return remainder > 0 ? remainder : remainder + n;
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int threadCnt = 1000;
        test(new ReentrantLockPseudoRandom(100), threadCnt);
        test(new AtomicPseudoRandom(100), threadCnt);
    }

    private static void test(PseudoRandom random,int threadCnt ) throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(threadCnt);

        List<Callable<Integer>> list = new ArrayList<>();
        for (int i = 0; i < threadCnt; i++) {
            list.add(()-> {
                for (int j = 0; j < 1000; j++) {
                    for (int k = 0; k < 100000; k++) {
                        //random.nextInt(10000);
                    }
                    random.nextInt(10000);
                }
                return 0;
            });
        }
        long t1 = System.currentTimeMillis();
        service.invokeAll(list);
        service.shutdown();
        service.awaitTermination(1, TimeUnit.DAYS);
        System.out.println(System.currentTimeMillis() - t1);
    }
}
