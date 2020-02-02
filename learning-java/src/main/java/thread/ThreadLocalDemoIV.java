package thread;

public class ThreadLocalDemoIV {

    private static ThreadLocal<Integer> tl = new InheritableThreadLocal<>();

    public static void main(String[] args) {
        tl.set(2);
        System.out.println(Thread.currentThread().getName() + " --> " + tl.get());
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " --> " + tl.get());
            tl.set(200);
            System.out.println(Thread.currentThread().getName() + " --> " + tl.get());
        }).start();
    }

}
