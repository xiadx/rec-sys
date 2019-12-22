package object;

public class ExtendsDemo {

    public static void main(String[] args) {
        Student s1 = new Student();
        s1.name = "abc";
        s1.height = 172;
        s1.print();

        Student s2 = new Student("def", 185, "computer");
        s2.print();
        s2.study();

        System.out.println(s2 instanceof Student);
        System.out.println(s2 instanceof Person);
        System.out.println(s2 instanceof Object);
        System.out.println(new Person() instanceof Student);
    }

}

class Person {

    String name;
    int height;

    public void print() {
        System.out.println("name->" + name + ",height->" + height);
    }

}

class Student extends Person {

    String major;

    public void study() {
        System.out.println("study");
    }

    public Student(String name, int height, String major) {
        this.name = name;
        this.height = height;
        this.major = major;
    }

    public Student() {

    }

}