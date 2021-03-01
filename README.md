# Custom list implementation
The list is built on the basis of an array, the sorting algorithm is [merge sort](https://en.wikipedia.org/wiki/Merge_sort) (parallel version). By default, the parallel version is only used if the number of elements in the array is greater than `LIMIT = 1 << 13`. Default capacity is `DEFAULT_CAPACITY = 10`
 and maximum supported array size is `MAX_ARRAY_SIZE = Integer.MAX_VALUE - 8`.

The implementation is represented by the class [`MyList<T>`](https://github.com/mozg1984/advanced_list/blob/master/src/com/khisamutdinov/MyList.java).

Example of running merge sorting test:

- MergeSort:

          1000 elements  =>       3 ms
          2000 elements  =>       1 ms
          4000 elements  =>       3 ms
          8000 elements  =>       7 ms
         16000 elements  =>      14 ms
         32000 elements  =>      27 ms
         64000 elements  =>      55 ms
        128000 elements  =>      74 ms
        256000 elements  =>      42 ms
        512000 elements  =>      70 ms
       1024000 elements  =>     136 ms
       2048000 elements  =>     344 ms
       4096000 elements  =>     846 ms
       8192000 elements  =>    1666 ms
      16384000 elements  =>    3527 ms
      32768000 elements  =>    7617 ms

- MergeSort (parallel version with `8 threads`):

          1000 elements  =>       3 ms
          2000 elements  =>       1 ms
          4000 elements  =>       3 ms
          8000 elements  =>       3 ms
         16000 elements  =>       6 ms
         32000 elements  =>      28 ms
         64000 elements  =>      59 ms
        128000 elements  =>      54 ms
        256000 elements  =>      63 ms
        512000 elements  =>     138 ms
       1024000 elements  =>     376 ms
       2048000 elements  =>     705 ms
       4096000 elements  =>    1629 ms
       8192000 elements  =>    3852 ms
      16384000 elements  =>    8111 ms
      32768000 elements  =>   18487 ms
