// 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，例如，如果输入如下4 X 4矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.

#include <iostream>
#include <vector>
using namespace std;

class Solution {
public:
    vector<int> printMatrix(vector<vector<int> > matrix) {
        vector<int> result;
        if (matrix.empty()) {
            return result;
        }
        int row = matrix.size();
        int col = matrix[0].size();
        int left = 0, right = col - 1, top = 0, btm = row - 1;
        while (left <= right && top <= btm) {
            for (int i = left; i <= right; ++i) {
                result.push_back(matrix[top][i]);
            }
            if (top < btm) {
                for (int i = top + 1; i <= btm; ++i) {
                    result.push_back(matrix[i][right]);
                }
            }
            if (top < btm && left < right) {
                for (int i = right - 1; i >= left; --i) {
                    result.push_back(matrix[btm][i]);
                }
            }
            if (top + 1 < btm && left < right) {
                for (int i = btm - 1; i >= top + 1; --i) {
                    result.push_back(matrix[i][left]);
                }
            }
            left++; right--; top++; btm--;
        }
        return result;
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
    int a[4][4] = {
        {1, 2, 3, 4},
        {5, 6, 7, 8},
        {9, 10, 11, 12},
        {13, 14, 15, 16}
    };
    vector<vector<int> > m;
    for (int i = 0; i < 4; ++i) {
        vector<int> r(a[i], a[i] + 4);
        m.push_back(r);
    }
    Solution s;
    print(s.printMatrix(m));
    return 0;
}
