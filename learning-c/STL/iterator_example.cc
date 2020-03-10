// iterator example

#include <iostream>
#include <vector>
using namespace std;

int main() {
    vector<int> v;
    for (int n = 0; n < 5; ++n) {
        v.push_back(n);
    }
    vector<int>::iterator i;
    for (i = v.begin(); i != v.end(); ++i) {
        cout << *i << " ";
        *i *= 2;
    }
    cout << endl;
    for (vector<int>::reverse_iterator j = v.rbegin(); j != v.rend(); ++j) {
        cout << *j << " ";
    }
    cout << endl;
    return 0;
}
