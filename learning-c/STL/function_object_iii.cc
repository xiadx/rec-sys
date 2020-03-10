// function object iii

#include <iostream>
#include <algorithm>
using namespace std;

template<class T>
void print(T first, T last) {
    for (; first != last; ++first) {
        cout << *first << " ";
    }
    cout << endl;
}

class Element {
public:
    int e;
    Element(int n) : e(n) {}
};

bool operator<(const Element &a, const Element &b) {
    return a.e < b.e;
}

bool Greater(const Element &a, const Element &b) {
    return a.e > b.e;
}

struct Less {
    bool operator()(const Element &a, const Element &b) {
        return (a.e % 10) < (b.e % 10);
    }
};

ostream &operator<<(ostream &o, const Element &a) {
    o << a.e;
    return o;
}

int main() {
    int a[4] = {5, 2, 4, 1};
    Element b[5] = {13, 12, 9, 8, 16};
    sort(a, a + 4);
    print(a, a + 4);
    sort(b, b + 5);
    print(b, b + 5);
    sort(b, b + 5, Greater);
    print(b, b + 5);
    sort(b, b + 5, Less());
    print(b, b + 5);
    return 0;
}
