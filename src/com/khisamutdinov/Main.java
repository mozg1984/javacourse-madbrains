package com.khisamutdinov;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class Main {
    private static final Random RAND = new Random(42); // random number generator
    private static final int LENGTH = 1000; // initial length of array to sort
    private static final int RUNS   =  16;  // how many times to grow by 2?

    public static Integer[] createRandomArray(int length) {
        Integer[] array = new Integer[length];
        for (int i = 0, n = array.length; i < n; i++) {
            array[i] = RAND.nextInt(1000000);
        }
        return array;
    }

    public static void testMergeSort() {
        int length = LENGTH;
        int runs = RUNS;

        for (int i = 1; i <= runs; i++) {
            Integer[] arr = createRandomArray(length);

            // run the algorithm and time how long it takes
            long startTime = System.currentTimeMillis();
            new MergeSort().sort(arr, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });
            long endTime = System.currentTimeMillis();

            System.out.printf("%10d elements  =>  %6d ms \n", length, endTime - startTime);
            length *= 2; // double size of array for next time
        }
    }

    public static void testParallelMergeSort() {
        int length = LENGTH;
        int runs = RUNS;

        for (int i = 1; i <= runs; i++) {
            Integer[] arr = createRandomArray(length);

            // run the algorithm and time how long it takes
            long startTime = System.currentTimeMillis();
            new MergeSort().parallelSort(arr, new Comparator<Integer>() {
                @Override
                public int compare(Integer o1, Integer o2) {
                    return o1.compareTo(o2);
                }
            });
            long endTime = System.currentTimeMillis();

            System.out.printf("%10d elements  =>  %6d ms \n", length, endTime - startTime);
            length *= 2; // double size of array for next time
        }
    }

    public static void main(String[] args) {
        MyList<Integer> myList = new MyList<>();
        myList.add(10);
        myList.add(1);
        myList.add(9);
        myList.add(2);
        myList.add(8);
        myList.add(3);
        myList.add(7);
        myList.add(4);
        myList.add(6);
        myList.add(5);

        // 10 -> 1 -> 9 -> 2 -> 8 -> 3 -> 7 -> 4 -> 6 -> 5 -> null
        myList.print();
        System.out.println(myList.size());
        System.out.println(myList.first(5));
        System.out.println(myList.contains(6));

        MyList<Integer> myList2 = new MyList<>();
        myList.add(110);
        myList.add(101);
        myList.add(109);
        myList.add(102);
        myList.add(108);
        myList.add(103);
        myList.add(107);
        myList.add(104);
        myList.add(106);
        myList.add(105);

        myList.addAll(myList2);
        // 10 -> 1 -> 9 -> 2 -> 8 -> 3 -> 7 -> 4 -> 6 -> 5 -> 110 -> 101 -> 109 -> 102 -> 108 -> 103 -> 107 -> 104 -> 106 -> 105 -> null
        myList.print();

        // 4 -> 8 -> 105 -> 108 -> 9 -> 102 -> 101 -> 110 -> 10 -> 5 -> 2 -> 1 -> 103 -> 104 -> 3 -> 6 -> 109 -> 107 -> 106 -> 7 -> null
        // AdvancedList<Integer> shuffledList = myList.shuffle();

        // 1 -> 2 -> 3 -> 4 -> 5 -> 6 -> 7 -> 8 -> 9 -> 10 -> 101 -> 102 -> 103 -> 104 -> 105 -> 106 -> 107 -> 108 -> 109 -> 110 -> null
        // Comparator<Integer> comparator = Comparator.comparing(Integer::intValue);
        AdvancedList<Integer> sortedList = myList.sort(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });

//        testMergeSort();
//        testParallelMergeSort();
    }
}
