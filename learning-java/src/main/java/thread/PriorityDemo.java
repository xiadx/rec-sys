package thread;

public class PriorityDemo implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(Thread.currentThread().getName() + " --> " + i);
        }
    }

    public static void main(String[] args) {
        System.out.println("priority --> " + Thread.currentThread().getPriority());
        Thread t1 = new Thread(new PriorityDemo(), "a");
        Thread t2 = new Thread(new PriorityDemo(), "b");
        Thread t3 = new Thread(new PriorityDemo(), "c");
        t1.setPriority(Thread.MIN_PRIORITY);
        t2.setPriority(Thread.NORM_PRIORITY);
        t3.setPriority(Thread.MAX_PRIORITY);
        t1.start();
        t2.start();
        t3.start();
    }

}
