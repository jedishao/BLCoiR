package uc.eecs.core.query;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import uc.eecs.core.graph.PageRank;
import utils.ItemSorter;
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
    List<String> stop =
        Arrays.asList(
            "java",
            "apache",
            "org",
            "you",
            "i",
            "they",
            "set",
            "need",
            "should",
            "can",
            "not",
            "method",
            "problem",
            "master",
            "application",
            "difficult",
            "see",
            "version",
            "app",
            "tid",
            "nid",
            "second",
            "workspace",
            "dump",
            "attach",
            "like",
            "quick",
            "jdwp",
            "implementation",
            "jdi",
            "jmdns",
            "feel",
            "shutdown",
            "object",
            "make",
            "seem",
            "guess",
            "find",
            "exception",
            "bug",
            "string",
            "but",
            "return",
            "notice",
            "scheme",
            "branch",
            "cause",
            "period",
            "use",
            "time",
            "become",
            "own",
            "wait",
            "api",
            "code",
            "have",
            "thread", "important", "eclipse", "detail",
            "improve");
    Set<String> set = new HashSet<>();
    StringBuilder query = new StringBuilder();
    HashMap<String, Double> itemMapC = getSalientClasses();
    //    System.out.println(itemMapC);
    for (String v : getTopKItems(itemMapC)) {
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
      if (selected.size() == 30) break;
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
