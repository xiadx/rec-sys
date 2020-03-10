// set example

#include <iostream>
#include <set>
using namespace std;

template<class T>
void print(T first, T last) {
    for (; first != last; ++first) {
        cout << *first << " ";
    }
    cout << endl;
}

class Element {
private:
    int e;
public:
    Element(int n) { e = n; }
    friend bool operator<(const Element &a, const Element &b) {
        return a.e < b.e;
    }
    friend ostream &operator<<(ostream &o, const Element &a) {
        o << a.e;
        return o;
    }
    friend class MyLess;
};

class MyLess {
public:
    bool operator()(const Element &a, const Element &b) {
        return (a.e % 10) < (b.e % 10);
    }
};

typedef multiset<Element> MSET1;
typedef multiset<Element, MyLess> MSET2;

int main() {
    const int SIZE = 6;
    Element a[SIZE] = {4, 22, 19, 8, 33, 40};
    MSET1 m1;
    m1.insert(a, a + SIZE);
    m1.insert(22);
    cout << m1.count(22) << endl;
    print(m1.begin(), m1.end());
    MSET1::iterator it = m1.find(19);
    if (it != m1.end()) {
        cout << "found " << *it << endl;
    }
    cout << *m1.lower_bound(22) << "," << *m1.upper_bound(22) << endl;
    it = m1.erase(m1.lower_bound(22), m1.upper_bound(22));
    print(m1.begin(), m1.end());
    cout << *it << endl;
    MSET2 m2;
    m2.insert(a, a + SIZE);
    print(m2.begin(), m2.end());
    return 0;
}
