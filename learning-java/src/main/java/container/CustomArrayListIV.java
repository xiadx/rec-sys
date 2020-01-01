package container;

public class CustomArrayListIV<E> {

    private Object[] elementData;
    private int size;

    private static final int DEFAULT_CAPACITY = 10;

    public CustomArrayListIV() {
        elementData = new Object[DEFAULT_CAPACITY];
    }

    public CustomArrayListIV(int capacity) {
        if (capacity < 0) {
            throw new RuntimeException("capacity parameter error");
        } else if (capacity == 0) {
            elementData = new Object[DEFAULT_CAPACITY];
        }
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0 ? true : false;
    }

    public void add(E e) {
        if (size == elementData.length) {
            Object[] newElement = new Object[elementData.length + (elementData.length >> 1)];
            System.arraycopy(elementData, 0, newElement, 0, elementData.length);
            elementData = newElement;
        }
        elementData[size++] = e;
    }

    public E get(int index) {
        checkRange(index);
        return (E)elementData[index];
    }

    public void set(E e, int index) {
        checkRange(index);
        elementData[index] = e;
    }

    public void checkRange(int index) {
        if (index < 0 || index > size - 1) {
            throw new RuntimeException("index parameter error");
        }
    }

    public void remove(int index) {
        checkRange(index);
        int n = elementData.length - index - 1;
        if (n > 0) {
            System.arraycopy(elementData, index + 1, elementData, index, n);
        }
        elementData[--size] = null;
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
        CustomArrayListIV<String> l = new CustomArrayListIV<String>();
        for (int i = 0; i < 40; i++) {
            l.add("" + i);
        }
        l.set("10", 11);
        System.out.println(l);
        System.out.println(l.get(10));
        l.remove(3);
        System.out.println(l.size());
        System.out.println(l.isEmpty());
        System.out.println(l);
    }

}
