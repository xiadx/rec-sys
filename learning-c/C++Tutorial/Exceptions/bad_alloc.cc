// bad_alloc standard exception

#include <iostream>
#include <exception>
using namespace std;

int main() {
    try {
        int *myarray = new int[1000];
    } catch (bad_alloc &e) {
        cout << "bad_alloc exception: " << e.what() << endl;
    }
    return 0;
}
