package thread;

public class SynchronizedDemoIX {

    public static void main(String[] args) {
        Web12306X w = new Web12306X(4, "web12306");
        new Passenger(w, "a", 2).start();
        new Passenger(w, "b", 1).start();
    }

}

class Passenger extends Thread {

    int seats;

    public Passenger(Runnable target, String name, int seats) {
        super(target, name);
        this.seats = seats;
    }

}

class Web12306X implements Runnable {

    int available;
    String name;

    public Web12306X(int available, String name) {
        this.available = available;
        this.name = name;
    }

    @Override
    public void run() {
        Passenger p = (Passenger)Thread.currentThread();
        boolean flag = this.bookTickets(p.seats);
        if (flag) {
            System.out.println("success --> " + Thread.currentThread().getName() + " --> " + p.seats);
        } else {
            System.out.println("failure --> " + Thread.currentThread().getName() + " --> no seat");
        }
    }

    public synchronized boolean bookTickets(int seats) {
        System.out.println("available --> " + available);
        if (seats > available) {
            return false;
        }
        available -= seats;
        return true;
    }

}
