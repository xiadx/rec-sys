package container;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;

public class CollectionsDemo {

    public static void main(String[] args) {
        List<String> l = new ArrayList<String>();
        for (int i = 0; i < 10; i++) {
            l.add("" + i);
        }
        System.out.println(l);

        Collections.shuffle(l);
        System.out.println(l);

        Collections.reverse(l);
        System.out.println(l);

        Collections.sort(l);
        System.out.println(l);

        System.out.println(Collections.binarySearch(l, "5"));
    }

}
