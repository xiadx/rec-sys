package thread;

import java.util.List;
import java.util.ArrayList;

public class SynchronizedDemoVIII {

    public static void main(String[] args) {
        List<Integer> available = new ArrayList<Integer>();
        available.add(1);
        available.add(2);
        available.add(3);
        available.add(6);
        available.add(7);
        List<Integer> s1 = new ArrayList<Integer>();
        s1.add(1);
        s1.add(2);
        List<Integer> s2 = new ArrayList<Integer>();
        s2.add(3);
        s2.add(6);
        CinemaL cinema = new CinemaL(available, "cinema");
        new Thread(new CustomerL(cinema, s1), "a").start();
        new Thread(new CustomerL(cinema, s2), "b").start();
    }

}

class CustomerL implements Runnable {

    CinemaL cinema;
    List<Integer> seats;

    public CustomerL(CinemaL cinema, List<Integer> seats) {
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

class CinemaL {

    List<Integer> available;
    String name;

    public CinemaL(List<Integer> available, String name) {
        this.available = available;
        this.name = name;
    }

    public boolean bookTickets(List<Integer> seats) {
        System.out.println(this.name + " --> available -->" + available);
        List<Integer> t = new ArrayList<Integer>();
        t.addAll(available);
        t.removeAll(seats);
        if (available.size() - t.size() != seats.size()) {
            return false;
        }
        available = t;
        return true;
    }

}
