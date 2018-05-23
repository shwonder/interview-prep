import java.util.*;

/**
 * Mostly for problems from the leetcode
 */
public class LC_Bst {
    /**
     * Definition for a binary tree node.
     */
    static public class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    /**
     * Definition of a binary tree node for question https://leetcode.com/problems/populating-next-right-pointers-in-each-node/
     */
    static public class TreeLinkNode {
        int val;
        TreeLinkNode left, right, next;
        TreeLinkNode(int x) { val = x; }
    }

    static public class Utils {
        /**
         * Creates binary tree based on node class defined on leetcode site.
         * Method creates tree from array of integers, null pointers are allowed and mean that node doesn't have a child.
         * Values in array are supposed to be placed into queue and then initialized in pairs for the current node.
         * That way samples on leetcode input matches to their trees outcome.
         * Another method would be to use heap-sort-like array, but it makes different outcome for null pointers in the
         * middle of a tree and doesn't match to the leetcode samples.
         * @param nodeValues array of values for tree nodes.
         * @return pointer to the root node
         */
        static public TreeNode makeTree(Integer[] nodeValues) {
            if (nodeValues == null || nodeValues.length == 0 || nodeValues[0] == null)
                return null;
            TreeNode root = new TreeNode(nodeValues[0].intValue());
            Queue<TreeNode> nodes = new ArrayDeque<TreeNode>();
            nodes.add(root);
            int cPos = 1;
            while (cPos < nodeValues.length && !nodes.isEmpty()) {
                TreeNode n = nodes.poll();
                if (cPos < nodeValues.length && nodeValues[cPos] != null) {
                    n.left = new TreeNode(nodeValues[cPos].intValue());
                    nodes.add(n.left);
                }
                ++cPos;
                if (cPos < nodeValues.length && nodeValues[cPos] != null) {
                    n.right = new TreeNode(nodeValues[cPos].intValue());
                    nodes.add(n.right);
                }
                ++cPos;
            }
            return root;
        }

        static public void printTree(TreeNode root) {
            if (root == null) {
                System.out.println("null");
                return;
            }
            List<TreeNode> level = new ArrayList<TreeNode>();
            level.add(root);
            while (level.size() > 0) {
                List<TreeNode> nextLevel = new ArrayList<TreeNode>();
                for (TreeNode node : level) {
                    if (node == null) {
                        System.out.print("null  ");
                        nextLevel.add(null);
                        nextLevel.add(null);
                    } else {
                        System.out.print(node.val + "  ");
                        nextLevel.add(node.left);
                        nextLevel.add(node.right);
                    }
                }
                System.out.println();
                boolean nextLevelNull = true;
                for (TreeNode node : nextLevel) {
                    if (node != null) {
                        nextLevelNull = false;
                        break;
                    }
                }
                if (nextLevelNull) {
                    break;
                }
                level = nextLevel;
            }
        }

        static public TreeLinkNode makeLinkedTree(Integer[] nodeValues) {
            if (nodeValues == null || nodeValues.length == 0 || nodeValues[0] == null)
                return null;
            TreeLinkNode root = new TreeLinkNode(nodeValues[0].intValue());
            Queue<TreeLinkNode> nodes = new ArrayDeque<TreeLinkNode>();
            nodes.add(root);
            int cPos = 1;
            while (cPos < nodeValues.length && !nodes.isEmpty()) {
                TreeLinkNode n = nodes.poll();
                if (cPos < nodeValues.length && nodeValues[cPos] != null) {
                    n.left = new TreeLinkNode(nodeValues[cPos].intValue());
                    nodes.add(n.left);
                }
                ++cPos;
                if (cPos < nodeValues.length && nodeValues[cPos] != null) {
                    n.right = new TreeLinkNode(nodeValues[cPos].intValue());
                    nodes.add(n.right);
                }
                ++cPos;
            }
            return root;
        }

        static public void printTree(TreeLinkNode root) {
            if (root == null) {
                System.out.println("null");
                return;
            }
            List<TreeLinkNode> level = new ArrayList<TreeLinkNode>();
            level.add(root);
            while (level.size() > 0) {
                List<TreeLinkNode> nextLevel = new ArrayList<TreeLinkNode>();
                for (TreeLinkNode node : level) {
                    if (node == null) {
                        System.out.print("null  ");
                        nextLevel.add(null);
                        nextLevel.add(null);
                    } else {
                        System.out.print(node.val + "(" + (node.next == null? "null" : node.next.val) + ")  ");
                        nextLevel.add(node.left);
                        nextLevel.add(node.right);
                    }
                }
                System.out.println();
                boolean nextLevelNull = true;
                for (TreeLinkNode node : nextLevel) {
                    if (node != null) {
                        nextLevelNull = false;
                        break;
                    }
                }
                if (nextLevelNull) {
                    break;
                }
                level = nextLevel;
            }
        }
    }

