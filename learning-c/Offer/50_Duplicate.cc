// 在一个长度为n的数组里的所有数字都在0到n-1的范围内。 数组中某些数字是重复的，但不知道有几个数字是重复的。也不知道每个数字重复几次。请找出数组中任意一个重复的数字。 例如，如果输入长度为7的数组{2,3,1,0,2,5,3}，那么对应的输出是第一个重复的数字2。

#include <iostream>
#include <vector>
#include <map>
using namespace std;

//class Solution {
//public:
//    // Parameters:
//    //        numbers:     an array of integers
//    //        length:      the length of array numbers
//    //        duplication: (Output) the duplicated number in the array number
//    // Return value:       true if the input is valid, and there are some duplications in the array number
//    //                     otherwise false
//    bool duplicate(int numbers[], int length, int *duplication) {
//        map<int, int> m;
//        for (int i = 0; i < length; ++i) {
//            m[numbers[i]] += 1;
//            if (m[numbers[i]] != 1) {
//                *duplication = numbers[i];
//                return true;
//            }
//        }
//        return false;
//    }
//};

//class Solution {
//public:
//    // Parameters:
//    //        numbers:     an array of integers
//    //        length:      the length of array numbers
//    //        duplication: (Output) the duplicated number in the array number
//    // Return value:       true if the input is valid, and there are some duplications in the array number
//    //                     otherwise false
//    bool duplicate(int numbers[], int length, int *duplication) {
//        vector<bool> k(length, false);
//        for (int i = 0; i < length; ++i) {
//            if (k[numbers[i]] == true) {
//                *duplication = numbers[i];
//                return true;
//            }
//            k[numbers[i]] = true;
//        }
//        return false;
//    }
//};

class Solution {
public:
    // Parameters:
    //        numbers:     an array of integers
    //        length:      the length of array numbers
    //        duplication: (Output) the duplicated number in the array number
    // Return value:       true if the input is valid, and there are some duplications in the array number
    //                     otherwise false
    bool duplicate(int numbers[], int length, int *duplication) {
        for (int i = 0; i < length; ++i) {
            int index = numbers[i] % length;
            if (numbers[index] >= length) {
                *duplication = index;
                return true;
            }
            numbers[index] += length;
        }
        return false;
    }
};

int main() {
    int numbers[] = {1, 1, 2};
    int a = 0, *d = &a;
    Solution s;
    s.duplicate(numbers, 3, d);
    cout << *d << endl;
    return 0;
}
