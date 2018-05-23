import java.lang.System;

public class LinkedList {
    static class Node {
        public int value;
        public Node next;

        public Node(int value) {
            this.value = value;
            next = null;
        }
    }

    static class Test {
        private static void testReverse(int[] testSet) {
            LinkedList.Node head = LinkedList.create(testSet);
            System.out.println("Initial linked list:");
            LinkedList.print(head);
            System.out.println("Reversed linked list:");
            LinkedList.Node reversed = LinkedList.reverse(head);
            LinkedList.print(reversed);
            head = LinkedList.reverse(reversed);
            Node list2 = LinkedList.create(testSet);
            for(Node it1 = head, it2 = list2; it1 != null && it2 != null; it1 = it1.next, it2 = it2.next) {
                if (it1.value != it2.value) {
                    System.out.println("List items doesn't match. Item in double reversed list is '"
                            + it1.value + "', item in original list is '" + it2.value + "'");
                    System.out.println("Original list:");
                    LinkedList.print(list2);
                    System.out.println("Double reverted list:");
                    LinkedList.print(head);
                    throw new RuntimeException("Values of two lists doesn't match");
                }
            }
            LinkedList.Node lastNode = LinkedList.getLastNode(reversed);
        }

        private static void testLoopInLinkedList() {
            Node list = new Node(1);
//            System.out.println(String.format("Null list has loops: %b, loop size: %d, ", hasLoop(null), getLoopSize(getNodeInLoop(null))));
        }
        private static void loopOperations_case(int[] testSet) {
            LinkedList.Node head = LinkedList.create(testSet);
            System.out.println("Initial linked list:");
            LinkedList.print(head);
            LinkedList.Node lastNode = LinkedList.getLastNode(head);

            for (int cyclePosition = 0; cyclePosition < testSet.length; ++cyclePosition) {
                System.out.println("Loop detected in the list: " + LinkedList.hasLoop(head));
                System.out.println("Making a loop of size " + (testSet.length - cyclePosition) + " starting at position " + cyclePosition);
                LinkedList.Node nodeLoopStart = LinkedList.getNthNode(head, cyclePosition);
                lastNode.next = nodeLoopStart;
                System.out.println("Printing first 15 nodes of looped list");
                LinkedList.printFirstN(head, 15);
                System.out.println("Loop detected in the list: " + LinkedList.hasLoop(head));
                LinkedList.Node nodeInside = LinkedList.getNodeInLoop(head);
                int loopSize = LinkedList.getLoopSize(nodeInside);
                System.out.println("Size of the loop: " + loopSize);
                int distanceToLoopNode = LinkedList.getDistanceBetweenNodes(head, nodeInside);
                System.out.println("Distance from the head to the node inside the loop (" + nodeInside.value + "): " + distanceToLoopNode);

                System.out.println("Beginning of list is part of loop: " + LinkedList.isNodeInsideLoop(head, nodeInside)
                        + ", last node is part of the loop: " + LinkedList.isNodeInsideLoop(lastNode, nodeInside));
                LinkedList.breakTheLoop(head, nodeInside);
                System.out.println("Removing the loop. List:");
                LinkedList.print(head);
            }
        }

        public static void testInsertSortedAndDelete() {
            int [] items = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
            Node ascendingList = LinkedList.create(items);
            System.out.println("Testing insert operation. List sorted in ascending order:");
            LinkedList.print(ascendingList);
            ascendingList = LinkedList.insertIntoSortedListAsc(ascendingList, 5);
            ascendingList = LinkedList.insertIntoSortedListAsc(ascendingList, 0);
            ascendingList = LinkedList.insertIntoSortedListAsc(ascendingList, 15);
            System.out.println("After inserting 0, 5 and 15:");
            LinkedList.print(ascendingList);
            System.out.println("Building ordered list from the empty one");
            Node list2 = LinkedList.insertIntoSortedListAsc(null, 13);
            list2 = LinkedList.insertIntoSortedListAsc(list2, 5);
            list2 = LinkedList.insertIntoSortedListAsc(list2, 18);
            list2 = LinkedList.insertIntoSortedListAsc(list2, 20);
            list2 = LinkedList.insertIntoSortedListAsc(list2, 10);
            LinkedList.print(list2);
            System.out.println("Deleting nodes");
            LinkedList.deleteNode(list2, list2);
            LinkedList.print(list2);
            Node node2del = list2.next.next;
            LinkedList.deleteNode(list2, node2del);
            LinkedList.print(list2);
            node2del = LinkedList.getLastNode(list2);
            LinkedList.deleteNode(list2, node2del);
            LinkedList.print(list2);
        }

