package container;

public class ListNode {

    ListNode prev;
    ListNode next;

    Object element;

    public ListNode(Object element) {
        super();
        this.element = element;
    }

    public ListNode(ListNode prev, ListNode next, Object element) {
        super();
        this.prev = prev;
        this.next = next;
        this.element = element;
    }

}
