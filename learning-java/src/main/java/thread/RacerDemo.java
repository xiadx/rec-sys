package thread;

public class RacerDemo implements Runnable {

    private String winner;

    public void run() {
        for (int s = 1; s <= 100; s++) {
            if (Thread.currentThread().getName().equals("rabbit") && s % 10 == 0) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println(Thread.currentThread().getName() + " --> " + s);
            boolean f = over(s);
            if (f) {
                break;
            }
        }
    }

    private boolean over(int s) {
        if (winner != null) {
            return true;
        } else {
            if (s == 100) {
                winner = Thread.currentThread().getName();
                System.out.println("winner --> " + winner);
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        RacerDemo racer = new RacerDemo();
        new Thread(racer, "tortoise").start();
        new Thread(racer, "rabbit").start();
    }

}
