package thread;

import java.util.Date;
import java.text.SimpleDateFormat;

public class SleepDemoIII {

    public static void main(String[] args) throws InterruptedException {
        int n = 10;
        while (true) {
            Thread.sleep(1000);
            System.out.println(n--);
            if (n <= 0) {
                break;
            }
        }
        Date currentTime = new Date(System.currentTimeMillis() + 1000 * 10);
        long endTime = currentTime.getTime();
        while (true) {
            System.out.println(new SimpleDateFormat("mm:ss").format(currentTime));
            Thread.sleep(1000);
            currentTime = new Date(currentTime.getTime() - 1000);
            if (currentTime.getTime() < endTime - 1000 * 10) {
                break;
            }
        }
    }

}
