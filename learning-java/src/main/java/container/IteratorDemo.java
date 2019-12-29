package container;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.HashSet;
import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

public class IteratorDemo {

    public static void main(String[] args) {
        List<String> l = new ArrayList<String>();
        l.add("a");
        l.add("b");
        l.add("c");
        for (Iterator<String> iter = l.iterator(); iter.hasNext(); ) {
            String t = iter.next();
            System.out.println(t);
        }

        Set<String> s = new HashSet<String>();
        s.add("a");
        s.add("b");
        s.add("c");
        for (Iterator<String> iter = s.iterator(); iter.hasNext(); ) {
            String t = iter.next();
            System.out.println(t);
        }

        Map<Integer, String> m = new HashMap<Integer, String>();
        m.put(1, "a");
        m.put(2, "b");
        m.put(3, "c");
        Set<Entry<Integer, String>> se = m.entrySet();
        for (Iterator<Entry<Integer, String>> iter = se.iterator(); iter.hasNext(); ) {
            Entry<Integer, String> t = iter.next();
            System.out.println(t.getKey() + "->" + t.getValue());
        }
        Set<Integer> ks = m.keySet();
        for (Iterator<Integer> iter = ks.iterator(); iter.hasNext(); ) {
            Integer k = iter.next();
            System.out.println(k + "->" + m.get(k));
        }

        List<String> al = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            al.add("" + i);
        }
        System.out.println(al);
        for (int i = 0; i < al.size(); i++) {
            String t = al.get(i);
            if (t.endsWith("2")) {
                al.remove(i);
            }
            System.out.println(al.size());
            System.out.println(al.get(i));
        }
    }

}
