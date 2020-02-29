// union

#include <iostream>
using namespace std;

union mytypes_t {
    char c;
    int i;
    float f;
} mytypes;

union mix_t {
    long l;
    struct {
        short hi;
        short io;
    } s;
    char c[4];
} mix;

struct {
    char title[50];
    char author[50];
    union {
        float dollars;
        int yens;
    } price;
} book;

int main() {
    mytypes.c = 'c';
    cout << mytypes.c << " " << mytypes.i << " " << mytypes.f << "\n";
    mytypes.i = 98;
    cout << mytypes.c << " " << mytypes.i << " " << mytypes.f << "\n";
    mytypes.f = 98.6;
    cout << mytypes.c << " " << mytypes.i << " " << mytypes.f << "\n";
    mix.l = 100;
    cout << mix.l << " " << mix.s.hi << " " << mix.s.io << " " << mix.c << "\n";
    mix.s.hi = 1;
    mix.s.io = 2;
    cout << mix.l << " " << mix.s.hi << " " << mix.s.io << " " << mix.c << "\n";
    mix.c[0] = 'a'; mix.c[1] = 'b'; mix.c[2] = 'c'; mix.c[3] = '\0';
    cout << mix.l << " " << mix.s.hi << " " << mix.s.io << " " << mix.c << "\n";
    return 0;
}
