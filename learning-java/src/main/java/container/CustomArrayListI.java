package container;

public class CustomArrayListI {

    private Object[] elementData;
    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    public CustomArrayListI() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    public CustomArrayListI(int capacity) {
        elementData = new Object[capacity];
    }

    public void add(Object obj) {
        elementData[size++] = obj;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("[");
        for (int i = 0; i < size; i++) {
            sb.append(elementData[i] + ",");
        }
        sb.setCharAt(sb.length() - 1, ']');

        return sb.toString();
    }

    public static void main(String[] args) {
        CustomArrayListI l1 = new CustomArrayListI();
        l1.add("a");
        l1.add("b");
        System.out.println(l1);

        CustomArrayListI l2 = new CustomArrayListI(2);
        l2.add("c");
        l2.add("d");
        System.out.println(l2);
//        l2.add("e");
//        System.out.println(l2);
    }

}
