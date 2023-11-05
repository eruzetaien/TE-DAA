import java.io.*;
import java.util.*;

/**
 * Source :
 * - Peek Sort : https://github.com/sebawild/nearly-optimal-mergesort-code
 * - Radix Sort : https://www.geeksforgeeks.org/radix-sort/
 */

public class Main {

    public static int[] smallSortedArray = new int[1000];
    public static int[] smallRandomArray = new int[1000];
    public static int[] smallReverseArray = new int[1000];

    public static int[] mediumSortedArray = new int[10000];
    public static int[] mediumRandomArray = new int[10000];
    public static int[] mediumReverseArray = new int[10000];

    public static int[] largeSortedArray = new int[100000];
    public static int[] largeRandomArray = new int[100000];
    public static int[] largeReverseArray = new int[100000];

    public static void mergeRuns(int[] A, int l, int m, int r, int[] B) {
        --m;// mismatch in convention with Sedgewick
        int i, j;
        assert B.length >= r+1;
        for (i = m+1; i > l; --i) B[i-1] = A[i-1];
        for (j = m; j < r; ++j) B[r+m-j] = A[j+1];
        for (int k = l; k <= r; ++k)
            A[k] = B[j] < B[i] ? B[j--] : B[i++];
    }

    public static int extendRunRight(final int[] A, int i, final int right) {
        while (i < right && A[i+1] >= A[i]) ++i;
        return i;
    }

    public static int extendRunLeft(final int[] A, int i, final int left) {
        while (i > left && A[i-1] <= A[i]) --i;
        return i;
    }

    public static void peeksort(final int[] a, final int l, final int r) {
        int n = r - l + 1;
        peeksort(a, l, r, l, r, new int[n]);
    }

    public static void peeksort(int[] A, int left, int right, int leftRunEnd, int rightRunStart, final int[] B) {
        if (leftRunEnd == right || rightRunStart == left) return;

        int mid = left + (right-left)/2;
        if (mid <= leftRunEnd) {
            // |XXXXXXXX|XX     X|
            peeksort(A, leftRunEnd+1, right, leftRunEnd+1,rightRunStart, B);
            mergeRuns(A, left, leftRunEnd+1, right, B);
        } else if (mid >= rightRunStart) {
            // |XX     X|XXXXXXXX|
            peeksort(A, left, rightRunStart-1, leftRunEnd, rightRunStart-1, B);
            mergeRuns(A, left, rightRunStart, right, B);
        } else {
            // find middle run
            final int i, j;
            i = extendRunLeft(A, mid, left);
            j = extendRunRight(A, mid, right);

            if (i == left && j == right) return;
            if (mid - i < j - mid) {
                // |XX     x|xxxx   X|
                peeksort(A, left, i-1, leftRunEnd, i-1, B);
                peeksort(A, i, right, j, rightRunStart, B);
                mergeRuns(A,left, i, right, B);
            } else {
                // |XX   xxx|x      X|
                peeksort(A, left, j, leftRunEnd, i, B);
                peeksort(A, j+1, right, j+1, rightRunStart, B);
                mergeRuns(A,left, j+1, right, B);
            }
        }
    }


    // A function to do counting sort of arr[]
    // according to the digit
    // represented by exp.
    public static void countSort(int arr[], int exp)
    {
        int n = arr.length;

        // Output array
        int[] output = new int[n];
        // Array yang berisi frekuensi tiap elemen
        int[] count = new int[10];
        int i;

        // Menghitung frekuensi tiap elemen
        for (i = 0; i < n; i++)
            count[(arr[i] / exp) % 10]++;

        // Menghitung frekuensi kumulatif tiap elemen
        for (i = 1; i < 10; i++)
            count[i] += count[i - 1];

        // Menyusun output array sesuai dengan frekuensi kumulatif
        for (i = n - 1; i >= 0; i--) {
            output[count[(arr[i] / exp) % 10] - 1] = arr[i];
            count[(arr[i] / exp) % 10]--;
        }

        // Menyalin output array ke array asli
        for (i = 0; i < n; i++)
            arr[i] = output[i];
    }

