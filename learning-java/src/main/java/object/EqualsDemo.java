package object;

public class EqualsDemo {

    public static void main(String[] args) {
        Object o1 = new Object();
        Object o2 = new Object();

        System.out.println(o1 == o1);
        System.out.println(o1 == o2);
        System.out.println(o1.equals(o2));

        User u1 = new User(1, "abc");
        User u2 = new User(1, "def");

        System.out.println(u1 == u2);
        System.out.println(u1.equals(u2));

        String s1 = new String("abc");
        String s2 = new String("abc");

        System.out.println(s1 == s2);
        System.out.println(s1.equals(s2));
    }

}

class User {

    int id;
    String name;

    public User(int id, String name) {
        super();
        this.id = id;
        this.name = name;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        User other = (User)obj;
        if (id != other.id) {
            return false;
        }
        return true;
    }

}