    // From leetcode: https://leetcode.com/problems/symmetric-tree/description/
    static class SymmetricTree {
        // result: 156 ms
        static public boolean isSymmetric_1(TreeNode root) {
            if (root == null)
                return true;
            ArrayList<TreeNode> cLevel = new ArrayList<TreeNode>();
            cLevel.add(root);
            while (cLevel.size() > 0) {
                int cLevelSize = cLevel.size();
                for (int idx = 0; idx < cLevelSize / 2; ++ idx) {
                    TreeNode left = cLevel.get(idx);
                    TreeNode right = cLevel.get(cLevelSize - idx - 1);
                    if (left != null && right != null) {
                        if (left.val != right.val) {
                            return false;
                        }
                    } else if (left != null || right != null) {
                        return false;
                    }
                }
                ArrayList<TreeNode> nextLevel = new ArrayList<TreeNode>(cLevel.size() * 2);
                for (TreeNode node : cLevel) {
                    if (node == null) {
                        nextLevel.add(null);
                        nextLevel.add(null);
                    } else {
                        nextLevel.add(node.left);
                        nextLevel.add(node.right);
                    }
                }
                boolean nextIsEmpty = true;
                for (TreeNode node : nextLevel) {
                    if (node != null) {
                        nextIsEmpty = false;
                        break;
                    }
                }
                if (nextIsEmpty)
                    return true;
                cLevel = nextLevel;
            }
            return true;
        }

        // Result: 19 ms
        static public boolean isSymmetric(TreeNode root) {
            if (root == null)
                return true;
            Stack<TreeNode> nodes = new Stack<TreeNode>();
            nodes.push(root.left);
            nodes.push(root.right);
            while (!nodes.isEmpty()) {
                TreeNode right = nodes.pop();
                TreeNode left = nodes.pop();
                if (left == null && right == null) {
                    continue;
                }
                if (left == null || right == null || left.val != right.val) {
                    return false;
                }
                nodes.push(left.right);
                nodes.push(right.left);
                nodes.push(left.left);
                nodes.push(right.right);
            }
            return true;
        }
    }

    // From leetcode: https://leetcode.com/problems/sum-root-to-leaf-numbers/description/
    static class SumRootToLeafNumbers {
        static private int getSum(TreeNode node, int pathNumb) {
            if (node == null) {
                return pathNumb;
            }
            pathNumb *= 10;
            pathNumb += node.val;
            int resSum = 0;
            if (node.left != null) {
                resSum += getSum(node.left, pathNumb);
            }
            if (node.right != null) {
                resSum += getSum(node.right, pathNumb);
            }
            if (resSum == 0) {
                return pathNumb;
            }
            return resSum;
        }

        static public int sumNumbers(TreeNode root) {
            if (root == null)
                return 0;
            return getSum(root, 0);
        }
    }

    // From leetcode: https://leetcode.com/problems/path-sum/
    static class FindSumInTreePath {
        static public boolean hasPathSum(TreeNode root, int sum) {
            if (root == null) {
                return false;
            }
            if ((root.left == null && root.right == null)&& sum == root.val) {
                return true;
            }
            if (root.left != null && hasPathSum(root.left, sum - root.val)) {
                return true;
            }
            if (root.right != null && hasPathSum(root.right, sum - root.val)) {
                return true;
            }
            return false;
        }
    }

    // From leetcode: https://leetcode.com/problems/path-sum-ii/
    static class FindAllPathsWithSum {
        static private void checkPath(TreeNode node, int sum, ArrayList<Integer> currentPath, List<List<Integer>> result) {
            if (node == null) {
                return;
            }
            if (node.left == null && node.right == null && node.val == sum) {
                ArrayList<Integer> r = (ArrayList<Integer>) currentPath.clone();
                r.add(node.val);
                result.add(r);
                return;
            }
            if (node.left != null || node.right != null) {
                currentPath.add(node.val);
                if (node.left != null) {
                    checkPath(node.left, sum - node.val, currentPath, result);
                }
                if (node.right != null) {
                    checkPath(node.right, sum - node.val, currentPath, result);
                }
                currentPath.remove(currentPath.size() - 1);
            }
        }

