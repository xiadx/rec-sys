package offer;

/**
 * 在一个二维数组中（每个一维数组的长度相同），每一行都按照从左到右递增的顺序排序，每一列都按照从上到下递增的顺序排序。请完成一个函数，输入这样的一个二维数组和一个整数，判断数组中是否含有该整数。
 */
public class Find {

    public boolean find(int target, int[][] array) {
        if (array.length != 0) {
            int row = 0;
            int col = array[0].length - 1;
            while (row < array.length && col >= 0) {
                if (array[row][col] == target) {
                    return true;
                } else if (array[row][col] > target) {
                    --col;
                } else {
                    ++row;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] array = {
                {1, 2, 3, 4},
                {2, 3, 4, 5},
                {3, 4, 5, 6},
                {6, 7, 8, 9}
        };
        Find f = new Find();
        System.out.println(f.find(8, array));
    }

}
