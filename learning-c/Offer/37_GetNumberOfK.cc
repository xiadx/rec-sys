// 统计一个数字在排序数组中出现的次数。

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    int GetNumberOfK(vector<int> data, int k) {
        int left = 0, right = data.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (data[mid] < k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        int i = left;
        left = 0, right = data.size() - 1;
        while (left <= right) {
            int mid = (left + right) / 2;
            if (data[mid] <= k) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        int j = left;
        return j - i;
    }
};

int main() {
    int a[] = {1, 2, 3, 3, 3, 3, 4, 5};
    vector<int> v(a, a + 8);
    Solution s;
    cout << s.GetNumberOfK(v, 3) << endl;
    return 0;
}
