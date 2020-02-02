package thread;

public class ThreadLocalDemoI {

    private static ThreadLocal<Integer> tl = new ThreadLocal<Integer>() {
        @Override
        protected Integer initialValue() {
            return 200;
        }
    };

    public static class TLRunnable implements Runnable {
        @Override
        public void run() {
            tl.set((int)(Math.random() * 99));
            System.out.println(Thread.currentThread().getName() + " --> " + tl.get());
        }
    }

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().getName() + " --> " + tl.get());
        tl.set(99);
        System.out.println(Thread.currentThread().getName() + " --> " + tl.get());
        new Thread(new TLRunnable(), "a").start();
        new Thread(new TLRunnable(), "b").start();
    }

}
