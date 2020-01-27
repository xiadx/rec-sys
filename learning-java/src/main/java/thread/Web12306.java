package thread;

public class Web12306 implements Runnable {

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
        Web12306 web = new Web12306();
        System.out.println(Thread.currentThread().getName());
        new Thread(web, "a").start();
        new Thread(web, "b").start();
        new Thread(web, "c").start();
    }

}
