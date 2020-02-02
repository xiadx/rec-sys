package thread;

import java.util.Timer;
import java.util.TimerTask;

public class TimerDemo extends TimerTask {

    public static void main(String[] args) {
        Timer timer = new Timer();
//        timer.schedule(new TimerDemo(), 1000);
        timer.schedule(new TimerDemo(), 1000, 200);
    }

    @Override
    public void run() {
        System.out.println("TimerTask");
    }

}
