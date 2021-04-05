package com.jaeheonshim.tetris.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Bag<T> {
    private List<T> bag = new ArrayList<>();

    public void add(T element) {
        bag.add(element);
    }

    public void shuffle() {
        Collections.shuffle(bag);
    }

    public boolean isEmpty() {
        return bag.isEmpty();
    }

    public T get() {
        return bag.remove(0);
    }
}
