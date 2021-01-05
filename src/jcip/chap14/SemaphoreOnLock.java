package jcip.chap14;

import jcip.GuardedBy;
import jcip.ThreadSafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@ThreadSafe
public class SemaphoreOnLock {

    protected final Lock lock = new ReentrantLock();
    // CONDITION-PREDICATE: permits(permits > 0)
    private final Condition permitAvailable = lock.newCondition();
    @GuardedBy("lock")
    private int permits;
    private final int COUNT;

    public SemaphoreOnLock(int permits) {
        lock.lock();
        try {
            this.permits = permits;
            COUNT = permits;
        } finally {
            lock.unlock();
        }
    }

    // BLOCK-UNTIL: permitAvailable
    public boolean acquire() throws InterruptedException {
        lock.lock();
        try {
            while (permits <= 0) {
                permitAvailable.await();
            }
            --permits;
            return true;
        } finally {
            lock.unlock();
        }
    }

    public void release() {
        lock.lock();
        try {
            ++permits;
            if (permits > COUNT) {
                permits = COUNT;
            }
            permitAvailable.signal();
        } finally {
            lock.unlock();
        }
    }
}
