package object;

public class OverrideDemo {

    public static void main(String[] args) {
        Horse h = new Horse();
        h.run();
    }

}

class Vehicle {

    public void run() {
        System.out.println("run");
    }

    public void stop() {
        System.out.println("stop");
    }

}

class Horse extends Vehicle {

    public void run() {
        System.out.println("horse run");
    }

}