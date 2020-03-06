// exceptions

#include <iostream>
using namespace std;

int main() {
    try {
        throw 20;
    } catch (int e) {
        cout << "An exception occurred. ";
        cout << "Exception Nr. " << e << endl;
    }
    return 0;
}
