package jcip.chap14;

import jcip.ThreadSafe;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

@ThreadSafe
public class OneShotLatch {

    private class  Sync extends AbstractQueuedSynchronizer {

        private Sync sync = new Sync();

        public void await() throws InterruptedException {
            sync.acquireSharedInterruptibly(0);
        }

        public void signal() {
            sync.releaseShared(0);
        }

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

    public static void main(String[] args) {

    }

}
