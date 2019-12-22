package object;

public class ArrayDemo {

    public static void main(String[] args) {
        int[] a = new int[10];
        String[] s = new String[5];
        System.out.println(a);
        System.out.println(s);
        a[0] = 13;
        a[1] = 15;
        a[2] = 20;
        for (int i = 0; i < a.length; i++) {
            a[i] = 10 * i;
        }
        for (int i = 0; i < a.length; i++) {
            System.out.println(a[i]);
        }
        User[] u = new User[3];
        u[0] = new User(1, "abc");
        u[1] = new User(2, "def");
        u[2] = new User(3, "ghi");
        for (int i = 0; i < u.length; i++) {
            System.out.println("id->" + u[i].id + ",name->" + u[i].name);
        }
        int[] b = {2, 4, 65};
        User[] c = {
                new User(1, "abc"),
                new User(2, "def")};
        for (int i = 0; i < b.length; i++) {
            System.out.println(b[i]);
        }
        for (int i = 0; i < c.length; i++) {
            System.out.println("id->" + c[i].id + ",name->" + c[i].name);
        }
        for (int m : b) {
            System.out.println(m);
        }
    }

}