package thread;

public class StateDemoII {

    public static void main(String[] args) {
        int activeCount = Thread.activeCount();
        System.out.println("active thread count --> " + activeCount);
        Thread t = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("lambda --> " + i);
            }
        });
        System.out.println(t.getState());
        t.start();
        System.out.println(t.getState());
        while (true) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(t.getState());
            int n = Thread.activeCount();
            if (n == activeCount) {
                break;
            }
        }
    }

}
