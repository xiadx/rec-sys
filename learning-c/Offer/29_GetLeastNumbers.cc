// 输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4,。

#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

//class Solution {
//public:
//    vector<int> GetLeastNumbers(vector<int> input, int k) {
//        vector<int> r;
//        if (input.empty() || k <= 0 || k > input.size()) {
//            return r;
//        }
//        sort(input.begin(), input.end());
//        for (int i = 0; i < k; ++i) {
//            r.push_back(input[i]);
//        }
//        return r;
//    }
//};

//class Solution {
//public:
//    vector<int> GetLeastNumbers(vector<int> input, int k) {
//        vector<int> r;
//        if (input.empty() || k <= 0 || k> input.size()) {
//            return r;
//        }
//        make_heap(input.begin(), input.end(), greater<int>());
//        for (int i = 0; i < k; ++i) {
//            pop_heap(input.begin(), input.end(), greater<int>());
//            r.push_back(input.back());
//            input.pop_back();
//        }
//        return r;
//    }
//};

//class Solution {
//public:
//    vector<int> GetLeastNumbers(vector<int> input, int k) {
//        vector<int> r;
//        if (input.empty() || k <= 0 || k > input.size()) {
//            return r;
//        }
//        for (int i = 0; i < k; ++i) {
//            r.push_back(input[i]);
//        }
//        make_heap(r.begin(), r.end(), less<int>());
//        for (int i = k; i < input.size(); ++i) {
//            if (input[i] < r[0]) {
//                pop_heap(r.begin(), r.end(), less<int>());
//                r.pop_back();
//                r.push_back(input[i]);
//                push_heap(r.begin(), r.end(), less<int>());
//            }
//        }
//        return r;
//    }
//};

class Solution {
public:
    void swap(int &a, int &b) {
        int temp = a;
        a = b;
        b = temp;
    }
    void adjust_heap(vector<int> &input, int i, int l) {
        int left = 2 * i + 1;
        int right = 2 * i + 2;
        int max = i;
        if (left < l && input[left] > input[max]) {
            max = left;
        }
        if (right < l && input[right] > input[max]) {
            max = right;
        }
        if (max != i) {
            swap(input[max], input[i]);
            adjust_heap(input, max, l);
        }
    }
    void make_heap(vector<int> &input, int l) {
        for (int i = (l - 1) / 2; i >= 0; --i) {
            adjust_heap(input, i, l);
        }
    }
    vector<int> GetLeastNumbers(vector<int> input, int k) {
        vector<int> r;
        if (input.empty() || k <= 0 || k > input.size()) {
            return r;
        }
        for (int i = 0; i < k; ++i) {
            r.push_back(input[i]);
        }
        make_heap(r, r.size());
        for (int i = k; i < input.size(); ++i) {
            if (input[i] < r[0]) {
                r[0] = input[i];
                adjust_heap(r, 0, r.size());
            }
        }
        return r;
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
    int a[] = {1, 3, 4, 2, 6};
    vector<int> v(a, a + 5);
    Solution s;
    print(s.GetLeastNumbers(v, 2));
    return 0;
}
