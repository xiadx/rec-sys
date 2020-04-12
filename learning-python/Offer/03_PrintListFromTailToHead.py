#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""输入一个链表，按链表从尾到头的顺序返回一个ArrayList。"""


class ListNode:

    def __init__(self, x):
        self.val = x
        self.next = None


class Solution:

    def printListFromTailToHead(self, listNode):
        l = []
        p = listNode
        while p:
            l.insert(0, p.val)
            p = p.next
        return l


def main():
    h = ListNode(1)
    h.next = ListNode(2)
    h.next.next = ListNode(3)
    s = Solution()
    print s.printListFromTailToHead(h)


if __name__ == "__main__":
    main()
