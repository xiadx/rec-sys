package thread;

public class SynchronizedDemoV implements Runnable {

    private int ticketNums = 10;
    private boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            buyTicketA();
//            buyTicketB();
        }
    }

    public void buyTicketA() {
        if (ticketNums <= 0) {
            flag = false;
            return;
        }
        synchronized (this) {
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
    }

    public synchronized void buyTicketB() {
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
        SynchronizedDemoV sd = new SynchronizedDemoV();
        new Thread(sd, "a").start();
        new Thread(sd, "b").start();
        new Thread(sd, "c").start();
    }

}
