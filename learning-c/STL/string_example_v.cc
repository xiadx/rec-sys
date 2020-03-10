// string example v

#include <iostream>
#include <algorithm>
#include <string>
using namespace std;

int main() {
    string s("afgcbed");
    string::iterator p = find(s.begin(), s.end(), 'c');
    if (p != s.end()) {
        cout << p -s.begin() << endl;
    }
    sort(s.begin(), s.end());
    cout << s << endl;
    next_permutation(s.begin(), s.end());
    cout << s << endl;
    return 0;
}
