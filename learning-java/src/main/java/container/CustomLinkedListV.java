package container;

public class CustomLinkedListV<E> {

    private ListNode first;
    private ListNode last;

    private int size;

    public void add(int index, E e) {
        checkRange(index);
        ListNode n = new ListNode(e);
        ListNode q = getLN(index);
        if (q != null) {
            ListNode prev = q.prev;
            prev.next = n;
            n.prev = prev;
            n.next = q;
            q.prev = n;
        }
    }

    public void remove(int index) {
        ListNode q = getLN(index);
        if (q != null) {
            ListNode prev = q.prev;
            ListNode next = q.next;
            if (prev != null) {
                prev.next = next;
            }
            if (next != null) {
                next.prev = prev;
            }
            if (index == 0) {
                first = next;
            }
            if (index == size - 1) {
                last = prev;
            }
            size--;
        }
    }

    public ListNode getLN(int index) {
        checkRange(index);
        ListNode q = null;
        if (index <= (size >> 1)) {
            q = first;
            for (int i = 0; i < index; i++) {
                q = q.next;
            }
        } else {
            q = last;
            for (int i = size - 1; i > index; i--) {
                q = q.prev;
            }
        }
        return q;
    }

    public E get(int index) {
        checkRange(index);
        ListNode q = getLN(index);
        return q != null ? (E)q.element : null;
    }

    private void checkRange(int index) {
        if (index < 0 || index > size - 1) {
            throw new RuntimeException("index parameter error");
        }
    }

    public void add(E e) {
        ListNode node = new ListNode(e);
        if (first == null) {
            first = node;
            last = node;
        } else {
            node.prev = last;
            node.next = null;
            last.next = node;
            last = node;
        }
        size++;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        ListNode current = first;
        while (current != null) {
            sb.append(current.element + ",");
            current = current.next;
        }
        sb.setCharAt(sb.length() - 1, ']');
        return sb.toString();
    }

    public static void main(String[] args) {
        CustomLinkedListV<String> ll = new CustomLinkedListV<String>();
        ll.add("a");
        ll.add("b");
        ll.add("c");
        ll.add("d");
        System.out.println(ll);
        System.out.println(ll.get(1));
    }

}
