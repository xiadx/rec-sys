package object;

public class PolymorphismDemo {

    public static void main(String[] args) {
        Animal a = new Animal();
        printName(a);

        Animal d = new Dog();
        printName(d);

        Animal c = new Cat();
        printName(c);

        Dog o = (Dog)d;
        o.run();
    }

    static void printName(Animal a) {
        a.printName();
    }

}

class Animal {

    public void printName() {
        System.out.println("animal");
    }

}

class Dog extends Animal {

    public void printName() {
        System.out.println("dog");
    }

    public void run() {
        System.out.println("dog run");
    }

}

class Cat extends Animal {

    public void printName() {
        System.out.println("cat");
    }

}