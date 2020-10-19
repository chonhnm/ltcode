#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#define SIZE 1000000
#define SIZE_LOG 14

void insertsort(int arr[], int a, int b);
void insertsort_rev(int arr[], int a, int b);
void selectsort(int arr[], int a, int b);
void mergesort(int arr[], int a, int b);
void mergesort2(int arr[], int a, int b);
void merge(int arr[], int p, int q, int r);

int main() {
    // int arr[] = {1, 3, 5, 4, 7, 24, 2, 33, 5, 122};
    int arr[SIZE];
    for (int i = 0; i < SIZE; i++) {
        arr[i] = rand() % 1000;
    }
    time_t t1 = time(NULL);
    // insertsort(arr, 0, SIZE - 1);
    // selectsort(arr, 0, SIZE-1);
    mergesort2(arr, 0, SIZE - 1);
    time_t t2 = time(NULL);
    double diff = difftime(t2, t1);
    printf("cost time: %f", diff);
    for (int i = 0; i < SIZE; i++) {
        if (i != SIZE - 1) {
            if (arr[i + 1] - arr[i] < 0) {
                perror("not sorted");
            }
        }
        // printf("%d\t", arr[i]);
    }
}

void insertsort(int arr[], int a, int b) {
    for (int i = a + 1; i <= b; i++) {
        int key = arr[i];
        int j = i - 1;
        while (j >= a && arr[j] > key) {
            arr[j + 1] = arr[j];
            j -= 1;
        }
        arr[j + 1] = key;
    }
}

void insertsort_rev(int arr[], int a, int b) {
    for (int i = a + 1; i <= b; i++) {
        int key = arr[i];
        int j = i - 1;
        while (j >= 0 && arr[j] < key) {
            arr[j + 1] = arr[j];
            j -= 1;
        }
        arr[j + 1] = key;
    }
}

void selectsort(int arr[], int a, int b) {
    for (int i = a; i < b; i++) {
        for (int j = i + 1; j < b; j++) {
            if (arr[j] < arr[i]) {
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
    }
}

void mergesort(int arr[], int a, int b) {
    if (a < b) {
        int q = (a + b) / 2;
        mergesort(arr, a, q);
        mergesort(arr, q + 1, b);
        merge(arr, a, q, b);
    }
}

void mergesort2(int arr[], int a, int b) {
    if (b - a > SIZE_LOG) {
        int q = (a + b) / 2;
        mergesort2(arr, a, q);
        mergesort2(arr, q + 1, b);
        merge(arr, a, q, b);
    } else {
        insertsort(arr, a, b);
    }
}

void merge(int arr[], int p, int q, int r) {
    int ts = r - p + 1;
    int ls = q - p + 1;
    int rs = r - q;
    int la[ls];
    int ra[rs];
    for (int i = 0; i < ls; i++) {
        la[i] = arr[p + i];
    }
    for (int i = 0; i < rs; i++) {
        ra[i] = arr[q + 1 + i];
    }
    int lidx = 0, ridx = 0;
    for (int i = 0; i < ts; i++) {
        if (lidx >= ls) {
            arr[i + p] = ra[ridx];
            ridx++;
        } else if (ridx >= rs) {
            arr[i + p] = la[lidx];
            lidx++;
        } else {
            if (la[lidx] <= ra[ridx]) {
                arr[i + p] = la[lidx];
                lidx++;
            } else {
                arr[i + p] = ra[ridx];
                ridx++;
            }
        }
    }
}