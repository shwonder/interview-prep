import java.util.*;
import java.util.LinkedList;

/**
 * Created by Mike on 9/21/2017.
 */
public class HashMaps {
    // From HackerRank
    static class Contacts {
        static String[] allNames;
        static int currLength = 0;

        static int findSubmatchCnt(String subName) {
            if (currLength > 0) {
                return findSubmatchCnt(subName, 0, currLength-1);
            } else {
                return 0;
            }
        }
            /**
             * Counts number of names starting with the prefix. Array should be already initialized and sorted.
             * It may be not filled yet, i.e. we can search in not completed list yet but whatever is already added so far.
             * @param subName
             * @param lIdx
             * @param rIdx
             * @return
             */
        static int findSubmatchCnt(String subName, int lIdx, int rIdx) {
            int cIdx = (lIdx + rIdx) / 2;
            if (allNames[cIdx].startsWith(subName)) {
                int mCnt = 1;
                for (int idx = cIdx - 1; idx >=0; --idx) {
                    if (allNames[idx].startsWith(subName)) {
                        ++mCnt;
                    } else {
                        break;
                    }
                }
                for (int idx = cIdx + 1; idx < currLength; ++idx) {
                    if (allNames[idx].startsWith(subName)) {
                        ++mCnt;
                    } else {
                        break;
                    }
                }
                return mCnt;
            }
            int cmp = allNames[cIdx].compareTo(subName);
            if (cmp > 0) {
                if (cIdx > lIdx) {
                    return findSubmatchCnt(subName, lIdx, cIdx - 1);
                } else {
                    return 0;
                }
            } else if (cmp < 0) {
                if (cIdx < rIdx) {
                    return findSubmatchCnt(subName, cIdx + 1, rIdx);
                } else {
                    return 0;
                }
            }
            return 1;
        }

        static int findMatchingIdxToInsert(String name, int lIdx, int rIdx) {
            int cIdx = (lIdx + rIdx) / 2;
            int cmp = allNames[cIdx].compareTo(name);
            if (cmp > 0) {
                if (lIdx == cIdx)
                    return cIdx;
                return findMatchingIdxToInsert(name, lIdx, cIdx);
            } else if (cmp < 0) {
                if (cIdx + 1 > rIdx)
                    return -1;
                return findMatchingIdxToInsert(name, cIdx+1, rIdx);
            }
            return cIdx;
        }

        static void placeToArray(String name) {
            int insertIdx = 0;
            if (currLength > 0) {
                insertIdx = findMatchingIdxToInsert(name, 0, currLength-1);
                if (insertIdx == -1) {
                    allNames[currLength] = name;
                } else {
                    for (int idx = currLength; idx > insertIdx; --idx) {
                        allNames[idx] = allNames[idx - 1];
                    }
                    allNames[insertIdx] = name;
                }
            } else {
                allNames[0] = name;
            }
            ++currLength;
        }

        static void run() {
            Scanner in = new Scanner(System.in);
            int n = in.nextInt();
            allNames = new String[n];
            for(int a0 = 0; a0 < n; a0++){
                String op = in.next();
                String contact = in.next();
                if (op.compareToIgnoreCase("add") == 0) {
                    placeToArray(contact);
                } else {
                    int matches = 0;
                    for(String name : allNames) {
                        if (name.startsWith(contact)) {
                            matches++;
                        }
                    }
                    System.out.println(matches);
                }
            }
        }

        static void printArray(String[] arr) {
            System.out.print("Array has " + arr.length + " elements: ");
            for (int idx = 0; idx < arr.length; ++idx) {
                System.out.print("\"" + arr[idx] + "\", ");
            }
            System.out.println();
        }

