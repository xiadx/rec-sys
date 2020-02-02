package thread;

public class SynchronizedDemoI implements Runnable {

    private int ticketNums = 10;
    private boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            buyTicket();
        }
    }

    public synchronized void buyTicket() {
        if (ticketNums <= 0) {
            flag = false;
            return;
        }
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " --> " + ticketNums--);
    }

    public static void main(String[] args) {
        new Thread(new SynchronizedDemoI()).start();
    }

}
