// heap example

#include <iostream>
#include <vector>
#include <algorithm>
using namespace std;

void print(vector<int> v) {
    vector<int>::iterator it;
    for (it = v.begin(); it != v.end(); ++it) {
        cout << *it << " ";
    }
    cout << endl;
}

int main() {
    int myints[] = {10, 20, 30, 5, 15};
    vector<int> v(myints, myints + 5);
    print(v);
    make_heap(v.begin(), v.end());
    print(v);
    pop_heap(v.begin(), v.end());
    print(v);
    v.pop_back();
    print(v);
    v.push_back(99);
    print(v);
    push_heap(v.begin(), v.end());
    print(v);
    sort_heap(v.begin(), v.end());
    print(v);
    return 0;
}
