// Given a m x n matrix, if an element is 0, set its entire row and column to 0. Do it in-place.
//
// Example 1:
//
// Input:
// [
//   [1,1,1],
//   [1,0,1],
//   [1,1,1]
// ]
// Output:
// [
//   [1,0,1],
//   [0,0,0],
//   [1,0,1]
// ]
// Example 2:
//
// Input:
// [
//   [0,1,2,0],
//   [3,4,5,2],
//   [1,3,1,5]
// ]
// Output:
// [
//   [0,0,0,0],
//   [0,4,5,0],
//   [0,3,1,0]
// ]
// Follow up:
//
// A straight forward solution using O(mn) space is probably a bad idea.
// A simple improvement uses O(m + n) space, but still not the best solution.
// Could you devise a constant space solution?

#include <iostream>
#include <unordered_set>
#include <vector>
using namespace std;

//class Solution {
//public:
//    void setZeroes(vector<vector<int> > &matrix) {
//        if (matrix.empty()) return;
//        int r = matrix.size(), c = matrix[0].size();
//        unordered_set<int> rows, cols;
//        for (int i = 0; i < r; ++i) {
//            for (int j = 0; j < c; ++j) {
//                if (matrix[i][j] == 0) {
//                    rows.insert(i);
//                    cols.insert(j);
//                }
//            }
//        }
//        for (unordered_set<int>::iterator it = rows.begin(); it != rows.end(); ++it) {
//            for (int j = 0; j < c; ++j) {
//                matrix[*it][j] = 0;
//            }
//        }
//        for (unordered_set<int>::iterator it = cols.begin(); it != cols.end(); ++it) {
//            for (int i = 0; i < r; ++i) {
//                matrix[i][*it] = 0;
//            }
//        }
//    }
//};

//class Solution {
//public:
//    void setZeroes(vector<vector<int> > &matrix) {
//        if (matrix.empty()) return;
//        int r = matrix.size(), c = matrix[0].size();
//        unordered_set<int> rows, cols;
//        for (int i = 0; i < r; ++i) {
//            for (int j = 0; j < c; ++j) {
//                if (matrix[i][j] == 0) {
//                    rows.insert(i);
//                    cols.insert(j);
//                }
//            }
//        }
//        for (auto it = rows.begin(); it != rows.end(); ++it) {
//            for (int j = 0; j < c; ++j) {
//                matrix[*it][j] = 0;
//            }
//        }
//        for (auto it = cols.begin(); it != cols.end(); ++it) {
//            for (int i = 0; i < r; ++i) {
//                matrix[i][*it] = 0;
//            }
//        }
//    }
//};

//class Solution {
//public:
//    void setZeroes(vector<vector<int> > &matrix) {
//        if (matrix.empty()) return;
//        int r = matrix.size(), c = matrix[0].size();
//        vector<bool> rows(r, false), cols(c, false);
//        for (int i = 0; i < r; ++i) {
//            for (int j = 0; j < c; ++j) {
//                if (matrix[i][j] == 0) {
//                    rows[i] = true;
//                    cols[j] = true;
//                }
//            }
//        }
//        for (int i = 0; i < r; ++i) {
//            if (rows[i]) {
//                for (int j = 0; j < c; ++j) {
//                    matrix[i][j] = 0;
//                }
//            }
//        }
//        for (int j = 0; j < c; ++j) {
//            if (cols[j]) {
//                for (int i = 0; i < r; ++i) {
//                    matrix[i][j] = 0;
//                }
//            }
//        }
//    }
//};

class Solution {
public:
    void setZeroes(vector<vector<int> > &matrix) {
        if (matrix.empty()) return;
        int r = matrix.size(), c = matrix[0].size();
        bool hasFirstRow = false, hasFirstCol = false;
        for (int j = 0; j < c; ++j) {
            if (matrix[0][j] == 0) hasFirstRow = true;
        }
        for (int i = 0; i < r; ++i) {
            if (matrix[i][0] == 0) hasFirstCol = true;
        }
        for (int i = 1; i < r; ++i) {
            for (int j = 1; j < c; ++j) {
                if (matrix[i][j] == 0) {
                    matrix[i][0] = 0;
                    matrix[0][j] = 0;
                }
            }
        }
        for (int i = 1; i < r; ++i) {
            if (matrix[i][0] == 0) {
                for (int j = 1; j < c; ++j) {
                    matrix[i][j] = 0;
                }
            }
        }
        for (int j = 1; j < c; ++j) {
            if (matrix[0][j] == 0) {
                for (int i = 1; i < r; ++i) {
                    matrix[i][j] = 0;
                }
            }
        }
        if (hasFirstRow) {
            for (int j = 0; j < c; ++j) {
                matrix[0][j] = 0;
            }
        }
        if (hasFirstCol) {
            for (int i = 0; i < r; ++i) {
                matrix[i][0] = 0;
            }
        }
    }
};

void print(vector<vector<int> > v) {
    for (int i = 0; i < v.size(); ++i) {
        for (int j = 0; j < v[i].size(); ++j) {
            cout << v[i][j] << " ";
        }
        cout << endl;
    }
}

int main() {
    vector<vector<int> > matrix(
        {
            {1, 1, 1},
            {1, 0, 1},
            {1, 1, 1}
        });
    Solution s;
    s.setZeroes(matrix);
    print(matrix);
    return 0;
}
