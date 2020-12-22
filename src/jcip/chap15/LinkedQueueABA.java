package jcip.chap15;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

public class LinkedQueueABA<E> {

    private static class Node<E> {
        final E item;
        final AtomicStampedReference<Node<E>> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicStampedReference<>(next, 0);
        }
    }

    private final Node<E> dummy = new Node<>(null, null);
    private final AtomicStampedReference<Node<E>> head = new AtomicStampedReference<>(dummy, 0);
    private final AtomicStampedReference<Node<E>> tail = new AtomicStampedReference<>(dummy, 0);

    public boolean put(E item) {
        Node<E> newNode = new Node<>(item, null);
        while (true) {
            Node<E> curTail = tail.getReference();
            int curStamp = tail.getStamp();
            Node<E> tailNext = curTail.next.getReference();
            int stamp = curTail.next.getStamp();
            if (curTail == tail.getReference() && curStamp == tail.getStamp()) {
                if (tailNext != null) {
                    tail.compareAndSet(curTail, tailNext, curStamp, curStamp + 1);
                } else {
                    if (curTail.next.compareAndSet(null, newNode, stamp, stamp + 1)) {
                        tail.compareAndSet(curTail, newNode, curStamp, curStamp  + 1);
                        return true;
                    }
                }
            }
        }
    }

    public int size() {
        int cnt = 0;
        Node<E> cur = head.getReference();
        Node<E> ne = cur.next.getReference();
        while (ne != null) {
            cnt++;
            cur = ne;
            ne = ne.next.getReference();
        }
        System.out.println(tail.getReference() == cur);
        return cnt;
    }

    public static void main(String[] args) throws InterruptedException {
        LinkedQueueABA<Object> list = new LinkedQueueABA<>();
        String a1 = "1";
        Object a2 ="2";
        Object a3 = "3";
        ExecutorService service = Executors.newCachedThreadPool();
        List<Callable<Object>> calls = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            calls.add(()-> {
                for (int j = 0; j < 2; j++) {
                    list.put(a1);
                    list.put(a2);
                    list.put(a3);
                    list.put(a1);
                }
                return null;
            });
        }
        service.invokeAll(calls);
        service.shutdown();
        service.awaitTermination(1, TimeUnit.MINUTES);
        System.out.println(list.size());
    }


}
