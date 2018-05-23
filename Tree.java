/**
 * Created by Mike on 9/1/2017.
 */
public class Tree {
    static class Node {
        int data;
        Node left;
        Node right;
        Node(int d) {
            this.data = d;
            this.left = null;
            this.right = null;
        }
        Node () {}
    }

    // From HackerRank
    static class isBst{
        static class SubtreeInfo {
            int minValue;
            int maxValue;
            boolean isBst;
            SubtreeInfo() {
                minValue = Integer.MAX_VALUE;
                maxValue = Integer.MIN_VALUE;
                isBst = false;
            }
        }
        static boolean isBst(Node root) {
            SubtreeInfo check = checkSubtree(root);
            return check.isBst;
        }

        static SubtreeInfo checkSubtree(Node node) {
            SubtreeInfo leftInfo = new SubtreeInfo();
            if (node.left != null) {
                leftInfo = checkSubtree(node.left);
                if (!leftInfo.isBst)
                    return leftInfo;
                if (leftInfo.maxValue >= node.data) {
                    leftInfo.isBst = false;
                    return leftInfo;
                }
            }
            SubtreeInfo rightInfo = new SubtreeInfo();
            if (node.right != null) {
                rightInfo = checkSubtree(node.right);
                if (!rightInfo.isBst)
                    return rightInfo;
                if (rightInfo.minValue <= node.data) {
                    rightInfo.isBst = false;
                    return rightInfo;
                }
            }
            SubtreeInfo result = new SubtreeInfo();
            if (node.left != null) {
                result.minValue = leftInfo.minValue;
            } else {
                result.minValue = node.data;
            }
            if (node.right != null) {
                result.maxValue = rightInfo.maxValue;
            } else {
                result.maxValue = node.data;
            }
            result.isBst = true;
            return result;
        }

        static void run() {
            Node root = new Node(4);
            root.left = new Node(2);
            root.left.left = new Node(1);
            root.left.right = new Node(13);
            root.right = new Node(6);
            root.right.left = new Node(5);
            root.right.right = new Node(7);

            System.out.println("is BST:" + isBst(root));
        }
    }

    // From HackerRank, https://www.hackerrank.com/challenges/tree-flow
    static class TreeFlow {
        int [][]matrix;
        int size;
        public TreeFlow(int size) {
            this.size = size;
            this.matrix = new int[size+1][size+1];
        }
        public void addWeight(int i, int j, int weight) {
            this.matrix[i][j] = weight;
            this.matrix[j][i] = -weight;
        }
        //public int get
    }
    static class Test {
        static void run() {
            isBst.run();
        }
    }
}
