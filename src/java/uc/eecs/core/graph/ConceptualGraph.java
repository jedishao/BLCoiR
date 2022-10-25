package uc.eecs.core.graph;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import utils.ItemSorter;
import utils.MiscUtility;
import utils.config.DatasetConfig;

import java.io.IOException;
import java.util.*;

public class ConceptualGraph {
  Map<String, List<List<String>>> itemsMap;
  ArrayList<String> conItems;

  public ConceptualGraph(Map<String, List<List<String>>> itemsMap, ArrayList<String> conItems) {
    this.itemsMap = itemsMap;
    this.conItems = conItems;
  }

  public DefaultDirectedGraph<String, DefaultEdge> buildGraph() {
    DefaultDirectedGraph<String, DefaultEdge> conGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    for (String relation : itemsMap.keySet()) {
      if (!conGraph.containsVertex(relation)) {
        conGraph.addVertex(relation);
        List<String> parent = itemsMap.get(relation).get(0);
        for (String s1 : parent) {
          if (!conGraph.containsVertex(s1)) {
            conGraph.addVertex(s1);
            if (!conGraph.containsEdge(s1, relation)) {
              conGraph.addEdge(s1, relation);
            }
          }
        }
        List<String> child = itemsMap.get(relation).get(1);
        for (String s2 : child) {
          if (!conGraph.containsVertex(s2)) {
            conGraph.addVertex(s2);
            if (!conGraph.containsEdge(relation, s2)) {
              conGraph.addEdge(relation, s2);
            }
          }
        }
      }
    }

    for (String s3 : conItems) {
      if (!conGraph.containsVertex(s3)) {
        conGraph.addVertex(s3);
      }
    }
    // concurrent
    for (String s4 : conItems) {
      for (String s5 : conItems) {
        if (!conGraph.containsEdge(s4, s5)) {
          conGraph.addEdge(s4, s5);
        }
      }
    }
    // top
    //    for (String t : top) {
    //      if (!conGraph.containsVertex(t)) {
    //        conGraph.addVertex(t);
    //        for (String kk : g.keySet()) {
    //          if (!conGraph.containsEdge(t, kk)) {
    //            conGraph.addEdge(t, kk);
    //          }
    //        }
    //      }
    //    }
    return conGraph;
  }

  public DefaultDirectedGraph<String, DefaultEdge> buildGraph_test(ArrayList<ArrayList<String>> list_2) {
    DefaultDirectedGraph<String, DefaultEdge> conGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    HashMap<String, ArrayList<String>> g = new HashMap<>();
    ArrayList<String> con = list_2.get(list_2.size() - 1);
    ArrayList<String> top = new ArrayList<>();
    list_2.remove(list_2.size() - 1);
    for (ArrayList<String> list_3 : list_2) {
      g.put(list_3.get(0), list_3);
    }

    for (String k : g.keySet()) {
      top.add(k);
      if (!conGraph.containsVertex(k)) {
        conGraph.addVertex(k);
        ArrayList<String> tmp = g.get(k);
        for (String w : tmp) {
          if (!conGraph.containsVertex(w)) {
            conGraph.addVertex(w);
          }
          if (!conGraph.containsEdge(k, w)) {
            conGraph.addEdge(k, w);
          }
        }
      }
    }
    // concurrent
    for (String k1 : con) {
      if (!conGraph.containsVertex(k1)) conGraph.addVertex(k1);
      for (String k2 : con) {
        if (!conGraph.containsVertex(k2)) conGraph.addVertex(k2);
        if (!conGraph.containsEdge(k1, k2)) {
          conGraph.addEdge(k1, k2);
        }
      }
    }
    // top
    for (String t : top) {
      if (!conGraph.containsVertex(t)) {
        conGraph.addVertex(t);
        for (String kk : g.keySet()) {
          if (!conGraph.containsEdge(t, kk)) {
            conGraph.addEdge(t, kk);
          }
        }
      }
    }
    return conGraph;
  }

  protected void showGraphEdges(DefaultDirectedGraph<String, DefaultEdge> classGraph) {
    // showing the graph
    HashSet<DefaultEdge> edges = new HashSet<>(classGraph.edgeSet());
    HashSet<String> nodes = new HashSet<>();
    for (DefaultEdge edge : edges) {
      System.out.println(classGraph.getEdgeSource(edge) + "/" + classGraph.getEdgeTarget(edge));
      nodes.add(classGraph.getEdgeSource(edge));
      nodes.add(classGraph.getEdgeTarget(edge));
    }
    MiscUtility.showList(nodes);
  }

  protected ArrayList<String> getTopKItems(HashMap<String, Double> tokendb) {
    List<Map.Entry<String, Double>> sorted = ItemSorter.sortHashMapDouble(tokendb);
    ArrayList<String> selected = new ArrayList<>();
    for (Map.Entry<String, Double> entry : sorted) {
      ArrayList<String> keyTokens = MiscUtility.str2List(entry.getKey());
      for (String keyToken : keyTokens) {
        if (keyToken.length() >= DatasetConfig.TOKEN_LEN_THRESHOLD) {
          if (!selected.contains(keyToken)) {
            selected.add(keyToken);
          }
        }
      }
      // selected.add(entry.getKey());
      if (selected.size() == DatasetConfig.MAX_ST_SUGGESTED_QUERY_LEN) break;
    }
    return selected;
  }

  public static void main(String[] args) throws IOException {
    //
    //    ConceptualGraph cg = new ConceptualGraph();
    //    ReadFile rf = new ReadFile();
    //    ArrayList<ArrayList<ArrayList<String>>> list_1 =
    //        rf.readFormFile("src/main/resources/concurrency/tomcat70_1.txt");
    //    for (ArrayList<ArrayList<String>> list_2 : list_1) {
    //      PageRankProviderMgr prp = new PageRankProviderMgr(cg.buildGraph_test(list_2));
    //      HashMap<String, Double> res = prp.getPageRanks();
    //      ArrayList<String> kw = cg.getTopKItems(res);
    //      for (String k : kw) {
    //        System.out.print(k + "\t");
    //      }
    //      System.out.println();
    //    }
  }
}
