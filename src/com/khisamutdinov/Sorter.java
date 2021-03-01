package com.khisamutdinov;

import java.util.Comparator;

public class Sorter<T> implements Runnable {
    private Object[] items;
    private int threadCount;
    private Comparator<T> comparator;

    public Sorter(Object[] items, int threadCount, Comparator<T> comparator) {
        this.items = items;
        this.threadCount = threadCount;
        this.comparator = comparator;
    }

    public void run() {
        new MergeSort().parallelSort(items, threadCount, comparator);
    }
}
