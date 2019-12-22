package object;

public class EncapsulationDemo {

    public static void main(String[] args) {
        Human h = new Human();
//        h.age = 13;
        h.name = "abc";
        h.height = 185;
        h.printAge();

        Staff s = new Staff(1, "abc", 18, true);
        System.out.println(s.toString());
    }

}

class Human {

    private int age;
    String name;
    protected int height;

    public void printAge() {
        System.out.println("age->" + age + ",name->" + name + ",height->" + height);
    }

}

class Boy extends Human {

    public void printAge() {
//        System.out.println(age);
    }

}

class Staff {

    private int id;
    private String name;
    private int age;
    private boolean man;

    public Staff(int id, String name, int age, boolean man) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.man = man;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age >= 1 && age <= 130) {
            this.age = age;
        } else {
            System.out.println("age is not normal");
        }
    }

    public boolean isMan() {
        return man;
    }

    public void setMan(boolean man) {
        this.man = man;
    }

    @Override
    public String toString() {
        return "Staff{id->" + id + ",name->'" + name + "',age->" + age + ",man->" + man + "}";
    }

}