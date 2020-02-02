package thread;

public class VolatileDemo {

    private volatile static int n = 0;

    public static void main(String[] args) {
        new Thread(() -> {
            while (n == 0) {

            }
        }).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        n = 1;
    }

}
