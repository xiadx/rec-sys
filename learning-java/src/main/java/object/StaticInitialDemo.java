package object;

public class StaticInitialDemo {

    int id;
    String name;
    static String company;

    static {
        System.out.println("static initial");
        company = "google";
        printCompany();
    }

    public StaticInitialDemo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void printName() {
        System.out.println("id->" + id + ",name->" + name);
    }

    public static void printCompany() {
        System.out.println(company);
    }

    public static void main(String[] args) {
        StaticInitialDemo sid = new StaticInitialDemo(1, "abc");
        sid.printName();
    }
}
