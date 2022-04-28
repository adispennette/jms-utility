package com.scratchpad.card.clock;

import java.util.*;

public class Clock {
    private CircularLinkedList<Integer> clock;

    public Clock(int size) {
        clock = new CircularLinkedList<>();
        for (int i = 0; i < size; i++) {
            clock.add(i);
        }
    }

    public Integer process(int position) {
        int nextPosition = position;
        while(clock.size() > 1) {
            clock.remove(nextPosition);
            nextPosition += position;
        }
        return clock.first();
    }

    private static class Node<T> {
        T item;
        Node<T> next;
        Node<T> prev;

        Node(Node<T> prev, T element, Node<T> next) {
            this.item = element;
            this.next = next;
            this.prev = prev;
        }
    }

    public class CircularLinkedList<T> {
        private Node first = null;
        private Node last = null;
        private int size = 0;

        public T first() { return (T)first.item; }
        public int size() { return size; }

        public boolean add(T value) {
            Objects.requireNonNull(value, "This collection does not allow nulls");
            final Node<T> l = last;
            final Node<T> newNode = new Node<>(l, value, first);
            last = newNode;
            if (l == null)
                first = newNode;
            else
                l.next = newNode;
            size++;
            return true;
        }

        public T remove(int index) {
            Node n = first;
            if(index < 0 || index > size) {
                throw new IndexOutOfBoundsException();
            }
            for (int c = 0;c < index;c++) {
                n = n.next;
            }
            return (T)unlink(n);
        }

        public T remove(T value) {
            Objects.requireNonNull(value, "This collection does not allow nulls");
            for (Node<T> x = first; x != null; x = x.next) {
                if (value.equals(x.item)) {
                    return unlink(x);
                }
            }
            return null;
        }

        T unlink(Node<T> node) {
            // assert x != null;
            final T element = node.item;

            final Node<T> next = node.next;
            final Node<T> prev = node.prev;

            next.prev = prev;
            prev.next = next;


//            if (prev == null) {
//                first = next;
//            } else {
//                prev.next = next;
//                node.prev = null;
//            }
//
//            if (next == null) {
//                last = prev;
//            } else {
//                next.prev = prev;
//                node.next = null;
//            }

            node.item = null;
            size--;
            return element;
        }
    }
}
