package com.scratchpad.collection;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CircularLinkedListV2Test {
    private CircularLinkedListV2 list;

    @BeforeEach
    void setUp() {
        list = new CircularLinkedListV2<String>();
    }

    @Test
    void add() {
        list.add("TEST");
        list.add("FOO");
        list.add("BAR");
        assertTrue(list.size() == 3);
        assertEquals("FOO", list.head().next.value);
        assertEquals("BAR", list.head().previous.value);
        assertEquals("TEST", list.tail().next.value);
        assertEquals("FOO", list.tail().previous.value);
    }

    @Test
    void delete() {
        list.add("TEST");
        list.add("FOO");
        list.add("BAR");
        list.delete("FOO");
        assertTrue(list.size() == 2);
        assertEquals("BAR", list.head().next.value);
        assertEquals("BAR", list.head().previous.value);
        assertEquals("TEST", list.tail().next.value);
        assertEquals("TEST", list.tail().previous.value);
    }

    @Test
    void delete_head() {
        list.add("TEST");
        list.add("FOO");
        list.add("BAR");
        list.delete("TEST");
        assertTrue(list.size() == 2);
        assertEquals("FOO", list.head().value);
        assertEquals("BAR", list.head().next.value);
        assertEquals("BAR", list.head().previous.value);
        assertEquals("FOO", list.tail().next.value);
        assertEquals("FOO", list.tail().previous.value);
    }

    @Test
    void delete_tail() {
        list.add("TEST");
        list.add("FOO");
        list.add("BAR");
        list.delete("BAR");
        assertTrue(list.size() == 2);
        assertEquals("FOO", list.tail().value);
        assertEquals("FOO", list.head().next.value);
        assertEquals("FOO", list.head().previous.value);
        assertEquals("TEST", list.tail().next.value);
        assertEquals("TEST", list.tail().previous.value);
    }

    @Test
    void delete_all() {
        list.add("TEST");
        list.add("FOO");
        list.add("BAR");
        list.delete("TEST");
        list.delete("FOO");
        list.delete("BAR");
        assertTrue(list.size() == 0);
        assertNull(list.tail());
        assertNull(list.head());
    }

    @Test
    void size() {
        list.add("TEST");
        list.add("FOO");
        list.add("BAR");
        int count = 0;
        Entry<String> current = list.head();
        do {
            count++;
            current = current.next;
        } while (!current.equals(list.head()));
        assertTrue(count == 3);
    }

    @Test
    void size_afterDelete() {
        list.add("TEST");
        list.add("FOO");
        list.add("BAR");
        list.delete("FOO");
        int count = 0;
        Entry<String> current = list.head();
        do {
            count++;
            current = current.next;
        } while (!current.equals(list.head()));
        assertTrue(count == 2);
    }

    @Test
    void contains() {
        list.add("TEST");
        list.add("FOO");
        list.add("BAR");
        assertTrue(list.contains("FOO"));
    }
}