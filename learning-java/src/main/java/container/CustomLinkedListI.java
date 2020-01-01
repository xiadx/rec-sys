package container;

public class CustomLinkedListI {

    private ListNode first;
    private ListNode last;

    private int size;

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
        CustomLinkedListI ll = new CustomLinkedListI();
        ll.add("a");
        ll.add("b");
        ll.add("c");
        ll.add("d");
        System.out.println(ll);
    }

}
