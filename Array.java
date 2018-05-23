import java.util.*;
import java.util.LinkedList;

/**
 * Created by Mike on 9/21/2017.
 */
public class Array {
    static class Utils {
        static public final String DEFAULT_SEPARATOR = ", ";
        static public final Character DEFAULT_ARRAY_AND_LIST_OPEN = '[';
        static public final Character DEFAULT_ARRAY_AND_LIST_CLOSE = ']';

        // List class method toArray(new Integer[0]) doesn't work with regular unboxed int type.
        // Hence this method is created for that case
        static public int[] listToArray(List<Integer> list) {
            int[] result = new int[list.size()];
            int idx = 0;
            for (int it : list) {
                result[idx] = it;
                ++idx;
            }
            return result;
        }

        static public List<Integer> arrayToList(int[] arr) {
            List<Integer> result = new ArrayList<Integer>(arr.length);
            for (int it : arr) {
                result.add(it);
            }
            return result;
        }

        static public String toString(List<Integer> list, String separator, Character open, Character close) {
            if (list == null)
                return "null";
            StringBuilder result = new StringBuilder();
            result.append(open);
            boolean first = true;
            for(int it : list) {
                if (!first) {
                    result.append(separator);
                } else {
                    first = false;
                }
                result.append(it);
            }
            result.append(close);
            return result.toString();
        }

        static public String toString(List<Integer> list) {
            return toString(list, DEFAULT_SEPARATOR, DEFAULT_ARRAY_AND_LIST_OPEN, DEFAULT_ARRAY_AND_LIST_CLOSE);
        }

        static public String toString(int[] arr, String separator, Character open, Character close) {
            return toString(arrayToList(arr), separator, open, close);
        }

        static public String toString(int[] arr) {
            return toString(arr, DEFAULT_SEPARATOR, DEFAULT_ARRAY_AND_LIST_OPEN, DEFAULT_ARRAY_AND_LIST_CLOSE);
        }

        static public List<int[]> toListOfArrays(List<List<Integer>> listOfLists) {
            List<int[]> result = new ArrayList<int[]>();
            for(List<Integer> list : listOfLists) {
                result.add(listToArray(list));
            }
            return result;
        }

        static public List<List<Integer>> toListOfLists(List<int[]> listOfArrays) {
            List<List<Integer>> result = new ArrayList<List<Integer>>(listOfArrays.size());
            for (int[] arr : listOfArrays) {
                result.add(arrayToList(arr));
            }
            return result;
        }

        static public String toString(List<List<Integer>> listOfLists,
                                      final String listItemSeparator,
                                      final Character listOpen,
                                      final Character listClose,
                                      final String itemSeparator) {
            if (listOfLists == null) {
                return "null";
            }
            StringBuilder result = new StringBuilder();
            result.append(listOpen);
            boolean first = true;
            for (List<Integer> list : listOfLists) {
                if (!first) {
                    result.append(listItemSeparator);
                } else {
                    first = false;
                }
                result.append(toString(list, itemSeparator, listOpen, listClose));
            }
            result.append(listClose);
            return result.toString();
        }

        static public String toString_listOfLists(List<List<Integer>> listOfLists) {
            return toString(listOfLists, DEFAULT_SEPARATOR, DEFAULT_ARRAY_AND_LIST_OPEN, DEFAULT_ARRAY_AND_LIST_CLOSE, DEFAULT_SEPARATOR);
        }

        static public String toString(List<int[]> listOfArrays, String listItemSeparator) {
            return toString(toListOfLists(listOfArrays), listItemSeparator, DEFAULT_ARRAY_AND_LIST_OPEN, DEFAULT_ARRAY_AND_LIST_CLOSE, DEFAULT_SEPARATOR);
        }

        // return -1 if arr1 less than arr2, 0 if they are equal, 1 if arr1 more than arr2
        static public int compare(int[] arr1, int[] arr2) {
            if (arr1.length != arr2.length) {
                // we just want to simplify our comparator for the first version.
                if (arr1.length > arr2.length)
                    return 1;
                else
                    return -1;
            }
            for (int idx = 0; idx < arr1.length; ++idx) {
                if (arr1[idx] < arr2[idx])
                    return -1;
                if (arr1[idx] > arr2[idx])
                    return 1;
            }
            return 0;
        }

        // Both, items of lists and arrays should be sorted already ascending
        static public boolean areOrderedListsEqual(List<int[]> list1, List<int[]> list2) {
            if (list1.size() != list2.size())
                return false;

            ListIterator<int[]> it1 = list1.listIterator();
            ListIterator<int[]> it2 = list2.listIterator();
            while(it1.hasNext()) {
                int[] arr1 = it1.next();
                int[] arr2 = it2.next();
                if (compare(arr1, arr2) != 0) {
                    return false;
                }
            }
            return true;
        }
    }
    static class Print {

        public static void printListOfArrays(List<int[]> lst) {
            for (int[] arr : lst) {
                printArray(arr);
            }
        }

        public static void printHashMapOfListsOfArrays(HashMap<Integer, List<int[]>> hashMap) {
            for (int key : hashMap.keySet()) {
                System.out.print(key);
                System.out.print(": ");
                printListOfArrays(hashMap.get(key));
                System.out.println();
            }
        }

        public static void printlnArray(int arr[]) {
            printArray(arr, " ");
            System.out.println();
        }

        public static void printArray(int arr[], String separator) {
            for (int i = 0; i < arr.length; ++i)
                System.out.print(arr[i] + separator);
        }

