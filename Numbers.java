import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

/**
 * Created by Mike on 9/8/2017.
 */
public class Numbers {
    // Useful articles to refresh bitwise operations and some properties:
    // https://www.geeksforgeeks.org/bitwise-hacks-for-competitive-programming/
    //

    public static class PairInt {
        public int a, b;
        public PairInt(int a, int b) {
            this.a = a;
            this.b = b;
        }
    }

    static public PairInt swap(PairInt p) {
        p.a ^= p.b;
        p.b ^= p.a;
        p.a ^= p.b;
        return p;
    }

    static public long getNextPowerOf2(int number) {
        System.out.println();
        return number;
    }

    // Checks if number contains all lowest bits set,
    // i.e. if it represents number 2^N-1
    // From Geeks4Geeks: https://practice.geeksforgeeks.org/problems/check-set-bits/0/?ref=self
    static public boolean hasAllBitsSet(int number) {
        return ((number +1) & number) == 0;
    }

    static public void tryStuff() {
        int a = 12;
        System.out.println("Number: " + (( (a + 1) ^ (~a)) ));

        int b = 56;
        System.out.println("a = " + a + ", b = " + b);
        a ^= b;
        b ^= a;
        a ^= b;
        System.out.println("a = " + a + ", b = " + b);

        Numbers.PairInt in = new Numbers.PairInt(a, b);
        Numbers.PairInt out = Numbers.swap(in);
        System.out.println("a = " + in.a + ", b = " + in.b);
        System.out.println("a = " + out.a + ", b = " + out.b);

    }

    public static boolean isPrime(int n) {
        if (n % 2 == 0)
            return false;
        long num = n;
        for (long idx = 3; idx * idx <= num; idx += 2) {
            if (num % idx == 0) {
                return false;
            }
        }
        return true;
    }

    // From leetcode https://leetcode.com/problems/reverse-integer/description/
    static public int reverseDigits(int numb) {
        long result = 0;
        boolean negative = numb < 0;
        if (numb == Integer.MIN_VALUE) {
            return 0;
        }
        numb = Math.abs(numb);
        while (numb != 0) {
            int rest = numb % 10;
            numb /= 10;
            result *= 10;
            result += rest;
            if (result > Integer.MAX_VALUE) {
                return 0;
            }
        }
        return negative ? (int)-result : (int)result;
    }

