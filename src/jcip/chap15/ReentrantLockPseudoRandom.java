package jcip.chap15;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockPseudoRandom extends PseudoRandom {

    private final Lock lock = new ReentrantLock(false);

    private int seed;

    public ReentrantLockPseudoRandom(int seed) {
        this.seed = seed;
    }

    @Override
    public int nextInt(int n) {
        lock.lock();
        try {
            int s = seed;
            seed = calculateNext(seed);
            int remainder = s % n;
            return remainder > 0 ? remainder : remainder + n;
        } finally {
            lock.unlock();
        }
    }
}
