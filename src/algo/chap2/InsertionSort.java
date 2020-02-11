package algo.chap2;

import algo.chap6.HeapSort;

import java.util.concurrent.ThreadLocalRandom;

public class InsertionSort {

    public static void main(String[] args) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        int count = 10000000;
        int[] a = new int[count];
        for (int i = 0; i < a.length; i++) {
            a[i] = current.nextInt(count);
        }
        int[] b = a.clone();
        long l = System.currentTimeMillis();
        MergeSort.sort(a);
        long l1 = System.currentTimeMillis();
        System.out.println(l1 -l);
        long t = System.currentTimeMillis();
        HeapSort.heapSort(b);
        long t1 = System.currentTimeMillis();
        System.out.println(t1 -t);
        for (int i = 0; i < a.length - 1; i++) {
            if (a[i] > a[i + 1]) {
                throw new NullPointerException();
            }
        }
    }

    public static void sort(int[] data) {
        if (data.length < 2) return;
        for (int i = 1; i < data.length; i++) {
            int key = data[i];
            int j;
            for (j = i - 1; j >= 0 && data[j] > key ; j--) {
                data[j + 1] = data[j];
            }
            data[j+1] = key;
        }

    }
}