        public static void printArray(Integer[] arr, String separator) {
            for (int i = 0; i < arr.length; ++i)
                System.out.print(arr[i] + separator);
        }

        public static void printArray(int[] arr) {
            if (arr.length > 100) {
                System.out.print("[array length " + arr.length + ", starts with ");
                for(int idx = 0; idx < 10; ++idx) {
                    System.out.print(arr[idx] + ", ");
                }
                System.out.print("... ends with ");
                for(int idx = 10; idx > 1; --idx) {
                    System.out.print(arr[arr.length - idx] + ", ");
                }
                System.out.print(arr[arr.length-1] + "]");
            } else {
                System.out.print(Arrays.toString(arr));
            }
        }

        public static void printList(List<Integer> list) {
            printArray(Utils.listToArray(list), ", ");
        }

        public static void printListOfLists(List<List<Integer>> listOfLists, final String separatorBetweenLists) {
            System.out.print(
                    Utils.toString(listOfLists,
                            separatorBetweenLists,
                            Utils.DEFAULT_ARRAY_AND_LIST_OPEN,
                            Utils.DEFAULT_ARRAY_AND_LIST_CLOSE,
                            Utils.DEFAULT_SEPARATOR));
        }
    }

    // This is from HackerRank
    static class LeftRotation {
        static int[] Rotate(int[] arr, int rotateNumb) {
            int shift = rotateNumb % arr.length;
            int [] result = new int[arr.length];
            for (int idx = 0; idx + shift < arr.length; ++idx) {
                result[idx] = arr[shift+idx];
            }
            for (int idx = 0; idx < shift; ++idx) {
                result[arr.length - shift + idx] = arr[idx];
            }
            return result;
        }
        static void run() {
            try {
                Scanner in = new Scanner(System.in);
                int arrSize = in.nextInt();
                int shift = in.nextInt();
                int[] arr = new int[arrSize];
                for (int idx = 0; idx < arrSize; ++idx) {
                    arr[idx] = in.nextInt();
                }
                int[] res = LeftRotation.Rotate(arr, shift);
                Print.printlnArray(res);
            } catch (Exception ioEx) {
                ioEx.printStackTrace();
            }
        }
    }

    static class BinarySearch {
        static public int findPositionOfItem(int[] arr, int itemToFind, int left, int right) {
            if (left > right)
                return -1;
            int mid = (left + right) / 2;
            if (arr[mid] == itemToFind)
                return mid;
            if (arr[mid] > itemToFind) {
                return findPositionOfItem(arr, itemToFind, left, mid - 1);
            } else {
                return findPositionOfItem(arr, itemToFind, mid + 1, right);
            }
        }

        static public void run() {
            int[] arr = new int[] {1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21};
            Print.printArray(arr);
            System.out.println();
            int pos = findPositionOfItem(arr, arr[8], 0, arr.length - 1);
            System.out.println("Found position of item " + arr[8] + " as " + pos);
            pos = findPositionOfItem(arr, 8, 0, arr.length - 1);
            System.out.println("Found position of 8 as " + pos);
        }
    }

    static class SumOf2 {
        // From leetcode. https://leetcode.com/problems/two-sum/description/
        // Key points: exactly one solution exists. Need to return indexes, not addendums.
        static public int[] getIdxs(int[] arr, int target) {
            for (int idx1 = 0; idx1 < arr.length - 1; ++idx1) {
                int ctarget = target - arr[idx1];
                for (int idx2 = idx1 + 1; idx2 < arr.length; ++idx2) {
                    if (arr[idx2] == ctarget) {
                        return new int[] { idx1, idx2 };
                    }
                }
            }
            return new int[] {};
        }

        // From leetcode https://leetcode.com/problems/two-sum-ii-input-array-is-sorted/description/
        // Key points: indexes are 1-based, not 0-based. Also input is ordered ascending
        static public int[] getIdxs_arrayOrderedAsc(int[] arr, int target) {
            int start = 0, end = arr.length - 1;
            while (start < end) {
                int sum = arr[start] + arr[end];
                if (sum < target) {
                    ++start;
                } else if (sum > target) {
                    --end;
                } else {
                    return new int[]{start + 1, end + 1};
                }
            }
            return new int[] {};
        }

        static public class TreeNode {
            int val;
            TreeNode left;
            TreeNode right;
            TreeNode(int x) { val = x; }
        }

        static private boolean isInTree(TreeNode root, int value) {
            if (root == null) {
                return false;
            }
            if (root.val > value) {
                return isInTree(root.left, value);
            } else if (root.val < value) {
                return isInTree(root.right, value);
            } else {
                return true;
            }
        }

