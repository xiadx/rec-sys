package thread;

public class ThreadLocalDemoII {

    private static ThreadLocal<Integer> tl = ThreadLocal.withInitial(() -> 1);

    public static class TLRunnable implements Runnable {
        @Override
        public void run() {
            Integer l = tl.get();
            System.out.println(Thread.currentThread().getName() + " --> " + l);
            tl.set(l - 1);
            System.out.println(Thread.currentThread().getName() + " --> " + tl.get());
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(new TLRunnable()).start();
        }
    }

}
