// typedef

#include <iostream>
using namespace std;

typedef char C;
typedef unsigned int WORD;
typedef char *pChar;
typedef char field[5];

int main() {
    C mychar = 'a', anotherchar = 'b';
    char c = 'c';
    C *ptc1 = &c;
    WORD myword = 98;
    char d = 'd';
    pChar ptc2 = &d;
    field name1 = {'a', 'b', 'c', 'd', '\0'};
    field name2 = "abcd";
    cout << mychar << " " << anotherchar << "\n";
    cout << myword << " " << "\n";
    cout << *ptc1 << " " << *ptc2 << "\n";
    cout << name1 << " " << name2 << "\n";
    return 0;
}
