// 在数组中的两个数字，如果前面一个数字大于后面的数字，则这两个数字组成一个逆序对。输入一个数组,求出这个数组中的逆序对的总数P。并将P对1000000007取模的结果输出。 即输出P%1000000007

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    long long InversePairsCore(vector<int> &data, vector<int> &copy, int start, int end) {
        if (start == end) {
            copy[start] = data[start];
            return 0;
        }
        int length = (end - start) / 2;
        long long left = InversePairsCore(copy, data, start, start + length);
        long long right = InversePairsCore(copy, data, start + length + 1, end);
        int i = start + length;
        int j = end;
        int indexCopy = end;
        long long count = 0;
        while (i >= start && j >= start + length + 1) {
            if (data[i] > data[j]) {
                copy[indexCopy--] = data[i--];
                count += j - start - length;
            } else {
                copy[indexCopy--] = data[j--];
            }
        }
        for (; i >= start; --i) {
            copy[indexCopy--] = data[i];
        }
        for (; j >= start + length + 1; --j) {
            copy[indexCopy--] = data[j];
        }
        return left + right + count;
    }
    int InversePairs(vector<int> data) {
        int length = data.size();
        if (length == 0) {
            return 0;
        }
        vector<int> copy(data.begin(), data.end());
        long long count = InversePairsCore(data, copy, 0, length - 1);
        return count % 1000000007;
    }
};

int main() {
    int a[] = {7, 5, 6, 4};
    vector<int> v(a, a + 4);
    Solution s;
    cout << s.InversePairs(v) << endl;
    return 0;
}
