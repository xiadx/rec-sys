package object;

public class ObjectDemo {

    public static void main(String[] args) {
        ObjectDemo od = new ObjectDemo();
        System.out.println(od.toString());

        Employee e = new Employee("abc", 6);
        System.out.println(e.toString());
    }

}

class Employee {

    String name;
    int age;

    Employee(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "name->" + name + ",age->" + age;
    }

}