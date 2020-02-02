package thread;

import java.util.List;
import java.util.ArrayList;

public class UnsafeDemoIII {

    public static void main(String[] args) {
        List<String> l = new ArrayList<String>();
        for (int i = 0; i < 1000; i++) {
            new Thread(() -> {
                l.add(Thread.currentThread().getName());
            }).start();
        }
        System.out.println(l.size());
    }

}
