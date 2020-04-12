// heap example

#include <iostream>
using namespace std;

void swap(int &a, int &b) {
    int temp = a;
    a = b;
    b = temp;
}

void adjust_heap(int *a, int i, int l) {
    int left = 2 * i + 1;
    int right = 2 * i + 2;
    int max = i;
    if (left < l && a[left] > a[max]) {
        max = left;
    }
    if (right < l && a[right] > a[max]) {
        max = right;
    }
    if (max != i) {
        swap(a[max], a[i]);
        adjust_heap(a, max, l);
    }
}

void make_heap(int *a, int l) {
    for (int i = (l - 1) / 2; i >= 0; --i) {
        adjust_heap(a, i, l);
    }
}

void heap_sort(int *a, int l) {
    make_heap(a, l);
    for (int i = l - 1; i >= 1; --i) {
        swap(a[0], a[i]);
        adjust_heap(a, 0, i);
    }
}

void print(int *a, int l) {
    for (int i = 0; i < l; ++i) {
        cout << a[i] << " ";
    }
    cout << endl;
}

int main() {
    int a[] = {1, 3, 5, 4, 2};
    heap_sort(a, 5);
    print(a, 5);
    return 0;
}
