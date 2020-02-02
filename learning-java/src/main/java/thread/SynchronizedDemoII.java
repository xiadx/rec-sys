package thread;

public class SynchronizedDemoII {

    public static void main(String[] args) {
        Account account = new Account(100, "assets");
        SynchronizedDrawing you = new SynchronizedDrawing(account, 80, "you");
        SynchronizedDrawing wife = new SynchronizedDrawing(account, 90, "wife");
        you.start();
        wife.start();
    }

}

class SynchronizedDrawing extends Thread {

    Account account;
    int drawingMoney;
    int packetMoney;

    public SynchronizedDrawing(Account account, int drawingMoney, String name) {
        super(name);
        this.account = account;
        this.drawingMoney = drawingMoney;
    }

    @Override
    public void run() {
        drawing();
    }

    public synchronized void drawing() {
        if (account.money - drawingMoney < 0) {
            return;
        }
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        account.money -= drawingMoney;
        packetMoney += drawingMoney;
        System.out.println(this.getName() + " --> account balance --> " + account.money);
        System.out.println(this.getName() + " --> packet money --> " + packetMoney);
    }

}
