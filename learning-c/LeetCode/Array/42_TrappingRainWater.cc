// Given n non-negative integers representing an elevation map where the width of each bar is 1, compute how much water it is able to trap after raining.
//
//
// The above elevation map is represented by array [0,1,0,2,1,0,1,3,2,1,2,1]. In this case, 6 units of rain water (blue section) are being trapped. Thanks Marcos for contributing this image!
//
// Example:
//
// Input: [0,1,0,2,1,0,1,3,2,1,2,1]
// Output: 6

#include <iostream>
#include <vector>
#include <stack>
using namespace std;

//class Solution {
//public:
//    int trap(vector<int> &height) {
//        int n = height.size();
//        int *leftPeak = new int[n]();
//        int *rightPeak = new int[n]();
//        for (int i = 1; i < n; ++i) {
//            leftPeak[i] = max(leftPeak[i - 1], height[i - 1]);
//        }
//        for (int i = n - 2; i >= 0; --i) {
//            rightPeak[i] = max(rightPeak[i + 1], height[i + 1]);
//        }
//        int sum = 0;
//        for (int i = 0; i < n; ++i) {
//            int h = min(leftPeak[i], rightPeak[i]);
//            if (h > height[i]) {
//                sum += h - height[i];
//            }
//        }
//        delete[] leftPeak;
//        delete[] rightPeak;
//        return sum;
//    }
//};

//class Solution {
//public:
//    int trap(vector<int> &height) {
//        int n = height.size();
//        int peakIndex = 0;
//        for (int i = 0; i < n; ++i) {
//            if (height[i] > height[peakIndex]) peakIndex = i;
//        }
//        int water = 0;
//        for (int i = 0, leftPeak = 0; i < peakIndex; ++i) {
//            if (height[i] > leftPeak) leftPeak = height[i];
//            else water += leftPeak - height[i];
//        }
//        for (int i = n - 1, rightPeak = 0; i > peakIndex; --i) {
//            if (height[i] > rightPeak) rightPeak = height[i];
//            else water += rightPeak - height[i];
//        }
//        return water;
//    }
//};

//class Solution {
//public:
//    int trap(vector<int> &height) {
//        int n = height.size();
//        int sum = 0;
//        int maxLeft = 0, maxRight = 0;
//        int indexLeft = 1, indexRight = n - 2;
//        for (int i = 1; i < n - 1; ++i) {
//            if (height[indexLeft - 1] < height[indexRight + 1]) {
//                maxLeft = max(maxLeft, height[indexLeft - 1]);
//                if (maxLeft > height[indexLeft]) {
//                    sum += maxLeft - height[indexLeft];
//                }
//                ++indexLeft;
//            } else {
//                maxRight = max(maxRight, height[indexRight + 1]);
//                if (maxRight > height[indexRight]) {
//                    sum += maxRight - height[indexRight];
//                }
//                --indexRight;
//            }
//        }
//        return sum;
//    }
//};

//class Solution {
//public:
//    int trap(vector<int> &height) {
//        int n = height.size();
//        int sum = 0;
//        int maxLeft = 0, maxRight = 0;
//        int indexLeft = 1, indexRight = n - 2;
//        while (indexLeft <= indexRight) {
//            if (height[indexLeft - 1] < height[indexRight + 1]) {
//                maxLeft = max(maxLeft, height[indexLeft - 1]);
//                if (maxLeft > height[indexLeft]) {
//                    sum += maxLeft - height[indexLeft];
//                }
//                ++indexLeft;
//            } else {
//                maxRight = max(maxRight, height[indexRight + 1]);
//                if (maxRight > height[indexRight]) {
//                    sum += maxRight - height[indexRight];
//                }
//                --indexRight;
//            }
//        }
//        return sum;
//    }
//};

class Solution {
public:
    int trap(vector<int> &height) {
        int ans = 0, current = 0;
        stack<int> st;
        while (current < height.size()) {
            while (!st.empty() && height[current] > height[st.top()]) {
                int top = st.top();
                st.pop();
                if (st.empty())
                    break;
                int distance = current - st.top() - 1;
                int bounded_height = min(height[current], height[st.top()]) - height[top];
                ans += distance * bounded_height;
            }
            st.push(current++);
        }
        return ans;
    }
};

int main() {
    vector<int> height({0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1});
    Solution s;
    cout << s.trap(height) << endl;
    return 0;
}
