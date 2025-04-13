import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.*;
import java.util.jar.Attributes;

class Coordinate {
    public int x;
    public int y;
};

class Edge {
    public int dest;
    public int type;
};

class StackItem {
    public int node;
    public int type;
};


class Pair {
    public int a;
    public int b;
};

public class wormhole {
    public static boolean walkGraph1(ArrayList<ArrayList<Edge>> edges) {
        for (int root = 0; root < edges.size(); root++) {
            Stack<StackItem> stack = new Stack<>();
            ArrayList<Boolean> visited = new ArrayList<>();
            for (int i=0; i < edges.size(); i++) {
                visited.add(false);
            }
            ArrayList<Boolean> seen = new ArrayList<>();
            for (int i=0; i < edges.size(); i++) {
                seen.add(false);
            }
            StackItem stackItem = new StackItem();
            stackItem.node = root;
            stackItem.type = 0;
            stack.push(stackItem);
            while (!stack.empty()) {
                StackItem next = stack.pop();
                for (int j = 0; j < edges.get(next.node).size(); j++) {
                    int child = edges.get(next.node).get(j).dest;
                    if ((next.type == 1) && (edges.get(next.node).get(j).type == 1)) {
                        continue;
                    }
                    if (child == root) {
                        // cycle in graph
                        return true;
                    }
                    if (visited.get(child)) {
                        continue;
                    }
                    if (seen.get(child)) {
                        // cycle in graph
                        continue;
                    }
                    StackItem stackItem1 = new StackItem();
                    stackItem1.node = child;
                    stackItem1.type = edges.get(next.node).get(j).type;
                    stack.push(stackItem1);
                    seen.set(stackItem1.node, true);
                }
                visited.set(next.node, true);
            }
        }
        return false;
    }

    public static boolean walkGraph(ArrayList<ArrayList<Edge>> edges) {
        for (int root = 0; root < edges.size(); root++) {
            Stack<StackItem> stack = new Stack<>();
            ArrayList<Boolean> visited = new ArrayList<>();
            StackItem stackItem = new StackItem();
            stackItem.node = root;
            stackItem.type = 0;
            stack.push(stackItem);
            while (!stack.empty()) {
                StackItem next = stack.pop();
                // if there is a wormhole and previous was not a wormhole jump, we have to
                // go to the wormhole
                if (next.type == 0) {
                    // look for a wormhole
                    for (int j = 0; j < edges.get(next.node).size(); j++) {
                        if (edges.get(next.node).get(j).type == 1) {
                            int child = edges.get(next.node).get(j).dest;
                            if (child == root) {
                                return true;
                            }
                            StackItem stackItem1 = new StackItem();
                            stackItem1.node = child;
                            stackItem1.type = edges.get(next.node).get(j).type;
                            stack.push(stackItem1);
                            break;
                        }
                    }
                } else {
                    // This was a wormhole jump, we have to see if there is
                    // another regular edge
                    for (int j = 0; j < edges.get(next.node).size(); j++) {
                        if (edges.get(next.node).get(j).type == 0) {
                            int child = edges.get(next.node).get(j).dest;
                            if (child == root) {
                                return true;
                            }
                            StackItem stackItem1 = new StackItem();
                            stackItem1.node = child;
                            stackItem1.type = edges.get(next.node).get(j).type;
                            stack.push(stackItem1);
                            break;
                        }
                    }
                }
            }
        }
        return false;
    }

    public static void printPairs(ArrayList<Pair> pairs) {
        for (int i=0; i < pairs.size(); i++){
            System.out.println(pairs.get(i).a + " " + pairs.get(i).b );
        }
        System.out.println("");
    }
    public static boolean analyze(ArrayList<ArrayList<Edge>> edges, ArrayList<Pair> pairs) {
        boolean result;
        // add the edges
        for (int i=0; i < pairs.size(); i++) {
            Edge aTob = new Edge();
            Edge bToa = new Edge();
            aTob.dest = pairs.get(i).b;
            aTob.type = 1;
            bToa.dest = pairs.get(i).a;
            bToa.type = 1;
            edges.get(pairs.get(i).a).add(aTob);
            edges.get(pairs.get(i).b).add(bToa);
        }
        result = walkGraph(edges);
        // remove the edges
        for (int i=0; i < pairs.size(); i++) {
            Edge aTob = new Edge();
            Edge bToa = new Edge();
            aTob.dest = pairs.get(i).b;
            aTob.type = 1;
            bToa.dest = pairs.get(i).a;
            bToa.type = 1;
            int size = edges.get(pairs.get(i).a).size();
            edges.get(pairs.get(i).a).remove( size - 1);
            size = edges.get(pairs.get(i).b).size();
            edges.get(pairs.get(i).b).remove(size -1);
        }
        return result;
    }