    public static int getMax(int[] arr){
        int n = arr.length;

        int mx = arr[0];
        for (int i = 1; i < n; i++)
            if (arr[i] > mx)
                mx = arr[i];
        return mx;
    }

    // The main function to that sorts arr[]
    // of size n using Radix Sort
    public static void radixsort(int arr[])
    {
        int m = getMax(arr);

        for (int exp = 1; m / exp > 0; exp *= 10)
            countSort(arr, exp);
    }

    private static void initiateSortedArray(){
        Random rand = new Random();

        smallSortedArray[0] = rand.nextInt(100);
        mediumSortedArray[0] = rand.nextInt(100);
        largeSortedArray[0] = rand.nextInt(100);

        for(int i = 1; i < 1000; i++){
            smallSortedArray[i] = smallSortedArray[i-1] + rand.nextInt(20);
            mediumSortedArray[i] = mediumSortedArray[i-1] + rand.nextInt(20);
            largeSortedArray[i] = largeSortedArray[i-1] + rand.nextInt(20);
        }

        for (int i = 1000; i < 10000; i++){
            mediumSortedArray[i] = mediumSortedArray[i-1] + rand.nextInt(20);
            largeSortedArray[i] = largeSortedArray[i-1] + rand.nextInt(20);
        }

        for (int i = 10000; i < 100000; i++){
            largeSortedArray[i] = largeSortedArray[i-1] + rand.nextInt(20);
        }
    }

    private static void initiateRandomArray(){
        Random rand = new Random();

        for(int i = 0; i < 1000; i++){
            smallRandomArray[i] = rand.nextInt(1000);
            mediumRandomArray[i] = rand.nextInt(1000);
            largeRandomArray[i] = rand.nextInt(1000);
        }

        for (int i = 1000; i < 10000; i++){
            mediumRandomArray[i] = rand.nextInt(1000);
            largeRandomArray[i] = rand.nextInt(1000);
        }

        for (int i = 10000; i < 100000; i++){
            largeRandomArray[i] = rand.nextInt(1000);
        }
    }

    private static void initiateReverseArray(){
        Random rand = new Random();

        int n = smallSortedArray.length - 1;
        for(int i = 0; i <= n; i++){
            smallReverseArray[n-i] = smallSortedArray[i];
        }

        n = mediumSortedArray.length - 1;
        for(int i = 0; i <= n; i++){
            mediumReverseArray[n-i] = mediumSortedArray[i];
        }

        n = largeSortedArray.length - 1;
        for(int i = 0; i <= n; i++){
            largeReverseArray[n-i] = largeSortedArray[i];
        }
    }

    public static void exportDatasets(){
        initiateSortedArray();
        initiateRandomArray();
        initiateReverseArray();

        exportDataset(smallSortedArray, "smallSortedArray.csv");
        exportDataset(mediumSortedArray, "mediumSortedArray.csv");
        exportDataset(largeSortedArray, "largeSortedArray.csv");

        exportDataset(smallRandomArray, "smallRandomArray.csv");
        exportDataset(mediumRandomArray, "mediumRandomArray.csv");
        exportDataset(largeRandomArray, "largeRandomArray.csv");

        exportDataset(smallReverseArray, "smallReverseArray.csv");
        exportDataset(mediumReverseArray, "mediumReverseArray.csv");
        exportDataset(largeReverseArray, "largeReverseArray.csv");

    }