        // From leetcode https://leetcode.com/problems/two-sum-iv-input-is-a-bst/description/
        // Input is a binary search tree
        static public boolean isSumInBst(TreeNode node, TreeNode root, int target) {
            if (node == null) {
                return false;
            }
            int ctarget = target - node.val;
            if (ctarget != node.val) {
                if (isInTree(root, ctarget)) {
                    return true;
                }
            }
            return isSumInBst(node.left, root, target) || isSumInBst(node.right, root, target);
        }
        // test input:
        // 1) [5,3,6,2,4,null,7]
        //    9
        //
        // 2) [2,1,3]
        //    4
    }
    // this is from leetcode: https://leetcode.com/problems/3sum/description/
    static class SumOf3 {
        static private List<List<Integer>> getAddendums(int[] arr, int target) {
            Arrays.sort(arr);
            List<List<Integer>> result = new ArrayList<List<Integer>>();
            for (int idx1 = 0; idx1 < arr.length - 2; ++idx1) {
                if (idx1 > 0 && arr[idx1-1] == arr[idx1]) {
                    continue;
                }
                int ctarget = target - arr[idx1];
                int start = idx1 + 1;
                int end = arr.length - 1;
                while (start < end) {
                    int sum = arr[start] + arr[end];
                    if (sum == ctarget) {
                        List<Integer> r = new LinkedList<Integer>();
                        r.add(arr[idx1]);
                        r.add(arr[start]);
                        r.add(arr[end]);
                        result.add(r);
                        while (start < end && arr[start + 1] == arr[start]) {
                            ++start;
                        }
                        while (start < end && arr[end] == arr[end - 1]) {
                            --end;
                        }
                        ++start;
                        --end;
                    } else if (sum < ctarget) {
                        ++start;
                    } else {
                        --end;
                    }
                }
            }
            return result;
        }
        static public List<List<Integer>> threeSum(int[] nums) {
            return getAddendums(nums, 0);
        }
    }

    // From leetcode https://leetcode.com/problems/3sum-closest/description/
    static class SumOf3Closest {
        static public int threeSumClosest(int[] nums, int target) {
            Arrays.sort(nums);
            int diff = Integer.MAX_VALUE;
            int result = 0;
            for (int idx1 = 0; idx1 < nums.length - 2; ++idx1) {
                int ctarget = target - nums[idx1];
                int left = idx1 + 1;
                int right = nums.length - 1;
                while (left < right) {
                    int sum = nums[left] + nums[right];
                    if (diff > Math.abs(ctarget - sum)) {
                        diff = Math.abs(ctarget - sum);
                        result = nums[idx1] + sum;
                        if (diff == 0) {
                            return result;
                        }
                    }
                    if (ctarget > sum) {
                        ++left;
                    } else {
                        --right;
                    }
                }
            }
            return result;
        }
    }

    static class SumOf4 {
        private static void addToResult(int[] nums, List<List<Integer>> result, int idx1, int idx2, int idx3, int idx4) {
            List<Integer> r = new ArrayList<Integer>(4);
            r.add(nums[idx1]);
            r.add(nums[idx2]);
            r.add(nums[idx3]);
            r.add(nums[idx4]);
            result.add(r);
        }

        // O(N^3)
        static public List<List<Integer>> fourSum(int[] nums, int target) {
            List<List<Integer>> result = new ArrayList<List<Integer>>();
            if (nums == null || nums.length < 4) {
                return result;
            }
            Arrays.sort(nums);
            if (nums.length == 4 && (nums[0] + nums[1] + nums[2] + nums[3]) == target) {
                List<Integer> r = new ArrayList<Integer>(4);
                r.add(nums[0]);
                r.add(nums[1]);
                r.add(nums[2]);
                r.add(nums[3]);
                result.add(r);
                return result;
            }
            for (int idx1 = 0; idx1 < nums.length - 3; ++idx1) {
                if (idx1 > 0 && nums[idx1] == nums[idx1-1]) {
                    continue;
                }
                for (int idx2 = idx1 + 1; idx2 < nums.length - 2; ++idx2) {
                    if (idx2 > idx1 + 1 && nums[idx2] == nums[idx2 - 1]) {
                        continue;
                    }
                    int left = idx2 + 1;
                    int right = nums.length - 1;
                    int ctarget = target - nums[idx1] - nums[idx2];
                    while (left < right) {
                        int sum = nums[left] + nums[right];
                        if (sum == ctarget) {
                            List<Integer> r = new ArrayList<Integer>(4);
                            r.add(nums[idx1]);
                            r.add(nums[idx2]);
                            r.add(nums[left]);
                            r.add(nums[right]);
                            result.add(r);
                            while (left < right && nums[left] == nums[left + 1]) {
                                ++left;
                            }
                            while (left < right && nums[right - 1] == nums[right]) {
                                --right;
                            }
                            ++left;
                            --right;
                        } else if (sum < ctarget) {
                            ++left;
                        } else {
                            --right;
                        }
                    }
                }
            }
            return result;
        }

        /**
         * Calculates sums of all pairs in array, returns hash map of sum to list of index pairs of items in array, i.e.
         * each list item has an array of 2 elements which are indexes in original array and sum of elements by those
         * indexes is a key in a hash map. We use indexes to track that we do not use the same element twice for the
         * sum of more than 2 elements (in four sum case, we check that all 4 elements are different, even if they have
         * the same value). Indexes in each list item are ordered ascending.
         * @param arr
         * @return
         */
        static public HashMap<Integer, List<int[]>> getSumOfPairsHashMap(int[] arr) {
            HashMap<Integer, List<int[]>> sums = new HashMap<Integer, List<int[]>>();
            for (int idx1 = 0; idx1 < arr.length - 1; ++idx1) {
                for (int idx2 = idx1 + 1; idx2 < arr.length; ++idx2) {
                    int sum = arr[idx1] + arr[idx2];
                    if (sums.containsKey(sum)) {
                        sums.get(sum).add(new int[]{idx1, idx2});
                    } else {
                        List<int[]> indexes = new ArrayList<int[]>();
                        int[] itemToAdd = new int[]{idx1, idx2};
                        indexes.add(itemToAdd);
                        sums.put(sum, indexes);
                    }
                }
            }
            return sums;
        }

