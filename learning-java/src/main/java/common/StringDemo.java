package common;

public class StringDemo {

    public static void main(String[] args) {
        String s1 = "abcdef";
        System.out.println(s1);
        System.out.println(s1.substring(2, 5));

        String s2 = "hello" + " java";
        String s3 = "hello java";
        System.out.println(s2 == s3);
        String s4 = "hello";
        String s5 = " java";
        String s6 = s4 + s5;
        System.out.println(s2 == s6);
        System.out.println(s2.equals(s6));
    }

}
