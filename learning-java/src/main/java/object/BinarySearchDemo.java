package object;

import java.util.Arrays;

public class BinarySearchDemo {

    public static void main(String[] args) {
        int[] a = {30, 20, 50, 10, 80, 9, 7, 12, 100, 40, 8};
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.binarySearch(a, 12));
        System.out.println(binarySearch(a, 12));
    }

    public static int binarySearch(int[] a, int value) {
        int low = 0;
        int high = a.length - 1;
        while (low <= high) {
            int mid = (low + high) / 2;
            if (value == a[mid]) {
                return mid;
            }
            if (value > a[mid]) {
                low = mid + 1;
            }
            if (value < a[mid]) {
                high = mid - 1;
            }
        }
        return -1;
    }

}
