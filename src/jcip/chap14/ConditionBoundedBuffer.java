package jcip.chap14;

import jcip.GuardedBy;
import jcip.ThreadSafe;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

@ThreadSafe
public class ConditionBoundedBuffer<V> {

    protected final Lock lock = new ReentrantLock();
    // CONDITION-PREDICATE: notFull(count < buf.length)
    private final Condition notFull = lock.newCondition();
    // CONDITION-PREDICATE: notEmpty(count > 0)
    private final Condition notEmpty = lock.newCondition();

    @GuardedBy("lock") private final V[] buf;
    @GuardedBy("lock") private int tail;
    @GuardedBy("lock") private int head;
    @GuardedBy("lock") private int count;

    public ConditionBoundedBuffer(int size) {
        lock.lock();
        try {
            buf = (V[]) new Object[size];
        } finally {
            lock.unlock();
        }
    }

    // BLOCK-UNTIL: notFull
    public void put(V v) throws InterruptedException {
        lock.lock();
        try {
            while (count == buf.length) {
                notFull.await();
            }
            buf[tail] = v;
            if(++tail == buf.length) {
                tail = 0;
            }
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    // BLOCK-UNTIL: notEmpty
    public V take() throws InterruptedException {
        lock.lock();
        try {
            while (count == 0) {
                notEmpty.await();
            }
            V v = buf[head];
            if (++head == buf.length) {
                head = 0;
            }
            count--;
            notFull.signal();
            return v;
        }finally {
            lock.unlock();
        }
    }


}
