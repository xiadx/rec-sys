// string example

#include <iostream>
#include <string>
using namespace std;

int main() {
    string s1("");
    string s2("hello");
    string s3(4, 'k');
    string s4("12345", 1, 3);
    cout << s1 << endl;
    cout << s2 << endl;
    cout << s3 << endl;
    cout << s4 << endl;
    s1 = "123";
    s2 = "abc";
    s1.append(s2);
    cout << s1 << endl;
    s1.append(s2, 1, 2);
    cout << s1 << endl;
    s1.append(3, 'k');
    cout << s1 << endl;
    s1.append("abcde", 2, 3);
    cout << s1 << endl;
    s1 = "hello";
    s2 = "hello, world";
    cout << s1.compare(s2) << endl;
    cout << s1.compare(1, 2, s2, 0, 3) << endl;;
    cout << s1.compare(0, 2, s2) << endl;
    cout << s1.compare("Hello") << endl;;
    cout << s1.compare(1, 2, "Hello") << endl;
    cout << s1.compare(1, 2, "Hello", 1, 2) << endl;
    s1 = "this is ok";
    s2 = s1.substr(2, 4);
    cout << s2 << endl;
    s1 = "west";
    s2 = "east";
    s1.swap(s2);
    cout << s1 << endl;
    cout << s2 << endl;
    return 0;
}
