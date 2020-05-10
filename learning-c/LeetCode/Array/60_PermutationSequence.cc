// The set [1,2,3,...,n] contains a total of n! unique permutations.
//
// By listing and labeling all of the permutations in order, we get the following sequence for n = 3:
//
// "123"
// "132"
// "213"
// "231"
// "312"
// "321"
// Given n and k, return the kth permutation sequence.
//
// Note:
//
// Given n will be between 1 and 9 inclusive.
// Given k will be between 1 and n! inclusive.
// Example 1:
//
// Input: n = 3, k = 3
// Output: "213"
// Example 2:
//
// Input: n = 4, k = 9
// Output: "2314"

#include <iostream>
#include <string>
using namespace std;

//class Solution {
//public:
//    int factorial(int n) {
//        int r = 1;
//        for (int i = 1; i <= n; ++i) {
//            r *= i;
//        }
//        return r;
//    }
//    string getPermutation(int n, int k) {
//        string s(n, '0');
//        string r;
//        for (int i = 0; i < n; ++i) {
//            s[i] += i + 1;
//        }
//        int b = k - 1, a;
//        for (int i = n - 1; i >= 1; --i) {
//            a = b / factorial(i);
//            b = b % factorial(i);
//            string::iterator p = next(s.begin(), a);
//            r.push_back(*p);
//            s.erase(p);
//        }
//        r.push_back(s[0]);
//        return r;
//    }
//};

class Solution {
public:
    int factorial(int n) {
        int r = 1;
        for (int i = 1; i <= n; ++i) {
            r *= i;
        }
        return r;
    }
    string kthPermutation(string &s, int k) {
        int n = s.size();
        string r;
        int b = factorial(n - 1);
        --k;
        for (int i = n - 1; i > 0; k %= b, b /= i, --i) {
            auto p = next(s.begin(), k / b);
            r.push_back(*p);
            s.erase(p);
        }
        r.push_back(s[0]);
        return r;
    }
    string getPermutation(int n, int k) {
        string s(n, '0');
        for (int i = 0; i < n; ++i) {
            s[i] += i + 1;
        }
        return kthPermutation(s, k);
    }
};

int main() {
    int n = 3, k = 1;
    Solution s;
    cout << s.getPermutation(n, k) << endl;
    return 0;
}
