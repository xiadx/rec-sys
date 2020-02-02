package thread;

public class DeadlockDemoI {

    public static void main(String[] args) {
        Markup g1 = new Markup(0, "g1");
        Markup g2 = new Markup(1, "g2");
        g1.start();
        g2.start();
    }

}

class Lipstick {

}

class Mirror {

}

class Markup extends Thread {

    static Lipstick lipstick = new Lipstick();
    static Mirror mirror = new Mirror();

    int choice;
    String girl;

    public Markup(int choice, String girl) {
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
                synchronized (mirror) {
                    System.out.println(this.girl + " --> mirror");
                }
            }
        } else {
            synchronized (mirror) {
                System.out.println(this.girl + " --> mirror");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (lipstick) {
                    System.out.println(this.girl + " --> lipstick");
                }
            }
        }
    }

}
