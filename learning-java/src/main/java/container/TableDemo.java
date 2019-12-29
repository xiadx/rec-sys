package container;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;

public class TableDemo {

    public static void main(String[] args) {
        Map<String, Object> r1 = new HashMap<String, Object>();
        r1.put("id", 1);
        r1.put("name", "a");
        r1.put("salary", 10000);
        Map<String, Object> r2 = new HashMap<String, Object>();
        r2.put("id", 2);
        r2.put("name", "b");
        r2.put("salary", 20000);
        Map<String, Object> r3 = new HashMap<String, Object>();
        r3.put("id", 3);
        r3.put("name", "c");
        r3.put("salary", 30000);

        List<Map<String, Object>> t = new ArrayList<Map<String, Object>>();
        t.add(r1);
        t.add(r2);
        t.add(r3);

        for (Map<String, Object> r : t) {
            Set<String> ks = r.keySet();
            for (String k : ks) {
                System.out.print(k + "->" + r.get(k) + ",");
            }
            System.out.println();
        }

        User u1 = new User(1, "a", 10000);
        User u2 = new User(2, "b", 20000);
        User u3 = new User(3, "c", 30000);
        List<User> l = new ArrayList<User>();
        l.add(u1);
        l.add(u2);
        l.add(u3);
        for (User u : l) {
            System.out.println(u);
        }
        Map<Integer, User> m = new HashMap<Integer, User>();
        m.put(u1.getId(), u1);
        m.put(u2.getId(), u2);
        m.put(u3.getId(), u3);
        Set<Integer> ks = m.keySet();
        for (Integer k : ks) {
            System.out.println(k + "=" + m.get(k));
        }
    }

}

class User {

    private int id;
    private String name;
    private double salary;

    public User() {

    }

    public User(int id, String name, double salary) {
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
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", salary=" + salary +
                '}';
    }
}
