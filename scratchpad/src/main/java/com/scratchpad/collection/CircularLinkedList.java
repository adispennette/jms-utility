package com.scratchpad.collection;

public class CircularLinkedList<T> {
    private Entry<T> head = null;
    private Entry<T> tail = null;
    private int size = 0;

    public int size() { return size; }

    public Entry<T> head() { return head; }

    public Entry<T> tail() { return tail; }

    public void add(T value) {
        Entry<T> newEntry = new Entry(value);

        if (head == null) {
            head = newEntry;
        } else {
            tail.next = newEntry; // point the tail to the new node
            newEntry.previous = tail; // set the old tail as the previous
        }
        tail = newEntry; // make the new entry the tail
        tail.next = head; // set the next entry to the head
        head.previous = tail; // update head to point to the new tail
        size++; // increase the size of the collection
        if (size == 2) { head.next = tail; } // edge case for the second entry
    }

    public void delete(T valueToDelete) {
        Entry<T> current = head;
        if (head == null) { // the list is empty
            return;
        }
        do {
            Entry<T> next = current.next;
            if (next.value == valueToDelete) {
                if (tail == head) { // the list has only one single element
                    head = null;
                    tail = null;
                } else {
                    current.next = next.next; // update the current next
                    next.next.previous = current; // update the new next to point to the current as the previous
                    if (head == next) { //we're deleting the head
                        head = head.next; // set the new head
                        head.previous = tail; // set the previous to tail
                    }
                    if (tail == next) { //we're deleting the tail
                        tail = current;
                    }
                }
                break;
            }
            current = next;
        } while (current != head);
        size--;
    }

    public boolean contains(T searchValue) {
        Entry<T> current = head;
        if (head != null) {
            do {
                if (current.value.equals(searchValue)) {
                    return true;
                }
                current = current.next;
            } while (current != head);
        }
        return false;
    }
}

class Entry<T> {
    T value;
    Entry<T> previous;
    Entry<T> next;

    public Entry(T value) {
        this.value = value;
    }
}