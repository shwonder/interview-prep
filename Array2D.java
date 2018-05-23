/**
 * Created by Mike on 3/4/2017.
 */
public class Array2D {

    static boolean validateArraySquare(int [][]pic) {
        int N = pic.length;
        for (int rowIdx = 0; rowIdx < N; ++rowIdx) {
            if (pic[rowIdx].length != N) {
                System.out.println("Array should be a square, number of rows: " + N + ", row # " + rowIdx+1 +
                                   " has size of " + pic[rowIdx].length);
                return false;
            }
        }
        return true;
    }

    static boolean validateArrayRectangle(int [][]pic) {
        int rowsCount = pic.length;
        if (rowsCount == 0)
            return true;
        int rowLength = pic[0].length;
        for (int rowIdx = 0; rowIdx < rowsCount; ++rowIdx) {
            if (pic[rowIdx].length != rowLength) {
                System.out.println("Array should be a rectangle, number of rows: " + rowsCount + ", row #0 has " +
                        rowLength + " items while row # " + rowIdx+1 + " has size of " + pic[rowIdx].length);
                return false;
            }
        }
        return true;
    }

    static int[][] rotateCounterClockwise(int[][] pic) {
        if (!validateArraySquare(pic))
            return pic;
        int N = pic.length;
        int[][] res = new int[N][N];
        for (int rowIdx = 0; rowIdx < N; ++rowIdx) {
            for (int colIdx = 0; colIdx < N; ++colIdx) {
                res[colIdx][rowIdx] = pic[rowIdx][N-1-colIdx];
            }
        }
        return res;
    }

    static int[][] rotateClockwise(int[][] pic) {
        if (!validateArraySquare(pic)) return pic;
        int N = pic.length;
        int[][] res = new int[N][N];
        for(int rIdx = 0; rIdx < N; ++rIdx){
            for(int cIdx = 0; cIdx < N; ++cIdx) {
                res[rIdx][cIdx] = pic[N-1-cIdx][rIdx];
            }
        }
        return res;
    }

    static void rotateClockwiseInplace(int[][] pic) {
        if (!validateArrayRectangle(pic)) return;
        int N = pic.length;
        for(int rIdx = 0; rIdx < N/2; ++rIdx) {
            for(int cIdx = 0; cIdx < N/2; ++cIdx) {
                int tmp = pic[rIdx][cIdx];
                pic[rIdx][cIdx] = pic[N-1-cIdx][rIdx];
                pic[N-1-cIdx][rIdx] = pic[N-1-rIdx][N-1-cIdx];
                pic[N-1-rIdx][N-1-cIdx] = pic[cIdx][N-1-rIdx];
                pic[cIdx][N-1-rIdx] = tmp;
            }
        }
        if ((N/2)*2 != N) {
            int rIdx = N/2+1;
            for(int cIdx = 0; cIdx < N/2; ++cIdx) {
                int tmp = pic[rIdx][cIdx];
                pic[rIdx][cIdx] = pic[N-1-cIdx][rIdx];
                pic[N-1-cIdx][rIdx] = pic[N-1-cIdx][N-1-rIdx];
                pic[N-1-cIdx][N-1-rIdx] = pic[cIdx][N-1-rIdx];
                pic[cIdx][N-1-rIdx] = tmp;
            }
        }
    }

    static void print(int[][] pic) {
        int N = pic.length;
        int M = pic[0].length;
        for (int rIdx = 0; rIdx < N; ++rIdx) {
            System.out.print(pic[rIdx][0]);
            for (int cIdx = 1; cIdx < M; ++cIdx) {
                System.out.print(", " + pic[rIdx][cIdx]);
            }
            System.out.println();
        }
    }

    static boolean doMatch(int[][] pic1, int[][] pic2) {
        if(pic1.length != pic2.length)
            return false;
        int N = pic1.length;
        for (int rIdx = 0; rIdx < N; ++rIdx) {
            for(int cIdx = 0; cIdx < N; ++cIdx) {
                if (pic1[rIdx][cIdx] != pic2[rIdx][cIdx])
                    return false;
            }
        }
        return true;
    }

