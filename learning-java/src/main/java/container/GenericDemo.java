package container;

public class GenericDemo {

    public static void main(String[] args) {
        CustomCollection<String> cc = new CustomCollection<String>();
        cc.set("a", 0);
        String a = cc.get(0);
        System.out.println(a);
    }

}

class CustomCollection<E> {

    Object[] objs = new Object[5];

    public void set(E e, int index) {
        objs[index] = e;
    }

    public E get(int index) {
        return (E)objs[index];
    }

}
