// deque example

#include <iostream>
#include <deque>
using namespace std;

int main() {
    deque<int> d;
    d.push_front(1);
    d.push_front(2);
    d.push_front(3);
    for (int i = 0; i < d.size(); ++i) {
        cout << d[i] << " ";
    }
    cout << endl;
    d.pop_front();
    for (int i = 0; i < d.size(); ++i) {
        cout << d[i] << " ";
    }
    cout << endl;
    return 0;
}
