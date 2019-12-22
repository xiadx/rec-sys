package object;

public class SuperDemo {

    public static void main(String[] args) {
        new Child();
        new Child().f();
    }

}

class Father {

    public int value;

    public Father() {
        super();
        System.out.println("father");
    }

    public void f() {
        value = 100;
        System.out.println("father value->" + value);
    }

}

class Child extends Father {

    public int value;

    public Child() {
        super();
        System.out.println("child");
    }

    public void f() {
        super.f();
        value = 200;
        System.out.println("child value->" + value);
        System.out.println("father value->" + super.value);
    }

}