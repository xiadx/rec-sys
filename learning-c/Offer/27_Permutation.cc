// 输入一个字符串,按字典序打印出该字符串中字符的所有排列。例如输入字符串abc,则打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。

#include <iostream>
#include <string>
#include <vector>
#include <algorithm>
using namespace std;

class Solution {
public:
    vector<string> Permutation(string str) {
        vector<string> result;
        if (str.empty()) {
            return result;
        }
        PermutationHelper(str, result, 0);
        sort(result.begin(), result.end());
        return result;
    }
    void PermutationHelper(string str, vector<string> &result, int begin) {
        if (begin == str.size() - 1) {
            if (find(result.begin(), result.end(), str) == result.end()) {
                result.push_back(str);
            }
        } else {
            for (int i = begin; i < str.size(); ++i) {
                swap(str[i], str[begin]);
                PermutationHelper(str, result, begin + 1);
                swap(str[i], str[begin]);
            }
        }
    }
    void swap(char &fir, char &sec) {
        char temp = fir;
        fir = sec;
        sec = temp;
    }
};

void print(vector<string> v) {
    vector<string>::iterator it;
    for (it = v.begin(); it != v.end(); ++it) {
        cout << *it << " ";
    }
    cout << endl;
}

int main() {
    string str = "abc";
    Solution s;
    print(s.Permutation(str));
    return 0;
}
