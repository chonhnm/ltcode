package jcip.chap15;

import jcip.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class LinkedQueue<E> {

    private static class Node<E> {
        E item;
        AtomicReference<Node<E>> next;

        Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<>(next);
        }
    }

    private final Node<E> dummy = new Node<>(null, null);
    private final AtomicReference<Node<E>> head = new AtomicReference<>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);

    public boolean put(E item) {
        Node<E> newNode = new Node<E>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            if (curTail == tail.get()) { // 确保两个curTail相同
                if (tailNext != null) {
                    // 中间状态, 帮其他线程移动tail
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    // 第一步：节点加入表尾
                    if(curTail.next.compareAndSet(null, newNode)) {
                        // 第二步：更新tail节点
                        tail.compareAndSet(curTail, newNode);
                        return true;
                    }
                }
            }
        }
    }


}
