package offer;

import java.util.Stack;
import java.util.ArrayList;

class ListNode {

    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }

}

/**
 * 输入一个链表，按链表从尾到头的顺序返回一个ArrayList。
 */
public class PrintListFromTailToHead {

    public ArrayList<Integer> printListFromTailToHead(ListNode listNode) {
        Stack<Integer> stack = new Stack<>();
        while (listNode != null) {
            stack.push(listNode.val);
            listNode = listNode.next;
        }
        ArrayList<Integer> list = new ArrayList<>();
        while (!stack.isEmpty()) {
            list.add(stack.pop());
        }
        return list;
    }

    public static void main(String[] args) {
        ListNode listNode = new ListNode(1);
        listNode.next = new ListNode(2);
        listNode.next.next = new ListNode(3);
        PrintListFromTailToHead p = new PrintListFromTailToHead();
        ArrayList<Integer> al = p.printListFromTailToHead(listNode);
        System.out.println(al);
    }

}
