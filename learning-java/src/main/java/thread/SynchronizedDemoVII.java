package thread;

public class SynchronizedDemoVII {

    public static void main(String[] args) {
        Cinema cinema = new Cinema(2, "cinema");
        new Thread(new Customer(cinema, 2), "a").start();
        new Thread(new Customer(cinema, 1), "b").start();
    }

}

class Customer implements Runnable {

    Cinema cinema;
    int seats;

    public Customer(Cinema cinema, int seats) {
        this.cinema = cinema;
        this.seats = seats;
    }

    @Override
    public void run() {
        synchronized (cinema) {
            boolean flag = cinema.bookTickets(seats);
            if (flag) {
                System.out.println("success --> " + Thread.currentThread().getName() + " --> " + seats);
            } else {
                System.out.println("failure --> " + Thread.currentThread().getName() + " --> no seat");
            }
        }
    }

}

class Cinema {

    int available;
    String name;

    public Cinema(int available, String name) {
        this.available = available;
        this.name = name;
    }

    public boolean bookTickets(int seats) {
        System.out.println("available --> " + available);
        if (seats > available) {
            return false;
        }
        available -= seats;
        return true;
    }

}
