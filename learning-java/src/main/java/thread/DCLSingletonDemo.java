package thread;

public class DCLSingletonDemo {

    private static volatile DCLSingletonDemo instance;

    private DCLSingletonDemo() {

    }

    public static DCLSingletonDemo getInstance() {
        if (null != instance) {
            return instance;
        }
        synchronized (DCLSingletonDemo.class) {
            if (null == instance) {
                instance = new DCLSingletonDemo();
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(DCLSingletonDemo.getInstance());
        }).start();
        System.out.println(DCLSingletonDemo.getInstance());
    }

}
