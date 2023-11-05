
import java.util.Arrays;

/**
 * Merging procedures and some related helpers
 * @author Sebastian Wild (wild@uwaterloo.ca)
 */
public class MergesAndRuns {

    /** turns on the counting of merge costs */
    public static final boolean COUNT_MERGE_COSTS = true;
    /** total merge costs of all merge calls */
    public static long totalMergeCosts = 0;

    /**
     * Merges runs A[l..m-1] and A[m..r] in-place into A[l..r]
     * with Sedgewick's bitonic merge (Program 8.2 in Algorithms in C++)
     * using B as temporary storage.
     * B.length must be at least r+1.
     */


    public static int extendWeaklyIncreasingRunLeft(final int[] A, int i, final int left) {
        while (i > left && A[i-1] <= A[i]) --i;
        return i;
    }

    public static int extendWeaklyIncreasingRunRight(final int[] A, int i, final int right) {
        while (i < right && A[i+1] >= A[i]) ++i;
        return i;
    }

    public static int extendStrictlyDecreasingRunLeft(final int[] A, int i, final int left) {
        while (i > left && A[i-1] > A[i]) --i;
        return i;
    }

    public static int extendStrictlyDecreasingRunRight(final int[] A, int i, final int right) {
        while (i < right && A[i+1] < A[i]) ++i;
        return i;
    }

    public static int extendRunRight(final int[] A, int i, final int right) {
        while (i < right && A[i+1] >= A[i]) ++i;
        return i;
    }

    public static int extendRunLeft(final int[] A, int i, final int left) {
        while (i > left && A[i-1] <= A[i]) --i;
        return i;
    }
}
