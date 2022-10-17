package uc.eecs.core.query;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;

public class GraphParser {
  DirectedGraph<String, DefaultEdge> graph;

  public GraphParser(DirectedGraph<String, DefaultEdge> graph) {
    this.graph = graph;
  }

  public String getQuery() {
    StringBuffer query = new StringBuffer();
    for (String v : graph.vertexSet()) {
      query.append(v).append(" ");
    }
    return query.toString();
  }

//  public String getQuery(int threshold) {
//    if (graph.vertexSet().size() > threshold){
//
//    }
//  }
}
