package uc.eecs.core.graph;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class PageRankPro {
  public SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> wgraph;
  public DefaultDirectedGraph<String, DefaultEdge> graph;
  public DefaultUndirectedGraph<String, DefaultEdge> ungraph;
  HashMap<String, Double> tokendb;
  HashMap<String, Double> oldScoreMap;
  HashMap<String, Double> newScoreMap;
  final double EDGE_WEIGHT_TH = 0.25;
  final double INITIAL_VERTEX_SCORE = 0.25;
  final double DAMPING_FACTOR = 0.85;
  final int MAX_ITERATION = 100;

  //    public PageRankPro(SimpleDirectedWeightedGraph<String, DefaultWeightedEdge> wgraph,
  // HashMap<String, Double> tokendb) {
  //        this.wgraph = wgraph;
  //        this.tokendb = tokendb;
  //        this.oldScoreMap = new HashMap<>();
  //        this.newScoreMap = new HashMap<>();
  //    }

  public PageRankPro(
          DefaultDirectedGraph<String, DefaultEdge> graph, HashMap<String, Double> tokendb) {
    this.graph = graph;
    this.tokendb = tokendb;
    this.oldScoreMap = new HashMap<>();
    this.newScoreMap = new HashMap<>();
  }

//  public PageRankPro(
//      DefaultUndirectedGraph<String, DefaultEdge> graph, HashMap<String, Double> tokendb) {
//    this.ungraph = graph;
//    this.tokendb = tokendb;
//    this.oldScoreMap = new HashMap<>();
//    this.newScoreMap = new HashMap<>();
//  }

  boolean checkSignificantDiff(double oldV, double newV) {
    double diff = 0.0;
    if (newV > oldV) {
      diff = newV - oldV;
    } else {
      diff = oldV - newV;
    }

    return diff > 1.0E-4;
  }

  public HashMap<String, Double> calculatePageRankWeighted() {
    double d = 0.85;
    double N = (double) this.wgraph.vertexSet().size();
    Iterator var6 = this.wgraph.vertexSet().iterator();

    while (var6.hasNext()) {
      String vertex = (String) var6.next();
      this.oldScoreMap.put(vertex, 0.25);
      this.newScoreMap.put(vertex, 0.25);
    }

    boolean enoughIteration = false;
    int itercount = 0;

    while (!enoughIteration) {
      int insignificant = 0;
      Iterator var9 = this.wgraph.vertexSet().iterator();

      String vertex;
      while (var9.hasNext()) {
        vertex = (String) var9.next();
        Set<DefaultWeightedEdge> incomings = this.wgraph.incomingEdgesOf(vertex);
        double trank = 1.0 - d;
        double comingScore = 0.0;
        Iterator var16 = incomings.iterator();

        while (var16.hasNext()) {
          DefaultWeightedEdge edge = (DefaultWeightedEdge) var16.next();
          String source1 = (String) this.wgraph.getEdgeSource(edge);
          int outdegree = this.wgraph.outDegreeOf(source1);
          double score = (Double) this.oldScoreMap.get(source1);
          double edgeWeight = this.wgraph.getEdgeWeight(edge);
          score *= edgeWeight;
          if (outdegree == 0) {
            comingScore += score;
          } else {
            comingScore += score / (double) outdegree;
          }
        }

        comingScore *= d;
        trank += comingScore;
        boolean significant =
            this.checkSignificantDiff((Double) this.oldScoreMap.get(vertex), trank);
        if (significant) {
          this.newScoreMap.put(vertex, trank);
        } else {
          ++insignificant;
        }
      }

      var9 = this.newScoreMap.keySet().iterator();

      while (var9.hasNext()) {
        vertex = (String) var9.next();
        this.oldScoreMap.put(vertex, (Double) this.newScoreMap.get(vertex));
      }

      ++itercount;
      if (insignificant == this.wgraph.vertexSet().size()) {
        enoughIteration = true;
      }

      if (itercount == 100) {
        enoughIteration = true;
      }
    }

    System.out.println("Iter count:" + itercount);
    this.recordNormalizeScores();
    return this.tokendb;
  }

  public HashMap<String, Double> calculatePageRank() {
    double d = 0.85;
    double N = (double) this.graph.vertexSet().size();
    Iterator var6 = this.graph.vertexSet().iterator();

    while (var6.hasNext()) {
      String vertex = (String) var6.next();
      this.oldScoreMap.put(vertex, 0.25);
      this.newScoreMap.put(vertex, 0.25);
    }

    boolean enoughIteration = false;
    int itercount = 0;

    while (!enoughIteration) {
      int insignificant = 0;
      Iterator var9 = this.graph.vertexSet().iterator();

      String vertex;
      while (var9.hasNext()) {
        vertex = (String) var9.next();
        Set<DefaultEdge> incomings = this.graph.incomingEdgesOf(vertex);
        double trank = 1.0 - d;
        double comingScore = 0.0;
        Iterator var16 = incomings.iterator();

        while (var16.hasNext()) {
          DefaultEdge edge = (DefaultEdge) var16.next();
          String source1 = (String) this.graph.getEdgeSource(edge);
          int outdegree = this.graph.outDegreeOf(source1);
          double score = (Double) this.oldScoreMap.get(source1);
          if (outdegree == 1) {
            comingScore += score;
          } else if (outdegree > 1) {
            comingScore += score / (double) outdegree;
          }
        }

        comingScore *= d;
        trank += comingScore;
        boolean significant =
            this.checkSignificantDiff((Double) this.oldScoreMap.get(vertex), trank);
        if (significant) {
          this.newScoreMap.put(vertex, trank);
        } else {
          ++insignificant;
        }
      }

      var9 = this.newScoreMap.keySet().iterator();

      while (var9.hasNext()) {
        vertex = (String) var9.next();
        this.oldScoreMap.put(vertex, (Double) this.newScoreMap.get(vertex));
      }

      ++itercount;
      if (insignificant == this.graph.vertexSet().size()) {
        enoughIteration = true;
      }

      if (itercount == 100) {
        enoughIteration = true;
      }
    }

    this.recordNormalizeScores();
    return this.tokendb;
  }

  public HashMap<String, Double> calculatePage() {
    double d = 0.85;
    double N = (double) this.ungraph.vertexSet().size();
    Iterator var6 = this.ungraph.vertexSet().iterator();

    while (var6.hasNext()) {
      String vertex = (String) var6.next();
      this.oldScoreMap.put(vertex, 0.25);
      this.newScoreMap.put(vertex, 0.25);
    }

    boolean enoughIteration = false;
    int itercount = 0;

    while (!enoughIteration) {
      int insignificant = 0;
      Iterator var9 = this.ungraph.vertexSet().iterator();

      String vertex;
      while (var9.hasNext()) {
        vertex = (String) var9.next();
        Set<DefaultEdge> incomings = this.ungraph.edgesOf(vertex);
        double trank = 1.0 - d;
        double comingScore = 0.0;
        Iterator var16 = incomings.iterator();

        while (var16.hasNext()) {
          DefaultEdge edge = (DefaultEdge) var16.next();
          String source1 = (String) this.ungraph.getEdgeSource(edge);
          int outdegree = this.ungraph.degreeOf(source1);
          double score = (Double) this.oldScoreMap.get(source1);
          if (outdegree == 1) {
            comingScore += score;
          } else if (outdegree > 1) {
            comingScore += score / (double) outdegree;
          }
        }

        comingScore *= d;
        trank += comingScore;
        boolean significant =
            this.checkSignificantDiff((Double) this.oldScoreMap.get(vertex), trank);
        if (significant) {
          this.newScoreMap.put(vertex, trank);
        } else {
          ++insignificant;
        }
      }

      var9 = this.newScoreMap.keySet().iterator();

      while (var9.hasNext()) {
        vertex = (String) var9.next();
        this.oldScoreMap.put(vertex, (Double) this.newScoreMap.get(vertex));
      }

      ++itercount;
      if (insignificant == this.ungraph.vertexSet().size()) {
        enoughIteration = true;
      }

      if (itercount == 100) {
        enoughIteration = true;
      }
    }

    this.recordNormalizeScores();
    return this.tokendb;
  }

  protected void recordNormalizeScores() {
    double maxRank = 0.0;
    Iterator var4 = this.newScoreMap.keySet().iterator();

    String key;
    double score;
    while (var4.hasNext()) {
      key = (String) var4.next();
      score = (Double) this.newScoreMap.get(key);
      if (score > maxRank) {
        maxRank = score;
      }
    }

    var4 = this.newScoreMap.keySet().iterator();

    while (var4.hasNext()) {
      key = (String) var4.next();
      score = (Double) this.newScoreMap.get(key);
      score /= maxRank;
      this.tokendb.put(key, score);
    }
  }

  protected void showTokenRanks() {
    Iterator var2 = this.tokendb.keySet().iterator();

    while (var2.hasNext()) {
      String key = (String) var2.next();
      System.out.println(key + " " + this.tokendb.get(key));
    }
  }
}
