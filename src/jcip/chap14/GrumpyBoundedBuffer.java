package jcip.chap14;

import jcip.exception.BufferEmptyException;
import jcip.exception.BufferFullException;

public class GrumpyBoundedBuffer<V> extends BaseBoundedBuffer<V> {

    public GrumpyBoundedBuffer(int size) {
        super(size);
    }

    public synchronized void put(V v) throws BufferFullException {
        if (isFull()) {
            throw new BufferFullException();
        }
        doPut(v);
    }

    public synchronized V take() throws BufferEmptyException {
        if (isEmpty()) {
            throw new IllegalStateException("buf is empty.");
        }
        return doTake();
    }

    public static void main(String[] args) throws InterruptedException {
        GrumpyBoundedBuffer<Integer> buf = new GrumpyBoundedBuffer<>(10);
        while (true) {
            try {
                buf.put(100);
                break;
            } catch (BufferFullException e) {
                e.printStackTrace();
            }
        }
        Integer take;
        while (true) {
            try {
                take = buf.take();
                break;
            } catch (BufferEmptyException e) {
                Thread.sleep(1);
            }
        }
        System.out.println(take);
    }
}
