package algo.chap6;

import java.util.concurrent.ThreadLocalRandom;

public class HeapSort {

    public static void main(String[] args) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        int count = 20;
        int[] a = new int[count];
        for (int i = 0; i < a.length; i++) {
            a[i] = current.nextInt(count*3);
        }
        sort(a);
        System.out.println(a);
    }

    public static void sort(int[] data) {
        HeapData heapData = new HeapData(data);
        buildMaxHeap(heapData);
        for (int i = data.length - 1; i > 0; i--) {
            exchange(data, 0, i);
            heapData.heapSize--;
            maxHeapify(heapData, 0);
        }
    }

    public static void buildMaxHeap(HeapData heapData) {
        for (int i = halve(heapData.source.length); i >= 0; i--) {
            maxHeapify(heapData, i);
        }
    }

    public static void maxHeapify(HeapData heapData, int index) {
        int[] data = heapData.source;
        int left = left(index);
        int right = right(index);
        int largest = index;
        if (left < heapData.heapSize && data[left] > data[index]) {
            largest = left;
        }
        if (right < heapData.heapSize && data[right] > data[largest]) {
            largest = right;
        }
        if (largest != index) {
            exchange(data, index, largest);
            maxHeapify(heapData, largest);
        }
    }

    private static void exchange(int[] data, int idx1, int idx2) {
        int temp = data[idx1];
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

    private static class HeapData {
        private int[] source;
        private int heapSize;

        private HeapData(int[] data) {
            this.source = data;
            heapSize = data.length;
        }
    }
}
