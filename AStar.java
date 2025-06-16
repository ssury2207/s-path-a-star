import java.util.*;

public class AStar {

    public static class Pair {
        int a;
        int b;

        public Pair(int a, int b) {
            this.a = a;
            this.b = b;
        }

        public int getA() {
            return a;
        }

        public int getB() {
            return b;
        }
    }

    public static void printGraph(List<Pair>[] graph, int n) {
        System.out.println("----------------------------");
        for (int i = 1; i <= n; i++) {
            List<Pair> tlist = graph[i];
            for (Pair p : tlist) {
                int u = i;
                int v = p.getA();
                int cost = p.getB();
                // print each edge with its cost
                System.out.println("Node :-> [" + u + "] --> [" + v + "] Cost " + cost);
            }
        }
        System.out.println("----------------------------");
    }

    public static void printHeuristic(int heuristic[], int n) {
        System.out.println("----------------------------");
        System.out.println("Heuristic values:");
        for (int i = 1; i <= n; i++) {
            System.out.println("Node " + i + " → " + (heuristic[i] == Integer.MAX_VALUE ? "Cannot reach " : heuristic[i]));
        }
        System.out.println("----------------------------");
    }

    public static void buildHueristic(List<Pair>[] graph, int n, int source, int target, int heuristic[]) {
        @SuppressWarnings("unchecked")
        List<Pair>[] revGraph = new ArrayList[n + 1];
        int m = graph.length;

        // reverse the graph: edges from target back to other nodes
        for (int i = 0; i <= n; i++) {
            revGraph[i] = new ArrayList<>();
        }

        for (int i = 0; i <= n; i++) {
            List<Pair> tlist = graph[i];
            for (Pair p : tlist) {
                int u = i;
                int v = p.getA();
                int cost = p.getB();
                // reverse direction but keep the same cost
                revGraph[v].add(new Pair(u, cost));
            }
        }

        // Dijkstra to compute shortest distances from target to every other node
        int distance[] = new int[n + 1];
        Arrays.fill(distance, Integer.MAX_VALUE);

        // priority queue holds pairs of (distance, node)
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.getA(), b.getA()));

        distance[target] = 0; // distance to itself is 0
        pq.add(new Pair(0, target));

        while (!pq.isEmpty()) {
            Pair p = pq.poll();
            int curr_cost = p.getA();
            int curr_node = p.getB();

            for (Pair curr_pair : revGraph[curr_node]) {
                int neighbour_node = curr_pair.getA();
                int neighbour_cost = curr_pair.getB();

                if (curr_cost + neighbour_cost < distance[neighbour_node]) {
                    distance[neighbour_node] = curr_cost + neighbour_cost;
                    pq.add(new Pair(distance[neighbour_node], neighbour_node));
                }
            }
        }

        for (int i = 1; i <= n; i++) {
            heuristic[i] = distance[i]; // heuristic is shortest distance from node to target
        }

        // printHeuristic(heuristic, n);

        // run A* using the computed heuristic
        aStarSearch(graph, heuristic, n, source, target);
    }

    public static void aStarSearch(List<Pair>[] graph, int heuristic[], int n, int source, int target) {
        int g[] = new int[n + 1]; // stores actual cost from source to each node
        Arrays.fill(g, Integer.MAX_VALUE);

        // priority queue stores (f-score, node)
        PriorityQueue<Pair> pq = new PriorityQueue<>((a, b) -> Integer.compare(a.getA(), b.getA()));

        g[source] = 0; // cost from source to itself is 0
        int f_score = g[source] + heuristic[source]; // total estimated cost = g + h
        pq.add(new Pair(f_score, source));

        boolean notFound = true;

        while (!pq.isEmpty()) {
            Pair p = pq.poll();
            int curr_f_score = p.getA();
            int curr_node = p.getB();

            if (curr_node == target) {
                System.out.println("Target is Found"+" from ["+source+"] to ["+target+"] Total Cost is "+g[target]);
                notFound = false;
                return;
            }

            for (Pair curr_pair : graph[curr_node]) {
                int neighbour_node = curr_pair.getA();
                int neighbour_cost = curr_pair.getB();

                // check if going through current node gives a better g-score
                if (g[curr_node] + neighbour_cost < g[neighbour_node]) {
                    g[neighbour_node] = g[curr_node] + neighbour_cost;
                    int temp_f_score = g[neighbour_node] + heuristic[neighbour_node]; // update f(n)
                    pq.add(new Pair(temp_f_score, neighbour_node));
                }
            }
        }

        if (notFound) {
           System.out.printf("No path is found from %d to %d \n", source, target);
        }
    }

    public static void main(String[] args) {

        int source = 2;
        int target = 4;

        // directional graph configuration
        // node u → node v with edge cost c
       int config[][] = {
            {1, 2, 2},
            {1, 3, 4},
            {2, 3, 1},
            {2, 4, 7},
            {3, 4, 3},
            {3, 5, 5},
            {4, 5, 1},
            {1, 5, 10},
            {2, 5, 8},
            {5, 4, 2} // creates a loop-like back edge (optional for testing)
        };

        int n = 5; // number of nodes
        int m = config.length;

        @SuppressWarnings("unchecked")
        List<Pair>[] graph = new ArrayList[n + 1];
        for (int i = 0; i <= n; i++) {
            graph[i] = new ArrayList<>();
        }

        // build graph from config array
        for (int i = 0; i < m; i++) {
            int u = config[i][0];
            int v = config[i][1];
            int cost = config[i][2];
            graph[u].add(new Pair(v, cost));
        }
        printGraph(graph, n);
        int heuristic[] = new int[n + 1];

        // build admissible heuristic by reverse Dijkstra from the target
        for(int i=1; i<=n; i++){
            buildHueristic(graph, n, source, i, heuristic);
            
        }
    }
}
