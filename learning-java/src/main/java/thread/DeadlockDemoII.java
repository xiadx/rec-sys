package thread;

public class DeadlockDemoII {

    public static void main(String[] args) {
        MarkupI g1 = new MarkupI(0, "g1");
        MarkupI g2 = new MarkupI(1, "g2");
        g1.start();
        g2.start();
    }

}

class LipstickI {

}

class MirrorI {

}

class MarkupI extends Thread {

    static LipstickI lipstick = new LipstickI();
    static MirrorI mirror = new MirrorI();

    int choice;
    String girl;

    public MarkupI(int choice, String girl) {
        this.choice = choice;
        this.girl = girl;
    }

    @Override
    public void run() {
        markup();
    }

    private void markup() {
        if (choice == 0) {
            synchronized (lipstick) {
                System.out.println(this.girl + " --> lipstick");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                synchronized (mirror) {
//                    System.out.println(this.girl + " --> mirror");
//                }
            }
            synchronized (mirror) {
                System.out.println(this.girl + " --> mirror");
            }
        } else {
            synchronized (mirror) {
                System.out.println(this.girl + " --> mirror");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//                synchronized (lipstick) {
//                    System.out.println(this.girl + " --> lipstick");
//                }
            }
            synchronized (lipstick) {
                System.out.println(this.girl + " --> lipstick");
            }
        }
    }

}