        static public void testSumOfLists() {
            Node list1 = create(new int[] {1, 9, 5, 6, 9, 3, 2});
            Node list2 = create(new int[] {5, 3, 7, 8});
            Node sum = sumLists(list1, list2);
            print(list1);
            print(list2);
            print(sum);
        }
        static public void run() {
            int [] items = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
//            testReverse(items);
//            loopOperations_case(items);
//            testInsertSortedAndDelete();
            // testSumOfLists();
            reversePractice(null);
            reverse1(null);
            Node list = new Node(5);
            Node newHead = reversePractice(list);
            assert newHead == list;
            list = LinkedList.create(items);
            print(list);
            Node loopStart = getNthNode(list, 5);
            Node lastNode = getLastNode(list);
            System.out.println(String.format("Loop will start with node %d and end wih node %d", loopStart.value, lastNode.value));
            lastNode.next = loopStart;
            newHead = reversePractice(list);
            assert newHead == list;
            printFirstN(newHead, 15);
            System.out.println("Done list test");

        }
    }

    public static Node create(int[] items) {
        if (items.length == 0)
            throw new IllegalArgumentException("At least one element is required");
        Node head = new Node(items[0]);
        Node lastNode = head;
        for (int idx = 1; idx < items.length; ++idx) {
            lastNode.next = new Node(items[idx]);
            lastNode = lastNode.next;
        }
        return head;
    }

    public static Node reverse1(Node listHead) {
        if (listHead == null || listHead.next == null) {
            return listHead;
        }
        Node current = listHead;
        Node prev = null;
        do {
            Node tmp = current.next;
            current.next = prev;
            prev = current;
            current = tmp;
        } while (current != null);
        return prev;
    }

    public static Node reverse(Node listHead) {
        Node lastNode = getLastNode(listHead);
        reverseInterval(listHead, lastNode);
        return listHead; // first and last are in place, only values are reverted
    }

    public static Node reversePractice(Node listHead) {
        if (listHead == null || listHead.next == null) {
            return listHead;
        }
        Node prevNode = listHead;
        Node currNode = listHead.next;
        prevNode.next = null;
        while (currNode != null) {
            Node temp = currNode.next;
            currNode.next = prevNode;
            prevNode = currNode;
            currNode = temp;
        }
        return prevNode;
    }

    /**
     * Reverses part of the linked list between two specified nodes, including them but addresses of boundary nodes
     * kept unchanged.
     * @param first
     * @param last
     */
    public static void reverseInterval(Node first, Node last) {
        if (first == null)
            throw new IllegalArgumentException("Argument 'first' value is null");
        if (last == null)
            throw new IllegalArgumentException("Argument 'last' value is null");

        if (first == last) {
            return;
        }

        int tmpValue = last.value;
        last.value = first.value;
        first.value = tmpValue;

        if (first.next == last) {
            return;
        }

        Node current = first.next;
        Node prev = last;
        while (current != last) {
            Node tmp = current.next;
            current.next = prev;
            prev = current;
            current = tmp;
        }
        first.next = prev;
    }

    public static void print(Node listHead) {
        Node current = listHead;
        System.out.print(current.value);
        current = current.next;
        while (current != null) {
            System.out.print(", " + current.value);
            current = current.next;
        }
        System.out.println();
    }

    public static void printFirstN(Node listHead, int n) {
        Node current = listHead;
        System.out.print(current.value);
        current = current.next;
        int printed = 1;
        while (current != null && printed < n) {
            System.out.print(", " + current.value);
            current = current.next;
            ++printed;
        }
        System.out.println();
    }

    public static Node getLastNode(Node head) {
        if (head == null)
            return null;
        while (head.next != null)
            head = head.next;
        return head;
    }

    public static Node getNthNode(Node head, int n) {
        int idx = 0;
        while (head.next != null && idx < n) {
            head = head.next;
            ++idx;
        }
        return head;
    }

    public static boolean hasLoop(Node listHead) {
        return getNodeInLoop(listHead) != null;
    }

    public static Node getNodeInLoop(Node listHead) {
        if (listHead == null)
            return null;
        Node ptr1 = listHead;
        Node ptr2 = listHead;
        while (true) {
            if (ptr2.next != null) {
                ptr2 = ptr2.next;
                if (ptr2.next != null)
                    ptr2 = ptr2.next;
                else
                    return null;
                ptr1 = ptr1.next;
                if (ptr1 == ptr2)
                    return ptr1;
            } else
                return null;
        }
    }

    public static int getLoopSize(Node nodeInsideLoop) {
        LinkedList.Node currentNode = nodeInsideLoop;
        int loopSize = 1;
        while (currentNode.next != nodeInsideLoop) {
            currentNode = currentNode.next;
            ++loopSize;
        }
        return loopSize;
    }