        static void runIntermediate() {
            allNames = new String[10];
            placeToArray("aa");
            placeToArray("ab");
            placeToArray("zz");
            placeToArray("a");
            placeToArray("cc");
            placeToArray("ac");
            placeToArray("za");
            placeToArray("ba");
            placeToArray("da");
            placeToArray("ad");
            printArray(allNames);
            System.out.println("Current length:" + currLength);
            System.out.println("Names starting with 'a':" + findSubmatchCnt("a", 0, currLength-1));
            System.out.println("Names starting with 'abc':" + findSubmatchCnt("abc", 0, currLength-1));
            System.out.println("Names starting with 'z':" + findSubmatchCnt("z", 0, currLength-1));
            System.out.println("Names starting with 'zed':" + findSubmatchCnt("zed", 0, currLength-1));
            System.out.println("Names starting with 'zz':" + findSubmatchCnt("zz", 0, currLength-1));
        }

        static void runHackTestInput() {
            allNames = new String[2];
            placeToArray("hack");
            placeToArray("hackerrank");
            System.out.println("Current length:" + currLength);
            System.out.println("Names starting with 'hack':" + findSubmatchCnt("hack"));
            System.out.println("Names starting with 'hak':" + findSubmatchCnt("hak"));

        }
    }

    // From HackerRank
    static class RunningMedian {
        static int[] runningValues;
        static int currLength = 0;

        static int findMatchingIdxToInsert(int value, int lIdx, int rIdx) {
            int cIdx = (lIdx + rIdx) / 2;
            if (runningValues[cIdx] > value) {
                if (lIdx == cIdx)
                    return cIdx;
                return findMatchingIdxToInsert(value, lIdx, cIdx);
            } else if (runningValues[cIdx] < value) {
                if (cIdx + 1 > rIdx)
                    return -1;
                return findMatchingIdxToInsert(value, cIdx+1, rIdx);
            }
            return cIdx;
        }

        static void placeToArray(int value) {
            int insertIdx = 0;
            if (currLength > 0) {
                insertIdx = findMatchingIdxToInsert(value, 0, currLength-1);
                if (insertIdx == -1) {
                    runningValues[currLength] = value;
                } else {
                    for (int idx = currLength; idx > insertIdx; --idx) {
                        runningValues[idx] = runningValues[idx - 1];
                    }
                    runningValues[insertIdx] = value;
                }
            } else {
                runningValues[0] = value;
            }
            ++currLength;
        }

        static float getMedian() {
            if (currLength % 2 == 1) {
                return runningValues[currLength / 2];
            } else {
                return ((float)runningValues[currLength/2 - 1] + runningValues[currLength/2]) / 2;
            }
        }

        static void run() {
            int[] stream0 = new int[]{ 12, 4, 5, 3, 8, 7};
            int[] stream = new int[]{ 1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
            runningValues = new int[stream.length];
            for(int idx = 0; idx < stream.length; ++idx) {
                placeToArray(stream[idx]);
                System.out.println(getMedian());
            }

//            List<String> list = new LinkedList<String>();
//            Iterator<String> it = list.iterator();
//            list.size()
        }

    }

    // From Geeks4Geeks: https://practice.geeksforgeeks.org/problems/twice-counter/0/?ref=self
    static class CountDuplicateWords {
        static public void run() {
            Scanner in = new Scanner(System.in);
            int casesNumb = in.nextInt();
            for (int cIdx = 0; cIdx < casesNumb; ++cIdx) {
                int wordsNumb = in.nextInt();
                int res = 0;
                HashMap<String, Integer> wordsMap = new LinkedHashMap<String, Integer>(wordsNumb / 2);
                for (int idx = 0; idx < wordsNumb; ++idx) {
                    String word = in.next();
                    if (wordsMap.containsKey(word)) {
                        wordsMap.put(word, wordsMap.get(word) + 1);
                    } else {
                        wordsMap.put(word, 1);
                    }
                }
                Iterator it = wordsMap.entrySet().iterator();
                while (it.hasNext()) {
                    Map.Entry pair = (Map.Entry) it.next();
                    if ((Integer)pair.getValue() == 2) {
                        res++;
                    }
                }
                System.out.println(res);
            }
        }
    }

    static class Test {
        static void run() {
            //Contacts.runHackTestInput();
            RunningMedian.run();
        }
    }
}
