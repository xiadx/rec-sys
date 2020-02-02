package thread;

import java.util.List;
import java.util.ArrayList;

public class SynchronizedDemoIV {

    public static void main(String[] args) {
        List<String> l = new ArrayList<String>();
        for (int i = 0; i < 50000; i++) {
            new Thread(() -> {
                synchronized (l) {
                    l.add(Thread.currentThread().getName());
                }
            }).start();
        }
        System.out.println(l.size());
    }

}
