package _2_add_two_numbers;

//You are given two non-empty linked lists representing two non-negative integers. The digits are stored in reverse order and each of their nodes contain a single digit. Add the two numbers and return it as a linked list.
//
//        You may assume the two numbers do not contain any leading zero, except the number 0 itself.
//
//        Example:
//
//        Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
//        Output: 7 -> 0 -> 8
//        Explanation: 342 + 465 = 807.


public class AddTwoNum {

    public static void main(String[] args) {// 4575+465=5040
        ListNode l1 = new ListNode(5);
        l1.next = new ListNode(7);
        l1.next.next = new ListNode(5);
        l1.next.next.next = new ListNode(4);
        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);
        ListNode listNode = addTwoNumbers2(l1, l2);
        printNode(listNode);
    }

    private static void printNode(ListNode listNode) {
        while (listNode != null) {
            System.out.print(listNode.val + ",");
            listNode = listNode.next;
        }
        System.out.println();
    }

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        int sum = l1.val + l2.val;
        int reminder = sum % 10;
        int quotient = sum / 10;
        ListNode result = new ListNode(reminder);
        ListNode ptr = result;
        ListNode next2 = l2.next;
        for (ListNode next1 = l1.next; next1 != null; next1 = next1.next) {
            if (next2 != null) {
                sum = next1.val + next2.val + quotient;
                next2 = next2.next;
            } else {
                sum = next1.val + quotient;
            }
            reminder = sum % 10;
            quotient = sum / 10;
            ptr.next = new ListNode(reminder);
            ptr = ptr.next;
        }
        while (next2 != null) {
            sum = next2.val + quotient;
            reminder = sum % 10;
            quotient = sum / 10;
            ptr.next = new ListNode(reminder);
            ptr = ptr.next;
            next2 = next2.next;
        }
        if (quotient != 0) {
            ptr.next = new ListNode(quotient);
        }

        return result;
    }

    public static ListNode addTwoNumbers2(ListNode l1, ListNode l2) {
        int sum = l1.val + l2.val;
        int reminder = sum % 10;
        int quotient = sum / 10;
        ListNode result = new ListNode(reminder);
        ListNode ptr = result;
        ListNode next2 = l2.next;
        boolean flag = false;
        ListNode next1 = l1.next;
        for (; next1 != null; next1 = next1.next) {
            if (next2 != null) {
                sum = next1.val + next2.val + quotient;
                next2 = next2.next;
            } else {
                flag = true;
                break;
            }
            reminder = sum % 10;
            quotient = sum / 10;
            ptr.next = new ListNode(reminder);
            ptr = ptr.next;
        }
        ListNode listNode = flag ? next1:next2;
        while (listNode != null) {
            sum = listNode.val + quotient;
            reminder = sum % 10;
            quotient = sum / 10;
            if (quotient == 0) {
                ptr.next = new ListNode(reminder);
                ptr.next.next = listNode.next;
                break;
            }
            ptr.next = new ListNode(reminder);
            ptr = ptr.next;
            listNode = listNode.next;
        }
        if (quotient != 0) {
            ptr.next = new ListNode(quotient);
        }

        return result;
    }


    public static class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            val = x;
        }

    }

}


