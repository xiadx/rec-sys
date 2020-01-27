package thread;

public class StartThreadDemo extends Thread {

    @Override
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
        StartThreadDemo t = new StartThreadDemo();
        t.start();
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
