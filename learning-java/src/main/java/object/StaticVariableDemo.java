package object;

public class StaticVariableDemo {

    int id;
    String name;

    static String company = "google";

    public StaticVariableDemo(int id, String name) {
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
        StaticVariableDemo svd = new StaticVariableDemo(1, "abc");
        svd.printName();
        svd.printCompany();
        StaticVariableDemo.printCompany();
    }

}