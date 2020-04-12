// 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。

#include <iostream>
#include <vector>
using namespace std;

void print(vector<int> v) {
    vector<int>::iterator it;
    for (it = v.begin(); it != v.end(); ++it) {
        cout << *it << " ";
    }
    cout << endl;
}

class Solution {
public:
    void reOrderArray(vector<int> &array) {
        vector<int> r;
        int size = array.size();
        for (int i = 0; i < size; ++i) {
            if (array[i] & 1) {
                r.push_back(array[i]);
            }
        }
        for (int i = 0; i < size; ++i) {
            if (!(array[i] & 1)) {
                r.push_back(array[i]);
            }
        }
        array = r;
    }
};

int main() {
    int a[] = {1, 2, 3, 4, 5};
    vector<int> v(a, a + 5);
    Solution s;
    s.reOrderArray(v);
    print(v);
    return 0;
}
