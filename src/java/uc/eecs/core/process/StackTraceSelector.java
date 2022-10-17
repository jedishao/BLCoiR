package uc.eecs.core.process;

import core.pagerank.PageRankProviderMgr;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;
import utils.ItemSorter;
import utils.MiscUtility;
import utils.config.DatasetConfig;

import java.util.*;

public class StackTraceSelector {
  ArrayList<String> traces;

  public StackTraceSelector(ArrayList<String> traces) {
    this.traces = traces;
  }

  public ArrayList<String> getSalientItemsFromST() {
    HashMap<String, Double> itemMapC = getSalientClasses();
    return getTopKItems(itemMapC);
  }

  public HashMap<String, Double> getSalientClasses() {
    TraceElemExtractor teExtractor = new TraceElemExtractor(this.traces);
    teExtractor.decodeTraces(false);
    // adding extra step
    teExtractor.expandTraceNodes();
    DirectedGraph<String, DefaultEdge> classGraph = teExtractor.getClassGraph();
    //        //showGraphEdges(classGraph);;
    //
    PageRankProviderMgr manager = new PageRankProviderMgr(classGraph);
    return manager.getPageRanks();
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

  protected void showGraphEdges(DirectedGraph<String, DefaultEdge> classGraph) {
    // showing the graph
    HashSet<DefaultEdge> edges = new HashSet<DefaultEdge>(classGraph.edgeSet());
    HashSet<String> nodes = new HashSet<>();
    for (DefaultEdge edge : edges) {
      System.out.println(classGraph.getEdgeSource(edge) + "/" + classGraph.getEdgeTarget(edge));
      nodes.add(classGraph.getEdgeSource(edge));
      nodes.add(classGraph.getEdgeTarget(edge));
    }
    MiscUtility.showList(nodes);
  }
}
