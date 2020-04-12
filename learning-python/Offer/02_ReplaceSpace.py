#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""请实现一个函数，将一个字符串中的每个空格替换成“%20”。例如，当字符串为We Are Happy.则经过替换之后的字符串为We%20Are%20Happy。"""


class Solution:

    def replaceSpace(self, s):
        l = []
        for c in s:
            if c == ' ':
                l.append('%20')
            else:
                l.append(c)
        return ''.join(l)


def main():
    str = "We Are Happy"
    s = Solution()
    print s.replaceSpace(str)


if __name__ == "__main__":
    main()
