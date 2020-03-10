// function object

#include <iostream>
using namespace std;

class CAverage {
public:
    double operator()(int a, int b, int c) {
        return (double)(a + b + c) / 3;
    }
};

int main() {
    CAverage average;
    cout << average(3, 2, 3);
    return 0;
}
