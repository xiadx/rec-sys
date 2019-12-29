package container;

import object.StaticInitialDemo;

import java.util.Map;
import java.util.TreeMap;

public class TreeMapDemo {

    public static void main(String[] args) {
        Map<Integer, String> m1 = new TreeMap<Integer, String>();
        m1.put(6, "a");
        m1.put(1, "b");
        m1.put(3, "c");
        System.out.println(m1);
        for (Integer k : m1.keySet()) {
            System.out.println(k + "->" + m1.get(k));
        }

        Staff s1 = new Staff(1, "a", 10000);
        Staff s2 = new Staff(1, "b", 20000);
        Staff s3 = new Staff(2, "c", 20000);
        Staff s4 = new Staff(2, "d", 20000);
        Map<Staff, String> m2 = new TreeMap<Staff, String>();
        m2.put(s1, "a");
        m2.put(s2, "b");
        m2.put(s3, "c");
        m2.put(s4, "d");
        for (Staff s : m2.keySet()) {
            System.out.println(s + "=" + m2.get(s));
        }
    }

}

class Staff implements Comparable<Staff> {

    int id;
    String name;
    double salary;

    public Staff() {

    }

    public Staff(int id, String name, double salary) {
        super();
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Staff{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }

    public int compareTo(Staff o) {
        if (this.salary > o.salary) {
            return 1;
        } else if (this.salary < o.salary) {
            return -1;
        } else {
            if (this.id > o.id) {
                return 1;
            } else if (this.id < o.id) {
                return -1;
            } else {
                return 0;
            }
        }
    }

}
