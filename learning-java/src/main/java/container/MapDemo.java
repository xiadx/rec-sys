package container;

import java.util.Map;
import java.util.HashMap;

public class MapDemo {

    public static void main(String[] args) {
        Map<Integer, String> m1 = new HashMap<Integer, String>();
        m1.put(1, "a");
        m1.put(2, "b");
        m1.put(3, "c");
        System.out.println(m1.get(1));
        System.out.println(m1.size());
        System.out.println(m1.isEmpty());
        System.out.println(m1.containsKey(2));
        System.out.println(m1.containsValue("a"));

        Map<Integer, String> m2 = new HashMap<Integer, String>();
        m2.put(4, "d");
        m2.put(5, "e");
        m1.putAll(m2);
        System.out.println(m1);
        m1.put(3, "f");
        System.out.println(m1);

        Employee e1 = new Employee(1, "a", 10000);
        Employee e2 = new Employee(2, "b", 20000);
        Employee e3 = new Employee(3, "c", 30000);
        Map<Integer, Employee> m3 = new HashMap<Integer, Employee>();
        m3.put(e1.getId(), e1);
        m3.put(e2.getId(), e2);
        m3.put(e3.getId(), e3);
        System.out.println(m3);

    }

}

class Employee {

    private int id;
    private String name;
    private double salary;

    public Employee(int id, String name, double salary) {
        super();
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
