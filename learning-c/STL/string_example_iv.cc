// string example iv

#include <iostream>
#include <sstream>
#include <string>
using namespace std;

int main() {
    string src("Avatar 123 5.2 Titanic K");
    istringstream istrStream(src);
    string s1, s2;
    int n; double d; char c;
    istrStream >> s1 >> n >> d >> s2 >> c;
    ostringstream ostrStream;
    ostrStream << s1 << endl << s2 << endl << n << endl << d << endl << c << endl;
    cout << ostrStream.str();
    return 0;
}
