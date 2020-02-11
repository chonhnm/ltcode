package algo.chap7;

public class QuickSort {

    public static void sort(int[] data) {
        sort(data, 0, data.length - 1);
    }

    private static void sort(int[] data, int p, int r) {
        if(p < r) {
            int q = partition(data, p, r);
            sort(data, p, q-1);
            sort(data, q + 1, r);
        }
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
        sort(a);
        System.out.println(a);
    }
}
