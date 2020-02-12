package algo.chap2;

import algo.chap6.HeapSort;
import algo.chap7.QuickSort;
import algo.chap8.CountingSort;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.ThreadLocalRandom;

public class InsertionSort {

    public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
//        check(MergeSort.class);
//        check(HeapSort.class);
        check(QuickSort.class);
        check(CountingSort.class);
    }

    private static void check(Class<?> cls) throws NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        int size = 10000000;
        boolean countingSort = cls.getSimpleName().equals("CountingSort");
        Method sort;
        if (countingSort) {
            sort = cls.getMethod("sort", int[].class, int.class);
        } else {
            sort = cls.getMethod("sort", int[].class);
        }
        Object o = cls.newInstance();
        int[] data = getdata(size);
        long t1 = System.currentTimeMillis();
        if (countingSort) {
            sort.invoke(o, data, size);
        } else {
            sort.invoke(o, data);
        }
        long t2 = System.currentTimeMillis();
        System.out.println(cls.getName() + ": " + (t2 - t1));
        for (int i = 0; i < data.length - 1; i++) {
            if (data[i] > data[i + 1]) {
                throw new NullPointerException();
            }
        }

    }

    private static int[] getdata(int count) {
        ThreadLocalRandom current = ThreadLocalRandom.current();
        int[] a = new int[count];
        for (int i = 0; i < a.length; i++) {
            a[i] = current.nextInt(count/100);
        }
        return a;
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
