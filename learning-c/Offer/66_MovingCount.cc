// 地上有一个m行和n列的方格。一个机器人从坐标0,0的格子开始移动，每一次只能向左，右，上，下四个方向移动一格，但是不能进入行坐标和列坐标的数位之和大于k的格子。 例如，当k为18时，机器人能够进入方格（35,37），因为3+5+3+7 = 18。但是，它不能进入方格（35,38），因为3+5+3+8 = 19。请问该机器人能够达到多少个格子？

#include <iostream>
using namespace std;

class Solution {
public:
    int movingCount(int threshold, int rows, int cols) {
        bool *flag = new bool[rows * cols];
        for (int i = 0; i < rows * cols; ++i) {
            flag[i] = false;
        }
        int count = moving(threshold, rows, cols, 0, 0, flag);
        return count;
    }
    int moving(int threshold, int rows, int cols, int i, int j, bool *flag) {
        int count = 0;
        if (i >= 0 && i < rows && j >= 0 && j < cols && getsum(i) + getsum(j) <= threshold && flag[i * cols + j] == false) {
            flag[i * cols + j] = true;
            count = 1 + moving(threshold, rows, cols, i + 1, j, flag)
                      + moving(threshold, rows, cols, i - 1, j, flag)
                      + moving(threshold, rows, cols, i, j - 1, flag)
                      + moving(threshold, rows, cols, i, j + 1, flag);
        }
        return count;
    }
    int getsum(int num) {
        int sum = 0;
        while (num) {
            sum += num % 10;
            num /= 10;
        }
        return sum;
    }
};

int main() {
    Solution s;
    cout << s.movingCount(1, 2, 2) << endl;
    return 0;
}
