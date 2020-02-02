package thread;

public class ThreadLocalDemoIII {

    private static ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> 1);

    public static class TLRunnable implements Runnable {
        public TLRunnable() {
            tl.set(-100);
            System.out.println(Thread.currentThread().getName() + " --> " + tl.get());
        }

        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " --> " + tl.get());
        }
    }

    public static void main(String[] args) {
        new Thread(new TLRunnable(), "a").start();
        new Thread(new TLRunnable(), "b").start();
    }

}
