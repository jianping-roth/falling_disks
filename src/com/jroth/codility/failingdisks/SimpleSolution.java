package com.jroth.codility.failingdisks;

/**
 * A[0] = 5 B[0] = 2 A[1] = 6 B[1] = 3 A[2] = 4 B[2] = 5 A[3] = 3 B[3] = 2 A[4]
 * = 6 B[4] = 4 A[5] = 2 A[6] = 3
 * 
 * @author Jianping
 *  cert3KN446-RGPERMBC6FUEMAFP
 *         http://codility.com/cert/view/certDU699J-KSXGUVJTCZRY7SSF/details
 * 
 *         max_full pipe of max size full of disks 2.140 s. TIMEOUT ERROR
 *         running time: >2.14 sec., time limit: 1.28 sec. max_random maximum
 *         size random data 2.150 s. TIMEOUT ERROR running time: >2.15 sec.,
 *         time limit: 1.36 sec. 87%
 */
public class SimpleSolution {
    static int getNumberDisks(int[] A, int[] B) {
        int currA = A.length - 1;
        int currB = 0;
        for (int b = 0; b < B.length; b++) {
            boolean add = true;
            int sinkIndex = -1;
            for (int a = 0; a < currA; a++) {
                if (B[b] > A[a]) {
                    sinkIndex = a - 1;
                    if (sinkIndex < 0) {
                        add = false;
                    }
                    break;
                }
            }
            if (add) {
                currB++;
            }

            // adjust the bottom
            if (sinkIndex >= 0) {
                // adjust the bottom to the one above the sink ring
                currA = sinkIndex - 1;
            } else {
                currA--;
            }

            if (currA < 0) {
                break;
            }
        }

        return currB;
    }

    // ([6, 15, 5, 17, 5, 20, 16, 17, 10, 13, 8, 10], [7, 4, 4, 3, 1])
    public static void main(String[] args) {
        {
            int A[] = { 6, 15, 5, 17, 5, 20, 16, 17, 10, 13, 8, 10 };
            int B[] = { 7, 4, 4, 3, 1 };
            int expected = 4;
            int result = getNumberDisks(A, B);
            if (result != expected) {
                System.out.println("expected : " + expected + " but got "
                        + result);
            } else {
                System.out.println("passed");
            }
        }
        {
            int A[] = { 5, 6, 4, 3, 6, 2, 3 };
            int B[] = { 2, 3, 5, 2, 4 };
            int expected = 4;
            int result = getNumberDisks(A, B);
            if (result != expected) {
                System.out.println("expected : " + expected + " but got "
                        + result);
            } else {
                System.out.println("passed");
            }
        }

        {
            int A[] = { 10, 15, 8, 11, 15, 11, 14, 10, 10, 18, 5, 15 };
            int B[] = { 3, 6, 5, 1, 6 };

            int expected = 5;
            int result = getNumberDisks(A, B);
            if (result != expected) {
                System.out.println("expected : " + expected + " but got "
                        + result);
            } else {
                System.out.println("passed");
            }
        }
    }
}
