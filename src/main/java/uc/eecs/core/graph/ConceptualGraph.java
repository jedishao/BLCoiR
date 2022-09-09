package uc.eecs.core.graph;

import core.pagerank.PageRankProvider;
import core.pagerank.PageRankProviderMgr;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import utils.ItemSorter;
import utils.MiscUtility;
import utils.ReadFile;
import utils.config.DatasetConfig;

import java.io.IOException;
import java.util.*;

public class ConceptualGraph {
  public DirectedGraph<String, DefaultEdge> buildGraph(ArrayList<ArrayList<String>> list_2) {
    DirectedGraph<String, DefaultEdge> conGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    HashMap<String, ArrayList<String>> g = new HashMap<>();
    ArrayList<String> top = list_2.get(0);
    list_2.remove(0);
    for (ArrayList<String> list_3 : list_2) {
      g.put(list_3.get(0), list_3);
    }
    //    String[] con = {
    //      "synchronized", "lock", "block",
    //    };

    for (String k : g.keySet()) {
      if (!conGraph.containsVertex(k)) {
        conGraph.addVertex(k);
        ArrayList<String> tmp = g.get(k);
        for (String w : tmp) {
          if (!conGraph.containsVertex(w)) {
            conGraph.addVertex(w);
            if (!conGraph.containsEdge(k, w)) {
              conGraph.addEdge(k, w);
            }
          }
        }
      }
    }
    // concurrent
    //    for (String k1 : con) {
    //      for (String k2 : con) {
    //        if (!conGraph.containsEdge(k1, k2)) {
    //          conGraph.addEdge(k1, k2);
    //        }
    //      }
    //    }
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

  protected void showGraphEdges(DirectedGraph<String, DefaultEdge> classGraph) {
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
    for (int i = 0; i < sorted.size(); i++) {
      Map.Entry<String, Double> entry = sorted.get(i);
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
    ConceptualGraph cg = new ConceptualGraph();
    ReadFile rf = new ReadFile();
    ArrayList<ArrayList<ArrayList<String>>> list_1 = rf.readFormFile();
    for (ArrayList<ArrayList<String>> list_2 : list_1) {
      PageRankProviderMgr prp = new PageRankProviderMgr(cg.buildGraph(list_2));
      HashMap<String, Double> res = prp.getPageRanks();
      ArrayList<String> kw = cg.getTopKItems(res);
      for (String k : kw) {
        System.out.print(k + "\t");
      }
      System.out.println();
    }
  }
}
