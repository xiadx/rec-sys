// bitset example

#include <iostream>
#include <bitset>
#include <string>
using namespace std;

int main() {
    bitset<7> bst1;
    bitset<7> bst2;
    cout << bst1 << endl;
    bst1.set(0, 1);
    cout << bst1 << endl;
    bst1 <<= 4;
    cout << bst1 << endl;
    bst1.set(2);
    cout << bst1 << endl;
    bst2 |= bst1;
    cout << bst2 << endl;
    cout << bst2.to_ulong() << endl;
    bst2.flip();
    cout << bst2 << endl;
    bst2.flip(6);
    cout << bst2 << endl;
    bitset<7> bst3 = bst2 ^ bst1;
    cout << bst3 << endl;
    cout << bst3[3] << "," << bst3[4] << endl;
    return 0;
}
