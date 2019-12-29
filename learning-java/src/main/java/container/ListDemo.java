package container;

import java.util.Collection;
import java.util.ArrayList;
import java.util.List;
import java.util.ArrayList;

public class ListDemo {

    public static void main(String[] args) {
        Collection<String> c = new ArrayList<String>();
        System.out.println(c.size());
        System.out.println(c.isEmpty());
        c.add("a");
        c.add("b");
        System.out.println(c);
        System.out.println(c.size());
        System.out.println(c.contains("a"));
        Object[] objs = c.toArray();
        System.out.println(objs);
        c.remove("a");
        System.out.println(c);
        c.clear();
        System.out.println(c.size());

        List<String> l1 = new ArrayList<String>();
        l1.add("a");
        l1.add("b");
        l1.add("c");
        List<String> l2 = new ArrayList<String>();
        l2.add("a");
        l2.add("d");
        l2.add("e");
        System.out.println(l1);
        System.out.println(l1.addAll(l2));
        System.out.println(l1);
        System.out.println(l1.removeAll(l2));
        System.out.println(l1);
        System.out.println(l1.retainAll(l2));
        System.out.println(l1);
        System.out.println(l1.containsAll(l2));

        List<String> l3 = new ArrayList<String>();
        l3.add("a");
        l3.add("b");
        l3.add("c");
        l3.add("d");
        System.out.println(l3);
        l3.add(2, "a");
        System.out.println(l3);
        l3.remove(2);
        System.out.println(l3);
        l3.set(2, "a");
        System.out.println(l3);
        System.out.println(l3.get(2));
        System.out.println(l3.indexOf("b"));
        System.out.println(l3.lastIndexOf("b"));
    }

}