    public  static int formpairs(ArrayList<ArrayList<Edge>> edges, ArrayList<Pair> pairs, 
                                 ArrayList<Boolean> wormholesPaired, int startIndex ) {
        Pair newPair = new Pair();
        Boolean found = false;
        int result = 0;
        // Boolean found = false;
        for (int i=startIndex; i < edges.size(); i++) {
            if (wormholesPaired.get(i)) {
                continue;
            }
            newPair.a = i;
            wormholesPaired.set(i, true);
            for (int j = i+1; j < edges.size(); j++) {
                if (wormholesPaired.get(j)) {
                    continue;
                }
                found = true;
                newPair.b = j;
                wormholesPaired.set(j, true);
                pairs.add(newPair);
                result += formpairs(edges, pairs, wormholesPaired, i+1);
                pairs.remove(newPair);
                wormholesPaired.set(j, false);
            }
            wormholesPaired.set(i, false);            
        }
        if (!found) {
            if (pairs.size() == edges.size()/2) {
                if (analyze(edges, pairs)) {
                    // printPairs(pairs);
                    return 1;
                }
            }
        }
        return  result;
    }
    public static int DoAnalysis(ArrayList<ArrayList<Edge>> edges) {
        ArrayList<Boolean> wormholesPaired = new ArrayList<>();
        for (int i = 0; i < edges.size(); i++) {
            wormholesPaired.add(false);
        }
        ArrayList<Pair> pairs = new ArrayList<>();
        return formpairs(edges, pairs, wormholesPaired,0);
    }

    public static void main(String[] args) throws java.io.FileNotFoundException, java.io.IOException {

        // Use BufferedReader rather than RandomAccessFile; it's much faster
        BufferedReader f = new BufferedReader(new FileReader("wormhole.in"));
        // input file name goes above
        PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("wormhole.out")));

        // Use StringTokenizer vs. readLine/split -- lots faster
        StringTokenizer st = new StringTokenizer(f.readLine());
        String token = st.nextToken();
        int numHoles = Integer.parseInt(token);
        ArrayList<Coordinate> wormholes = new ArrayList<>();
        for (int i=0; i < numHoles; i++) {
            st = new StringTokenizer(f.readLine());
            Coordinate coordinate = new Coordinate();
            coordinate.x = Integer.parseInt(st.nextToken());
            coordinate.y = Integer.parseInt(st.nextToken());
            wormholes.add(coordinate);
        }
        ArrayList<ArrayList<Edge>> edges = new ArrayList<>();
        for (int i = 0; i < wormholes.size(); i++) {
            edges.add(new ArrayList<>());
        }
        for (int i = 0; i < wormholes.size(); i++) {
            int closest = Integer.MAX_VALUE;
            int closestNode = 0;
            // find the closest wormhole to the right
            for (int j=0; j < wormholes.size(); j++) {
                if (i==j) {
                    continue;
                }
                if (wormholes.get(i).y == wormholes.get(j).y) {
                    if (wormholes.get(i).x < wormholes.get(j).x) {
                        if ( wormholes.get(j).x < closest) {
                            closest = wormholes.get(j).x;
                            closestNode = j;
                        }
                    }
                }
            }
            // Add an edge from the smaller x to bigger x
            if (closest != Integer.MAX_VALUE) {
                // found a dest to the right
                Edge edge = new Edge();
                edge.dest = closestNode;
                edge.type = 0;
                edges.get(i).add(edge);
            }

        }
        int total = DoAnalysis(edges);
 	out.println(total);
	out.close();
    }
}
