// 牛客最近来了一个新员工Fish，每天早晨总是会拿着一本英文杂志，写些句子在本子上。同事Cat对Fish写的内容颇感兴趣，有一天他向Fish借来翻看，但却读不懂它的意思。例如，“student. a am I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，正确的句子应该是“I am a student.”。Cat对一一的翻转这些单词顺序可不在行，你能帮助他么？

#include <iostream>
#include <string>
using namespace std;

//class Solution {
//public:
//    string ReverseSentence(string str) {
//        int l = str.size();
//        if (l == 0) {
//            return str;
//        }
//        int i = 0, j = 0;
//        while (j < l) {
//            if (str[j] == ' ') {
//                for (int m = i, n = j - 1; n > m; ++m, --n) swap(str[m], str[n]);
//                i = j + 1;
//            }
//            j++;
//        }
//        for (int m = i, n = j - 1; n > m; ++m, --n) swap(str[m], str[n]);
//        for (int m = 0, n = l - 1; n > m; ++m, --n) swap(str[m], str[n]);
//        return str;
//    }
//};

class Solution {
public:
    string ReverseSentence(string str) {
        int l = str.size();
        if (l == 0) {
            return str;
        }
        int i = 0, j = 0;
        while (j < l) {
            if (str[j] == ' ') {
                reverse(str.begin() + i, str.begin() + j);
                i = j + 1;
            }
            j++;
        }
        reverse(str.begin() + i, str.begin() + j);
        reverse(str.begin(), str.end());
        return str;
    }
};

int main() {
    string str = "student. a am I";
    Solution s;
    cout << s.ReverseSentence(str) << endl;
    return 0;
}
