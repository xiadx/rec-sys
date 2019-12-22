package object;

public class StudentDemo {

    int id;
    String name;
    int age;

    Computer comp;

    void study() {
        System.out.println("study --" + comp.brand);
    }

    void play() {
        System.out.println("play --" + comp.brand);
    }

    public static void main(String[] args) {
        StudentDemo sd = new StudentDemo();
//        sd.study();
//        sd.play();

        sd.id = 1001;
        sd.name = "abc";
        sd.age = 18;

        Computer c = new Computer();
        c.brand = "dell";

        sd.comp = c;

        sd.study();
        sd.play();
    }

}

class Computer {

    String brand;

}