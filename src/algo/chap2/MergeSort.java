package algo.chap2;

import java.util.Arrays;

public class MergeSort {

    public static void main(String[] args) {
        int[] a = new int[]{3,2,8,7,1,9,2};
        sort(a);
        for (int i : a) {
            System.out.print(i + ", ");
        }
    }

    public static void sort(int[] data) {
        sortHelper(data, 0, data.length - 1);
    }

    private static void sortHelper(int[] data, int p, int r) {
        if (p < r) {
            int q = (p + r) / 2;
            sortHelper(data, p, q);
            sortHelper(data, q + 1, r);
            merge(data, p, q, r);
        }
    }

    private static void merge(int[] data, int p, int q, int r) {
        int n1 = q - p + 1;
        int n2 = r - q;
        int[] left = new int[n1];
        int[] right = new int[n2];
        System.arraycopy(data, p, left, 0, n1);
        System.arraycopy(data, q + 1, right, 0, n2);
        int i = 0, j = 0;
        for (int k = p; k <= r; k++) {
            if (i >= n1) {
                data[k] = right[j];
                j++;
            } else if (j >= n2) {
                data[k] = left[i];
                i++;
            } else {
                if (left[i] <= right[j]) {
                    data[k] = left[i];
                    i++;
                } else {
                    data[k] = right[j];
                    j++;
                }
            }
        }
    }
}
