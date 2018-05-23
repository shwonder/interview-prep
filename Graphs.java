import java.util.*;
import java.util.LinkedList;

/**
 * Created by Mike on 10/12/2017.
 */
public class Graphs {
    static public class Search {
        static Map<Integer, Node> idToNode = new HashMap<Integer, Node>();
        static class Node {
            int id;
            List<Node> adjacent = new ArrayList<Node>();
            public Node(int id) {
                this.id = id;
            }
        }

        public Search(int nodesCnt) {
            idToNode.clear();
            for(int idx = 1; idx <= nodesCnt; ++idx) {
                Node n = new Node(idx);
                idToNode.put(idx, n);
            }
        }

        public void addConnection(int node1, int node2) {
            Node n1 = idToNode.get(node1);
            Node n2 = idToNode.get(node2);
            n1.adjacent.add(n2);
            n2.adjacent.add(n1);
        }

        static public boolean connectedDfs(int start, int end) {
            HashSet<Node> visited = new HashSet<Node>();
            return connectedDfs(idToNode.get(start), idToNode.get(end), visited);
        }

        static private boolean connectedDfs(Node start, Node end, HashSet<Node> visited) {
            if (start == end) {
                return true;
            }
            if (visited.contains(start)) {
                return false;
            }
            visited.add(start);
            for(Node child : start.adjacent) {
                if (connectedDfs(child, end, visited)) {
                    return true;
                }
            }
            return false;
        }

        static public boolean connectedBfs(int start, int end) {
            HashSet<Node> visited = new HashSet<Node>();
            return connectedBfs(idToNode.get(start), idToNode.get(end), visited);
        }

        static public boolean connectedBfs(Node start, Node end, HashSet<Node> visited) {
            if (start == end) {
                return true;
            }
            if (visited.contains(start)) {
                return false;
            }
            visited.add(start);
            LinkedList<Node> nextToVisit = new LinkedList<Node>(start.adjacent);
            while(!nextToVisit.isEmpty()) {
                Node next = nextToVisit.removeFirst();
                if (next == end) {
                    return true;
                } else if (!visited.contains(next)) {
                    nextToVisit.addAll(nextToVisit.size(), next.adjacent);
                }
                visited.add(next);
            }
            return false;
        }

        static private void testCase(int node1, int node2) {
            System.out.println("DFS. Node " + node1 + " connected to node " + node2 + ": " + connectedDfs(node1, node2));
            System.out.println("BFS. Node " + node1 + " connected to node " + node2 + ": " + connectedBfs(node1, node2));
            System.out.println();
        }

        static public void run() {
            Search graph = new Search(5);
            graph.addConnection(1, 2);
            graph.addConnection(1, 3);
            graph.addConnection(1, 5);
            testCase(1, 2);
            testCase(2, 3);
            testCase(1, 3);
            testCase(1, 4);
            testCase(3, 4);
        }
    }

    // From the HackerRank, https://www.hackerrank.com/challenges/ctci-bfs-shortest-reach
    public static class Graph {
        boolean matrix[][];
        int vSize;

        public Graph(int size) {
            this.vSize = size;
            this.matrix = new boolean[size][size];
        }

        public void addEdge(int first, int second) {
            if (first != second) {
                this.matrix[first][second] = true;
                this.matrix[second][first] = true;
            }
        }

        public int[] shortestReach(int startId) { // 0 indexed, startId is also index in array, i.e. less than vertex number by 1
            class VisitNode {
                int idx;
                int distance;
                VisitNode(int idx, int distance) {
                    this.idx = idx;
                    this.distance = distance;
                }
            }

            int[] res = new int[this.vSize];
            for (int idx = 0; idx < res.length; ++idx) {
                res[idx] = -1;
            }
            LinkedList<VisitNode> nextToVisit = new LinkedList<VisitNode>();
            nextToVisit.add(new VisitNode(startId, 0));
            while(!nextToVisit.isEmpty()) {
                VisitNode node = nextToVisit.removeFirst();
                // XXX. Important! We can have configuration when we can reach a node from two or more different
                // neighbours. In that case if that node is already in a queue and another node can add it to the queue
                // before it is processed (e.g. 5 nodes in circle, see test g4 below) then we can override already
                // calculated value by bigger one. So we need to check if we already visited it here, i.e. if it is == -1
                if (res[node.idx] == -1 || res[node.idx] > node.distance) {
                    res[node.idx] = node.distance;
                }
                for(int idx = 0; idx < vSize; ++idx) {
                    // XXX. Important! need to check if node was already visited, i.e. check that it is still -1,
                    // otherwise ignore, this can be a back edge reference, edges are undirected
                    if (this.matrix[node.idx][idx] && res[idx] == -1) {
                        nextToVisit.addLast(new VisitNode(idx, node.distance + 6));
                    }
                }
            }
            return res;
        }

