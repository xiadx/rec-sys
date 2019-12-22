package object;

public class StringDemo {

    public static void main(String[] args) {
        String s1 = "abc";
        System.out.println(s1);
        String s2 = new String("def");
        System.out.println(s2);
        String s3 = "abc" + "def";
        System.out.println(s3);
        String s4 = "18" + 19;
        System.out.println(s4);

        String s5 = "cat";
        String s6 = "cat";
        String s7 = new String("cat");
        System.out.println(s5 == s6);
        System.out.println(s5 == s7);
        System.out.println(s5.equals(s7));

        String s8 = "java";
        String s9 = "Java";
        System.out.println(s8.charAt(0));
        System.out.println(s8.length());
        System.out.println(s8.equals(s9));
        System.out.println(s8.equalsIgnoreCase(s9));
        System.out.println(s8.indexOf("a"));
        System.out.println(s8.indexOf("b"));

        String s10 = s8.replace("j", "N");
        System.out.println(s10);

        String s11 = "How are you?";
        System.out.println(s11.startsWith("How"));
        System.out.println(s11.endsWith("you"));
        System.out.println(s11.substring(4));
        System.out.println(s11.substring(4, 7));

        System.out.println(s11.toLowerCase());
        System.out.println(s11.toUpperCase());

        String s12 = "  How old are you!  ";
        System.out.println(s12.trim());
        System.out.println(s12);
    }

}
