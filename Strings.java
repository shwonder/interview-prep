import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mike on 3/28/2017.
 */
public class Strings {

    static void swap(StringBuffer buff, int idx1, int idx2) {
        char tmp = buff.charAt(idx1);
        buff.setCharAt(idx1, buff.charAt(idx2));
        buff.setCharAt(idx2, tmp);
    }

    public static class Permutation {
        private static int num;

        static void permutation(StringBuffer buff, int s, int e) {
            if (s < e) {
                for (int idx = s; idx <= e; ++idx) {
                    swap(buff, s, idx);
                    System.out.println("-- swapped " + buff.charAt(idx) + " with " + buff.charAt(s) + " " + s + ", " + idx);
                    permutation(buff, s + 1, e);
                    swap(buff, s, idx);
                    System.out.println("++ swapped " + buff.charAt(idx) + " with " + buff.charAt(s) + " " + s + ", " + idx);
                }
            } else {
                System.out.println("* " + (++num) + " : " + buff.toString());
            }
        }

        static void allPermutations(String str) {
            StringBuffer buff = new StringBuffer();
            for (int idx= 0; idx < str.length() - 1; ++idx) {
                for (int idx2 = 0; idx2 < str.length(); ++idx) {

                    //        buff[]
                }
            }
        }

        public static void permutation(String str) {
            num = 0;
            permutation("", str);
        }

        private static void permutation(String prefix, String str) {
            int n = str.length();
            if (n == 0) System.out.println((++num) + ": " + prefix);
            else {
                for (int i = 0; i < n; i++)
                    permutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i+1, n));
            }
        }

        private static void partialPermutation(String prefix, String str, int k) {
            if (prefix.length() == k) {
                System.out.println("pperm of " + k + " it " + (++num) + ": " + prefix);
            } else {
                int n = str.length();
                for (int i = 0; i < n; i++) {
                    partialPermutation(prefix + str.charAt(i), str.substring(0, i) + str.substring(i + 1, n), k);
                }
            }
        }

        public static void run() {
            permutation("ABCDE");
            System.out.println();
            System.out.println();
//        permutation("ABCD");

            num = 0;
            permutation(new StringBuffer("ABCD"), 0, 3);
            System.out.println();
            num = 0;
            permutation(new StringBuffer("AB"), 0, 1);
            num = 0;
            partialPermutation("", "ABCDE", 3);
        }
    }

    public static class AnagramsByDeletion {
        public static int numberNeeded(String first, String second) {
            int firstChars[] = new int [26];
            int secondChars[] = new int[26];
            for (int idx=0; idx < first.length(); ++idx) {
                int idxInArr = first.charAt(idx)-'a';
                ++firstChars[idxInArr];
            }
            for (int idx=0; idx < second.length(); ++idx) {
                ++secondChars[second.charAt(idx)-'a'];
            }
            int diff = 0;
            for (int idx = 0; idx < 26; ++idx) {
                diff += Math.abs(firstChars[idx] - secondChars[idx]);
            }
            return diff;
        }

        public static void run() {
            String first = "abcdef";
            String second = "fedcba";
            System.out.println(String.format("First: [%s], second: [%s]. Diff: %d", first, second, numberNeeded(first, second)));

            first = "abcde";
            second = "fghijkl";
            System.out.println(String.format("First: [%s], second: [%s]. Diff: %d", first, second, numberNeeded(first, second)));

            first = "poiuy";
            second = "paobi";
            System.out.println(String.format("First: [%s], second: [%s]. Diff: %d", first, second, numberNeeded(first, second)));
        }
    }

    public static class RansomNote {
        Map<String, Integer> magazineMap;
        Map<String, Integer> noteMap;

        public RansomNote(String magazine, String note) {
            this.magazineMap = new HashMap<String, Integer>();
            this.noteMap = new HashMap<String, Integer>();
            for(String magWord : magazine.split(" ")){
                if (magazineMap.containsKey(magWord)) {
                    // magazineMap.get(magWord) += 1;
                    magazineMap.put(magWord, magazineMap.get(magWord) + 1);
                } else {
                    magazineMap.put(magWord, 1);
                }
            }
            for (String noteWord : note.split(" ")) {
                if (noteMap.containsKey(noteWord)) {
                    noteMap.put(noteWord, noteMap.get(noteWord) + 1);
                } else {
                    noteMap.put(noteWord, 1);
                }
            }
        }

        public boolean solve() {
            for (String noteWord : noteMap.keySet()) {
                if (!magazineMap.containsKey(noteWord)) {
                    return false;
                }
                if (magazineMap.get(noteWord) < noteMap.get(noteWord)) {
                    return false;
                }
            }
            return true;
        }

        static public void runCase(String magazine, String note) {
            RansomNote r = new RansomNote(magazine, note);
            System.out.println(String.format("Magazine map keys %s, values %s, note map keys %s, values %s. Result: %s",
                    Arrays.toString(r.magazineMap.keySet().toArray()),
                    Arrays.toString(r.magazineMap.values().toArray()),
                    Arrays.toString(r.noteMap.keySet().toArray()),
                    Arrays.toString(r.noteMap.values().toArray()),
                    r.solve()));
        }

        static public void run() {
            runCase("two times three is not four", "two times two is four");
        }

    }

    static class Test {
        public static void run() {
            //AnagramsByDeletion.run();
            //Permutation.run();
            RansomNote.run();
        }
    }
}
