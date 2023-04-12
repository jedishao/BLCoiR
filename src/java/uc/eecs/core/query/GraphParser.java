package uc.eecs.core.query;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import uc.eecs.core.graph.PageRank;
import uc.eecs.nlp.StopWords;import utils.ItemSorter;
import utils.MiscUtility;
import utils.config.DatasetConfig;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GraphParser {
  DefaultDirectedGraph<String, DefaultEdge> graph;

  public GraphParser(DefaultDirectedGraph<String, DefaultEdge> graph) {
    this.graph = graph;
  }

  public String getQuery() {
    String[] sym = {
      "(", ")", ":", "[", "]", "}", "{", "#", "-", "|", "%", "@", ".", "+", "=", "\\", "/", "#",
      "*", "?", "^", "$", "\"", "'", "<", ">"
    };
    StringBuilder query = new StringBuilder();
    HashMap<String, Double> itemMapC = getSalientClasses();
    //    System.out.println(itemMapC);
    List<String> tmp = new ArrayList<>();
    for (String v : getTopKItems(itemMapC)) {
//      System.out.println(v);
      Pattern num = Pattern.compile("(.*)\\d+(.*)");
      Matcher m = num.matcher(v);
      if (!m.matches()) {
        for (String s : sym) {
          v = v.replace(s, " ");
        }
        if (v.length() > 2) {
          if (!tmp.contains(v)){
            tmp.add(v);
          }
        }
      }
    }
    List<String> tmpQuery = new ArrayList<>();
//    System.out.println("=======================");
    for (String s : tmp) {
//      System.out.println(s);
      if (s.split(" ").length > 1) {
        for (String s1 : s.split(" "))
          if (!StopWords.STOP.contains(s1.trim().toLowerCase())){
            if (!tmpQuery.contains(s1)){
              tmpQuery.add(s1);
            }
          }

      } else {
        if (!StopWords.STOP.contains(s.trim().toLowerCase())){
          if (!tmpQuery.contains(s)){
            tmpQuery.add(s);
          }
        }
      }
    }
    for (String s2 : tmpQuery) {
      query.append(s2).append(" ");
    }
    return query.toString();
  }

  public String getQuery1(int size) {
    String[] sym = {
      "(", ")", ":", "[", "]", "}", "{", "#", "-", "|", "%", "@", ".", "+", "=", "\\", "/", "#",
      "*", "?", "^", "$", "\"", "'", "<", ">"
    };
    List<String> stop =
        Arrays.asList(
            "java", "apache", "org", "you", "i", "they", "set", "need", "should", "can", "not",
            "method", "problem", "master");
    Set<String> set = new HashSet<>();
    StringBuilder query = new StringBuilder();
    HashMap<String, Double> itemMapC = getSalientClasses();
    for (String v : getTopKItems(itemMapC, size)) {
      Pattern num = Pattern.compile("(.*)\\d+(.*)");
      Matcher m = num.matcher(v);
      if (!m.matches()) {
        for (String s : sym) {
          v = v.replace(s, " ");
        }
        if (v.length() > 2) set.add(v);
      }
    }

    for (String s : set) {
      if (!stop.contains(s.trim().toLowerCase())) query.append(s).append(" ");
    }
    //    for (String v : graph.vertexSet()) {
    //      Pattern num = Pattern.compile("(.*)\\d+(.*)");
    //      Matcher m = num.matcher(v);
    //      if (!m.matches()) {
    //        for (String s : sym) {
    //          v = v.replace(s, " ");
    //        }
    //        if (v.length() > 2) query.append(v).append(" ");
    //      }
    //      }
    return query.toString();
  }

  public HashMap<String, Double> getSalientClasses() {
    PageRank manager = new PageRank(graph);
    return manager.getPageRanks();
  }

  protected ArrayList<String> getTopKItems(HashMap<String, Double> tokendb) {
    List<Map.Entry<String, Double>> sorted = ItemSorter.sortHashMapDouble(tokendb);
    ArrayList<String> selected = new ArrayList<>();
    for (Map.Entry<String, Double> entry : sorted) {
      ArrayList<String> keyTokens = MiscUtility.str2List(entry.getKey());
      for (String keyToken : keyTokens) {
        //        if (keyToken.length() >= DatasetConfig.TOKEN_LEN_THRESHOLD) {
        if (!selected.contains(keyToken)) {
          selected.add(keyToken);
        }
        // }
      }
      // selected.add(entry.getKey());
      if (selected.size() == 40) break;
    }
    return selected;
  }

  protected ArrayList<String> getTopKItems(HashMap<String, Double> tokendb, int size) {
    List<Map.Entry<String, Double>> sorted = ItemSorter.sortHashMapDouble(tokendb);
    ArrayList<String> selected = new ArrayList<>();
    for (Map.Entry<String, Double> entry : sorted) {
      ArrayList<String> keyTokens = MiscUtility.str2List(entry.getKey());
      for (String keyToken : keyTokens) {
        //        if (keyToken.length() >= DatasetConfig.TOKEN_LEN_THRESHOLD) {
        if (!selected.contains(keyToken)) {
          selected.add(keyToken);
        }
        // }
      }
      // selected.add(entry.getKey());
      if (selected.size() == size) break;
    }
    return selected;
  }

  //  public String getQuery(int threshold) {
  //    if (graph.vertexSet().size() > threshold){
  //
  //    }
  //  }
  public static void main(String[] args) {
    String s = "sd sa";
    System.out.println(s.length());
  }
}
