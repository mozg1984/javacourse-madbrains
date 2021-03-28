package com.javacourse.homework.part1;

import java.util.Arrays;
import java.util.Comparator;

public class MergeSort {
    // The limit of the number of elements in an array
    // only after exceeding which parallel sorting is possible
    static final int LIMIT = 1 << 13;

    // Arranges the elements of the given array into sorted order
    public <T> void sort(Object[] items, Comparator<T> comparator) {
        if (items == null) {
            throw new IllegalArgumentException("Items is null");
        }

        // recursion exit criteria
        if (items.length < 2) {
            return;
        }

        // split array in half
        Object[] left  = Arrays.copyOfRange(items, 0, items.length / 2);
        Object[] right = Arrays.copyOfRange(items, items.length / 2, items.length);

        // sort the halves
        sort(left, comparator);
        sort(right, comparator);

        // merge them back together
        merge(items, left, right, comparator);
    }

    public <T> void parallelSort(Object[] items, Comparator<T> comparator) {
        // int cores = Runtime.getRuntime().availableProcessors();
        int cores = 8;
        parallelSort(items, cores, comparator);
    }

    public <T> void parallelSort(Object[] items, int threadCount, Comparator<T> comparator) {
        if (threadCount <= 1 || items.length <= LIMIT) {
            sort(items, comparator);
            return;
        }

        if (items == null) {
            throw new IllegalArgumentException("Items is null");
        }

        // recursion exit criteria
        if (items.length < 2) {
            return;
        }

        // split array in half
        Object[] left  = Arrays.copyOfRange(items, 0, items.length / 2);
        Object[] right = Arrays.copyOfRange(items, items.length / 2, items.length);

        // sort the halves
        Thread lThread = new Thread(new Sorter<T>(left,  threadCount / 2, comparator));
        Thread rThread = new Thread(new Sorter<T>(right, threadCount / 2, comparator));
        lThread.start();
        rThread.start();

        try {
            lThread.join();
            rThread.join();
        } catch (InterruptedException ie) {}

        // merge them back together
        merge(items, left, right, comparator);
    }

    // Combines the contents of sorted left/right arrays into output array items
    private <T> void merge(Object[] items, Object[] left, Object[] right, Comparator<T> comparator) {
        int leftIndex = 0;
        int rightIndex = 0;
        int sortedIndex = 0;

        while (leftIndex < left.length && rightIndex < right.length) {
            int comparison = comparator.compare((T) left[leftIndex], (T) right[rightIndex]);
            if (comparison <= 0) {
                items[sortedIndex] = left[leftIndex];
                leftIndex++;
            } else {
                items[sortedIndex] = right[rightIndex];
                rightIndex++;
            }
            sortedIndex++;
        }

        // Handle the left over elements if any in the left side
        // and places them in the sorted array
        while (leftIndex < left.length) {
            items[sortedIndex] = left[leftIndex];
            leftIndex++;
            sortedIndex++;
        }

        // Handle the left over elements if any in the right side.
        // and places them in the sorted array
        while (rightIndex < right.length) {
            items[sortedIndex] = right[rightIndex];
            rightIndex++;
            sortedIndex++;
        }
    }
}