        /*
        * Submission is ok, performance:
        * 282 / 282 test cases passed. Status: Accepted. Runtime: 101 ms
        */
        static public List<List<Integer>> fourSum_Hash(int[] nums, int target) {
            Arrays.sort(nums);
            List<int[]> result = new ArrayList<int[]>();
            HashMap<Integer, List<int[]>> sumOfPairs = getSumOfPairsHashMap(nums);
            for (int s : sumOfPairs.keySet()) {
                int sum2 = target - s;
                if (sumOfPairs.containsKey(sum2)) {
                    List<int[]> idxList1 = sumOfPairs.get(s);
                    List<int[]> idxList2 = sumOfPairs.get(sum2);
                    for (int[] idxs1 : idxList1) {
                        for (int[] idxs2 : idxList2) {
                            // We need to check if all indexes for both sub-sum elements are different.
                            // Since this is a 4-sum, we do not need to run it through loop and for faster execution we just
                            // unroll loops to 4 checks.
                            if (idxs1[0] == idxs2[0] || idxs1[1] == idxs2[0] || idxs1[0] == idxs2[1] || idxs1[1] == idxs2[1]) {
                                continue;
                            }
                            int[] r = new int[4];
                            // Unrolling merge of 2 sorted ascending 2-element arrays
                            if (idxs1[0] < idxs2[0]) {
                                r[0] = (nums[idxs1[0]]);
                                if (idxs1[1] < idxs2[0]) {
                                    r[1] = nums[idxs1[1]];
                                    r[2] = nums[idxs2[0]];
                                    r[3] = nums[idxs2[1]];
                                } else {
                                    r[1] = nums[idxs2[0]];
                                    if (idxs1[1] < idxs2[1]) {
                                        r[2] = nums[idxs1[1]];
                                        r[3] = nums[idxs2[1]];
                                    } else {
                                        r[2] = nums[idxs2[1]];
                                        r[3] = nums[idxs1[1]];
                                    }
                                }
                            } else {
                                r[0] = nums[idxs2[0]];
                                if (idxs1[0] < idxs2[1]) {
                                    r[1] = nums[idxs1[0]];
                                    if (idxs1[1] < idxs2[1]) {
                                        r[2] = nums[idxs1[1]];
                                        r[3] = nums[idxs2[1]];
                                    } else {
                                        r[2] = nums[idxs2[1]];
                                        r[3] = nums[idxs1[1]];
                                    }
                                } else {
                                    r[1] = nums[idxs2[1]];
                                    r[2] = nums[idxs1[0]];
                                    r[3] = nums[idxs1[1]];
                                }
                            }
                            boolean fullMatch = false;
                            for (int[] tmp : result) {
                                fullMatch = true;
                                for (int cIdx = 0; cIdx < 4; ++cIdx) {
                                    if (tmp[cIdx] != r[cIdx]) {
                                        fullMatch = false;
                                        break;
                                    }
                                }
                                if (fullMatch)
                                    break;
                            }
                            if (!fullMatch)
                                result.add(r);
                        }
                    }
                }
            }


            List<int[]> tmpRes = BadAttempts.SumOf3_2.sort(result);
            List<List<Integer>> res = new ArrayList<List<Integer>>(tmpRes.size());
            for (int[] rArr : tmpRes) {
                ArrayList<Integer> rLst = new ArrayList<Integer>(4);
                rLst.add(rArr[0]);
                rLst.add(rArr[1]);
                rLst.add(rArr[2]);
                rLst.add(rArr[3]);
                res.add(rLst);
            }
            return res;
        }
    }

    static class MaxSubsequenceSum {
        static public int maxSubArray(int[] nums) {
            int sum = 0;
            int maxSum = Integer.MIN_VALUE;
            for (int idx = 0; idx < nums.length; ++idx) {
                sum += nums[idx];
                if (maxSum < sum) {
                    maxSum = sum;
                }
                if (sum < 0) {
                    sum = 0;
                }
            }
            return maxSum;
        }
    }

    // From Geeks4Geeks https://www.geeksforgeeks.org/maximum-sum-such-that-no-two-elements-are-adjacent/
    static class MaxSumNonAdjacent {
        static public int maxSum(int[] nums) {
            if (nums == null || nums.length == 0) {
                return 0;
            }
            int maxIncluding = nums[0];
            int maxExcluding = 0;
            for (int idx = 1; idx < nums.length; ++idx) {
                if (maxExcluding + nums[idx] > maxIncluding) {
                    int tmp = maxExcluding;
                    maxExcluding = maxIncluding;
                    maxIncluding = tmp + nums[idx];
                } else {
                    maxExcluding = maxIncluding;
                }
            }
            return Math.max(maxExcluding, maxIncluding);
        }
        static void run() {
            Scanner in = new Scanner(System.in);
            int numberOfCases = in.nextInt();
            for (int cIdx = 0; cIdx < numberOfCases; ++cIdx) {
                int arrSize = in.nextInt();
                int[] nums = new int[arrSize];
                for (int idx = 0; idx < arrSize; ++idx) {
                    nums[idx] = in.nextInt();
                }
                System.out.println(maxSum(nums));
            }
        }
    }

    // From leetcode: https://leetcode.com/problems/best-time-to-buy-and-sell-stock/description/
    static class BuySellStock {

    }

