// There are N children standing in a line. Each child is assigned a rating value.
//
// You are giving candies to these children subjected to the following requirements:
//
// Each child must have at least one candy.
// Children with a higher rating get more candies than their neighbors.
// What is the minimum candies you must give?
//
// Example 1:
//
// Input: [1,0,2]
// Output: 5
// Explanation: You can allocate to the first, second and third child with 2, 1, 2 candies respectively.
// Example 2:
//
// Input: [1,2,2]
// Output: 4
// Explanation: You can allocate to the first, second and third child with 1, 2, 1 candies respectively.
//             The third child gets 1 candy because it satisfies the above two conditions.

#include <iostream>
#include <vector>
using namespace std;

//class Solution {
//public:
//    int candy(vector<int> &ratings) {
//        int n = ratings.size();
//        vector<int> candies(n, 1);
//        bool flag = true;
//        while (flag) {
//            flag = false;
//            for (int i = 0; i < n; ++i) {
//                if (i != n - 1 && ratings[i] > ratings[i + 1] && candies[i] <= candies[i + 1]) {
//                    candies[i] = candies[i + 1] + 1;
//                    flag = true;
//                }
//            }
//            for (int i = n - 1; i >= 0; --i) {
//                if (i != 0 && ratings[i] > ratings[i - 1] && candies[i] <= candies[i - 1]) {
//                    candies[i] = candies[i - 1] + 1;
//                    flag = true;
//                }
//            }
//        }
//        int sum = 0;
//        for (int i = 0; i < n; ++i) {
//            sum += candies[i];
//        }
//        return sum;
//    }
//};

//class Solution {
//public:
//    int candy(vector<int> &ratings) {
//        int n = ratings.size();
//        vector<int> left2right(n, 1), right2left(n, 1);
//        for (int i = 1; i < n; ++i) {
//            if (ratings[i] > ratings[i - 1]) {
//                left2right[i] = left2right[i - 1] + 1;
//            }
//        }
//        for (int i = n - 2; i >= 0; --i) {
//            if (ratings[i] > ratings[i + 1]) {
//                right2left[i] = right2left[i + 1] + 1;
//            }
//        }
//        int sum = 0;
//        for (int i = 0; i < n; ++i) {
//            sum += max(left2right[i], right2left[i]);
//        }
//        return sum;
//    }
//};

//class Solution {
//public:
//    int candy(vector<int> &ratings) {
//        int n = ratings.size();
//        vector<int> candies(n, 1);
//        for (int i = 1; i < n; ++i) {
//            if (raatings[i] > ratings[i - 1]) {
//                candies[i] = candies[i - 1] + 1;
//            }
//        }
//        int sum = candies[n - 1];
//        for (int i = n - 2; i >= 0; --i) {
//            if (ratings[i] > ratings[i + 1] && candies[i] <= candies[i + 1]) {
//                candies[i] = candies[i + 1] + 1;
//            }
//            sum += candies[i];
//        }
//        return sum;
//    }
//};

class Solution {
public:
    int count(int n) {
        return (n * (n + 1)) / 2;
    }
    int candy(vector<int> &ratings) {
        int n = ratings.size();
        int candies = 0;
        int oldSlope = 0;
        int up = 0, down = 0;
        for (int i = 1; i < n; ++i) {
            int newSlope = ratings[i] > ratings[i - 1] ? 1 : (ratings[i] < ratings[i - 1] ? -1 : 0);
            if ((oldSlope > 0 && newSlope == 0) || (oldSlope < 0 && newSlope >= 0)) {
                candies += count(up) + count(down) + max(up, down);
                down = 0; up = 0;
            }
            if (newSlope < 0) ++down;
            if (newSlope > 0) ++up;
            if (newSlope == 0) ++candies;
            oldSlope = newSlope;
        }
        candies += count(up) + count(down) + max(up, down) + 1;
        return candies;
    }
};

int main() {
    vector<int> ratings({1, 2, 87, 87, 87, 2, 1});
    Solution s;
    cout << s.candy(ratings) << endl;
    return 0;
}
