package object;

public class ParameterPassingDemo {

    int id;
    String name;

    public ParameterPassingDemo(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void parameterPassing(ParameterPassingDemo u) {
        u.name = "def";
    }

    public void print() {
        System.out.println("id->" + id + ",name->" + name);
    }

    public static void main(String[] args) {
        ParameterPassingDemo ppd = new ParameterPassingDemo(1, "abc");
        ppd.print();
        ppd.parameterPassing(ppd);
        ppd.print();
    }

}