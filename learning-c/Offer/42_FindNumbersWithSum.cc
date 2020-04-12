// 输入一个递增排序的数组和一个数字S，在数组中查找两个数，使得他们的和正好是S，如果有多对数字的和等于S，输出两个数的乘积最小的。
// 对应每个测试案例，输出两个数，小的先输出。

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    vector<int> FindNumbersWithSum(vector<int> array, int sum) {
        vector<int> v;
        int i = 0, j = array.size() - 1;
        while (i < j) {
            int sumx = array[i] + array[j];
            if (sumx > sum) j--;
            if (sumx < sum) i++;
            if (sumx == sum) {
                v.push_back(array[i]);
                v.push_back(array[j]);
                break;
            }
        }
        return v;
    }
};

void print(vector<int> v) {
    vector<int>::iterator it;
    for (it = v.begin(); it != v.end(); ++it) {
        cout << *it << " ";
    }
    cout << endl;
}

int main() {
    int a[] = {1, 2, 3, 4};
    vector<int> v(a, a + 4);
    Solution s;
    print(s.FindNumbersWithSum(v, 5));
    return 0;
}
