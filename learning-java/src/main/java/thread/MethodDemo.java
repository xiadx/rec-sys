package thread;

public class MethodDemo implements Runnable {

    private String name;

    public MethodDemo(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " --> " + name);
    }

    public static void main(String[] args) {
        System.out.println(Thread.currentThread().isAlive());
        Thread t = new Thread(new MethodDemo("a"), "x");
        t.setName("y");
        t.start();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(t.isAlive());
    }

}