    static class BadAttempts {
        // Sum of 3 item should be 0 in the original task, but here we'll assume it is a param passed from outside
        // This is the first attempt. Solution is working with indexes in array and keeps them in a hash, instead of
        // addendum values. So result may have the same values of addendums if initial array contains duplicates of
        // addendums in dirrerent items of array, i.e. permutations are allowed here. Method returning addendum list removes
        // results with permutations and all addendums there are unique.
        static class SumOf3_1 {
            // result is correct but speed is slow, because additional operations are done. It is still O(N^2) but
            // constant is big. Tests on leetcode timed out.

            /**
             * Returns list of arrays of indexes in original array. Those are elements of an array which sum up to a target.
             * Each set of indexes in result is guaranteed to be unique. Elements are not guaranteed to be unique
             *
             * @param arr
             * @param target
             * @return
             */
            static public List<int[]> getListOfIndexes(int[] arr, int target) {
                List<int[]> result = new ArrayList<int[]>();
                HashMap<Integer, List<int[]>> sums = SumOf4.getSumOfPairsHashMap(arr);
                for (int idx = 0; idx < arr.length; ++idx) {
                    int ct = target - arr[idx];
                    if (sums.containsKey(ct)) {
                        // check if indexes in precalculated sum already contain the current index
                        for (int[] res : sums.get(ct)) {
                            boolean collision = false;
                            for (int i : res) {
                                if (i == idx) {
                                    // index the same, we do not want to include the same item twice
                                    collision = true;
                                    break;
                                }
                            }
                            if (!collision) {
                                // three indexes can have permutations. Although we know that first item is always on the
                                // left of second, i.e. first index in sum array is always less than second due to loop
                                // conditions, but here we add the third index and we can have permutations.
                                // Therefore we need to check if new triplet is already in the map. So we sort them and
                                // check if result already has the same set of indexes
                                int[] candidate = new int[]{res[0], res[1], idx};
                                ArraySort.BubbleSort.sort(candidate);
                                boolean match = !result.isEmpty();
                                for (int[] r : result) {
                                    match = true;
                                    for (int i = 0; i < r.length; ++i) {
                                        if (r[i] != candidate[i]) {
                                            match = false;
                                            break;
                                        }
                                    }
                                    if (match) {
                                        break;
                                    }
                                }
                                if (!match) {
                                    result.add(candidate);
                                }
                            }
                        }
                    }
                }
                return result;
            }

            static public List<int[]> getListOfAddends(int[] arr, int target) {
                List<int[]> indexes = getListOfIndexes(arr, target);
                List<int[]> result = new ArrayList<int[]>();
                for (int[] idxs : indexes) {
                    int[] addendums = new int[idxs.length];
                    for (int i = 0; i < idxs.length; ++i) {
                        addendums[i] = arr[idxs[i]];
                    }
                    ArraySort.BubbleSort.sort(addendums);
                    boolean match = !result.isEmpty();
                    for (int[] r : result) {
                        match = true;
                        for (int i = 0; i < r.length; ++i) {
                            if (addendums[i] != r[i]) {
                                match = false;
                                break;
                            }
                        }
                        if (match) {
                            break;
                        }
                    }
                    if (!match) {
                        result.add(addendums);
                    }
                }
                return result;
            }

        }

        static class SumOf3_2 {
            private static int[] getPairInAscendingOrder(int add1, int add2) {
                if (add1 < add2) {
                    return new int[]{add1, add2};
                } else {
                    return new int[]{add2, add1};
                }
            }

            private static boolean areOrderedArraysMatch(int[] arr1, int[] arr2) {
                if (arr1.length != arr2.length) {
                    return false;
                }
                for (int idx = 0; idx < arr1.length; ++idx) {
                    if (arr1[idx] != arr2[idx]) {
                        return false;
                    }
                }
                return true;
            }

            private static int[] getTripletInAscendingOrder(int[] pair, int third) {
                if (third <= pair[0]) {
                    return new int[]{third, pair[0], pair[1]};
                }
                if (third >= pair[1]) {
                    return new int[]{pair[0], pair[1], third};
                }
                return new int[]{pair[0], third, pair[1]};
            }

            /**
             * Returns hash map. Key contains sum of pair of elements in a given array, value is a two-element array of
             * indexes in the given array, items of the array by those indexes are addendums to the sum which s a key.
             * We return indexes instead of array element values because we'd like to make sure that we do not include
             * the same element twice into result when we'll use those numbers for triplets or quadruple sum.
             *
             * @param arr
             * @return
             */
            public static HashMap<Integer, List<int[]>> getSumOf2Addendums(int[] arr) {
                HashMap<Integer, List<int[]>> result = new HashMap<Integer, List<int[]>>();
                for (int idx1 = 0; idx1 < arr.length - 1; ++idx1) {
                    for (int idx2 = idx1 + 1; idx2 < arr.length; ++idx2) {
                        int sum = arr[idx1] + arr[idx2];
                        // get pair of indexes of addendums in ascending order
                        // We know that idx1 is always less than idx2
                        int[] candidate = new int[]{idx1, idx2};
                        if (result.containsKey(sum)) {
                            boolean matchFound = false;
                            for (int[] adds : result.get(sum)) {
                                matchFound |= areOrderedArraysMatch(adds, candidate);
                            }
                            if (!matchFound) {
                                result.get(sum).add(candidate);
                            }
                        } else {
                            List<int[]> addendums = new ArrayList<int[]>();
                            addendums.add(candidate);
                            result.put(sum, addendums);
                        }
                    }
                }
                return result;
            }

