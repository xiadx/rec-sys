// list example ii

#include <iostream>
#include <list>
using namespace std;

int main() {
    list<int> monkeys;
    int n, m;
    while (true) {
        cin >> n >> m;
        if (n == 0 && m == 0) {
            break;
        }
        monkeys.clear();
        for (int i = 1; i <= n; ++i) {
            monkeys.push_back(i);
        }
        list<int>::iterator it = monkeys.begin();
        while (monkeys.size() > 1) {
            for (int i = 1; i < m; ++i) {
                ++it;
                if (it == monkeys.end()) {
                    it = monkeys.begin();
                }
            }
            it = monkeys.erase(it);
            if (it == monkeys.end()) {
                it = monkeys.begin();
            }
        }
        cout << monkeys.front() << endl;
    }
    return 0;
}
