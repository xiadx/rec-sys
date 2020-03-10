// list example

#include <iostream>
#include <list>
#include <algorithm>
using namespace std;

class Element {
private:
    int e;
public:
    Element(int n) {
        e = n;
    }
    friend bool operator<(const Element &a, const Element &b);
    friend bool operator==(const Element &a, const Element &b);
    friend ostream &operator<<(ostream &o, const Element &s);
};

bool operator<(const Element &a, const Element &b) {
    return a.e < b.e;
}

bool operator==(const Element &a, const Element &b) {
    return a.e == b.e;
}

ostream &operator<<(ostream &o, const Element &a) {
    o << a.e;
    return o;
}

template<class T>
void print(T first, T last) {
    for (; first != last; ++first) {
        cout << *first << " ";
    }
    cout << endl;
}

int main() {
    Element a[5] = {1, 3, 2, 4, 2};
    Element b[7] = {10, 30, 20, 30, 30, 40, 40};
    list<Element> l1(a, a + 5), l2(b, b + 7);
    l1.sort();
    print(l1.begin(), l1.end());
    l1.remove(2);
    print(l1.begin(), l1.end());
    l2.pop_front();
    print(l2.begin(), l2.end());
    l2.unique();
    print(l2.begin(), l2.end());
    l2.sort();
    l1.merge(l2);
    print(l1.begin(), l1.end());
    print(l2.begin(), l2.end());
    l1.reverse();
    print(l1.begin(), l1.end());
    l2.insert(l2.begin(), a + 1, a + 4);
    print(l2.begin(), l2.end());
    list<Element>::iterator p1, p2, p3;
    p1 = find(l1.begin(), l1.end(), 30);
    p2 = find(l2.begin(), l2.end(), 2);
    p3 = find(l2.begin(), l2.end(), 4);
    l1.splice(p1, l2, p2, p3);
    print(l1.begin(), l1.end());
    print(l2.begin(), l2.end());
    return 0;
}
