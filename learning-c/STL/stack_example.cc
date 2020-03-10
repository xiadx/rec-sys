// stack example

#include <iostream>
#include <stack>
using namespace std;

int main() {
    int n, k;
    stack<int> stk;
    cin >> n >> k;
    if (n == 0) {
        cout << 0;
        return 0;
    }
    while (n) {
        stk.push(n % k);
        n /= k;
    }
    while (!stk.empty()) {
        cout << stk.top();
        stk.pop();
    }
    cout << endl;
    return 0;
}
