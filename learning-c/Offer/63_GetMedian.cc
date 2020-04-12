// 如何得到一个数据流中的中位数？如果从数据流中读出奇数个数值，那么中位数就是所有数值排序之后位于中间的数值。如果从数据流中读出偶数个数值，那么中位数就是所有数值排序之后中间两个数的平均值。我们使用Insert()方法读取数据流，使用GetMedian()方法获取当前读取数据的中位数。

#include <iostream>
#include <algorithm>
#include <vector>
using namespace std;

class Solution {
public:
    void Insert(int num) {
        int size = min.size() + max.size();
        if ((size & 1) == 0) {
            if (max.size() > 0 && num < max[0]) {
                max.push_back(num);
                push_heap(max.begin(), max.end(), less<int>());
                num = max[0];
                pop_heap(max.begin(), max.end(), less<int>());
                max.pop_back();
            }
            min.push_back(num);
            push_heap(min.begin(), min.end(), greater<int>());
        } else {
            if (min.size() > 0 && num > min[0]) {
                min.push_back(num);
                push_heap(min.begin(), min.end(), greater<int>());
                num = min[0];
                pop_heap(min.begin(), min.end(), greater<int>());
                min.pop_back();
            }
            max.push_back(num);
            push_heap(max.begin(), max.end(), less<int>());
        }
    }
    double GetMedian() {
        int size = min.size() + max.size();
        if (size <= 0) {
            return 0;
        }
        if ((size & 1) == 0) {
            return (max[0] + min[0]) / 2.0;
        } else {
            return min[0];
        }
    }
private:
    vector<int> min;
    vector<int> max;
};

int main() {
    Solution s;
    s.Insert(1);
    s.Insert(2);
    cout << s.GetMedian() << endl;
    return 0;
}