            public static List<int[]> getTripletAddendumIndexes(int[] arr, int target) {
                HashMap<Integer, List<int[]>> addendums = getSumOf2Addendums(arr);
                List<int[]> result = new ArrayList<int[]>();
                for (int idx = 0; idx < arr.length; ++idx) {
                    int ct = target - arr[idx];
                    if (addendums.containsKey(ct)) {
                        for (int[] adds : addendums.get(ct)) {
                            boolean sameIndexFound = false;
                            for (int ai = 0; ai < adds.length; ++ai) {
                                if (adds[ai] == idx) {
                                    sameIndexFound = true;
                                }
                            }
                            if (sameIndexFound) {
                                continue;
                            }
                            int[] candidate = getTripletInAscendingOrder(adds, idx);
                            if (result.isEmpty()) {
                                result.add(candidate);
                            } else {
                                boolean matchFound = false;
                                for (int[] r : result) {
                                    matchFound |= areOrderedArraysMatch(r, candidate);
                                }
                                if (!matchFound) {
                                    result.add(candidate);
                                }
                            }
                        }
                    }
                }
                return result;
            }

            public static List<int[]> getQuadrupletAddendumIndexes(int[] arr, int target) {
                HashMap<Integer, List<int[]>> addendums = getSumOf2Addendums(arr);
                List<int[]> result = new ArrayList<int[]>();
                for (int pairSum : addendums.keySet()) {
                    int ct = target - pairSum;
                    if (addendums.containsKey(ct)) {
                        List<int[]> p1Idxs = addendums.get(ct);
                        List<int[]> p2Idxs = addendums.get(pairSum);
                        for (int[] idxs1 : p1Idxs) {
                            for (int[] idxs2 : p2Idxs) {
                                if (!hasCommonItemsInOrderedArrays(idxs1, idxs2)) {
                                    int[] candidate = new int[]{idxs1[0], idxs1[1], idxs2[0], idxs2[1]};
                                    sort(candidate);
                                    boolean matchFound = false;
                                    for (int[] res : result) {
                                        if (areOrderedArraysMatch(res, candidate)) {
                                            matchFound = true;
                                            break;
                                        }
                                    }
                                    if (!matchFound) {
                                        result.add(candidate);
                                    }
                                }
                            }
                        }
                    }
                }
                return result;
            }

            private static boolean hasCommonItemsInOrderedArrays(int[] arr1, int[] arr2) {
                for (int idx1 = 0; idx1 < arr1.length; ++idx1) {
                    for (int idx2 = 0; idx2 < arr2.length; ++idx2) {
                        if (arr1[idx1] == arr2[idx2]) {
                            return true;
                        }
                    }
                }
                return false;
            }

            private static void sort(int[] arr) {
                for (int idx1 = arr.length - 1; idx1 > 0; --idx1) {
                    for (int idx2 = 0; idx2 < idx1; ++idx2) {
                        if (arr[idx2] > arr[idx2 + 1]) {
                            int tmp = arr[idx2];
                            arr[idx2] = arr[idx2 + 1];
                            arr[idx2 + 1] = tmp;
                        }
                    }
                }
            }

            private static List<int[]> sort(List<int[]> arr) {
                // idea is to find minimal item and put it into the result. Exclude minimal from source. Repeat
                // We do not want to modify source, so let's first create copy, and copy
                List<int[]> tmp = new ArrayList<int[]>(arr.size());
                for (int[] arrIt : arr) {
                    tmp.add(arrIt);
                }
                List<int[]> result = new ArrayList<int[]>(arr.size());
                while (tmp.size() > 0) {
                    int minIdx = tmp.size() - 1;
                    for (int idx = 0; idx < tmp.size() - 1; ++idx) {
                        int cmpRes = Utils.compare(tmp.get(idx), tmp.get(minIdx));
                        if (cmpRes < 0) {
                            minIdx = idx;
                        }
                    }
                    result.add(tmp.get(minIdx));
                    tmp.remove(minIdx);
                }
                return result;
            }

            public static List<int[]> getAddendumsFromIndexes(int[] arr, List<int[]> indexes) {
                List<int[]> result = new ArrayList<int[]>();
                for (int[] i : indexes) {
                    int[] candidate = new int[i.length];
                    for (int idx = 0; idx < i.length; ++idx) {
                        candidate[idx] = arr[i[idx]];
                    }
                    sort(candidate);
                    if (result.isEmpty()) {
                        result.add(candidate);
                    } else {
                        boolean matchFound = false;
                        for (int[] r : result) {
                            matchFound |= areOrderedArraysMatch(r, candidate);
                        }
                        if (!matchFound) {
                            result.add(candidate);
                        }
                    }
                }
                return result;
            }
        }
    }

    static class Test {

        private static long testSumOf3_alg1(int[] sample) {
            long startTime = System.currentTimeMillis();
            List<int[]> res = BadAttempts.SumOf3_2.sort(BadAttempts.SumOf3_1.getListOfAddends(sample, 0));
            long endTime = System.currentTimeMillis();
            for (int[] addends : res) {
                Print.printlnArray(addends);
            }
            System.out.println("number of items in result: " + res.size());
            return endTime - startTime;
        }

        private static long testSumOf3_alg2(int[] sample) {
            long startTime = System.currentTimeMillis();
            List<int[]> res = BadAttempts.SumOf3_2.getTripletAddendumIndexes(sample, 0);
            List<int[]> res2 = BadAttempts.SumOf3_2.getAddendumsFromIndexes(sample, res);
            List<int[]> res3 = BadAttempts.SumOf3_2.sort(res2);
            long endTime = System.currentTimeMillis();
            for (int[] addends : res3) {
                Print.printlnArray(addends);
            }
            System.out.println("number of items in result: " + res3.size());
            return endTime - startTime;
        }

