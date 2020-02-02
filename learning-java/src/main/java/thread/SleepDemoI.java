package thread;

public class SleepDemoI implements Runnable {

    private int ticketNums = 99;

    @Override
    public void run() {
        while (true) {
            if (ticketNums < 0) {
                break;
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " --> " + ticketNums--);
        }
    }

    public static void main(String[] args) {
        SleepDemoI sd = new SleepDemoI();
        System.out.println(Thread.currentThread().getName());
        new Thread(sd, "d").start();
        new Thread(sd, "e").start();
        new Thread(sd, "f").start();
    }

}
