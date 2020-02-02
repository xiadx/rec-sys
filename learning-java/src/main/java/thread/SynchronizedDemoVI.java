package thread;

import java.util.concurrent.CopyOnWriteArrayList;

public class SynchronizedDemoVI {

    public static void main(String[] args) throws InterruptedException {
        CopyOnWriteArrayList<String> l = new CopyOnWriteArrayList<String>();
        for (int i = 0; i < 50000; i++) {
            new Thread(() -> {
                l.add(Thread.currentThread().getName());
            }).start();
        }
        Thread.sleep(10000);
        System.out.println(l.size());
    }

}
