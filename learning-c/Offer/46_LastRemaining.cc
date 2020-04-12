// 每年六一儿童节,牛客都会准备一些小礼物去看望孤儿院的小朋友,今年亦是如此。HF作为牛客的资深元老,自然也准备了一些小游戏。其中,有个游戏是这样的:首先,让小朋友们围成一个大圈。然后,他随机指定一个数m,让编号为0的小朋友开始报数。每次喊到m-1的那个小朋友要出列唱首歌,然后可以在礼品箱中任意的挑选礼物,并且不再回到圈中,从他的下一个小朋友开始,继续0...m-1报数....这样下去....直到剩下最后一个小朋友,可以不用表演,并且拿到牛客名贵的“名侦探柯南”典藏版(名额有限哦!!^_^)。请你试着想下,哪个小朋友会得到这份礼品呢？(注：小朋友的编号是从0到n-1)

#include <iostream>
using namespace std;

struct ListNode {
    int val;
    struct ListNode *next;
    ListNode(int x) : val(x), next(NULL) {}
};

class Solution {
public:
    int LastRemaining(int n, int m) {
        if (n <= 0) {
            return -1;
        }
        ListNode *h = new ListNode(0);
        ListNode *p = h;
        for (int i = 1; i < n; ++i) {
            p->next = new ListNode(i);
            p = p->next;
        }
        p->next = h;
        while (h->next != h) {
            for (int i = 1; i < m; ++i) {
                h = h->next;
                p = p->next;
            }
            h = h->next;
            p->next = h;
        }
        return h->val;
    }
};

int main() {
    Solution s;
    cout << s.LastRemaining(3, 2) << endl;
    return 0;
}
