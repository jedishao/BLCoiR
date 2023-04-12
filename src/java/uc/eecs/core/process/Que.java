package uc.eecs.core.process;

import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import utils.config.BugReportsID;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgraph.graph.DefaultEdge;
import uc.eecs.br.loader.BRLoader;
import uc.eecs.core.query.GraphParser;
import uc.eecs.nlp.DepManager;
import uc.eecs.nlp.TextNormalizer;
import utils.config.DatasetConfig;
import utils.config.QueryConfig;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Que {
  public List<String> getItems(List<String> list, DepManager depManager) {
    TextNormalizer tn = new TextNormalizer(list);
    ArrayList<SemanticGraph> dependencies = depManager.getDependencies(tn.removeHttp());
    return getItemsMap1(dependencies);
  }

  public void shown(ArrayList<SemanticGraph> dependences) {
    for (SemanticGraph semanticGraph : dependences) {
      System.out.println(semanticGraph.getAllNodesByPartOfSpeechPattern("NN"));
      System.out.println(semanticGraph.toRecoveredSentenceString());
      for (IndexedWord i : semanticGraph.getRoots()) {
        System.out.println(i.lemma());
        System.out.println(semanticGraph.getChildList(i));
        for (IndexedWord j : semanticGraph.getChildList(i)) {
          if (semanticGraph.getChildList(j).size() > 0) {
            System.out.println(j);
            System.out.println("--->" + semanticGraph.getChildList(j));
          }
        }
      }
    }
  }

  protected DefaultDirectedGraph<String, DefaultEdge> getItems(
      ArrayList<SemanticGraph> dependences) {
    DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    List<String> root = new ArrayList<>();
    for (SemanticGraph semanticGraph : dependences) {
      Set<String> noun = new HashSet<>();
      for (IndexedWord i : semanticGraph.getRoots()) {
        root.add(i.lemma());
        checkVer(graph, i.lemma());
        addVer(graph, semanticGraph, i, noun);
        List<IndexedWord> NOUN = semanticGraph.getAllNodesByPartOfSpeechPattern("NN(.*)");
        for (IndexedWord n : NOUN) {
          noun.add(n.lemma());
          if (!graph.containsVertex(n.lemma())) {
            checkVer(graph, n.lemma());
            checkEdge(graph, i.lemma(), n.lemma());
          }
        }
      }
      for (String w1 : noun) {
        if (graph.containsVertex(w1)) {
          for (String w2 : noun) {
            if (!Objects.equals(w1, w2)) {
              if (graph.containsVertex(w2)) {
                if (!graph.containsEdge(w1, w2)) {
                  graph.addEdge(w1, w2);
                }
              }
            }
          }
        }
      }
    }
    if (!graph.containsVertex("")) graph.addVertex("");
    for (String s : root) {
      if (!graph.containsEdge("", s)) graph.addEdge("", s);
    }
    return graph;
  }

  protected DefaultDirectedGraph<String, DefaultEdge> getItems1(
          ArrayList<SemanticGraph> dependences) {
    DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    List<String> root = new ArrayList<>();
    for (SemanticGraph semanticGraph : dependences) {
      Set<String> noun = new HashSet<>();
      for (IndexedWord i : semanticGraph.getRoots()) {
        root.add(i.lemma());
        checkVer(graph, i.lemma());
        addVer(graph, semanticGraph, i, noun);
        List<IndexedWord> NOUN = semanticGraph.getAllNodesByPartOfSpeechPattern("NN(.*)");
        for (IndexedWord n : NOUN) {
          noun.add(n.lemma());
          if (!graph.containsVertex(n.lemma())) {
            checkVer(graph, n.lemma());
            checkEdge(graph, i.lemma(), n.lemma());
          }
        }
      }
      for (String w1 : noun) {
        if (graph.containsVertex(w1)) {
          for (String w2 : noun) {
            if (!Objects.equals(w1, w2)) {
              if (graph.containsVertex(w2)) {
                if (!graph.containsEdge(w1, w2)) {
                  graph.addEdge(w1, w2);
                }
              }
            }
          }
        }
      }
    }
    if (!graph.containsVertex("")) graph.addVertex("");
    for (String s : root) {
      if (!graph.containsEdge("", s)) graph.addEdge("", s);
    }
    return graph;
  }

  protected void checkVer(DefaultDirectedGraph<String, DefaultEdge> graph, String j) {
    if (!graph.containsVertex(j)) {
      graph.addVertex(j);
    }
  }

  protected void checkEdge(
      DefaultDirectedGraph<String, DefaultEdge> graph, String from, String to) {
    if (!graph.containsEdge(from, to)) {
      graph.addEdge(from, to);
    }
  }

  protected void addVer(
      DefaultDirectedGraph<String, DefaultEdge> graph,
      SemanticGraph semanticGraph,
      IndexedWord root,
      Set<String> noun) {
    checkVer(graph, root.lemma());
    if (semanticGraph.getChildList(root).size() > 0) {
      for (IndexedWord j : semanticGraph.getChildList(root)) {
        if (graph.containsVertex(j.lemma())) {
          checkEdge(graph, root.lemma(), j.lemma());
          continue;
        }
        if (j.lemma().contains(".")) {
          String m = j.lemma();
          String[] r1 = m.split("\\.");
          if (r1.length > 1) {
            for (String r2 : r1) {
              if (r2.length() > 1) {
                noun.add(r2);
                checkVer(graph, r2);
                checkEdge(graph, root.lemma(), r2);
              }
            }
            for (String r3 : r1) {
              String[] spilt = splitCamelCase(r3);
              if (spilt != null) {
                noun.add(r3);
                checkVer(graph, r3);
                checkEdge(graph, root.lemma(), r3);
                for (String s2 : spilt) {
                  if (s2.length() > 1) {
                    noun.add(s2);
                    checkVer(graph, s2);
                    checkEdge(graph, r3, s2);
                  }
                }
              }
            }
          }
          if (semanticGraph.getChildList(j).size() > 0) {
            addVer(graph, semanticGraph, j, noun);
          }
        } else if (j.tag().startsWith("NN")) {
          String child = j.lemma();
          Pattern num = Pattern.compile("(.*)\\d+(.*)");
          Matcher m = num.matcher(child);
          if (!m.matches()) {
            noun.add(child);
            checkVer(graph, child);
            checkEdge(graph, root.lemma(), child);
            String[] spilt = splitCamelCase(child);
            if (spilt != null) {
              for (String s2 : spilt) {
                if (s2.length() > 1) {
                  noun.add(s2);
                  checkVer(graph, s2);
                  checkEdge(graph, child, s2);
                }
              }
            }
          }
          if (semanticGraph.getChildList(j).size() > 0) {
            addVer(graph, semanticGraph, j, noun);
          }
        } else if (j.tag().startsWith("VB")) {
          String child = j.lemma();
          checkVer(graph, child);
          checkEdge(graph, root.lemma(), child);
          if (semanticGraph.getChildList(j).size() > 0) {
            addVer(graph, semanticGraph, j, noun);
          }
        }
      }
    }
  }

  protected List<String> getItemsMap1(ArrayList<SemanticGraph> dependences) {
    List<String> itemsMap = new ArrayList<>();
    for (SemanticGraph semanticGraph : dependences) {
      List<SemanticGraphEdge> root = semanticGraph.edgeListSorted();
      List<String> noun = new ArrayList<>();
      List<String> verb = new ArrayList<>();
      if (root.size() > 0) {
        itemsMap.add(root.get(0).getGovernor().lemma());
        for (SemanticGraphEdge semanticGraphEdge : semanticGraph.edgeIterable()) {
          // System.out.println(semanticGraphEdge.getDependent().lemma());
          if (semanticGraphEdge.getDependent().lemma().contains(".")) {
            String m = semanticGraphEdge.getDependent().lemma();
            String[] r1 = m.split("\\.");
            if (r1.length > 1) {
              for (String r2 : r1) {
                if (r2.length() > 1) itemsMap.add(r2);
              }
              for (String r3 : r1) {
                String[] spilt = splitCamelCase(r3);
                if (spilt != null) {
                  for (String s2 : spilt) {
                    if (s2.length() > 1) itemsMap.add(s2);
                  }
                }
              }
            }
          } else if (semanticGraphEdge.getDependent().tag().startsWith("NN")) {
            // && itemsMap.contains(semanticGraphEdge.getGovernor().lemma())) {
            String child = semanticGraphEdge.getDependent().lemma();
            itemsMap.add(child);
            String[] spilt = splitCamelCase(child);
            if (spilt != null) {
              for (String s2 : spilt) {
                if (s2.length() > 1) itemsMap.add(s2);
              }
            }
          } else if (semanticGraphEdge.getDependent().tag().startsWith("VB")) {
            String child = semanticGraphEdge.getDependent().lemma();
            itemsMap.add(child);
          }
        }
      }
    }
    Set<String> set = new HashSet<>();
    for (String ite : itemsMap) {
      if (ite.length() > 2) {
        if (!ite.contains("=") && !ite.contains("/")) {
          set.add(ite);
        }
      }
    }
    return new ArrayList<>(set);
  }

  private String[] splitCamelCase(String word) {
    Pattern pattern = Pattern.compile(QueryConfig.CAMEL_CASE);
    Matcher matcher = pattern.matcher(word);
    if (matcher.matches()) {
      String s = word.replaceAll("[A-Z]", "_$0");
      return s.split("_");
    }
    return null;
  }

  public static void main(String[] args) {
    Que que = new Que();
    DepManager depManager = new DepManager();
    for (int id : BugReportsID.ECF) {
      String brPath =
          DatasetConfig.DATASET_DIR
              + DatasetConfig.BLIZZARD
              + "/"
              + DatasetConfig.ECF
              + "/BR/"
              + id
              + ".txt";
      List<String> con = BRLoader.loadBugContentList(brPath);
      String tile = BRLoader.loadBRTitle(brPath);
      TextNormalizer tn = new TextNormalizer(con);
      ArrayList<SemanticGraph> dependencies = depManager.getDependencies(tn.removeHttp());
      DefaultDirectedGraph<String, DefaultEdge> graph = que.getItems(dependencies);
      GraphParser gp = new GraphParser(graph);
      String[] s = tile.split(" ");
      StringBuilder sb = new StringBuilder();
      int i = 0;
      String[] sym = {
        "(", ")", ":", "[", "]", "}", "{", "#", "-", "|", "%", "@", ".", "+", "=", "\\", "/", "#",
        "*", "?", "^", "$", "\"", "'", "<", ">", ","
      };
      for (String v : s) {
        if (i > 1) {
          Pattern num = Pattern.compile("(.*)\\d+(.*)");
          Matcher m = num.matcher(v);
          if (!m.matches()) {
            for (String s1 : sym) {
              v = v.replace(s1, " ");
            }
            if (v.length() > 2) sb.append(v).append(" ");
          }
        }
        i++;
      }
      System.out.println(gp.getQuery() + " " + sb);
    }
  }
}