        static public void printDistances(int[] distances, int startId) {
            for (int i = 0; i < distances.length; i++) {
                if (i != startId) {
                    System.out.print(distances[i]);
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

        static public void run() {
            Graph g = new Graph(4);
            g.addEdge(1 - 1, 2 - 1);
            g.addEdge(1 - 1, 3 - 1);
            int [] dist = g.shortestReach(1 - 1);
            Graph.printDistances(dist, 1-1);

            Graph g2 = new Graph(3);
            g2.addEdge(2 - 1, 3 - 1);
            int[] dist2 = g2.shortestReach(2 - 1);
            Graph.printDistances(dist2, 2 - 1);

            Graph g3 = new Graph(5);
            int[] dist3 = g3.shortestReach(1 - 1);
            Graph.printDistances(dist3, 1-1);

            Graph g4 = new Graph(5);
            g4.addEdge(0, 1);
            g4.addEdge(1, 2);
            g4.addEdge(2, 3);
            g4.addEdge(3, 4);
            g4.addEdge(0, 4);
            int[] dist4 = g4.shortestReach(3);
            Graph.printDistances(dist4, 3);
        }
    }

    // From HackerRank, https://www.hackerrank.com/challenges/ctci-connected-cell-in-a-grid
    public static class ConnectedRegion {
        private boolean[][] matrix;
        private int sizeX, sizeY;
        static class Coord {
            public int x;
            public int y;
            public Coord(int x, int y) {
                this.x = x;
                this.y = y;
            }
        }

        public ConnectedRegion(boolean[][] matrix) {
            this.matrix = matrix;
            this.sizeX = matrix.length;
            this.sizeY = matrix[0].length;
        }

        private List<Coord> getAdjacentPoints(Coord pt) {
            List<Coord> result = new ArrayList<Coord>(8);
            int startX = Math.max(pt.x - 1, 0);
            int endX = Math.min(pt.x + 1, sizeX - 1); // index is zero based
            int startY = Math.max(pt.y - 1, 0);
            int endY = Math.min(pt.y + 1, sizeY - 1);
            for(int idxX = startX; idxX <= endX; ++idxX) {
                for (int idxY = startY; idxY <= endY; ++idxY) {
                    if (idxX != pt.x || idxY != pt.y) {
                        result.add(new Coord(idxX, idxY));
                    }
                }
            }
            return result;
        }

        private int getRegionSize(Coord pt, int currSize) {
            if (!matrix[pt.x][pt.y]) {
                return 0;
            }
            int size = currSize + 1;
            matrix[pt.x][pt.y] = false;
            for (Coord adjacent : getAdjacentPoints(pt)) {
                size += getRegionSize(adjacent, 0);
            }
            return size;
        }

        private int getMaxRegionSize() {
            int maxSize = 0;
            for (int ix = 0; ix < this.sizeX; ++ix) {
                for (int iy = 0; iy < sizeY; ++iy) {
                    if (matrix[ix][iy]) {
                        int regSize = getRegionSize(new Coord(ix, iy), 0);
                        if (regSize > maxSize) {
                            maxSize = regSize;
                        }
                    }
                }
            }
            return maxSize;
        }

        static private void runCase(boolean[][] matrix) {
            ConnectedRegion reg = new ConnectedRegion(matrix);
            System.out.println(reg.getMaxRegionSize());
        }

        static public void run() {
            boolean [][] matrix = new boolean[][] {
                    {false, true},
                    {false, true}
            };
            runCase(matrix);
            matrix = new boolean[][] {
                    {true, true, false, false},
                    {false, true, true, false},
                    {false, false, true, false},
                    {true, false, false, false}
            };
            runCase(matrix);

            matrix = new boolean[][] {
                    {true,  false, false, false},
                    {false, false, true, false},
                    {false, false, true, false},
                    {true,  true,  true, false}
            };
            runCase(matrix);

            matrix = new boolean[][] {
                    {true,  false, false, true },
                    {false, false, false, true},
                    {false, true,  true,  true},
                    {false, false, false, false}
            };
            runCase(matrix);

            matrix = new boolean[][] {
                    {true},
            };
            runCase(matrix);

            matrix = new boolean[][] {
                    {false},
            };
            runCase(matrix);

            matrix = new boolean[][] {
                    {true, true, false, false},
                    {false, false, true, false},
            };
            runCase(matrix);

            matrix = new boolean[][] {
                    {true,  true},
                    {false, true},
                    {false, false},
                    {true,  false}
            };
            runCase(matrix);

        }

    }

    static class Test {
        public static void run() {
            // Search.run();
            // Graph.run();
            ConnectedRegion.run();
        }
    }


}
