// map example

#include <iostream>
#include <map>
#include <string>
using namespace std;

class CStudent {
public:
    struct CInfo {
        int id;
        string name;
    };
    int score;
    CInfo info;
};

typedef multimap<int, CStudent::CInfo> MAP_STD;

int main() {
    MAP_STD mp;
    CStudent st;
    string cmd;
    while (cin >> cmd) {
        if (cmd == "add") {
            cin >> st.info.name >> st.info.id >> st.score;
            mp.insert(MAP_STD::value_type(st.score, st.info));
        } else if (cmd == "query") {
            int score;
            cin >> score;
            MAP_STD::iterator p = mp.lower_bound(score);
            if (p != mp.begin()) {
                --p;
                score = p->first;
                MAP_STD::iterator maxp = p;
                int maxId = p->second.id;
                for (; p != mp.begin() && p->first == score; --p) {
                    if (p->second.id > maxId) {
                        maxp = p;
                        maxId = p->second.id;
                    }
                }
                if (p->first == score) {
                    if (p->second.id > maxId) {
                        maxp = p;
                        maxId = p->second.id;
                    }
                }
                cout << maxp->second.name << " " << maxp->second.id << " " << maxp->first << endl;
            } else {
                cout << "nobody" << endl;
            }
        }
    }
    return 0;
}
