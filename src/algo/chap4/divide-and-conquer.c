#include "divide-and-conquer.h"

#include <limits.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define SIZE 100000000

int main() {
    int *arr = malloc(sizeof(int) * SIZE);
    for (int i = 0; i < SIZE; i++) {
        arr[i] = rand() % 1000 - 500;
    }
    int lidx, ridx;
    time_t t1 = time(NULL);
    int a = max_sub_array(arr, 0, SIZE - 1, &lidx, &ridx);
    time_t t2 = time(NULL);
    double diff = difftime(t2, t1);
    printf("a:%d, lidx:%d, ridx:%d, costs:%f\n", a, lidx, ridx, diff);

    free(arr);
}

int max_sub_array(int arr[], int low, int high, int *lidx, int *ridx) {
    if (low >= high) {
        *lidx = low;
        *ridx = low;
        return arr[low];
    } else {
        int max_crossing_array(int arr[], int low, int mid, int high, int *lidx, int *ridx);
        int mid = (low + high) / 2;
        int llow, lhigh;
        int clow, chigh;
        int rlow, rhigh;
        int lsum = max_sub_array(arr, low, mid, &llow, &lhigh);
        int csum = max_crossing_array(arr, low, mid, high, &clow, &chigh);
        int rsum = max_sub_array(arr, mid + 1, high, &rlow, &rhigh);

        if (lsum > csum && lsum > rsum) {
            *lidx = llow;
            *ridx = lhigh;
            return lsum;
        } else if (rsum > csum && rsum > lsum) {
            *lidx = rlow;
            *ridx = rhigh;
            return rsum;
        } else {
            *lidx = clow;
            *ridx = chigh;
            return csum;
        }
    }
}

int max_crossing_array(int arr[], int low, int mid, int high, int *lidx, int *ridx) {
    int lsum = INT_MIN;
    int rsum = INT_MIN;
    int sum = 0;
    for (int i = mid; i >= low; i--) {
        sum += arr[i];
        if (sum > lsum) {
            lsum = sum;
            *lidx = i;
        }
    }
    sum = 0;
    for (int i = mid + 1; i <= high; i++) {
        sum += arr[i];
        if (sum > rsum) {
            rsum = sum;
            *ridx = i;
        }
    }
    return lsum + rsum;
}

int max_sub_array_force(int arr[], int low, int high, int *lidx, int *ridx) {
    int maxsum = INT_MIN;
    int l = -1, r = -1;
    for (int i = low; i <= high; i++) {
        int sum = 0;
        for (int j = i; j <= high; j++) {
            sum += arr[j];
            if (sum > maxsum) {
                maxsum = sum;
                l = i;
                r = j;
            }
        }
    }
    *lidx = l;
    *ridx = r;
    return maxsum;
}