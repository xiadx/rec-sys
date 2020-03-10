// find example

#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

int main() {
    int a[4] = {10, 20, 30, 40};
    vector<int> v;
    v.push_back(1); v.push_back(2); v.push_back(3); v.push_back(4);
    vector<int>::iterator p;
    p = find(v.begin(), v.end(), 3);
    if (p == v.end()) {
        cout << "not found" << endl;
    } else {
        cout << "find " << *p << endl;
    }
    int *q = find(a, a + 4, 20);
    if (q == a + 4) {
        cout << "not found" << endl;
    } else {
        cout << "find " << *q << endl;
    }
    return 0;
}