        // test and debug some pieces of algorithm
        private static void testSumOf3_testing() {
            int[] arr = new int[] {-1, 0, 1, 2, -1, -4};
            int[] arr2 = ArraySort.Test.cloneArray(arr);
            int[] arr3 = ArraySort.Test.cloneArray(arr);
            Print.printlnArray(arr);
            List<int[]> res = BadAttempts.SumOf3_1.getListOfIndexes(arr, 0);
            for(int[] idx : res) {
                Print.printlnArray(idx);
                int[] addends = new int[] {arr[idx[0]], arr[idx[1]], arr[idx[2]]};
                Print.printlnArray(addends);
                System.out.println();
            }
            System.out.println("Now look at addends");
            res = BadAttempts.SumOf3_1.getListOfAddends(arr2, 0);
            for (int[] addends : res) {
                Print.printlnArray(addends);
            }
            res = BadAttempts.SumOf3_2.sort(res);
            System.out.println("And now result arrays sorted:");
            for (int[] addends : res) {
                Print.printlnArray(addends);
            }

            System.out.println("Algorithm 2. First, indexes");
            List<int[]> idxRes = BadAttempts.SumOf3_2.getTripletAddendumIndexes(arr3, 0);
            for(int[] idxs : idxRes) {
                Print.printlnArray(idxs);
            }
            System.out.println("Now look at addends");
            res = BadAttempts.SumOf3_2.getAddendumsFromIndexes(arr3, idxRes);
            for (int[] addends : res) {
                Print.printlnArray(addends);
            }
            res = BadAttempts.SumOf3_2.sort(res);
            System.out.println("And now result arrays sorted:");
            for (int[] addends : res) {
                Print.printlnArray(addends);
            }
        }

        private static void testSumOf3() {
            int[] arr = new int[] { -1, 0, 1, 2, -1, -4 };
            testSumOf3_E2E(arr);

            arr = new int[] {
                    12,5,-12,4,-11,11,2,7,2,-5,-14,-3,-3,3,2,-10,9,-15,2,14,-3,-15,-3,-14,-1,-7,11,-4,-11,12,-15,-14,2,10,-2,-1,6,7,13,-15,-13,6,-10,-9,-14,7,-12,3,-1,5,2,11,6,14,12,-10,14,0,-7,11,-10,-7,4,-1,-12,-13,13,1,9,3,1,3,-5,6,9,-4,-2,5,14,12,-5,-6,1,8,-15,-10,5,-15,-2,5,3,3,13,-8,-13,8,-5,8,-6,11,-12,3,0,-2,-6,-14,2,0,6,1,-11,9,2,-3,-6,3,3,-15,-5,-14,5,13,-4,-4,-10,-10,11
            };
            testSumOf3_E2E(arr);
            arr = new int[] {
                    -11,-3,-6,12,-15,-13,-7,-3,13,-2,-10,3,12,-12,6,-6,12,9,-2,-12,14,11,-4,11,-8,8,0,-12,4,-5,10,8,7,11,-3,7,5,-3,-11,3,11,-13,14,8,12,5,-12,10,-8,-7,5,-9,-11,-14,9,-12,1,-6,-8,-10,4,9,6,-3,-3,-12,11,9,1,8,-10,-3,2,-11,-10,-1,1,-15,-6,8,-7,6,6,-10,7,0,-7,-7,9,-8,-9,-9,-14,12,-5,-10,-15,-9,-15,-7,6,-10,5,-7,-14,3,8,2,3,9,-12,4,1,9,1,-15,-13,9,-14,11,9
            };
            testSumOf3_E2E(arr);
            // Progress on leetcode: "233 / 313 test cases passed"
        }

        private static void testSumOf3_E2E(int[] arr) {
            Print.printlnArray(arr);
            System.out.println("Algorithm 1");
            long timeTaken = testSumOf3_alg1(arr);
            System.out.println("Time spent: " + timeTaken);
            System.out.println("Algorithm 2");
            timeTaken = testSumOf3_alg2(arr);
            System.out.println("Time spent: " + timeTaken);
            List<int[]> res1 = BadAttempts.SumOf3_2.sort(BadAttempts.SumOf3_1.getListOfAddends(arr, 0));
            System.out.println("Algorithm 3");
            long start = System.currentTimeMillis();
            List<List<Integer>> res2list = SumOf3.getAddendums(arr, 0);
            long end = System.currentTimeMillis();
            List<int[]> res2 = BadAttempts.SumOf3_2.sort(Utils.toListOfArrays(res2list));
            System.out.println("Result is " + (Utils.areOrderedListsEqual(res1, res2) ? "" : "NOT ") + "correct. Time taken: " + (end - start));
            System.out.println(Utils.toString_listOfLists(res2list));
        }

        private static void testSumOf3Closest() {
            int[] arr = new int[] {-1, 2, 1, -4};
            System.out.println(SumOf3Closest.threeSumClosest(arr, 1));
            arr = new int[] { -1, 0, 1, 2, -1, -4 };
            System.out.println(SumOf3Closest.threeSumClosest(arr, 0));
            System.out.println(SumOf3Closest.threeSumClosest(arr, 1));
        }

