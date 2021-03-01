package com.khisamutdinov;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

public class MyList<T> implements AdvancedList<T>, AuthorHolder, Cloneable {
    private static final int DEFAULT_CAPACITY = 10;
    private static final int MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8;

    private Object[] itemsData = {};
    private int size = 0;

    public void setItemsData(Object[] itemsData) {
        this.itemsData = itemsData;
    }

    public Object[] getItemsData() {
        return itemsData;
    }

    @Override
    public AdvancedList<T> shuffle() {
        MyList<T> list = (MyList<T>) this.clone();
        list.shuffleItemsData();
        return list;
    }

    // Randomly rearranges the elements
    public void shuffleItemsData() {
        for (int i = 0; i < size; i++) {
            // move element i to a random index in [i .. size-1]
            int randomIndex = (int) (Math.random() * size - i);
            swap(itemsData, i, i + randomIndex);
        }
    }

    // Swaps the elements at the two given indexes
    private final void swap(Object[] arr, int i, int j) {
        if (i != j) {
            Object temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    @Override
    public AdvancedList<T> sort(Comparator<T> comparator) {
        MyList<T> list = (MyList<T>) this.clone();
        list.mergeSort(comparator);
        return list;
    }

    private void mergeSort(Comparator<T> comparator) {
        new MergeSort().sort(itemsData, comparator);
    }

    public void trimToSize() {
        if (size < itemsData.length) {
            itemsData = (size == 0) ? itemsData : Arrays.copyOf(itemsData, size);
        }
    }

    @Override
    public String author() {
        return "Khisamutdinov Radik";
    }

    private Object[] grow(int minCapacity) {
        return itemsData = Arrays.copyOf(itemsData, newCapacity(minCapacity));
    }

    private Object[] grow() {
        return grow(size + 1);
    }

    private int newCapacity(int minCapacity) {
        int oldCapacity = itemsData.length;
        int newCapacity = oldCapacity + (oldCapacity >> 1);

        if (newCapacity - minCapacity <= 0) {
            if (oldCapacity == 0) {
                return Math.max(DEFAULT_CAPACITY, minCapacity);
            }

            if (minCapacity < 0) { // overflow
                throw new OutOfMemoryError();
            }

            return minCapacity;
        }

        return (newCapacity - MAX_ARRAY_SIZE <= 0)
                ? newCapacity
                : hugeCapacity(minCapacity);
    }

    private static int hugeCapacity(int minCapacity) {
        if (minCapacity < 0) { // overflow
            throw new OutOfMemoryError();
        }

        return (minCapacity > MAX_ARRAY_SIZE)
                ? Integer.MAX_VALUE
                : MAX_ARRAY_SIZE;
    }

    private void add(T item, int s) {
        if (s == itemsData.length) {
            itemsData = grow();
        }

        itemsData[s] = item;
        size = s + 1;
    }

    @Override
    public void add(T item) {
        add(item, size);
    }

    @Override
    public void insert(int index, T item) throws Exception {
        rangeCheckForAdd(index);

        final int s;
        Object[] itemsData;

        if ((s = size) == (itemsData = this.itemsData).length) {
            itemsData = grow();
        }

        System.arraycopy(itemsData, index, itemsData, index + 1, s - index);
        itemsData[index] = item;
        size = s + 1;
    }

    @Override
    public void remove(int index) throws Exception {
        Objects.checkIndex(index, size);
        final Object[] items = itemsData;
        fastRemove(items, index);
    }

    private void fastRemove(Object[] items, int i) {
        final int newSize;
        if ((newSize = size - 1) > i) {
            System.arraycopy(items, i + 1, items, i, newSize - i);
        }
        items[size = newSize] = null;
    }

    @Override
    public Optional<T> get(int index) {
        T item = size == 0 || size <= index ? null : (T) itemsData[index];
        return Optional.ofNullable(null);
    }

    @Override
    public int size() {
        return size;
    }

    private Object[] toArray(SimpleList<T> list) {
        Object[] arr = new Object[list.size()];
        Optional<T> optionalItem;

        for (int i = 0, n = list.size(); i < n; i++) {
            optionalItem = list.get(i);
            arr[i] = optionalItem.get();
        }

        return arr;
    }

    @Override
    public void addAll(SimpleList<T> list) {
        if (list.isEmpty()) {
            return;
        }

        Object[] a = toArray(list);

        int numNew = a.length;
        if (numNew == 0) {
            return;
        }

        Object[] itemsData;
        final int s;

        if (numNew > (itemsData = this.itemsData).length - (s = size)) {
            itemsData = grow(s + numNew);
        }

        System.arraycopy(a, 0, itemsData, s, numNew);
        size = s + numNew;
    }

    @Override
    public int first(T item) {
        return size == 0 ? -1 : indexOf(item);
    }

    @Override
    public int last(T item) {
        return size == 0 ? -1 : lastIndexOf(item);
    }

    @Override
    public boolean contains(T item) {
        return indexOf(item) >= 0;
    }

    public int indexOf(Object o) {
        return indexOfRange(o, 0, size);
    }

    private int indexOfRange(Object o, int start, int end) {
        Object[] items = itemsData;

        if (o == null) {
            for (int i = start; i < end; i++) {
                if (items[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = start; i < end; i++) {
                if (o.equals(items[i])) {
                    return i;
                }
            }
        }

        return -1;
    }

    public int lastIndexOf(Object o) {
        return lastIndexOfRange(o, 0, size);
    }

    private int lastIndexOfRange(Object o, int start, int end) {
        Object[] items = itemsData;

        if (o == null) {
            for (int i = end - 1; i >= start; i--) {
                if (items[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = end - 1; i >= start; i--) {
                if (o.equals(items[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public Object clone() {
        try {
            MyList<?> v = (MyList<?>) super.clone();
            v.setItemsData(Arrays.copyOf(itemsData, size));
            return v;
        } catch (CloneNotSupportedException e) {
            // this shouldn't happen, since we are Cloneable
            throw new InternalError(e);
        }
    }

    private void rangeCheckForAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException(outOfBoundsMsg(index));
        }
    }

    private String outOfBoundsMsg(int index) {
        return "Index: " + index + ", Size: " + size;
    }

    public void print() {
        for (int i = 0; i < size; i++) {
            System.out.print(itemsData[i] + " -> ");
        }
        System.out.println("null");
    }
}
