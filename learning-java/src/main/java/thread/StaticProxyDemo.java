package thread;

public class StaticProxyDemo {

    public static void main(String[] args) {
        new WeddingCompany(new You()).happyMarry();
    }

}

interface Marry {

    void happyMarry();

}

class You implements Marry {

    @Override
    public void happyMarry() {
        System.out.println("marry");
    }

}

class WeddingCompany implements Marry {

    private Marry target;

    public WeddingCompany(Marry target) {
        this.target = target;
    }

    @Override
    public void happyMarry() {
        ready();
        this.target.happyMarry();
        after();
    }

    private void ready() {
        System.out.println("ready");
    }

    private void after() {
        System.out.println("after");
    }

}
