// iterator example iii

#include <iostream>
#include <list>
#include <algorithm>
using namespace std;

int main() {
    int a[5] = {1, 2, 3, 4, 5};
    list<int> l(a, a + 5);
    list<int>::iterator p = l.begin();
    advance(p, 2);
    cout << *p << endl;
    advance(p, -1);
    cout << *p << endl;
    list<int>::iterator q = l.end();
    q--;
    cout << distance(p, q) << endl;
    iter_swap(p, q);
    for (p = l.begin(); p != l.end(); ++p) {
        cout << *p << " ";
    }
    cout << endl;
    return 0;
}
