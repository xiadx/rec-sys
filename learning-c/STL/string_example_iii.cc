// string example iii

#include <iostream>
#include <string>
using namespace std;

int main() {
    string s1("real steel");
    s1.replace(1, 3, "123456", 2, 4);
    cout << s1 << endl;
    string s2("harry potter");
    s2.replace(2, 3, 5, '0');
    cout << s2 << endl;
    int n = s2.find("00000");
    s2.replace(n, 5, "xxx");
    cout << s2 << endl;
    s1 = "real steel";
    s1.erase(1, 3);
    cout << s1 << endl;
    s1.erase(5);
    cout << s1 << endl;
    s1 = "limitless";
    s2 = "00";
    s1.insert(2, "123");
    cout << s1 << endl;
    s1.insert(3, s2);
    cout << s1 << endl;
    s1.insert(3, 5, 'x');
    cout << s1 << endl;
    return 0;
}
