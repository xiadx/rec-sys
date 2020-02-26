// array of structures

#include <iostream>
#include <string>
#include <sstream>
using namespace std;

#define N_MOVIES 3

struct movies_t {
    string title;
    int year;
} films[N_MOVIES];

void printmovie(movies_t movie);

int main() {
    string mystr;
    int n;

    for(n = 0; n < N_MOVIES; n++) {
        cout << "Enter title: ";
        getline(cin, films[n].title);
        cout << "Enter year: ";
        getline(cin, mystr);
        stringstream(mystr) >> films[n].year;
    }

    cout << "\nYou have entered thes movies:\n";
    for (n = 0; n < N_MOVIES; n++) {
        printmovie(films[n]);
    }
    return 0;
}

void printmovie(movies_t movie) {
    cout << movie.title;
    cout << " (" << movie.year << ")\n";
}
