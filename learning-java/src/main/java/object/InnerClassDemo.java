package object;

import javax.sound.midi.Soundbank;

public class InnerClassDemo {

    public static void test(AnonymousInterface ai) {
        ai.ai();
    }

    public static void main(String[] args) {
        Outer.StaticInnerClass sic = new Outer.StaticInnerClass();
        System.out.println(sic.name);

        Outer.InnerClass ic = new Outer().new InnerClass();
        ic.show();

        test(new AnonymousInterface() {
            public void ai() {
                System.out.println("ai");
            }
        });
    }

}

class Outer {

    private int age = 10;

    static class StaticInnerClass {

        String name = "StaticInnerClass";

    }

    class InnerClass {

        int age = 20;

        public void show() {
            int age = 30;
            System.out.println("outer class age " + Outer.this.age);
            System.out.println("inner class age " + this.age);
            System.out.println("ocal variable age " + age);
        }

    }

}

interface AnonymousInterface {

    void ai();

}
