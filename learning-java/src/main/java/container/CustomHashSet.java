package container;

import java.util.HashMap;

public class CustomHashSet {

    HashMap map;

    private static final Object PRESENT = new Object();

    public CustomHashSet() {
        map = new HashMap();
    }

    public int size() {
        return map.size();
    }

    public void add(Object o) {
        map.put(o, PRESENT);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for (Object key : map.keySet()) {
            sb.append(key + ",");
        }
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }

    public static void main(String[] args) {
        CustomHashSet s = new CustomHashSet();
        s.add("a");
        s.add("b");
        s.add("c");
        System.out.println(s);
    }

}
