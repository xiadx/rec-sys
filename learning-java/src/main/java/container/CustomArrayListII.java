package container;

public class CustomArrayListII<E> {

    private Object[] elementData;
    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    public CustomArrayListII() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    public CustomArrayListII(int capacity) {
        elementData = new Object[capacity];
    }

    public void add(E e) {
        if (size == elementData.length) {
            Object[] newElement = new Object[elementData.length + (elementData.length >> 1)];
            System.arraycopy(elementData, 0, newElement, 0, elementData.length);
            elementData = newElement;
        }
        elementData[size++] = e;
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
        CustomArrayListII l = new CustomArrayListII();
        for (int i = 0; i < 100; i++) {
            l.add("" + i);
        }
        System.out.println(l);
    }

}
