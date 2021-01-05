package jcip.chap14;

import jcip.GuardedBy;
import jcip.ThreadSafe;

@ThreadSafe
public class ThreadGate {

    // CONDITION-PREDICATE: opened-since(n) (isOpen || generation > n)
    @GuardedBy("this") private boolean isOpen;
    @GuardedBy("this") private int generation;

    public synchronized void close() {
        isOpen = false;
    }

    public synchronized void open() {
        ++generation;
        isOpen = true;
        notifyAll();
    }

    // BLOCK-UNTIL:  opened-since(generation)
    public synchronized void await() throws InterruptedException {
        int arrivalGeneration = generation;
        while (!isOpen && arrivalGeneration == generation) {
            wait();
        }
    }

}
