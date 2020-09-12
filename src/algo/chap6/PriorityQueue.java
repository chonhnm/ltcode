package algo.chap6;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PriorityQueue<E> {

    private Object[] queue;
    private int size;
    private Comparator<E> comparator;

    private static final int DEFAULT_INITIAL_CAPACITY = 11;

    public PriorityQueue() {
        queue = new Object[DEFAULT_INITIAL_CAPACITY];
    }

    public PriorityQueue(Collection<? extends E> c) {
        initElementsFromCollection(c);
        buildMaxHeap();
    }

    public E peek() {
        return (E) queue[0];
    }

    public E poll() {
        if (size < 1) {
            throw new RuntimeException("heap underflow");
        }
        E result = (E) queue[0];
        queue[0] = queue[--size];
        maxHeapify(queue, 0, size);
        return result;
    }

    public boolean add(E value) {
        if (value == null) throw new NullPointerException();
        if (size >= queue.length) {
            grow(size + 1);
        }
        increaseValue(queue, size, value);
        size++;
        return true;
    }

    private void grow(int minCapacity) {
        int cap = 2 * minCapacity;
        queue = Arrays.copyOf(queue, cap);
    }

    private static <E> void increaseValue(Object[] data, int index, E value) {
        data[index] = value;
        while (index > 0 && ((Comparable)data[parent(index)]).compareTo(data[index]) < 0) {
            exchange(data, index, parent(index));
            index = parent(index);
        }
    }

    private void initElementsFromCollection(Collection<? extends E> c) {
        Object[] es = c.toArray();
        int len = es.length;
        // If c.toArray incorrectly doesn't return Object[], copy it.
        if (es.getClass() != Object[].class)
            es = Arrays.copyOf(es, len, Object[].class);
        if (len == 1 || this.comparator != null)
            for (Object e : es)
                if (e == null)
                    throw new NullPointerException();
        this.queue = ensureNonEmpty(es);
        this.size = len;
    }

    private Object[] ensureNonEmpty(Object[] es) {
        return (es.length > 0) ? es : new Object[1];
    }

    private void buildMaxHeap() {
        final Object[] data =  queue;
        for (int i = halve(size); i >= 0; i--) {
            maxHeapify(data, i, size);
        }
    }

    private static void maxHeapify(Object[] data, int index, int size) {
        int left = left(index);
        int right = right(index);
        int largest = index;
        if (left < size && ((Comparable) data[left]).compareTo(data[index]) > 0) {
            largest = left;
        }
        if (right < size && ((Comparable) data[right]).compareTo(data[largest]) > 0) {
            largest = right;
        }
        if (largest != index) {
            exchange(data, index, largest);
            maxHeapify(data, largest, size);
        }
    }

    private static void exchange(Object[] data, int idx1, int idx2) {
        Object temp = data[idx1];
        data[idx1] = data[idx2];
        data[idx2] = temp;
    }

    private static int parent(int index) {
        assert index != 0;
        return (index - 1) >>> 1;
    }

    private static int left(int index) {
        return (index << 1) + 1;
    }

    private static int right(int index) {
        return (index << 1) + 2;
    }

    private static int halve(int length) {
        return (length >>> 1) - 1;
    }

    public static void main(String[] args) {
        int[] a = new int[]{2,3,7,5,32,3};
        List<Integer> list = Arrays.stream(a).boxed().collect(Collectors.toList());
        PriorityQueue<Integer> queue = new PriorityQueue<>(list);
        queue.add(8);
        queue.add(100);
        Integer peek = queue.peek();
        Integer poll1 = queue.poll();
        Integer poll2 = queue.poll();
        System.out.println("");
    }

}
