import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Random;


/*
THIS CODE WAS MY OWN WORK, IT WAS WRITTEN WITHOUT CONSULTING
CODE WRITTEN BY OTHER STUDENTS OR COPIED FROM ONLINE RESOURCES. Philip Cardozo
*/


public class Sorting {

    //-------------------------------------------------------------
    //---------- Bubble Sort --------
    //-------------------------------------------------------------
    public static void BubbleSort(long[] a) {
        // Standard Bubble Sort: repeatedly swap adjacent out-of-order elements
        // Outer loop runs until all elements are in place (n-1 passes for n elements)
        for (int i = 0; i < a.length - 1; i++) {
            // After i-th pass, the last i elements are in correct place, so inner loop goes till n-i-1
            for (int j = 0; j < a.length - 1 - i; j++) {
                // If adjacent elements are out of order, swap them
                if (a[j] > a[j+1]) {
                    long temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                }
            }
        }
    }

    //-------------------------------------------------------------
    //---------- Optimized Bubble Sort --------
    //-------------------------------------------------------------
    public static void BubbleSortOptimized(long[] a) {
        // Optimized Bubble Sort: stop early if no swaps in a full pass (meaning array is sorted)
        boolean swapped;
        // We assume list not sorted initially, enter loop
        for (int i = 0; i < a.length - 1; i++) {
            swapped = false;
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j+1]) {
                    // Swap out-of-order adjacent pair
                    long temp = a[j];
                    a[j] = a[j+1];
                    a[j+1] = temp;
                    swapped = true;  // mark that we did a swap in this pass
                }
            }
            // If no swaps occurred in this pass, array is already sorted; break out
            if (!swapped) {
                break;
            }
        }
    }

    //------------------------------------------------------------------
    //---------- Insertion Sort ----------
    //------------------------------------------------------------------
    public static void InsertionSort(long[] a) {
        // Insertion Sort: build sorted portion at beginning of array
        // Start with first element as trivially sorted, then insert each subsequent element into sorted portion
        for (int i = 1; i < a.length; i++) {
            long key = a[i];           // element to insert into sorted left side
            int j = i - 1;
            // Shift elements in sorted part to right to make space for key, until correct position found
            while (j >= 0 && a[j] > key) {
                a[j+1] = a[j];        // move element one position to the right
                j--;
            }
            // Insert key into the right position (just after the last element larger than key)
            a[j+1] = key;
        }
    }

    //------------------------------------------------------------
    //---------- Selection Sort ----------
    //------------------------------------------------------------
    public static void SelectionSort(long[] a) {
        // Selection Sort: repeatedly select minimum element from unsorted tail and swap with first unsorted position
        for (int i = 0; i < a.length - 1; i++) {
            int minIndex = i;
            // Find index of smallest element in a[i..end] segment
            for (int j = i + 1; j < a.length; j++) {
                if (a[j] < a[minIndex]) {
                    minIndex = j;
                }
            }
            // Swap the found minimum with the element at position i (first unsorted element)
            if (minIndex != i) {
                long temp = a[i];
                a[i] = a[minIndex];
                a[minIndex] = temp;
            }
        }
    }

    //-----------------------------------------------------------------------
    //---------- Recursive MergeSort (Array) ----------
    //-----------------------------------------------------------------------
    public static void MergeSort(long[] a) {
        // Public interface for recursive MergeSort on array
        // Creates an auxiliary array and calls the recursive sort function
        if (a == null || a.length < 2) return;  // If array has 0 or 1 elements, it's already sorted
        long[] aux = new long[a.length];        // auxiliary array for merges
        sort(a, aux, 0, a.length - 1);
    }

    // Recursively sort subarray a[lo..hi] using auxiliary array aux for merging
    private static void sort(long[] a, long[] aux, int lo, int hi) {
        if (lo >= hi) {
            // Base case: zero or one element in subarray, already sorted
            return;
        }
        int mid = lo + (hi - lo) / 2;
        // Recursively sort the left half [lo..mid]
        sort(a, aux, lo, mid);
        // Recursively sort the right half [mid+1..hi]
        sort(a, aux, mid + 1, hi);
        // Merge the two sorted halves back into a[lo..hi]
        merge(a, aux, lo, mid, hi);
    }

    private static void merge(long[] a, long[] aux, int lo, int mid, int hi) {
        // Merge sorted subarrays a[lo..mid] and a[mid+1..hi] into one sorted sequence
        // Copy both halves into auxiliary array aux
        for (int k = lo; k <= hi; k++) {
            aux[k] = a[k];
        }
        int i = lo;       // pointer for left half (aux[lo..mid])
        int j = mid + 1;  // pointer for right half (aux[mid+1..hi])
        // k iterates from lo to hi to fill a[lo..hi] with sorted elements
        for (int k = lo; k <= hi; k++) {
            if (i > mid) {
                // Left half exhausted; take element from right half
                a[k] = aux[j++];
            } else if (j > hi) {
                // Right half exhausted; take element from left half
                a[k] = aux[i++];
            } else if (aux[i] <= aux[j]) {
                // Next element is smaller in left half; take from left
                a[k] = aux[i++];
            } else {
                // Next element is smaller in right half; take from right
                a[k] = aux[j++];
            }
        }
    }

    //-------------------------------------------------------------
    //---------- QuickSort ----------
    //-------------------------------------------------------------
    public static void QuickSort(long[] a) {
        // Public interface for QuickSort (simple version, not optimized)
        if (a == null || a.length < 2) return;
        QuickSort(a, 0, a.length - 1);
    }

    private static void QuickSort(long[] a, int left, int right) {
        // Standard QuickSort: recursively partition and sort subarrays
        if (left >= right) {
            return;  // Base case: one or no elements in range
        }
        // Choose a pivot index (using rightmost element as pivot for simplicity)
        int pivotIndex = right;
        // Partition the array around the pivot and get the pivot's final position
        int newPivotIndex = partition(a, left, right, pivotIndex);
        // Recursively sort elements on the left of the pivot
        QuickSort(a, left, newPivotIndex - 1);
        // Recursively sort elements on the right of the pivot
        QuickSort(a, newPivotIndex + 1, right);
    }

    private static int partition(long[] a, int left, int right, int pIdx) {
        // Partition the subarray a[left..right] around the pivot at index pIdx
        long pivotValue = a[pIdx];
        // Move pivot element to end (position 'right') for convenience
        if (pIdx != right) {
            long temp = a[pIdx];
            a[pIdx] = a[right];
            a[right] = temp;
        }
        int storeIndex = left;
        // Iterate through subarray [left..right-1], moving smaller elements to the front
        for (int i = left; i < right; i++) {
            if (a[i] < pivotValue) {
                // Swap current element with element at storeIndex and advance storeIndex
                long temp = a[i];
                a[i] = a[storeIndex];
                a[storeIndex] = temp;
                storeIndex++;
            }
        }
        // Place pivot value in its correct sorted position
        long temp = a[storeIndex];
        a[storeIndex] = a[right];
        a[right] = temp;
        // Return the index where pivot is now located
        return storeIndex;
    }

    //-----------------------------------------------------------------------
    // ---------- Optimized QuickSort ----------
    //-----------------------------------------------------------------------
    public static void shuffle(long[] a) {
        // Fisher-Yates shuffle to randomly permute the array (helps avoid worst-case for QuickSort)
        Random rand = new Random();
        for (int i = 0; i < a.length; i++) {
            int r = i + rand.nextInt(a.length - i);
            long temp = a[i];
            a[i] = a[r];
            a[r] = temp;
        }
    }

    public static void QuickSortOptimized(long[] a) {
        if (a == null || a.length < 2) return;
        // Shuffle array to eliminate worst-case patterns (e.g., sorted input)
        shuffle(a);
        // Sort using QuickSort with optimizations (small subarray cutoff)
        QuickSortOptimized(a, 0, a.length - 1);
    }

    private static void QuickSortOptimized(long[] a, int left, int right) {
        // Optimized QuickSort: uses insertion sort for small partitions to improve performance
        int CUTOFF = 10;  // threshold for switching to insertion sort
        if (right <= left) {
            return;
        }
        // If the current subarray size is <= CUTOFF, use insertion sort for this segment
        if (right - left < CUTOFF) {
            for (int i = left + 1; i <= right; i++) {
                long key = a[i];
                int j = i - 1;
                while (j >= left && a[j] > key) {
                    a[j+1] = a[j];
                    j--;
                }
                a[j+1] = key;
            }
            return;  // subarray is sorted, return to previous recursion level
        }
        // Otherwise, proceed with QuickSort partitioning
        int pivotIndex = right;  // choose rightmost element as pivot (array is shuffled, so this is random choice)
        int newPivotIndex = partition(a, left, right, pivotIndex);
        // Recursively sort the subarrays around the pivot
        QuickSortOptimized(a, left, newPivotIndex - 1);
        QuickSortOptimized(a, newPivotIndex + 1, right);
    }

    //---------------------------------------------------------------------------
    // ---------- Non-Recursive MergeSort (Bottom-up MergeSort) ----------
    //---------------------------------------------------------------------------
    public static void MergeSortNonRec(long[] a) {
        // Bottom-up MergeSort: iteratively merge subarrays of increasing size
        int n = a.length;
        if (n < 2) return;
        long[] aux = new long[n];
        // Merge subarrays of size width, doubling width each iteration
        for (int width = 1; width < n; width *= 2) {
            // For each pair of subarrays of current width
            for (int lo = 0; lo < n - width; lo += 2 * width) {
                int mid = lo + width - 1;
                int hi = Math.min(lo + 2 * width - 1, n - 1);
                // Merge a[lo..mid] and a[mid+1..hi]
                merge(a, aux, lo, mid, hi);
            }
        }
    }

    //---------------------------------------------------------------------
    // ---------- Linked List MergeSort (calls MyLinkedList.mergeSort) ----------
    //---------------------------------------------------------------------
    public static void MergeSortLL(MyLinkedList list) {
        // Sort the provided linked list using merge sort (in-place on the list)
        list.mergeSort();
    }

    //------------------------------------------------------
    //---------- Helper methods for testing and array creation ----------
    //------------------------------------------------------
    public static boolean testSort(long[] a) {
        // Test if array a is sorted correctly by comparing to Java's built-in sort result
        long[] aCopy = new long[a.length];
        System.arraycopy(a, 0, aCopy, 0, a.length);
        Arrays.sort(a);
        for (int i = 0; i < a.length; i++) {
            if (aCopy[i] != a[i]) {
                return false;
            }
        }
        return true;
    }

    // Create an array of n random elements in range (0, n*10]
    private static long[] randArray(int n) {
        long[] rand = new long[n];
        for (int i = 0; i < n; i++) {
            rand[i] = (long) (Math.random() * n * 10);
        }
        return rand;
    }

    // Create an array of n elements in ascending order (1..n)
    private static long[] orderedArray(int n) {
        long[] arr = new long[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i + 1;
        }
        return arr;
    }

    // Copy array elements into a linked list (to prepare for MergeSort on linked list)
    private static void copyArrayToLinkedList(long[] arr, MyLinkedList list) {
        for (int i = 0; i < arr.length; i++) {
            list.addLast(arr[i]);
        }
    }

    private static long timestamp;
    private static void startTimer() {
        timestamp = System.nanoTime();
    }
    private static double endTimer() {
        return (System.nanoTime() - timestamp) / 1000000.0;  // convert to milliseconds
    }

    //---------------------------------------------
    //---------- Main method (experiment runner) ----------
    //---------------------------------------------
    public static void main(String[] args) {
        // Flag to test with an already sorted array (corner case)
        boolean useOrderedArray = false;

        // Algorithm indices for reference
        final int BUBBLE = 0, BUBBLEOPT = 1, SELECT = 2, INSERT = 3, QUICK = 4,
                  QUICKOPT = 5, MERGEREC = 6, MERGENONREC = 7, MERGELINKEDLIST = 8;
        int[] algorithms = { BUBBLE, BUBBLEOPT, SELECT, INSERT, MERGEREC,
                              MERGENONREC, MERGELINKEDLIST, QUICK, QUICKOPT };

        // max exponent for array size (we will test sizes 2^1, 2^2, ..., 2^max)
        int max = 14;
        // number of runs to average for each input size (to smooth out fluctuations)
        int runs = 5;
        double[][] stats = new double[algorithms.length][max];
        MyLinkedList list; // linked list for MergeSortLL

        // Loop through each sorting algorithm
        for (int algIndex = 0; algIndex < algorithms.length; algIndex++) {
            switch (algIndex) {
                case BUBBLE: 
                    System.out.print("Running Bubble Sort ..."); 
                    break;
                case BUBBLEOPT: 
                    System.out.print("Running Optimized Bubble Sort ..."); 
                    break;
                case SELECT: 
                    System.out.print("Running Selection Sort ..."); 
                    break;
                case INSERT: 
                    System.out.print("Running Insertion Sort ..."); 
                    break;
                case QUICK: 
                    System.out.print("Running Quicksort ..."); 
                    break;
                case QUICKOPT: 
                    System.out.print("Running Optimized Quicksort ..."); 
                    break;
                case MERGEREC: 
                    System.out.print("Running MergeSort Recursive ..."); 
                    break;
                case MERGENONREC: 
                    System.out.print("Running MergeSort Non Recursive ..."); 
                    break;
                case MERGELINKEDLIST: 
                    System.out.print("Running MergeSort for Linked List ..."); 
                    break;
            }
            // Loop through each array size (powers of 2 from 2^1 up to 2^max)
            for (int j = 0; j < max; j++) {
                int n = (int) Math.pow(2, j+1);   // array size
                double avgTime = 0.0;
                // Run 'runs' iterations for averaging
                for (int k = 0; k < runs; k++) {
                    long[] a;
                    if (useOrderedArray) {
                        a = orderedArray(n);
                    } else {
                        a = randArray(n);
                    }
                    // Prepare linked list copy for MergeSortLL
                    list = new MyLinkedList();
                    copyArrayToLinkedList(a, list);

                    startTimer();
                    // Execute the sorting algorithm based on algIndex
                    switch (algIndex) {
                        case BUBBLE: BubbleSort(a); break;
                        case BUBBLEOPT: BubbleSortOptimized(a); break;
                        case SELECT: SelectionSort(a); break;
                        case INSERT: InsertionSort(a); break;
                        case MERGEREC: MergeSort(a); break;
                        case MERGENONREC: MergeSortNonRec(a); break;
                        case MERGELINKEDLIST: MergeSortLL(list); break;
                        case QUICK: QuickSort(a); break;
                        case QUICKOPT: QuickSortOptimized(a); break;
                    }
                    double time = endTimer();
                    avgTime += time;
                    // If we sorted the linked list, copy results back to array for verification
                    if (algIndex == MERGELINKEDLIST) {
                        a = list.toArray();
                    }
                    // Verify that the array is sorted correctly
                    if (!testSort(a)) {
                        System.out.println("The sorting is INCORRECT! (N=" + a.length + ", run=" + k + ")");
                    }
                }
                avgTime /= runs;
                stats[algIndex][j] = avgTime;
            }
            System.out.println("done.");
        }

        // Print header for results table
        DecimalFormat format = new DecimalFormat("0.0000");
        System.out.println();
        System.out.println("Average running time:");
        System.out.println("N\tBubbleSort\tBubbleSortOpt\tSelectionSort\tInsertionSort\tQuickSort\tQuickSortOpt\tMergeSortRec\tMergeSortNon\tMergeSortList");
        // Print each row of results (for each array size)
        for (int j = 0; j < stats[0].length; j++) {
            int size = (int) Math.pow(2, j+1);
            System.out.print(size + "\t");
            for (int i = 0; i < stats.length; i++) {
                System.out.print(format.format(stats[i][j]) + "\t\t");
            }
            System.out.println();
        }
    }
}
