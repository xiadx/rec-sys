package thread;

public class TerminateThreadDemo implements Runnable {

    private boolean flag = true;
    private String name;

    public TerminateThreadDemo(String name) {
        this.name = name;
    }

    @Override
    public void run() {
        int i = 1;
        while (flag) {
            System.out.println(name + " --> " + i++);
        }
    }

    public void terminate() {
        this.flag = false;
    }

    public static void main(String[] args) {
        TerminateThreadDemo tt = new TerminateThreadDemo("terminate");
        new Thread(tt).start();
        for (int i = 0; i <= 99; i++) {
            if (i == 88) {
                tt.terminate();
                System.out.println("terminate over");
            }
            System.out.println("main --> " + i);
        }
    }

}
