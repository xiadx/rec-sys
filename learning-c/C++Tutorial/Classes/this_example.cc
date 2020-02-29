// this

#include <iostream>
using namespace std;

class CDummy {
public:
    int isitme(CDummy &param);
};

int CDummy::isitme(CDummy &param) {
    if (&param == this) return true;
    else return false;
}

int main() {
    CDummy a;
    CDummy *b = &a;
    if (b->isitme(a))
        cout << "Yes, &a is b";
    return 0;
}
