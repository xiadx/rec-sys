// 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    void FindNumsAppearOnce(vector<int> data, int *num1, int *num2) {
        if (data.size() < 2) {
            return;
        }
        int resultExclusiveOR = 0;
        for (int i = 0; i < data.size(); ++i) {
            resultExclusiveOR ^= data[i];
        }
        unsigned int indexOf1 = FindFirstBitIs1(resultExclusiveOR);
        *num1 = 0, *num2 = 0;
        for (int j = 0; j < data.size(); ++j) {
            if (IsBit1(data[j], indexOf1)) {
                *num1 ^= data[j];
            } else {
                *num2 ^= data[j];
            }
        }
    }
    unsigned int FindFirstBitIs1(int num) {
        int indexBit = 0;
        while ((num & 1) == 0 && (indexBit < 8 * sizeof(int))) {
            num = num >> 1;
            indexBit++;
        }
        return indexBit;
    }
    bool IsBit1(int num, unsigned int indexBit) {
        num = num >> indexBit;
        return (num & 1);
    }
};

int main() {
    int a[] = {1, 2, 3, 3};
    vector<int> v(a, a + 4);
    int x = 0, y = 0;
    int *num1, *num2;
    num1 = &x, num2 = &y;
    Solution s;
    s.FindNumsAppearOnce(v, num1, num2);
    cout << "num1:" << *num1 << ",num2:" << *num2 << endl;
    return 0;
}
