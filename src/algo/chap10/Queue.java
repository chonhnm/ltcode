package algo.chap10;

public class Queue<T> {

    private Object[] data;
    private int head = 0;
    private int tail = 0;
    private int size = 0;
    private static final int INIT_SIZE = 16;

    public Queue() {
        this(INIT_SIZE);
    }

    public Queue(int size) {
        data = new Object[size];
    }

    public void enqueue(T value) {
        if (size == data.length) {
            resize(size);
        }
        data[tail] = value;
        if (tail == data.length - 1) {
            tail = 0;
        } else {
            tail++;
        }
        size++;
    }

    public T dequeue() {
        if (size == 0) {
            throw new RuntimeException("queue underflow");
        }
        T val = (T) data[head];
        data[head] = null;
        if (head == data.length - 1) {
            head = 0;
        } else {
            head++;
        }
        size--;
        return val;
    }
    private void resize(int size) {
        Object[] newData = new Object[size * 2];
        int cnt = 0;
        for (int i = head; i < data.length; i++) {
            newData[cnt++] = data[i];
        }
        for (int i = 0; i < tail; i++) {
            newData[cnt++] = data[i];
        }
        data = newData;
        head = 0;
        tail = cnt;
        this.size = cnt;
    }


}
