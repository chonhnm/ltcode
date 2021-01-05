package jcip.chap15;

import jcip.ThreadSafe;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

@ThreadSafe
public class ConcurrentStack<E> {

    private final AtomicReference<Node<E>> top;


    public ConcurrentStack() {
        this.top = new AtomicReference<>();
    }

    public void push(E item) {
        while (true) {
            Node<E> oldNode = top.get();
            Node<E> newNode = new Node<>(item, oldNode);
            if (top.compareAndSet(oldNode, newNode)) {
                return;
            }
        }
    }

    public E pop() {
        while (true) {
            Node<E> oldNode = top.get();
            if (oldNode != null) {
                Node<E> next = oldNode.next;
                if (top.compareAndSet(oldNode, next)) {
                    return oldNode.item;
                }
            } else {
                return null;
            }
        }
    }

    private static class Node<E> {
        final E item;
        Node<E> next;

        Node(E item, Node<E> next) {
            this.item = item;
            this.next = next;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ConcurrentStack<Integer> stack = new ConcurrentStack<>();
        int total = 333;
        int threadCnt = 3;
        int tpc = total / threadCnt;
        Thread pushThread = new Thread(() -> {
            for (int i = 0; i < total; i++) {
                stack.push(i);
            }
        });
        ExecutorService popService = Executors.newFixedThreadPool(threadCnt);
        List<Callable<Object>> list = new ArrayList<>();
        for (int j = 0; j < threadCnt; j++) {
            list.add(() -> {
                for (int i = 0; i < tpc; i++) {
                    System.out.print(stack.pop() + "\t");
                    if (i % 10 == 0) {
                        System.out.println();
                    }
                }
                return null;
            });
        }
        pushThread.start();
        popService.invokeAll(list);
        pushThread.join();
        popService.shutdown();
        popService.awaitTermination(1, TimeUnit.MINUTES);

    }

}
