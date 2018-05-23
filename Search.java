import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Mike on 10/14/2017.
 */
public class Search {
    // From HackerRank, https://www.hackerrank.com/challenges/ctci-ice-cream-parlor
    static public class IceCreamParlor{
        static class IceCream implements Comparable{
            int flavor;
            int index;

            public IceCream(int flavor, int index) {
                this.flavor = flavor;
                this.index = index;
            }

            @Override
            public int compareTo(Object o) {
                if (o == null) {
                    throw new NullPointerException();
                }
                if (o instanceof Integer) {
                    return Integer.compare(this.flavor, (Integer)o);
                }
                if (o instanceof IceCream) {
                    return Integer.compare(this.flavor, ((IceCream)o).flavor);
                }
                throw new ClassCastException();
            }

            @Override
            public boolean equals(Object o){
                if (o == null) {
                    return false;
                }
                return compareTo(o) == 0;
            }
        }

        static public class Solution {
            public static int binarySearch(int first, int last, IceCream[] arr, int search) {
                if (first > last) {
                    return -1;
                }
                int med = (first + last) / 2;
                if (arr[med].flavor == search) {
                    return arr[med].index;
                }
                if (arr[med].flavor > search) {
                    return binarySearch(first, med - 1, arr, search);
                }
                return binarySearch(med + 1, last, arr, search);
            }

            public static void runCase(int money, int[] arr) {
                IceCream[] iceCreams = new IceCream[arr.length];
                for (int i = 0; i < arr.length; i++)
                    iceCreams[i] = new IceCream(arr[i], i + 1);
                runCase(money, iceCreams);
            }

            private static void runCase(int money, IceCream[] arr) {
                Arrays.sort(arr);
                int firstIndex = 100000, secondIndex = 100000;
                for(int i = 0; i < arr.length - 1 ; i++) {
                    int search = money - arr[i].flavor;
                    if(search >= arr[i].flavor) {
                        int index = binarySearch( i + 1, arr.length - 1, arr, search);
                        if( index != -1 ) {
                            System.out.println( Math.min(arr[i].index, index) + " " + Math.max(arr[i].index, index));
                            break;

                        }
                    }
                }
            }

            static public void run() {
                runCase(4, new int[] {1, 4, 5, 3, 2});
                runCase(4, new int[] {2, 2, 4, 3});
            }
        }



    }

    static public class Test{
        static public void run() {
            IceCreamParlor.Solution.run();
        }
    }
}
