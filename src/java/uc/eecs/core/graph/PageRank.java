package uc.eecs.core.graph;

import org.jgraph.graph.DefaultEdge;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultUndirectedGraph;

import java.util.HashMap;
import java.util.HashSet;

public class PageRank {
    public DefaultDirectedGraph<String, DefaultEdge> graph;
    public DefaultUndirectedGraph<String, DefaultEdge> ungraph;

    public PageRank(DefaultDirectedGraph<String, DefaultEdge> graph) {
        this.graph = graph;
    }
    public PageRank(DefaultUndirectedGraph<String, DefaultEdge> ungraph) {
        this.ungraph = ungraph;
    }

    public HashMap<String, Double> getPageRanks() {
        if (this.graph == null) {
            return null;
        } else {
            HashSet<String> vertices = new HashSet<>(this.graph.vertexSet());
            HashMap<String, Double> tokendb = new HashMap<>();

            for (String token : vertices) {
                tokendb.put(token, 0.0);
            }

            PageRankPro prProvider = new PageRankPro(this.graph, tokendb);
            return prProvider.calculatePageRank();
        }
    }

    public static void main(String[] args) {
        DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
        graph.addVertex("1");
        graph.addVertex("2");
        graph.addVertex("3");
        graph.addVertex("4");
        graph.addVertex("5");
        graph.addVertex("6");
        graph.addVertex("7");
        graph.addVertex("8");
        graph.addVertex("9");
        graph.addVertex("10");
        graph.addVertex("11");
        graph.addVertex("12");
        graph.addEdge("1", "2");
        graph.addEdge("1", "3");
        graph.addEdge("2", "4");
        graph.addEdge("2", "5");
        graph.addEdge("2", "6");
        graph.addEdge("6", "10");
        graph.addEdge("6", "12");
        graph.addEdge("6", "11");
        graph.addEdge("3", "7");
        graph.addEdge("3", "8");
        graph.addEdge("3", "9");
        graph.addEdge("4", "10");
        graph.addEdge("10", "4");
        graph.addEdge("4", "11");
        graph.addEdge("11", "4");
        graph.addEdge("11", "10");
        graph.addEdge("10", "11");
        HashMap<String, Double> tokendb = new PageRank(graph).getPageRanks();
        System.out.println(tokendb);
    }
}