    // From leetcode https://leetcode.com/problems/string-to-integer-atoi/description/
    static public int myAtoi(String str) {
        if (str == null) {
            return 0;
        }
        str = str.trim();
        if (str.length() == 0) {
            return 0;
        }
        boolean negative = false;
        int cPos = 0;
        long result = 0;
        if (str.charAt(0)=='-') {
            negative = true;
            cPos = 1;
        } else if (str.charAt(0) == '+') {
            cPos = 1;
        }
        while (cPos < str.length()) {
            char ch = str.charAt(cPos);
            if (Character.isDigit(ch)) {
                result *= 10;
                result += ch - '0';
                if (result > Integer.MAX_VALUE) {
                    break;
                }
            } else {
                break;
            }
            ++cPos;
        }
        if (negative) {
            result = -result;
        }
        if (result >= Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (result <= Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) result;
    }

    // From leetcode https://leetcode.com/problems/valid-number/description/
    static public boolean isNumber(String s) {
        // {+|-}[d+{.}{d}|.d+]{e{+|-}d+}
        if (s == null) {
            return false;
        }
        s = s.trim();
        if (s.length() == 0) {
            return false;
        }
        int cPos = 0;
        if (s.charAt(cPos) == '-' || s.charAt(cPos) == '+') {
            ++cPos;
        }
        boolean hasInt = false;
        while (cPos < s.length() && Character.isDigit(s.charAt(cPos))) {
            ++cPos;
            hasInt = true;
        }
        if (cPos == s.length()) {
            return true;
        }
        boolean hasPoint = false;
        if (s.charAt(cPos) == '.') {
            ++cPos;
            hasPoint = true;
        }
        boolean hasFraction = false;
        while (cPos < s.length() && Character.isDigit(s.charAt(cPos))) {
            ++cPos;
            hasFraction = true;
        }
        if (!hasInt && !hasFraction) {
            return false;
        }
        if (cPos == s.length()) {
            return true;
        }
        if (s.charAt(cPos) == 'e' || s.charAt(cPos) == 'E') {
            ++cPos;
            if (cPos < s.length() && (s.charAt(cPos) == '-' || s.charAt(cPos) == '+')) {
                ++cPos;
            }
        }
        if (cPos == s.length()) {
            return false;
        }
        while (cPos < s.length() && Character.isDigit(s.charAt(cPos))) {
            ++cPos;
        }
        if (cPos == s.length()) {
            return true;
        }
        return false;
    }

    // From Geeks4geeks: https://www.geeksforgeeks.org/number-unique-triplets-whose-xor-zero/
    static public class TripletsXor {
        static public int countTriplets(int[] numb) {
            int res = 0;
            if (numb.length < 3)
                return 0;

            Set numbers = new HashSet(numb.length);
            for (int idx = 0; idx < numb.length; ++idx) {
                numbers.add(numb[idx]);
            }
            for (int idx = 0; idx < numb.length; ++idx) {
                for (int idx2 = idx + 1; idx2 < numb.length; ++idx2) {
                    int pair = numb[idx] ^ numb[idx2];
                    if (numbers.contains(pair)) {
                        ++res;
                    }
                }
            }
            return res / 3;
        }
    }

    // From Gekks4geeks: https://www.geeksforgeeks.org/calculate-xor-1-n/
    static public int getXorOfAllNumbersUptoN(int N) {
        int rest = N % 4;
        if (rest == 0) {
            return N;
        } else if (rest == 1) {
            return 1;
        } else if (rest == 2) {
            return N + 1;
        } else if (rest == 3) {
            return 0;
        }
        throw new RuntimeException("Impossible! N % 4 should be from 0 to 3");
    }

    // https://www.geeksforgeeks.org/equal-sum-xor/
    // Good important fact:
    // a + b = a ^ b + 2 * (a & b)

    static public class Test {
        static private void isNumberCase(String s, boolean expectPositive) {
            boolean isNumber = isNumber(s);
            System.out.println(String.format("String '%s' is%s a number -%s expected",
                    s,
                    isNumber ? "" : " not",
                    !(isNumber ^ expectPositive) ? "" : " NOT"));
        }
        static private void testIsNumber() {
            // test cases are taken from a discussion about the task
            isNumberCase("123", true);
            isNumberCase(" 123 ", true);
            isNumberCase("0", true);
            isNumberCase("0123", true);  //Cannot agree
            isNumberCase("00", true);  //Cannot agree
            isNumberCase("-10", true);
            isNumberCase("-0", true);
            isNumberCase("123.5", true);
            isNumberCase("123.000000", true);
            isNumberCase("-500.777", true);
            isNumberCase("0.0000001", true);
            isNumberCase("0.00000", true);
            isNumberCase("0.", true);  //Cannot be more disagree!!!
            isNumberCase("00.5", true);  //Strongly cannot agree
            isNumberCase("123e1", true);
            isNumberCase("1.23e10", true);
            isNumberCase("0.5e-10", true);
            isNumberCase("1.0e4.5", false);
            isNumberCase("0.5e04", true);
            isNumberCase("12 3", false);
            isNumberCase("1a3", false);
            isNumberCase("", false);
            isNumberCase("     ", false);
            isNumberCase(null, false);
            isNumberCase(".1", true); //Ok, if you say so
            isNumberCase(".", false);
            isNumberCase("2e0", true);  //Really?!
            isNumberCase("+.8", true);
            isNumberCase(" 005047e+6", true);  //Damn = =|||
            isNumberCase(" .e16", false);
        }

        static private void testAtoi() {
            System.out.println(Numbers.myAtoi("-2147483648"));
            System.out.println(Numbers.myAtoi("2147483647"));
            System.out.println(Numbers.myAtoi("2147483648"));
            System.out.println(Numbers.myAtoi("  -214748364800  "));
            System.out.println(Numbers.myAtoi("  214ABC7483648 dkd   "));
            System.out.println(Numbers.myAtoi("-ABC"));
            System.out.println(Numbers.myAtoi("   2 1 4 7 4 8 3648"));
            System.out.println(Numbers.myAtoi("   +-+--2 1 4 7 4 8 3648"));

        }

        private static void testReverseDigits() {
            System.out.println(Numbers.reverseDigits(Integer.MIN_VALUE + 1));
            System.out.println(Numbers.reverseDigits(-2147483648));
        }

        static public void run() {
            testIsNumber();
        }
    }

}
