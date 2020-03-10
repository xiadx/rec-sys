// iterator example ii

#include <iostream>
#include <vector>
using namespace std;

int main() {
    vector<int> v(10);
    for (int i = 0; i < v.size(); ++i) {
        cout << v[i] << " ";
    }
    cout << endl;
    vector<int>::iterator iter;
    for (iter = v.begin(); iter != v.end(); ++iter) {
        cout << *iter << " ";
    }
    cout << endl;
    for (iter = v.begin(); iter < v.end(); ++iter) {
        cout << *iter << " ";
    }
    cout << endl;
    iter = v.begin();
    while (iter < v.end()) {
        cout << *iter << " ";
        iter += 2;
    }
    cout << endl;
    return 0;
}
