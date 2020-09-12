package algo.chap8;

public class CountingSort {

    public static void sort(int[] data, int max) {
        int[] result = new int[data.length];
        sort(data, result, max);
        System.arraycopy(result, 0, data, 0, data.length);
    }

    private static void sort(int[] data, int[] result, int max) {
        int[] counting = new int[max + 1];
        for (int datum : data) {
            counting[datum] = counting[datum] + 1;
        }
        for (int i = 1; i < counting.length; i++) {
            counting[i] = counting[i] + counting[i - 1];
        }
        for (int i = data.length - 1; i >= 0; i--) {
            result[counting[data[i]] - 1] = data[i];
            counting[data[i]] = counting[data[i]] - 1;
        }
    }

}