        private static void testSumOf4_badAttempt() {
            System.out.println("Sum of 4.");
            int arr[] = new int[] {1, 0, -1, 0, -2, 2};
            Print.printlnArray(arr);
            List<int[]> res = BadAttempts.SumOf3_2.getQuadrupletAddendumIndexes(arr, 0);
            for (int[] idxs : res) {
                Print.printlnArray(idxs);
            }
            System.out.println();
            List<int[]> res2 = BadAttempts.SumOf3_2.getAddendumsFromIndexes(arr, res);
            for (int[] idxs : res2) {
                Print.printlnArray(idxs);
            }
            System.out.println("Result addendums sorted");
            List<int[]> res3 = BadAttempts.SumOf3_2.sort(res2);
            for (int[] idxs : res3) {
                Print.printlnArray(idxs);
            }
        }

        static private void testSumOf4_case(int[] arr, int target) {
            // algorithm 1, n^3, 2 pointers
            long start = System.currentTimeMillis();
            List<List<Integer>> res = SumOf4.fourSum(arr, target);
            long end = System.currentTimeMillis();
            System.out.println(String.format("Sum of 4, n^3, took %d ms, result: %s", end - start, Utils.toString_listOfLists(res)));

            // algorithm 2, n^2, hash map
            start = System.currentTimeMillis();
            List<int[]> res1 = BadAttempts.SumOf3_2.getQuadrupletAddendumIndexes(arr, target);
            List<int[]> res2 = BadAttempts.SumOf3_2.getAddendumsFromIndexes(arr, res1);
            List<int[]> res3 = BadAttempts.SumOf3_2.sort(res2);
            end = System.currentTimeMillis();
            System.out.println(String.format("Sum of 4, n^2 v1, took %d ms, result: ", end - start));
            for (int[] idxs : res3) {
                Print.printArray(idxs);
                System.out.print(", ");
            }
            System.out.println();

            // algorithm 3, n^2, hash map
            start = System.currentTimeMillis();
            List<List<Integer>> res4 = SumOf4.fourSum_Hash(arr, target);
            end = System.currentTimeMillis();
            System.out.println(String.format("Sum of 4, n^2 v2, took %d ms, result: %s", end - start, Utils.toString_listOfLists(res4)));
        }

        static private void testSumOf4_n3() {
            int[] arr = {1, 0, -1, 0, -2, 2}; // result: [[-1,  0, 0, 1], [-2, -1, 1, 2], [-2,  0, 0, 2]]
            List<List<Integer>> res = SumOf4.fourSum(arr, 0);
            System.out.println(Utils.toString_listOfLists(res));
            arr = new int[] {-3,-1,0,2,4,5};
            res = SumOf4.fourSum(arr, 2);
            System.out.println(Utils.toString_listOfLists(res)); // [[-3,-1,2,4]] // 174 / 282 test cases passed
            arr = new int[] {-3,-2,-1,0,0,1,2,3};
            res = SumOf4.fourSum(arr, 0); // [[-3,-2,2,3],[-3,-1,1,3],[-3,0,0,3],[-3,0,1,2],[-2,-1,0,3],[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]] // 192 / 282 test cases passed
            System.out.println(Utils.toString_listOfLists(res));
        }

        static private void testSumOf4() {
            int[] arr = {1, 0, -1, 0, -2, 2}; // result: [[-1,  0, 0, 1], [-2, -1, 1, 2], [-2,  0, 0, 2]]
            testSumOf4_case(arr, 0);
            arr = new int[] {-3,-1,0,2,4,5}; // [[-3,-1,2,4]] // 174 / 282 test cases passed
            testSumOf4_case(arr, 0);
            arr = new int[] {-3,-2,-1,0,0,1,2,3}; // [[-3,-2,2,3],[-3,-1,1,3],[-3,0,0,3],[-3,0,1,2],[-2,-1,0,3],[-2,-1,1,2],[-2,0,0,2],[-1,0,0,1]] // 192 / 282 test cases passed
            testSumOf4_case(arr, 0);
        }

        static private void testMaxSubsequenceSum() {
            int arr[] = new int[] {-2,1,-3,4,-1,2,1,-5,4};
            Array.Print.printArray(arr);
            System.out.println(MaxSubsequenceSum.maxSubArray(arr));
        }

        //static private void testNonAdjacent_case()
        static private void testMaxNonAdjacent() {
            Map<Integer, int[]> arraysAndResults = new HashMap<Integer, int[]>();
            arraysAndResults.put(13, new int[] {3, 2, 7, 10});
            arraysAndResults.put(15, new int[] {3, 2, 5, 10, 7});
            arraysAndResults.put(110, new int[] {5, 5, 10, 100, 10, 5});
            arraysAndResults.put(4, new int[] {1, 2, 3});
            arraysAndResults.put(20, new int[] {1, 20, 3});
            arraysAndResults.put(80, new int[] {5, 5, 10, 40, 50, 35});
            for (int res : arraysAndResults.keySet()) {
                int[] nums = arraysAndResults.get(res);
                Print.printlnArray(nums);
                int max = MaxSumNonAdjacent.maxSum(nums);
                System.out.println(String.format("Max sum of non-adjacent elements: %d, expected: %d", max, res));
            }
        }

        static public void run() {
            //testSumOf3();
            // testSumOf3Closest();
            // testSumOf4_n3();
            //testSumOf4();
            //testMaxSubsequenceSum();
            testMaxNonAdjacent();
            //LeftRotation.run();
            //BinarySearch.run();
        }

    }
}
