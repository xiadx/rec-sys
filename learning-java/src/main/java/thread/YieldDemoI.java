package thread;

public class YieldDemoI implements Runnable {

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " --> start");
        Thread.yield();
        System.out.println(Thread.currentThread().getName() + " --> end");
    }

    public static void main(String[] args) {
        YieldDemoI yd = new YieldDemoI();
        new Thread(yd, "a").start();
        new Thread(yd, "b").start();
    }

}