        static public List<List<Integer>> pathSum(TreeNode root, int sum) {
            List<List<Integer>> res = new ArrayList<List<Integer>>();
            ArrayList<Integer> path = new ArrayList<Integer>();
            checkPath(root, sum, path, res);
            return res;
        }
    }

    // From leetcode: https://leetcode.com/problems/binary-tree-paths/
    static class FindAllPaths {
        static private void getPaths(TreeNode node, String currentPath, List<String> result) {
            if (node == null) {
                return;
            }
            String nodePath = currentPath + ((currentPath.length() > 0) ? "->" : "") + node.val;
            if (node.left == null && node.right == null) {
                result.add(nodePath);
            } else {
                if (node.left != null) {
                    getPaths(node.left, nodePath, result);
                }
                if (node.right != null) {
                    getPaths(node.right, nodePath, result);
                }
            }
        }

        static public List<String> binaryTreePaths(TreeNode root) {
            List<String> result = new ArrayList<String>();
            getPaths(root, "", result);
            return result;
        }
    }

    // From leetcode: https://leetcode.com/problems/populating-next-right-pointers-in-each-node/
    static class ConnectNodesToRight {
        static public void connectNode(TreeLinkNode node, TreeLinkNode rightSibling) {
            if (node == null)
                return;
            // tree is perfect or filled completely. If left child exists, right also exists. If left is null, right is also null;
            if (node.left == null)
                return;
            node.left.next = node.right;
            if (rightSibling != null) {
                node.right.next = rightSibling.left;
            }
            connectNode(node.left, node.right);
            connectNode(node.right, node.right.next);
        }

        static public void connect(TreeLinkNode root) {
            if (root == null)
                return;
            if (root.left != null) {
                connectNode(root, null);
            }
        }
    }

    // From leetcode: https://leetcode.com/problems/populating-next-right-pointers-in-each-node-ii/
    static class ConnectNodesToRight2 {
        static public void connectNode(TreeLinkNode node, int cLeven, ArrayList<TreeNode> path, TreeLinkNode rightSibling) {
            if (node == null)
                return;
            if (node.left != null) {
                if (node.right != null) {
                    node.left.next = node.right;
                } else {
                    // find leftmost sibling on the right side
                }
            }
            if (rightSibling != null) {
                node.right.next = rightSibling.left;
            }
            //connectNode(node.left, node.right);
            //connectNode(node.right, node.right.next);
        }
    }

    // From leetcode: https://leetcode.com/problems/binary-tree-right-side-view/
    static class RightSideView {
        static private void getRightViewNodes(TreeNode node, int cLevel, List<Integer> result)  {
            if (node == null) {
                return;
            }
            if (cLevel > result.size()) {
                result.add(node.val);
            }
            if (node.right != null) {
                getRightViewNodes(node.right, cLevel + 1, result);
            }
            if (node.left != null) {
                getRightViewNodes(node.left, cLevel + 1, result);
            }
        }

        static public List<Integer> rightSideView(TreeNode root) {
            List<Integer> result = new ArrayList<Integer>();
            getRightViewNodes(root, 1, result);
            return result;
        }

    }

    // Need to try: https://leetcode.com/problems/boundary-of-binary-tree - premium

    static class Test {
        static private Integer[][] commonTrees = new Integer[][] {
                new Integer[] {1,2,2,3,4,4,3},
                new Integer[] {1,2,2,null,3,null,3},
                new Integer[] {1,2,2,3,4,4,3, 5, 6, 7, 8, 8, 7, 6, 5}
        };
        static private void isSymmetric_case(Integer[] arr) {
            TreeNode root = Utils.makeTree(arr);
            Utils.printTree(root);
            boolean symm = SymmetricTree.isSymmetric(root);
            System.out.println("Tree is " + (symm ? "" : "NOT ") + "symmetric");
            System.out.println();
        }

        static private void testIsSymmetric() {
            for (Integer[] arr : commonTrees) {
                isSymmetric_case(arr);
            }
        }

        static private void sumAllPaths_case(Integer[] arr) {
            TreeNode root = Utils.makeTree(arr);
            Utils.printTree(root);
            int sum = SumRootToLeafNumbers.sumNumbers(root);
            System.out.println("Tree sum is " + sum);
        }

