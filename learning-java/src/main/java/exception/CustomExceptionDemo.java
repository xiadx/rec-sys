package exception;

public class CustomExceptionDemo {

    public static void main(String[] args) {
        Person p = new Person();
        p.setAge(-10);
    }

}

class Person {

    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 0) {
            try {
                throw new IllegalAgeException("age exception");
            } catch (IllegalAgeException e) {
                e.printStackTrace();
            }

        }
    }

}

class IllegalAgeException extends Exception {

    public IllegalAgeException() {

    }

    public IllegalAgeException(String msg) {
        super(msg);
    }

}
