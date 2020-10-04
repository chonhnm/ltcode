#include <stdio.h>
#include <stdlib.h>
#define SIZE 10

void insert_sort(void *v[], int left, int right, int (*comp)(void *, void *));
void swap(void *v[], int a, int b);
int intcomp(int *a, int *b);

int main2() {
    int *arr[SIZE];
    for (size_t i = 0; i < SIZE; i++) {
        arr[i] = malloc((sizeof *arr));
        *arr[i] = rand() % 100;
    }
    for (int i = 0; i < SIZE; i++) {
        printf("%d\t", *arr[i]);
    }
    printf("\n");
    insert_sort((void **)arr, 0, 9, (int (*)(void *, void *))intcomp);
    for (int i = 0; i < SIZE; i++) {
        printf("%d\t", *arr[i]);
    }
}

void insert_sort(void *v[], int left, int right, int (*comp)(void *, void *)) {
    for (int i = left + 1; i <= right; i++) {
        void *key = v[i];
        int j = i - 1;
        while (j >= left && (*comp)(v[j], key) > 0) {
            swap(v, j, i);
            j -= 1;
        }
        v[j + 1] = key;
    }
}

void swap(void *v[], int a, int b) {
    void *tmp = v[a];
    v[a] = v[b];
    v[b] = tmp;
}

int intcomp(int *a, int *b) { return *a - *b; }