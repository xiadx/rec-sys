package thread;

public class UnsafeDemoII {

    public static void main(String[] args) {
        Account account = new Account(100, "assets");
        Drawing you = new Drawing(account, 80, "you");
        Drawing wife = new Drawing(account, 90, "wife");
        you.start();
        wife.start();
    }

}

class Drawing extends Thread {

    Account account;
    int drawingMoney;
    int packetMoney;

    public Drawing(Account account, int drawingMoney, String name) {
        super(name);
        this.account = account;
        this.drawingMoney = drawingMoney;
    }

    @Override
    public void run() {
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