    public static void exportDataset(int[] arr, String filename){
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            for (int element : arr) {
                writer.println(element);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static int[] importDataset(String filename){
        String pathFileCSV = filename;

        try (BufferedReader reader = new BufferedReader(new FileReader(pathFileCSV))) {
            String line;
            List<Integer> integerArray = new ArrayList<>();

            // Membaca baris demi baris dari file CSV.
            while ((line = reader.readLine()) != null) {
                // Membagi baris menjadi elemen dengan pemisah tertentu (biasanya koma dalam CSV).
                String[] parts = line.split(",");

                if (parts.length > 0) {
                    try {
                        // Mengambil nilai integer dari kolom pertama (indeks 0).
                        int intValue = Integer.parseInt(parts[0]);
                        integerArray.add(intValue);
                    } catch (NumberFormatException e) {
                        // Tangani kesalahan jika ada data yang tidak valid.
                        System.err.println("Data tidak valid: " + line);
                    }
                }
            }

            // Array integerArray sekarang berisi semua data dari file CSV.
            // Anda dapat melakukan operasi apapun yang Anda butuhkan pada array ini.
            return integerArray.stream().mapToInt(i -> i).toArray();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void checkRunningTime(){

        final double MILI2NANO = 1e6;

        double[][] peekSortTime = new double[3][3];
        double[][] radixSortTime = new double[3][3];

        double startTime, endTime;

        // Small Sorted Array
        int[] smallSortedArrayCopy = smallSortedArray.clone();

        startTime = System.nanoTime()/MILI2NANO;
        peeksort(smallSortedArray, 0, smallSortedArray.length-1);
        endTime = System.nanoTime()/MILI2NANO;
        peekSortTime[0][0] = (endTime - startTime);

        startTime = System.nanoTime()/MILI2NANO;
        radixsort(smallSortedArrayCopy);
        endTime = System.nanoTime()/MILI2NANO;
        radixSortTime[0][0] = (endTime - startTime);

        // Medium Sorted Array
        int[] mediumSortedArrayCopy = mediumSortedArray.clone();

        startTime = System.nanoTime()/MILI2NANO;
        peeksort(mediumSortedArray, 0, mediumSortedArray.length-1);
        endTime = System.nanoTime()/MILI2NANO;
        peekSortTime[0][1] = (endTime - startTime);

        startTime = System.nanoTime()/MILI2NANO;
        radixsort(mediumSortedArrayCopy);
        endTime = System.nanoTime()/MILI2NANO;
        radixSortTime[0][1] = (endTime - startTime);

        // Large Sorted Array
        int[] largeSortedArrayCopy = largeSortedArray.clone();

        startTime = System.nanoTime()/MILI2NANO;
        peeksort(largeSortedArray, 0, largeSortedArray.length-1);
        endTime = System.nanoTime()/MILI2NANO;
        peekSortTime[0][2] = (endTime - startTime);

        startTime = System.nanoTime()/MILI2NANO;
        radixsort(largeSortedArrayCopy);
        endTime = System.nanoTime()/MILI2NANO;
        radixSortTime[0][2] = (endTime - startTime);

        // ===================================================== //

        // Small Random Array
        int[] smallRandomArrayCopy = smallRandomArray.clone();

        startTime = System.nanoTime()/MILI2NANO;
        peeksort(smallRandomArray, 0, smallRandomArray.length-1);
        endTime = System.nanoTime()/MILI2NANO;
        peekSortTime[1][0] = (endTime - startTime);

        startTime = System.nanoTime()/MILI2NANO;
        radixsort(smallRandomArrayCopy);
        endTime = System.nanoTime()/MILI2NANO;
        radixSortTime[1][0] = (endTime - startTime);

        // Medium Random Array
        int[] mediumRandomArrayCopy = mediumRandomArray.clone();

        startTime = System.nanoTime()/MILI2NANO;
        peeksort(mediumRandomArray, 0, mediumRandomArray.length-1);
        endTime = System.nanoTime()/MILI2NANO;
        peekSortTime[1][1] = (endTime - startTime);

        startTime = System.nanoTime()/MILI2NANO;
        radixsort(mediumRandomArrayCopy);
        endTime = System.nanoTime()/MILI2NANO;
        radixSortTime[1][1] = (endTime - startTime);

        // Large Random Array
        int[] largeRandomArrayCopy = largeRandomArray.clone();

        startTime = System.nanoTime()/MILI2NANO;
        peeksort(largeRandomArray, 0, largeRandomArray.length-1);
        endTime = System.nanoTime()/MILI2NANO;
        peekSortTime[1][2] = (endTime - startTime);

        startTime = System.nanoTime()/MILI2NANO;
        radixsort(largeRandomArrayCopy);
        endTime = System.nanoTime()/MILI2NANO;
        radixSortTime[1][2] = (endTime - startTime);


        // ===================================================== //

        // Small Reverse Array
        int[] smallReverseArrayCopy = smallReverseArray.clone();

        startTime = System.nanoTime()/MILI2NANO;
        peeksort(smallReverseArray, 0, smallReverseArray.length-1);
        endTime = System.nanoTime()/MILI2NANO;
        peekSortTime[2][0] = (endTime - startTime);

        startTime = System.nanoTime()/MILI2NANO;
        radixsort(smallReverseArrayCopy);
        endTime = System.nanoTime()/MILI2NANO;
        radixSortTime[2][0] = (endTime - startTime);

        // Medium Reverse Array
        int[] mediumReverseArrayCopy = mediumReverseArray.clone();

        startTime = System.nanoTime()/MILI2NANO;
        peeksort(mediumReverseArray, 0, mediumReverseArray.length-1);
        endTime = System.nanoTime()/MILI2NANO;
        peekSortTime[2][1] = (endTime - startTime);

        startTime = System.nanoTime()/MILI2NANO;
        radixsort(mediumReverseArrayCopy);
        endTime = System.nanoTime()/MILI2NANO;
        radixSortTime[2][1] = (endTime - startTime);

        // Large Reverse Array
        int[] largeReverseArrayCopy = largeReverseArray.clone();

        startTime = System.nanoTime()/MILI2NANO;
        peeksort(largeReverseArray, 0, largeReverseArray.length-1);
        endTime = System.nanoTime()/MILI2NANO;
        peekSortTime[2][2] = (endTime - startTime);

        startTime = System.nanoTime()/MILI2NANO;
        radixsort(largeReverseArrayCopy);
        endTime = System.nanoTime();
        radixSortTime[2][2] = (endTime - startTime);


        System.out.println("Sorted Array");
        System.out.println(Arrays.toString(peekSortTime[0]));
        System.out.println(Arrays.toString(radixSortTime[0]));

        System.out.println("Random Array");
        System.out.println(Arrays.toString(peekSortTime[1]));
        System.out.println(Arrays.toString(radixSortTime[1]));

        System.out.println("Reverse Array");
        System.out.println(Arrays.toString(peekSortTime[2]));
        System.out.println(Arrays.toString(radixSortTime[2]));
    }

    public static void importDataset(){
        smallSortedArray = importDataset( "smallSortedArray.csv");
        mediumSortedArray = importDataset( "mediumSortedArray.csv");
        largeSortedArray = importDataset( "largeSortedArray.csv");

        smallRandomArray = importDataset( "smallRandomArray.csv");
        mediumRandomArray = importDataset( "mediumRandomArray.csv");
        largeRandomArray = importDataset( "largeRandomArray.csv");

        smallReverseArray = importDataset( "smallReverseArray.csv");
        mediumReverseArray = importDataset( "mediumReverseArray.csv");
        largeReverseArray = importDataset( "largeReverseArray.csv");
    }

    public static void checkPeekSortMemoryUsage(String fileName){
        int[] arr = importDataset(fileName);

        peeksort(arr, 0, arr.length-1);

        // Run the garbage collector
//        Runtime.getRuntime().gc();
        // Calculate the used memory
        long memoryUed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Used memory For Peek Sort " + memoryUed + " bytes");
    }

    public static void checkRadixSortMemoryUsage(String fileName){
        int[] arr = importDataset(fileName);

        radixsort(arr);

        // Run the garbage collector
        Runtime.getRuntime().gc();
        // Calculate the used memory
        long memoryUed = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        System.out.println("Used memory For Radix Sort " + memoryUed + " bytes");
    }

    public static void main(String[] args) {

    }



}