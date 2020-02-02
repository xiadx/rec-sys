package thread;

public class JoinDemoII {

    public static void main(String[] args) {
        new Thread(new Father()).start();
    }

}

class Father implements Runnable {

    @Override
    public void run() {
        System.out.println("father thread begin");
        Thread t = new Thread(new Son());
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("father thread end");
    }

}

class Son implements Runnable {

    @Override
    public void run() {
        System.out.println("son thread begin");
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("son --> " + i);
        }
        System.out.println("son thread end");
    }

}
