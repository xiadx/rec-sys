package common;

public class StringBuilderDemo {

    public static void main(String[] args) {
        StringBuilder sb1 = new StringBuilder("abcdefg");
        System.out.println(sb1.hashCode());
        System.out.println(Integer.toHexString(sb1.hashCode()));
        System.out.println(sb1);
        sb1.setCharAt(2, 'M');
        System.out.println(sb1);

        StringBuilder sb2 = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            char t = (char)('a' + i);
            sb2.append(t);
        }
        System.out.println(sb2);
        sb2.reverse();
        System.out.println(sb2);
        sb2.insert(0, 'A').insert(6, 'B').insert(10, 'C');
        System.out.println(sb2);
        sb2.delete(20, 23);
        System.out.println(sb2);
        System.out.println(sb2.indexOf("zyx"));

        String s = "";
        long n1 = Runtime.getRuntime().freeMemory();
        long t1 = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            s = s + i;
        }
        long n2 = Runtime.getRuntime().freeMemory();
        long t2 = System.currentTimeMillis();
        System.out.println("n1 - n2 " + (n1 - n2));
        System.out.println("t2 - t1 " + (t2 - t1));

        StringBuilder sb3 = new StringBuilder("");
        long n3 = Runtime.getRuntime().freeMemory();
        long t3 = System.currentTimeMillis();
        for (int i = 0; i < 50000; i++) {
            sb3.append(i);
        }
        long n4 = Runtime.getRuntime().freeMemory();
        long t4 = System.currentTimeMillis();
        System.out.println("n3 - n4 " + (n3 - n4));
        System.out.println("t4 - t3 " + (t4 - t3));
    }

}
