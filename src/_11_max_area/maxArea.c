#include <assert.h>
#include <stdlib.h>

int maxArea(int*, int);
int maxArea2(int*, int);

int main() {
    int arr[] = {1, 8, 6, 2, 5, 4, 8, 3, 7};
    assert(maxArea(arr, 9) == 49);
    assert(maxArea2(arr, 9) == 49);
}

int maxArea(int* height, int heightSize) {
    int result = 0;
    for (size_t i = 0; i < heightSize; i++) {
        for (size_t j = i + 1; j < heightSize; j++) {
            int a = height[i];
            int b = height[j];
            int area = a > b ? b * (j - i) : a * (j - i);
            if (area > result) {
                result = area;
            }
        }
    }
    return result;
}

int maxArea2(int* height, int heightSize) {
    int result = 0;
    int* ap = height;
    int* bp = height + heightSize - 1;
    while (ap < bp) {
        int area;
        if (*ap > *bp) {
            area = *bp * (bp - ap);
            bp--;
        } else {
            area = *ap * (bp - ap);
            ap++;
        }
        result = area > result ? area : result;
    }
    return result;
}