/**
 * Created by Mike on 2/23/2017.
 */
public class DoubleLinkedList {
    static class NodeDL {
        public int value;
        public NodeDL next;
        public NodeDL prev;

        public NodeDL(int value) {
            this.value = value;
            this.next = null;
            this.prev = null;
        }
    }

    static class Test {
        private static void testInsert(int[] testSet) {
            NodeDL head = createDL(testSet);
        }

        public static void Run() {
            int [] items = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12};

        }
    }

    public static NodeDL createDL(int[] items) {
        if (items.length == 0)
            throw new IllegalArgumentException("At least one element is required");
        NodeDL head = new NodeDL(items[0]);
        NodeDL prevNode = head;
        for (int idx = 1; idx < items.length; ++idx) {
            prevNode.next = new NodeDL(items[idx]);
            prevNode.next.prev = prevNode;
            prevNode = prevNode.next;
        }
        return head;
    }

    public static void print(NodeDL head) {
        if (head != null) {
            System.out.print(head.value);
            head = head.next;
            while (head != null) {
                System.out.print(", " + head.value);
            }
        }
        System.out.println();
    }

    public static NodeDL insertIntoSortedListAsc(NodeDL head, int newNodeValue) {
        NodeDL newNode = new NodeDL(newNodeValue);
        if (head == null) {
            return newNode;
        }
        if (head.value > newNodeValue) {
            head.prev = newNode;
            newNode.next = head;
            return newNode;
        }
        NodeDL current = head;
        while (current.value <= newNodeValue && current.next != null) {
            current = current.next;
        }
        newNode.next = current;
        current.prev.next = newNode;
        return head;
    }

}
