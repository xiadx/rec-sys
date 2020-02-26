// null-terminated sequences of characters

#include <iostream>
using namespace std;

int main() {
    char question[] = "Please enter your first name: ";
    char greeting[] = "Hello, ";
    char yourname[80];
    cout << question;
    cin >> yourname;
    cout << greeting << yourname << "!";
    return 0;
}
