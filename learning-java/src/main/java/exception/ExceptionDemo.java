package exception;

public class ExceptionDemo {

    public static void main(String[] args) {
//        int a = 0, b = 1;
//        System.out.println(b / a);

//        String s = null;
//        System.out.println(s.length());

//        Animal d = new Dog();
//        System.out.println(d instanceof  Animal);
//        System.out.println(d instanceof Cat);
//        Cat c = (Cat)d;

        int[] x = new int[5];
        System.out.println(x[5]);
    }

}

class Animal {

}

class Dog extends Animal {

}

class Cat extends Animal {

}
