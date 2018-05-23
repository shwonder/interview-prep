import java.util.Stack;
/**
 * Stack or/and queue exercises
 */
public class StackQueue {
    // From HackerRank
    static class UnmatchedBrackets{

        static boolean isClosing(Character br) {
            return isClosing(br.charValue());
        }
        static boolean isClosing(char br) {
            return br == ']'
                    || br == ')'
                    || br == '}';
        }
        static boolean isMatching(char closing, char br) {
            return (closing == ']' && br == '[')
                    || (closing == ')' && br == '(')
                    || (closing == '}' && br == '{');
        }
        static boolean check(String str) {
            Stack<Character> brackets = new java.util.Stack<Character>();
            for(int idx = 0; idx < str.length(); ++idx) {
                if (isClosing(str.charAt(idx))) {
                    if (!brackets.empty() && isMatching(str.charAt(idx), brackets.peek())) {
                        brackets.pop();
                    } else {
                        return false;
                    }
                } else {
                    brackets.push(str.charAt(idx));
                }
            }
            return brackets.empty();
        }

        public static void run() {
            String[] arr = new String[3];
            arr[0] = "{[()]}";
            arr[1] = "{[(])}";
            arr[2] = "{{[[(())]]}}";

            for (int idx = 0; idx < arr.length; ++idx) {
                System.out.println("Brackets: '" + arr[idx] + "' balanced: " + check(arr[idx]));
            }
        }
    }

    //From HackerRank, https://www.hackerrank.com/challenges/ctci-queue-using-two-stacks
    static class QueueBy2Stacks {
        public static class MyQueue<T> {
            Stack<T> stackNewestOnTop = new Stack<T>();
            Stack<T> stackOldestOnTop = new Stack<T>();
            int invalidNewestOnTop = 0;

            public void enqueue(T value) { // Push onto newest stack
//                if (stackNewestOnTop.empty()) {
//                    while(!stackOldestOnTop.empty()) {
//                        stackNewestOnTop.push(stackOldestOnTop.pop());
//                    }
//                } else {
//                    stackOldestOnTop.clear();
//                }
                stackNewestOnTop.push(value);
            }

            public T peek() {
                if (stackOldestOnTop.empty()) {
                    while(!stackNewestOnTop.empty()) {
                        stackOldestOnTop.push(stackNewestOnTop.pop());
                    }
                }
                return stackOldestOnTop.peek();
            }

            public T dequeue() {
                if (stackOldestOnTop.empty()) {
                    while(!stackNewestOnTop.empty()) {
                        stackOldestOnTop.push(stackNewestOnTop.pop());
                    }
                }
                return stackOldestOnTop.pop();
            }
        }
        public static enum OpType {
            PUSH,
            POP,
            PRINT
        }
        private MyQueue<Integer> myQueue = new MyQueue<Integer>();

        public void doOperation(OpType op) {
            doOperation(op, 0);
        }

        public void doOperation(OpType op, int arg) {
            switch(op) {
                case PUSH:
                    myQueue.enqueue(arg);
                    break;
                case POP:
                    myQueue.dequeue();
                    break;
                case PRINT:
                    System.out.println(myQueue.peek());
                    break;
            }
        }

        static public void run() {
            QueueBy2Stacks queue = new QueueBy2Stacks();
            queue.doOperation(OpType.PUSH, 42);
            queue.doOperation(OpType.POP);
            queue.doOperation(OpType.PUSH, 14);
            queue.doOperation(OpType.PRINT);
            queue.doOperation(OpType.PUSH, 28);
            queue.doOperation(OpType.PRINT);
            queue.doOperation(OpType.PUSH, 60);
            queue.doOperation(OpType.PUSH, 78);
            queue.doOperation(OpType.POP);
            queue.doOperation(OpType.POP);
        }
    }
    static class Test{
        static void run() {
            QueueBy2Stacks.run();
        }
    }
}
