package com.scratchpad.collection;

import java.util.HashMap;
import java.util.Map;

public class CircularLinkedListV2<T> {
    private Entry<T> head = null;
    private Entry<T> tail = null;
    private Map<T, Entry> entries = new HashMap<>();


    public int size() { return entries.size(); }

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
        entries.put(value, newEntry);
    }

    public void delete(T valueToDelete) {
        if (head != null) {
            Entry<T> toDelete = entries.get(valueToDelete);
            Entry<T> previous = toDelete.previous;
            Entry<T> next = toDelete.next;
            if(head == tail && head == toDelete) { // the collection contains only one value
                head = null;
                tail = null;
            } else if(toDelete == head) { // Delete the head entry
                head = next;
            } else if(toDelete == tail) { // delete the tail entry
                tail = previous;
            }
            // rewire the pointers for deleted entries previous and next
            previous.next = next;
            next.previous = previous;
            entries.remove(valueToDelete);
        } // else the collection is empty and there is nothing to do
    }

    public boolean contains(T searchValue) {
        return entries.get(searchValue) != null;
    }
}

//class Entry<T> {
//    T value;
//    Entry<T> previous;
//    Entry<T> next;
//
//    public Entry(T value) {
//        this.value = value;
//    }
//}