    public static int getDistanceBetweenNodes(Node start, Node end) {
        Node currentNode = start;
        int distance = 0;
        while (currentNode != end) {
            if (currentNode != null) {
                currentNode = currentNode.next;
                ++distance;
            } else {
                return -1;
            }
        }
        return distance;
    }

    public static Node getNthNodeBeforeTheNode(Node head, int n, Node referenceNode) {
        Node current = head;
        for (int idx = 0; idx < n; ++idx) {
            if (current != null) {
                current = current.next;
            } else {
                return null;
            }
        }
        Node nodeNBeforeCurrent = head;
        while (current != referenceNode) {
            if (current != null) {
                current = current.next;
                nodeNBeforeCurrent = nodeNBeforeCurrent.next;
            } else {
                return null;
            }
        }
        return nodeNBeforeCurrent;
    }

    public static Node getNthNodeFromEnd(Node head, int n) {
        return getNthNodeBeforeTheNode(head, n, null);
    }

    public static boolean isNodeInsideLoop(Node nodeToTest, Node nodeInALoop) {
        Node currentLoopNode = nodeInALoop;
        do {
            if (nodeToTest == currentLoopNode)
                return true;
            currentLoopNode = currentLoopNode.next;
        } while (currentLoopNode != nodeInALoop);
        return false;
    }

    public static Node getBeginningOfTheLoop(Node head, Node nodeInside) {
        Node currentNode = head;
        while (currentNode != nodeInside) {
            if (isNodeInsideLoop(currentNode, nodeInside)) {
                return currentNode;
            }
            currentNode = currentNode.next;
        }
        if (isNodeInsideLoop(currentNode, nodeInside))
            return currentNode;
        return null;
    }

    public static Node getBeginningOfTheLoop(Node head) {
        Node insideLoopNode = getNodeInLoop(head);
        return getBeginningOfTheLoop(head, insideLoopNode);
    }

    public static Node breakTheLoop(Node head, Node insideLoopNode) {
        Node beginningOfTheLoop = getBeginningOfTheLoop(head, insideLoopNode);
        Node currentNode = beginningOfTheLoop;
        while (currentNode.next != beginningOfTheLoop) {
            currentNode = currentNode.next;
        }
        currentNode.next = null;
        return beginningOfTheLoop;
    }

    public static Node insertIntoSortedListAsc(Node head, int newNodeValue) {
        Node newNode = new Node(newNodeValue);
        if (head == null) {
            return newNode;
        }
        Node currentNode = head;
        if (head.value > newNodeValue) {
            newNode.next = head;
            return newNode;
        } else {
            while (currentNode.next != null && currentNode.next.value < newNodeValue) {
                currentNode = currentNode.next;
            }
            newNode.next = currentNode.next;
            currentNode.next = newNode;
            return head;
        }
    }

    public static void deleteNode(Node head, Node nodeToDelete) {
        if (nodeToDelete == null)
            return;
        if (nodeToDelete == head) {
            if (head.next == null) {
                head.value = -1;
            } else {
                head.value = head.next.value;
                head.next = head.next.next;
            }
        } else {
            Node current = head;
            while (current != null && current.next != nodeToDelete) {
                current = current.next;
            }
            if (current == null)
                return;
            current.next = current.next.next;
        }
    }

    public static int getListLen(Node listHead) {
        int listLen = 0;
        Node cnode = listHead;
        while (cnode != null) {
            listLen++;
            cnode = cnode.next;
        }
        return listLen;
    }

    public static Node sumLists(Node list1, Node list2) {
        int list1Len = getListLen(list1);
        int list2Len = getListLen(list2);
        Node cList1, cList2;
        int lenDiff;
        if (list2Len > list1Len) {
            cList1 = list2;
            cList2 = list1;
            lenDiff = list2Len - list1Len;
        } else {
            cList1 = list1;
            cList2 = list2;
            lenDiff = list1Len - list2Len;
        }
        for(int idx = 0; idx < lenDiff; ++idx) {
            Node list2Node = new Node(0);
            list2Node.next = cList2;
            cList2 = list2Node;
        }
        Node sumList = sumEqualLenLists(cList1, cList2);
        if (sumList.value > 9){
            Node carryNode = new Node(1);
            carryNode.next = sumList;
            sumList = carryNode;
        }
        return sumList;
    }

    private static Node sumEqualLenLists(Node list1, Node list2){
        if (list1.next == null) {
            return new Node(list1.value + list2.value);
        }
        Node n = sumEqualLenLists(list1.next, list2.next);
        int nodeVal = 0;
        if (n.value > 9) {
            n.value = n.value - 10;
            nodeVal = 1;
        }
        nodeVal += list1.value + list2.value;
        Node res = new Node(nodeVal);
        res.next = n;
        return res;
    }

}
