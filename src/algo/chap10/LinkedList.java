package algo.chap10;

public class LinkedList<E> {

    public LinkedList() {
        sentinel = new Node<>(null, null, null);
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public void addFirst(E e) {
        Node<E> newNode = new Node<>(null, e, null);
        newNode.next = sentinel.next;
        sentinel.next.prev = newNode;
        sentinel.next = newNode;
        newNode.prev = sentinel;
    }

    public boolean delete(E e) {
        Node<E> node = search(e);
        node.prev.next = node.next;
        node.next.prev = node.prev;
        return node != sentinel;
    }

    private Node<E> search(E e) {
        Node<E> next = sentinel.next;
        if (e == null) {
            while (next != sentinel && next.item != null) {
                next = next.next;
            }
        } else {
            while (next != sentinel && !e.equals(next.item)) {
                next = next.next;
            }
        }
        return next;
    }

    private Node<E> sentinel;

    private static class Node<E> {
        Node<E> prev;
        E item;
        Node<E> next;

        public Node(Node<E> prev, E item, Node<E> next) {
            this.prev = prev;
            this.item = item;
            this.next = next;
        }
    }
}
