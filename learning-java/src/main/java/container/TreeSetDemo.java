package container;

import java.util.Set;
import java.util.TreeSet;

public class TreeSetDemo {

    public static void main(String[] args) {
        Set<Integer> s = new TreeSet<Integer>();
        s.add(3);
        s.add(1);
        s.add(2);
        s.add(6);
        System.out.println(s);

        Emp e1 = new Emp(1, "a", 10000);
        Emp e2 = new Emp(1, "b", 20000);
        Emp e3 = new Emp(2, "c", 20000);
        Emp e4 = new Emp(2, "d", 20000);
        Set<Emp> se = new TreeSet<Emp>();
        se.add(e1);
        se.add(e2);
        se.add(e3);
        se.add(e4);
        for (Emp e : se) {
            System.out.println(e);
        }
    }

}

class Emp implements Comparable<Emp> {

    int id;
    String name;
    double salary;

    public Emp() {

    }

    public Emp(int id, String name, double salary) {
        super();
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "Emp{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }

    public int compareTo(Emp o) {
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
