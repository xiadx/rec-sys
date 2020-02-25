// overloaded function

#include <iostream>
using namespace std;

int operate(int a, int b) {
    return (a * b);
}

float operate(float a, float b) {
    return (a / b);
}

int main() {
    int x = 5, y = 2;
    float n = 5.0, m = 2.0;
    cout << operate(x, y);
    cout << "\n";
    cout << operate(n, m);
    cout << "\n";
    return 0;
}
