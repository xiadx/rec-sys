package container;

import java.util.List;
import java.util.Vector;

public class VectorDemo {

    public static void main(String[] args) {
        List<String> v = new Vector<String>();
        for (int i = 0; i < 20; i++) {
            v.add("" + i);
        }
        System.out.println(v);
    }

}
