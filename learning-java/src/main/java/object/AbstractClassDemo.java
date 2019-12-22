package object;

public class AbstractClassDemo {

    public static void main(String[] args) {
//        AbstractAnimal a = new AbstractAnimal();
        AbstractAnimal a = new DogObject();
        a.run();
    }

}

abstract class AbstractAnimal {

    abstract public void run();

}

class DogObject extends AbstractAnimal {

    @Override
    public void run() {
        System.out.println("dog run");
    }

}
