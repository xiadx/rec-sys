// function object ii

#include <iostream>
#include <vector>
#include <numeric>
using namespace std;

template<class T>
void print(T first, T last) {
    for (; first != last; ++first) {
        cout << *first << " ";
    }
    cout << endl;
}

int square(int total, int value) {
    return total + value * value;
}

template<class T>
class SumPowers {
private:
    int power;
public:
    SumPowers(int p) : power(p) {}
    const T operator()(const T &total, const T &value) {
        T v = value;
        for (int i = 0; i < power - 1; ++i) {
            v = v * value;
        }
        return total + v;
    }
};

int main() {
    const int SIZE = 5;
    int a[] = {1, 2, 3, 4, 5};
    vector<int> v(a, a + SIZE);
    print(v.begin(), v.end());
    cout << accumulate(v.begin(), v.end(), 0, SumPowers<int>(2)) << endl;
    cout << accumulate(v.begin(), v.end(), 0, SumPowers<int>(3)) << endl;
    cout << accumulate(v.begin(), v.end(), 0, SumPowers<int>(4)) << endl;
    return 0;
}
