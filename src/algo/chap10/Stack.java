package algo.chap10;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Stack<T> {

    private Object[] data;
    private int top;
    private static final int INIT_SIZE = 16;

    public Stack() {
        this(INIT_SIZE);
    }

    public Stack(int size) {
        data = new Object[size];
        top = -1;
    }

    public boolean isEmpty() {
        return top == -1;
    }

    public void push(T value) {
        top++;
        if (top == data.length) {
            resize(top);
        }
        data[top] = value;
    }

    public T pop() {
        if (top == -1) {
            throw new RuntimeException("stack underflow");
        }
        T val = (T) data[top];
        data[top] = null;
        top--;
        return val;
    }

    private void resize(int size) {
        data = Arrays.copyOf(data, data.length * 2);
    }

    public static void main(String[] args) {
        Map map = new HashMap<>(4);
        map.put("a", 1);
    }

}
