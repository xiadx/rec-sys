package thread;

public class UnsafeDemoI implements Runnable {

    private int ticketNums = 10;
    private boolean flag = true;

    @Override
    public void run() {
        while (flag) {
            if (ticketNums < 0) {
                flag = false;
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
        UnsafeDemoI ud = new UnsafeDemoI();
        new Thread(ud, "a").start();
        new Thread(ud, "b").start();
        new Thread(ud, "c").start();
    }

}
