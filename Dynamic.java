import java.util.*;

/**
 * Created by Mike on 10/11/2017.
 */
public class Dynamic {
    static public class CoinChange {
        static class ChangeResult {
            List<long[]> changes;
            void addToResult(int [] coins, int coin) {
                for(int idx = 0; idx < coins.length; ++idx) {
                    if (coins[idx] == coin) {
                        if (changes == null) {
                            changes = new ArrayList<long[]>();
                            changes.add(new long[coins.length]);
                        }
                        for(long[] change : changes) {
                            ++change[idx];
                        }
                    }
                }
            }

            ChangeResult copyNew() {
                ChangeResult result = new ChangeResult();
                if (this.changes != null) {
                    result.changes = new ArrayList<long[]>(this.changes.size());
                    for (long[] change : this.changes) {
                        long[] changeCopy = Arrays.copyOf(change, change.length);
                        result.changes.add(changeCopy);
                    }
                }
                return result;
            }

            void removeDuplicates() {
                Set<Integer> changesToRemove = new HashSet<Integer>();

                this.changes.listIterator();
                for(ListIterator<long[]> it = this.changes.listIterator(); it.hasNext();) {
                    long[] arr1 = it.next();
                    for (ListIterator<long[]> it2 = this.changes.listIterator(it.nextIndex()); it2.hasNext();) {
                        long[] arr2 = it2.next();
                        if (arr1.length != arr2.length) {
                            throw new RuntimeException("Array sizes should match! array 1 size " + arr1.length + " array 2 size " + arr2.length);
                        }
                        boolean match = true;
                        for(int idx = 0; idx < arr1.length; ++idx) {
                            if (arr1[idx] != arr2[idx]) {
                                match = false;
                                break;
                            }
                        }
                        if (match) {
                            changesToRemove.add(it2.previousIndex() );
//                            System.out.println("Adding to remove list match of array " + Arrays.toString(arr2) + " idx "
//                                    + (it2.previousIndex()) + " to array " + Arrays.toString(arr1) + " idx "
//                                    + (it.previousIndex()));
                        }
                    }
                }
                int removedCnt = 0;
                for (int idxToRemove : changesToRemove) {
//                    System.out.println("Removing change number " + idxToRemove + " from list of size " + this.changes.size());
                    this.changes.remove(idxToRemove - removedCnt);
                    ++removedCnt;
                }
            }

            void mergeChanges(ChangeResult newResults) {
                if (changes == null) {
                    changes = new ArrayList<long[]>();
                }
                for(long[] change : newResults.changes) {
                    this.changes.add(change);
                }
                removeDuplicates();
            }

        }

        private static long countOptions(int[] coins, int money, ChangeResult[] results) {
            if (money == 0) {
                if (results[0] == null) {
                    results[0] = new ChangeResult();
                }
                return 1;
            }
            if (money < 0) {
                return 0;
            }
            if (money > 250) {
                throw new IllegalArgumentException("Argument 'money' should be not more than 250");
            }
            if (results[money] != null && results[money].changes != null) {
                return results[money].changes.size();
            }
            int numb = 0;
            ChangeResult myResult = new ChangeResult();
            for(int idx = 0; idx < coins.length; ++idx) {
                boolean found = false;
                for (int idx2 = 0; idx2 < idx; ++idx2) {
                    if (money - coins[idx] == coins[idx2]){
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    if (countOptions(coins, money - coins[idx], results) > 0) {
                        ChangeResult res = results[money-coins[idx]];
                        ChangeResult myRes = res.copyNew();
                        myRes.addToResult(coins, coins[idx]);
                        myResult.mergeChanges(myRes);
                    }
                }
            }
            results[money] = myResult;
            if (myResult.changes != null) {
                return myResult.changes.size();
            } else {
                return 0;
            }
        }

        public static long makeChange(int[] coins, int money) {
            ChangeResult [] results = new ChangeResult[money + 1];
            return countOptions(coins, money, results);
        }

        private static void printTestCase(int[] coins, int money, long number) {
            System.out.println(String.format("Coins: %s, money: %d, number of changes: %d", Arrays.toString(coins), money, number));
        }

        private static void testRun(int[] coins, int money) {

        }

        public static void run() {
            int [] coins = new int[] {1, 2, 3};
            int money = 4;
            printTestCase(coins, money, makeChange(coins, money));
            coins = new int[] {2, 5, 3, 6};
            money = 10;
            printTestCase(coins, money, makeChange(coins, money));

            coins = new int[] {1, 3, 5};
            money = 4;
            printTestCase(coins, money, makeChange(coins, money));

        }
    }

    static public class CoinChange2 {
        public static long makeChange(int[] coins, int money) {
            long [] changes = new long[money + 1];
            changes[0] = 1;
            for (int coin : coins) {
                for (int idx = coin; idx <= money; ++idx) {
                    changes[idx] += changes[idx - coin];
                }
            }
            return changes[money];
        }

        public static void run() {
            int [] coins = new int[] {1, 2, 3};
            int money = 4;
            CoinChange.printTestCase(coins, money, makeChange(coins, money));
            coins = new int[] {2, 5, 3, 6};
            money = 10;
            CoinChange.printTestCase(coins, money, makeChange(coins, money));

            coins = new int[] {1, 3, 5};
            money = 4;
            CoinChange.printTestCase(coins, money, makeChange(coins, money));

        }

    }

    // From HackerRank, https://www.hackerrank.com/challenges/ctci-recursive-staircase
    static class Staircases {
        static private long[] results = new long[37];

        static public long countVariants(int staircaseHeight) {
            if (staircaseHeight <= 0) {
                return 0;
            }
            if (staircaseHeight > 36)
                throw new IllegalArgumentException("Height should be not more than 36");
            if (results[staircaseHeight] != 0)
                return results[staircaseHeight];
            long res = countVariants(staircaseHeight - 1); // lets make one step
            res += countVariants(staircaseHeight - 2);
            res += countVariants(staircaseHeight - 3);
            results[staircaseHeight] = res;
            return res;
        }

        static public void runCase(int height) {
            System.out.println("Number of ways to climb staircase of height " + height + " is " + countVariants(height));
        }

        static public void run() {
            results[1] = 1;
            results[2] = 2;
            results[3] = 4;
            runCase(0);
            runCase(1);
            runCase(3);
            runCase(7);
        }
    }

    static public class Test {
        static public void run() {
            // CoinChange2.run();
            Staircases.run();

        }
    }

}
