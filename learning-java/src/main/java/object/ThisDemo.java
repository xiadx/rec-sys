package object;

public class ThisDemo {

    int a, b, c;

    ThisDemo(int a, int b) {
        this.a = a;
        this.b = b;
    }

    ThisDemo(int a, int b, int c) {
        this(a, b);
        this.c = c;
    }

    void sing() {

    }

    void eat() {
        this.sing();
        System.out.println("eat");
        System.out.println("a->" + this.a + ",b->" + this.b + ",c->" + this.c);
    }

    public static void main(String[] args) {
        ThisDemo td1 = new ThisDemo(2, 3);
        ThisDemo td2 = new ThisDemo(2, 3, 4);
        td1.eat();
        td2.eat();
    }

}