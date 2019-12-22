package object;

public class InterfaceDemo {

    public static void main(String[] args) {
        AnimalInterface a = new AnimalObject();
        a.run();

        C c = new D();
        c.a();
        c.b();
        c.c();

        Volant v = new Angel();
        v.fly();
        Honest h = new People();
        h.helpOther();
        Volant b = new Bird();
        b.fly();
        ((Angel)v).helpOther();
    }

}

interface AnimalInterface {

//    public static final String NAME = "dog";
    String NAME = "dog";

//    public static final int AGE = 18;
    int AGE = 18;

//    public abstract void run();
    void run();

}

class AnimalObject implements AnimalInterface {

    public void run() {
        System.out.println("name->" + NAME + ",age->" + AGE + ",run");
    }

}

interface A {

    void a();

}

interface B {

    void b();

}

interface C extends A, B {

    void c();

}

class D implements C {

    public void a() {
        System.out.println("a");
    }

    public void b() {
        System.out.println("b");
    }

    public void c() {
        System.out.println("c");
    }

}


interface Volant {

    void fly();

}

interface Honest {

    void helpOther();

}

class Angel implements Volant, Honest {

    public void fly() {
        System.out.println("Angel.fly()");
    }

    public void helpOther() {
        System.out.println("Angel.helpOther()");
    }

}

class People implements Honest {

    public void helpOther() {
        System.out.println("People.helpOther()");
    }

}

class Bird implements Volant {

    public void fly() {
        System.out.println("Bird.fly()");
    }

}