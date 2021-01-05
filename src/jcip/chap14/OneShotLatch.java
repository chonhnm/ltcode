package jcip.chap14;

import jcip.ThreadSafe;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

@ThreadSafe
public class OneShotLatch {

    private Sync sync = new Sync();

    public void await() throws InterruptedException {
        sync.acquireSharedInterruptibly(0);
    }

    public void signal() {
        sync.releaseShared(0);
    }

    private class  Sync extends AbstractQueuedSynchronizer {

        @Override
        protected int tryAcquireShared(int arg) {
            // success if latch is open(state == 1), else fail
            int state = getState();
            return state == 1? 1: -1;
        }

        @Override
        protected boolean tryReleaseShared(int ignore) {
            setState(1);
            return true;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        OneShotLatch oneShotLatch = new OneShotLatch();
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            executorService.submit(()-> {
                try {
                    System.out.println("begin:" + Thread.currentThread().getName());
                    oneShotLatch.await();
                    System.out.println("end:" + Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
        }
        Thread.sleep(100);
        System.out.println("release");
        oneShotLatch.signal();
        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.MINUTES);


    }

}