    static int[][] createAndFillSequentially(int N, int startValue, int diff) {
        int currValue = startValue;
        int[][] res = new int[N][N];
        for(int rIdx = 0; rIdx < N; ++rIdx) {
            for(int cIdx = 0; cIdx < N; ++cIdx) {
                res[rIdx][cIdx] = currValue;
                currValue += diff;
            }
        }
        return res;
    }

    static int[][] createAndFillSequentially(int N, int startValue) {
        return createAndFillSequentially(N, startValue, 1);
    }

    static int[][] createAndFillSequentiallyRectangle(int N, int M, int startValue, int diff) {
        int currValue = startValue;
        int[][] res = new int[N][M];
        for(int rIdx = 0; rIdx < N; ++rIdx) {
            for(int cIdx = 0; cIdx < M; ++cIdx) {
                res[rIdx][cIdx] = currValue;
                currValue += diff;
            }
        }
        return res;
    }

    static void printSpiral(int[][] pic) {
        int sizeX = pic[0].length;
        int sizeY = pic.length;
        for (int delta = 0; delta < Math.min(sizeX/2, sizeY/2); ++delta) {
            int rowSize = sizeX - 2 * delta;
            int colSize = sizeY - 2 * delta;
            int col = delta;
            int row = delta;
            for (; col < delta + rowSize -1; ++col) {
                System.out.print(pic[row][col] + " ");
            }
            col = sizeX - delta - 1;
            for (; row < delta + colSize - 1; ++row) {
                System.out.print(pic[row][col] + " ");
            }
            row = sizeY - delta - 1;
            for (; col > delta; --col) {
                System.out.print(pic[row][col] + " ");
            }
            col = delta;
            for (; row > delta; --row) {
                System.out.print(pic[row][col] + " ");
            }
        }
    }

    static class Test{
        static void run() {
            int[][] arr = createAndFillSequentially(10, 1);
            System.out.println("Initial array:");
            print(arr);
            System.out.println();
            int[][] rotcc1 = rotateCounterClockwise(arr);
            System.out.println("Rotated array:");
            print(rotcc1);
            int[][] rotcc2 = rotateCounterClockwise(rotcc1);
            System.out.println();
            System.out.println("Rotated array second time:");
            print(rotcc2);
            int[][] rotcc3 = rotateCounterClockwise(rotcc2);
            System.out.println();
            System.out.println("Rotated array third time:");
            print(rotcc3);
            int[][] rotcc4 = rotateCounterClockwise(rotcc3);
            System.out.println();
            System.out.println("Rotated array forth time:");
            print(rotcc4);
            System.out.println("Initial array and rotated forth time do match: " + doMatch(arr, rotcc4));

            int[][] rotc1 = rotateClockwise(arr);
            System.out.println();
            System.out.println("Rotated clockwise (1):");
            print(rotc1);
            System.out.println("Clockwise rotated and counter clockwise three times match: " + doMatch(rotc1, rotcc3));

            int[][] rotc1_1 = createAndFillSequentially(10, 1);
            rotateClockwiseInplace(rotc1_1);
            System.out.println();
            System.out.println("Rotated clockwise inplace (1):");
            print(rotc1_1);
            System.out.println("Clockwise rotated and rotated inplace match: " + doMatch(rotc1, rotc1_1));

            System.out.println("Spiral array print:");
            printSpiral(arr);
            int [][]rectangle1 = createAndFillSequentiallyRectangle(10, 5, 1, 1);
            System.out.println();
            System.out.println("Rectangle array:");
            print(rectangle1);
            printSpiral(rectangle1);
            int [][]square11 = createAndFillSequentially(11, 1);
            System.out.println();
            System.out.println("Square 1 by 11");
            print(square11);
            printSpiral(square11);
        }
    }
}
