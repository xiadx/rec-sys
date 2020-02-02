package thread;

public class DaemonDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(new God());
        Thread t2 = new Thread(new Me());
        t1.setDaemon(true);
        t1.start();
        t2.start();
    }

}

class God implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("god --> " + i);
        }
    }

}

class Me implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("me --> " + i);
        }
    }

}
