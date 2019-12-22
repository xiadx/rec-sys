package object;

public class UserDemo {

    int id;
    String name;
    String pwd;

    public UserDemo() {

    }

    public UserDemo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserDemo(int id, String name, String pwd) {
        this.id = id;
        this.name = name;
        this.pwd = pwd;
    }

    public static void main(String[] args) {
        UserDemo ud1 = new UserDemo();
        UserDemo ud2 = new UserDemo(101, "abc");
        UserDemo ud3 = new UserDemo(102, "def", "123");
        System.out.println("ud1: id->" + ud1.id + ",name->" + ud1.name + ",pwd->" + ud1.pwd);
        System.out.println("ud2: id->" + ud2.id + ",name->" + ud2.name + ",pwd->" + ud2.pwd);
        System.out.println("ud3: id->" + ud3.id + ",name->" + ud3.name + ",pwd->" + ud3.pwd);
    }

}