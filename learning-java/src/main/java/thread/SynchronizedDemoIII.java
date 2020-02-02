package thread;

public class SynchronizedDemoIII {

    public static void main(String[] args) {
        Account account = new Account(100, "assets");
        SynchronizedDrawingI you = new SynchronizedDrawingI(account, 80, "you");
        SynchronizedDrawingI wife = new SynchronizedDrawingI(account, 90, "wife");
        you.start();
        wife.start();
    }

}

class SynchronizedDrawingI extends Thread {

    Account account;
    int drawingMoney;
    int packetMoney;

    public SynchronizedDrawingI(Account account, int drawingMoney, String name) {
        super(name);
        this.account = account;
        this.drawingMoney = drawingMoney;
    }

    @Override
    public void run() {
        drawing();
    }

    public void drawing() {
        if (account.money <= 0) {
            return;
        }
        synchronized (account) {
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

}
