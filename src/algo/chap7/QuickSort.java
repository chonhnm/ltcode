package algo.chap7;

import java.util.concurrent.ThreadLocalRandom;

public class QuickSort {

    public static void sort(int[] data) {
        _sort(data, 0, data.length - 1);
    }

    public static int select(int[] data, int order) {
        if (order > data.length) {
            throw new RuntimeException("too big");
        }
        return _select(data, 0, data.length - 1, order);
    }

    private static int _select(int[] data, int p, int r, int order) {
        if (p == r) {
            return data[p];
        }
        int q = randomPartition(data, p, r);
        int k = q - p + 1;
        if ( order == k) {
            return data[q];
        } else if ( order < k) {
            return _select(data, p, q - 1, order);
        } else {
            return _select(data, q + 1, r, order - k);
        }
    }

    private static void _sort(int[] data, int p, int r) {
        if(p < r) {
            int q = randomPartition(data, p, r);
            _sort(data, p, q-1);
            _sort(data, q + 1, r);
        }
    }

    private static final ThreadLocalRandom RANDOM = ThreadLocalRandom.current();

    private static int randomPartition(int[] data, int p, int r) {
        int i = RANDOM.nextInt(r - p + 1) + p;
        exchange(data, r, i);
        return partition(data, p, r);
    }

    private static int partition(int[] data, int p, int r) {
        int x = data[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (data[j] <= x) {
                i++;
                exchange(data, i, j);
            }
        }
        i++;
        exchange(data, i, r);
        return i;
    }

    private static void exchange(int[] data, int i, int j) {
        int temp = data[i];
        data[i] = data[j];
        data[j] = temp;

    }

    public static void main(String[] args) {
        int[] a = new int[]{3,11,2,8,9,44,3,7,9};
//        sort(a);
//        System.out.println(a);
        int select = select(a, 3);
        int select1 = select(a, 4);
        int select2 = select(a, 5);
        int select3 = select(a, 6);
        int select4 = select(a, 9);
        int select5 = select(a, 11);
    }
}
