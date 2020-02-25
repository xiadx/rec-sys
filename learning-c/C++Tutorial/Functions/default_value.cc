// default values in functions

#include <iostream>
using namespace std;

int divide(int a, int b = 2) {
    int r;
    r = a / b;
    return (r);
}

int main() {
    cout << divide(12);
    cout << endl;
    cout << divide(20, 4);
    return 0;
}
