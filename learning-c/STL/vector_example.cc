// vector example

#include <iostream>
#include <vector>
using namespace std;

template<class T>
void print(const vector<T> &v) {
    typename vector<T>::const_iterator iter;
    for (iter = v.begin(); iter != v.end(); ++iter) {
        cout << *iter << " ";
    }
    cout << endl;
}

int main() {
    int a[5] = {1, 2, 3, 4, 5};
    vector<int> v(a, a + 5);
    cout << v.end() - v.begin() << endl;
    print(v);
    v.insert(v.begin() + 2, 6);
    print(v);
    v.erase(v.begin() + 2);
    print(v);
    v.insert(v.begin(), v.begin() + 1, v.begin() + 3);
    print(v);
    v.erase(v.begin() + 1, v.begin() + 3);
    print(v);
    return 0;
}
