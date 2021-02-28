package com.khisamutdinov;

import java.util.Collections;

public class Main {

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
    }
}
