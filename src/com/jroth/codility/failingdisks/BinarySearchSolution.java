/**
 * There is an old dry well. Its sides are made of concrete rings. Each such ring is one meter high, but the rings can have different (internal) diameters. Nevertheless, all the rings are centered on one another. The well is N meters deep; that is, there are N concrete rings inside it.
You are about to drop M concrete disks into the well. Each disk is one meter thick, and different disks can have different diameters. Once each disk is dropped, it falls down until: * it hits the bottom of the well; * it hits a ring whose internal diameter is smaller then the disk's diameter; or * it hits a previously dropped disk. (Note that if the internal diameter of a ring and the diameter of a disk are equal, then the disk can fall through the ring.)
The disks you are about to drop are ready and you know their diameters, as well as the diameters of all the rings in the well. The question arises: how many of the disks will fit into the well?
Write a function:
int falling_disks(int A[], int N, int B[], int M);
that, given two zero-indexed arrays of integers − A, containing the internal diameters of the N rings (in top-down order), and B, containing the diameters of the M disks (in the order they are to be dropped) − returns the number of disks that will fit into the well.
For example, given the following two arrays:
  A[0] = 5    B[0] = 2
  A[1] = 6    B[1] = 3
  A[2] = 4    B[2] = 5
  A[3] = 3    B[3] = 2
  A[4] = 6    B[4] = 4
  A[5] = 2
  A[6] = 3
the function should return 4, as all but the last of the disks will fit into the well. The figure shows the situation after dropping four disks.

Assume that:
Complexity:
expected worst-case time complexity is O(N);
expected worst-case space complexity is O(N), beyond input storage (not counting the storage required for input arguments).
Elements of input arrays can be modified.
Copyright 2009–2013 by Codility Limited. All Rights Reserved. Unauthorized copying, publication or disclosure prohibited.
 */
package com.jroth.codility.failingdisks;

import java.util.ArrayList;
import java.util.List;

/**
 * The idea is to compute a cone with the largest diameter at the top and smallest at the bottom, skipping all 
 * the intermediate rings. A list of WellRing is created at the start to keep the cone. Since the cone
 * is sorted, so, I can use binary search to locate the smallest ring that is larger than the disk. 
 * 
 * Feb 5, 2013
 * @author Jianping
 */
public class BinarySearchSolution {
    static class WellRing {
        final int diameter;
        final int index;

        WellRing(int diameter, int index) {
            this.diameter = diameter;
            this.index = index;
        }

        @Override
        public String toString() {
            return "dia=" + diameter + ";index=" + index;
        }
    }

    public int falling_disks(int[] A, int[] B) {
        if (A.length == 0) {
            return 0;
        }
        
        // build the cone. the cone is sorted from the largest to the smallest
        List<WellRing> cone = new ArrayList<WellRing>(A.length);
        int min = Integer.MAX_VALUE;
        for (int a = 0; a < A.length; a++) {
            if (A[a] < min) {
                min = A[a];
                cone.add(new WellRing(A[a], a));
            }
        }

        WellRing[] coneArray = cone.toArray(new WellRing[cone.size()]);
        int bottomCone = coneArray.length - 1;

        // throw disks to the well
        int bottomWell = A.length - 1;
        int count = 0;
        for (int b : B) {
            if (b <= coneArray[bottomCone].diameter) {
                // bottom
                bottomWell = bottomWell - 1;
            }
            else {
                int sinkIndex = getSinkIndex(coneArray, b, 0, bottomCone);
                if (sinkIndex == -1) {
                    // the disk is too large
                    continue;
                }

                bottomWell = sinkIndex - 1;
    
                // adjust the bottom
                int oldBottom = bottomCone;
                for (int c = oldBottom; c >= 0; c--) {
                    if (coneArray[c].index > bottomWell) {
                        bottomCone = c-1;
                    }
                }
            }
            count++;

            if (bottomWell < 0) {
                break;
            }
        }

        return count;
    }

    private int getSinkIndex(WellRing[] coneArray, int diameter, int start,
            int end) {
        if (start == end) {
            if  (coneArray[start].diameter >= diameter) {
                return coneArray[start+1].index - 1;
            } else {
                return coneArray[start].index - 1;
            }
        }

        int mid = start + (end - start + 1) / 2;
        int midDiameter = coneArray[mid].diameter;
        if (midDiameter >= diameter) {
            return getSinkIndex(coneArray, diameter, mid + 1 < end ? mid + 1
                    : end, end);
        }
       
        return getSinkIndex(coneArray, diameter, start, mid - 1 < start ? start
                : mid - 1);
    }

    // ([6, 15, 5, 17, 5, 20, 16, 17, 10, 13, 8, 10], [7, 4, 4, 3, 1])
    public static void main(String[] args) {
        BinarySearchSolution diskDropper = new BinarySearchSolution();
        {
            int A[] = { 6, 15, 5, 17, 5, 20, 16, 17, 10, 13, 8, 10 };
            int B[] = { 7, 4, 4, 3, 1 };
            int expected = 4;
            int result = diskDropper.falling_disks(A, B);
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
            int result = diskDropper.falling_disks(A, B);
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
            int result = diskDropper.falling_disks(A, B);
            if (result != expected) {
                System.out.println("expected : " + expected + " but got "
                        + result);
            } else {
                System.out.println("passed");
            }
        }
        {
            int A[] = {};
            int B[] = { 3, 6, 5, 1, 6 };

            int expected = 0;
            int result = diskDropper.falling_disks(A, B);
            if (result != expected) {
                System.out.println("expected : " + expected + " but got "
                        + result);
            } else {
                System.out.println("passed");
            }
        }

        {
            int A[] = { 3 };
            int B[] = { 3, 6, 5, 1, 6 };

            int expected = 1;
            int result = diskDropper.falling_disks(A, B);
            if (result != expected) {
                System.out.println("expected : " + expected + " but got "
                        + result);
            } else {
                System.out.println("passed");
            }
        }

        {
            int A[] = { 3, 3, 1, 3 };
            int B[] = { 3, 3, 5, 1, 6 };

            int expected = 2;
            int result = diskDropper.falling_disks(A, B);
            if (result != expected) {
                System.out.println("expected : " + expected + " but got "
                        + result);
            } else {
                System.out.println("passed");
            }
        }

        {
            int A[] = { 19, 7, 18, 9, 5, 5, 9, 8, 11, 16, 19, 16 };
            int B[] = { 7, 3, 2, 7, 6 };

            int expected = 4;
            int result = diskDropper.falling_disks(A, B);
            if (result != expected) {
                System.out.println("expected : " + expected + " but got "
                        + result);
            } else {
                System.out.println("passed");
            }
        }
        {
            int A[] = { 7, 6, 6, 6, 19, 7, 7, 6, 8, 13, 9, 12 };
            int B[] = { 7, 3, 2, 5, 6 };

            int expected = 1;
            int result = diskDropper.falling_disks(A, B);
            if (result != expected) {
                System.out.println("expected : " + expected + " but got "
                        + result);
            } else {
                System.out.println("passed");
            }
        }
        {       
            int A[] = { 6, 15, 5, 17, 5, 20, 16, 17, 10, 13, 8, 10 };
            int B[] = { 7, 4, 4, 3, 1 };
            int expected = 4;
            int result = diskDropper.falling_disks(A, B);
            if (result != expected) {
                System.out.println("expected : " + expected + " but got "
                        + result);
            } else {
                System.out.println("passed");
            }
        }
    }
}
