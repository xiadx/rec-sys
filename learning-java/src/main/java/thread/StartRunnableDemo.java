package thread;

public class StartRunnableDemo implements Runnable {

    public void run() {
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("song");
        }
    }

    public static void main(String[] args) {
        new Thread(new StartRunnableDemo()).start();
        for (int i = 0; i < 20; i++) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("code");
        }
    }

}
