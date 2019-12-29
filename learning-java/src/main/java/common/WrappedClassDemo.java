package common;

public class WrappedClassDemo {

    public static void main(String[] args) {
        Integer a = new Integer(3);
        Integer b = Integer.valueOf(3);
        Integer c = 3;
        System.out.println(a == b);
        System.out.println(b == c);
        System.out.println(a.intValue());
        System.out.println(a.doubleValue());

        Integer e = new Integer("123");
        Integer f = Integer.parseInt("123");
        System.out.println(e);
        System.out.println(f.toString());

        System.out.println(Integer.MAX_VALUE);
        System.out.println(Integer.MIN_VALUE);
        System.out.println(Float.MAX_VALUE);
        System.out.println(Float.MIN_VALUE);

        Integer g = 234;
        Integer h = Integer.valueOf(234);
        System.out.println(g == h);
        System.out.println(g.equals(h));
        Integer i = -128;
        Integer j = Integer.valueOf(-128);
        System.out.println(i == j);
        System.out.println(i.equals(j));
    }

}
