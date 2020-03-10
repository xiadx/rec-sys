// string example ii

#include <iostream>
#include <string>
using namespace std;

int main() {
    string s1("source code");
    int n;
    if ((n = s1.find('u')) != string::npos) {
        cout << n << "," << s1.substr(n) << endl;
    }
    if ((n = s1.find("source", 3)) == string::npos) {
        cout << "not found" << endl;
    }
    if ((n = s1.find("code")) != string::npos) {
        cout << n << "," << s1.substr(n) << endl;
    }
    if ((n = s1.find_first_of("ceo")) != string::npos) {
        cout << n << "," << s1.substr(n) << endl;
    }
    if ((n = s1.find_last_of('e')) != string::npos) {
        cout << n << "," << s1.substr(n) << endl;
    }
    if ((n = s1.find_first_not_of("eou", 1)) != string::npos) {
        cout << n << "," << s1.substr(n) << endl;
    }
    return 0;
}
