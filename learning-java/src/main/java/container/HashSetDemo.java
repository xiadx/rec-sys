package container;

import java.util.Set;
import java.util.HashSet;

public class HashSetDemo {

    public static void main(String[] args) {
        Set<String> s1 = new HashSet<String>();
        s1.add("a");
        s1.add("b");
        s1.add("c");
        System.out.println(s1);
        s1.remove("b");
        System.out.println(s1);

        Set<String> s2 = new HashSet<String>();
        s2.add("d");
        s2.addAll(s1);
        System.out.println(s2);
    }

}
