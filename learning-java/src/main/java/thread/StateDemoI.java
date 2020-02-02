package thread;

import java.lang.Thread.State;

public class StateDemoI {

    public static void main(String[] args) {
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("lambda --> " + i);
            }
        });
        System.out.println(t.getState());
        t.start();
        System.out.println(t.getState());
        State s = t.getState();
        while (s != State.TERMINATED) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            s = t.getState();
            System.out.println(s);
        }
    }

}
