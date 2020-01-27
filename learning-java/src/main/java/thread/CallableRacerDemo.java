package thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;

public class CallableRacerDemo implements Callable<Integer> {

    private String winner;

    @Override
    public Integer call() throws Exception {
        for (int s = 1; s <= 100; s++) {
            if (Thread.currentThread().getName().equals("pool-1-thread-1") && s % 10 == 0) {
                Thread.sleep(100);
            }
            System.out.println(Thread.currentThread().getName() + " --> " + s);
            boolean flag = gameOver(s);
            if (flag) {
                return s;
            }
        }
        return null;
    }

    private boolean gameOver(int s) {
        if (winner != null) {
            return true;
        } else {
            if (s == 100) {
                winner = Thread.currentThread().getName();
                System.out.println("winner --> " + winner);
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        CallableRacerDemo racer = new CallableRacerDemo();
        ExecutorService es = Executors.newFixedThreadPool(2);
        Future<Integer> f1 = es.submit(racer);
        Future<Integer> f2 = es.submit(racer);
        Integer r1 = f1.get();
        Integer r2 = f2.get();
        System.out.println(r1 + " --> " + r2);
        es.shutdownNow();
    }

}
