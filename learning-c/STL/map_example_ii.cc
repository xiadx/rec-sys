// map example ii

#include <iostream>
#include <map>
using namespace std;

template<class T1, class T2>
ostream &operator<<(ostream &o, const pair<T1, T2> &p) {
    o << "(" << p.first << "," << p.second << ")";
    return o;
}

template<class T>
void print(T first, T last) {
    for (; first != last; ++first) {
        cout << *first << " ";
    }
    cout << endl;
}

typedef map<int, double, greater<int> > MYMAP;

int main() {
    MYMAP mp;
    mp.insert(MYMAP::value_type(15, 2.7));
    pair<MYMAP::iterator, bool> p = mp.insert(make_pair(15, 99.3));
    if (!p.second) {
        cout << *(p.first) << " already exists" << endl;
    }
    cout << mp.count(15) << endl;
    mp.insert(make_pair(20, 9.3));
    cout << mp[40] << endl;
    print(mp.begin(), mp.end());
    mp[15] = 6.28;
    mp[17] = 3.14;
    print(mp.begin(), mp.end());
    return 0;
}
