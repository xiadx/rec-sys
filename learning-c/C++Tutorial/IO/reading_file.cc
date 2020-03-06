// reading a text file

#include <iostream>
#include <fstream>
#include <string>
using namespace std;

int main() {
    string line;
    ifstream myfile("example.txt");
    if (myfile.is_open()) {
        while (!myfile.eof()) {
            getline(myfile, line);
            cout << line << endl;
        }
        myfile.close();
    } else {
        cout << "Unable to open file";
    }
    return 0;
}