        static private void testSumAllPathRootToLeaf() {
            for (Integer[] arr : commonTrees) {
                sumAllPaths_case(arr);
            }
        }

        static private void pathSumExists_case(Integer[] arr, int findSum, boolean expected) {
            TreeNode root = Utils.makeTree(arr);
            Utils.printTree(root);
            boolean hasSum = FindSumInTreePath.hasPathSum(root, findSum);
            System.out.println("Tree has " + (hasSum? "" : "NO ") + "path sum " + findSum + ", expected: " + expected);
        }

        static private void testPathSumExists() {
            pathSumExists_case(commonTrees[0], 7, true);
            pathSumExists_case(commonTrees[0], 8, false);
            pathSumExists_case(commonTrees[1], 6, true);
            pathSumExists_case(commonTrees[1], 3, false);
            pathSumExists_case(commonTrees[1], 70, false);
            pathSumExists_case(commonTrees[2], 15, true);
            Integer[] arr = new Integer[] {5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1};
            pathSumExists_case(arr, 26, true);
            pathSumExists_case(arr, 9, false);
            pathSumExists_case(arr, 18, true);
            pathSumExists_case(arr, 17, false);
        }

        static private void allPathsSum_case(Integer[] arr, int findSum, int numberExpected) {
            TreeNode root = Utils.makeTree(arr);
            Utils.printTree(root);
            List<List<Integer>> res = FindAllPathsWithSum.pathSum(root, findSum);
            System.out.println("Tree has " + res.size() + " paths root to leaf with sum " + findSum + ", expected: " + numberExpected);
            Array.Print.printListOfLists(res, "\n");
            System.out.println();
        }

        static private void testAllPathSum() {
            allPathsSum_case(commonTrees[0], 7, 2);
            allPathsSum_case(commonTrees[1], 6, 2);
            allPathsSum_case(commonTrees[2], 15, 2);
            allPathsSum_case(commonTrees[0], 70, 0);
            Integer[] arr = new Integer[] {5, 4, 8, 11, null, 13, 4, 7, 2, null, null, 5, 1}; // slightly different than testPathSumExists
            allPathsSum_case(arr, 22, 2);
            allPathsSum_case(arr, 27, 1);
        }

        static private void testAllPaths_case(Integer[] arr) {
            TreeNode root = Utils.makeTree(arr);
            Utils.printTree(root);
            List<String> res = FindAllPaths.binaryTreePaths(root);
            System.out.print("[");
            boolean first = true;
            for (String path: res) {
                if (first) {
                    first = false;
                } else {
                    System.out.print(", ");
                }
                System.out.print(path);
            }
            System.out.println("]");
        }

        static private void testAllPaths() {
            Integer[] arr = new Integer[] {1, 2, 3, null, 5};
            testAllPaths_case(arr);
            arr = new Integer[] {5, 4, 8, 11, null, 13, 4, 7, 2, null, null, null, 1};
            testAllPaths_case(arr);
            for (Integer[] array: commonTrees) {
                testAllPaths_case(array);
            }
        }

        static private void testConnect_case(Integer[] arr) {
            TreeLinkNode root = Utils.makeLinkedTree(arr);
            Utils.printTree(root);
            ConnectNodesToRight.connect(root);
            Utils.printTree(root);
        }

        static private void testConnect() {
            testConnect_case(new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15});
            testConnect_case(new Integer[]{-1,0,1,2,3,4,5,6,7,8,9,10,11,12,13});
            List<Integer> arr = new ArrayList<Integer>(31);
            for (int i = 1; i < 32; ++i) {
                arr.add(i);
            }
            testConnect_case(arr.toArray(new Integer[0]));
        }

        static private void testRightSideView_case(Integer[] arr) {
            TreeNode root = Utils.makeTree(arr);
            Utils.printTree(root);
            List<Integer> result = RightSideView.rightSideView(root);
            System.out.println(Array.Utils.toString(result));
        }

        static private void testRightSideView() {
            testRightSideView_case(commonTrees[0]);
            testRightSideView_case(new Integer[]{1, 2, 3, null, 5, null, 4});
            testRightSideView_case(new Integer[]{1, 2, 3, null, 5});
            testRightSideView_case(new Integer[]{1, 2, 3, null, 5, null, 4, 6, null, null, null, 7});
            /*
               1            <---
 /   \
2     3         <---
 \     \
  5     4       <---
            */
        }

        static public void run() {
            testRightSideView();
        }
    }

}
