#!/usr/bin/env python
# -*- coding: utf-8 -*-


"""在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。"""


class Solution:

    def Find(self, target, array):
        if len(array) != 0:
            row = 0
            col = len(array[0]) - 1
            while row < len(array) and col >= 0:
                if array[row][col] == target:
                    return True
                elif array[row][col] > target:
                    col -= 1
                else:
                    row += 1
        return False


def main():
    array = [
        [1, 2, 3, 4],
        [2, 3, 4, 5],
        [3, 4, 5, 6],
        [6, 7, 8, 9]
    ]
    s = Solution()
    print s.Find(8, array)


if __name__ == "__main__":
    main()
