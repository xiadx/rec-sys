package container;

public class CustomLinkedListIII {

    private ListNode first;
    private ListNode last;

    private int size;

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

    public Object get(int index) {
        if (index < 0 || index > size - 1) {
            throw new RuntimeException("index parameter error");
        }
        ListNode q = getLN(index);
        return q != null ? q.element : null;
    }

    public void add(Object obj) {
        ListNode node = new ListNode(obj);
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
        CustomLinkedListIII ll = new CustomLinkedListIII();
        ll.add("a");
        ll.add("b");
        ll.add("c");
        ll.add("d");
        System.out.println(ll);
        ll.remove(0);
        System.out.println(ll);
        ll.remove(2);
        System.out.println(ll);
    }

}
