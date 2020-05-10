// According to the Wikipedia's article: "The Game of Life, also known simply as Life, is a cellular automaton devised by the British mathematician John Horton Conway in 1970."
//
// Given a board with m by n cells, each cell has an initial state live (1) or dead (0). Each cell interacts with its eight neighbors (horizontal, vertical, diagonal) using the following four rules (taken from the above Wikipedia article):
//
// Any live cell with fewer than two live neighbors dies, as if caused by under-population.
// Any live cell with two or three live neighbors lives on to the next generation.
// Any live cell with more than three live neighbors dies, as if by over-population..
// Any dead cell with exactly three live neighbors becomes a live cell, as if by reproduction.
// Write a function to compute the next state (after one update) of the board given its current state. The next state is created by applying the above rules simultaneously to every cell in the current state, where births and deaths occur simultaneously.
//
// Example:
//
// Input:
// [
//   [0,1,0],
//   [0,0,1],
//   [1,1,1],
//   [0,0,0]
// ]
// Output:
// [
//   [0,0,0],
//   [1,0,1],
//   [0,1,1],
//   [0,1,0]
// ]
// Follow up:
//
// Could you solve it in-place? Remember that the board needs to be updated at the same time: You cannot update some cells first and then use their updated values to update other cells.
// In this question, we represent the board using a 2D array. In principle, the board is infinite, which would cause problems when the active area encroaches the border of the array. How would you address these problems?

#include <iostream>
#include <vector>
using namespace std;

//class Solution {
//public:
//    void gameOfLife(vector<vector<int> > &board) {
//        if (board.empty()) return;
//        int rows = board.size();
//        int cols = board[0].size();
//        vector<vector<int> > copyBoard(rows, vector<int>(cols, 0));
//        for (int i = 0; i < rows; ++i) {
//            for (int j = 0; j < cols; ++j) {
//                copyBoard[i][j] = board[i][j];
//            }
//        }
//        int neighbors[3] = {0, 1, -1};
//        for (int row = 0; row < rows; ++row) {
//            for (int col = 0; col < cols; ++col) {
//                int liveNeighbors = 0;
//                for (int i = 0; i < 3; ++i) {
//                    for (int j = 0; j < 3; ++j) {
//                        if (!(neighbors[i] == 0 && neighbors[j] == 0)) {
//                            int r = row + neighbors[i];
//                            int c = col + neighbors[j];
//                            if ((r < rows && r >= 0) && (c < cols && c >= 0) && copyBoard[r][c] == 1) ++liveNeighbors;
//                        }
//                    }
//                }
//                if ((copyBoard[row][col] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)) {
//                    board[row][col] = 0;
//                }
//                if (copyBoard[row][col] == 0 && liveNeighbors == 3) {
//                    board[row][col] = 1;
//                }
//            }
//        }
//    }
//};

class Solution {
public:
    void gameOfLife(vector<vector<int> > &board) {
        if (board.empty()) return;
        int rows = board.size();
        int cols = board[0].size();
        int neighbors[3] = {0, 1, -1};
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                int liveNeighbors = 0;
                for (int i = 0; i < 3; ++i) {
                    for (int j = 0; j < 3; ++j) {
                        if (!(neighbors[i] == 0 && neighbors[j] == 0)) {
                            int r = row + neighbors[i];
                            int c = col + neighbors[j];
                            if ((r < rows && r >= 0) && (c < cols && c >= 0) && abs(board[r][c]) == 1) ++liveNeighbors;
                        }
                    }
                }
                if ((board[row][col] == 1) && (liveNeighbors < 2 || liveNeighbors > 3)) {
                    board[row][col] = -1;
                }
                if (board[row][col] == 0 && liveNeighbors == 3) {
                    board[row][col] = 2;
                }
            }
        }
        for (int row = 0; row < rows; ++row) {
            for (int col = 0; col < cols; ++col) {
                if (board[row][col] > 0) board[row][col] = 1;
                else board[row][col] = 0;
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
    vector<vector<int> > board({
        {0, 1, 0},
        {0, 0, 1},
        {1, 1, 1},
        {0, 0, 0}
    });
    Solution s;
    s.gameOfLife(board);
    print(board);
    return 0;
}
