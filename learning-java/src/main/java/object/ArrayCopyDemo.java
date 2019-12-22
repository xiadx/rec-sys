package object;

import java.util.Arrays;

public class ArrayCopyDemo {

    public static void main(String[] args) {
        String[] s1 = {"a", "b", "c", "d", "e", "f"};
        String[] s2 = new String[10];
        System.arraycopy(s1, 2, s2, 6, 3);
        for (int i = 0; i < s2.length; i++) {
            System.out.println(i + "->" + s2[i]);
        }

        System.arraycopy(s1, 3, s1, 3 - 1, s1.length - 3);
        s1[s1.length - 1] = null;
        for (int i = 0; i < s1.length; i++) {
            System.out.println(i + "->" + s1[i]);
        }

        String[] s3 = {"a", "b", "c", "d", "e", "f"};
        String[] s4 = remove(s3, 3);
        for (int i = 0; i < s4.length; i++) {
            System.out.println(i + "->" + s4[i]);
        }

        int[] a = {100, 20, 30, 5, 150, 80, 200};
        System.out.println(a);
        System.out.println(Arrays.toString(a));
        Arrays.sort(a);
        System.out.println(Arrays.toString(a));
        System.out.println(Arrays.binarySearch(a, 30));

        Object[] o1 = {1001, "abc", 18, "2006.6.6"};
        Object[] o2 = {1002, "def", 19, "2016.6.6"};
        Object[] o3 = {1003, "ghi", 20, "2026.6.6"};

        Object[][] t = new Object[3][];
        t[0] = o1;
        t[1] = o2;
        t[2] = o3;
        for (Object[] o : t) {
            System.out.println(Arrays.toString(o));
        }
    }

    public static String[] remove(String[] s, int index) {
        if (index < 0) {
            index = 0;
        }
        if (index > s.length - 1) {
            index = s.length - 1;
        }
        System.arraycopy(s, index + 1, s, index, s.length - index - 1);
        s[s.length - 1] = null;
        return s;
    }

}
