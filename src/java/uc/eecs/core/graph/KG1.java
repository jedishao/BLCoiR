package uc.eecs.core.graph;

import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import uc.eecs.br.loader.BRLoader;
import uc.eecs.core.FaultLocalizationRunner;
import uc.eecs.core.query.GraphParser;
import uc.eecs.nlp.DepManager;
import uc.eecs.nlp.TextNormalizer;
import utils.ConCorpus;
import utils.MiscUtility;
import utils.config.DatasetConfig;
import utils.config.QueryConfig;
import utils.config.ResConfig;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class KG1 {
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

  private String[] splitCamelCase(String word) {
    Pattern pattern = Pattern.compile(QueryConfig.CAMEL_CASE);
    Matcher matcher = pattern.matcher(word);
    if (matcher.matches()) {
      String s = word.replaceAll("[A-Z]", "_$0");
      return s.split("_");
    }
    return null;
  }

  protected DefaultDirectedGraph<String, DefaultEdge> getItems(
      ArrayList<SemanticGraph> dependences) {
    DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    for (SemanticGraph semanticGraph : dependences) {
      Set<String> noun = new HashSet<>();
      for (IndexedWord root : semanticGraph.getRoots()) {
        System.out.println(root + "----->" + root.tag());
        for (IndexedWord child : semanticGraph.getChildList(root)) {
          if (child.tag().startsWith("NN")) {
            System.out.println(
                child.originalText() + "---->" + semanticGraph.getEdge(root, child).getRelation());
          }
          getChildren(child, semanticGraph);
        }
      }
    }
    return graph;
  }

  protected boolean getChildren(IndexedWord child, SemanticGraph semanticGraph) {
    if (semanticGraph.getChildList(child).isEmpty()) return false;
    for (IndexedWord c1 : semanticGraph.getChildList(child)) {
      System.out.println("ccc" + "=======" + child + "---->" + c1);
      getChildren(c1, semanticGraph);
      if (c1.tag().startsWith("NN")) {
        System.out.println(
            c1.originalText() + "---->" + semanticGraph.getEdge(child, c1).getRelation());
      }
    }
    return true;
  }

  static Set<String> set = new HashSet<>();

  protected DefaultDirectedGraph<String, DefaultEdge> getItems1(
      ArrayList<SemanticGraph> dependences, List<String> con) {
    DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    for (SemanticGraph semanticGraph : dependences) {
      List<String> rootList = new ArrayList<>();
      for (IndexedWord root : semanticGraph.getRoots()) {
        rootList.add(root.lemma().toLowerCase());
        //        System.out.println(root.lemma());
        checkVer(graph, root.lemma().toLowerCase());
      }
      for (SemanticGraphEdge s : semanticGraph.edgeListSorted()) {
        if (s.getDependent().tag().startsWith("NN")) {
          if (s.getDependent().tag().equals("NNP") || s.getDependent().tag().equals("NNS")) {
            con.add(s.getDependent().lemma().toLowerCase());
          }
          if (s.getGovernor().tag().startsWith("NN")
              || rootList.contains(s.getGovernor().lemma().toLowerCase())) {
            // System.out.println("nn" + "-------->" + s.getDependent().word());
            checkVer(graph, s.getGovernor().lemma().toLowerCase());
            checkVer(graph, s.getDependent().lemma().toLowerCase());
            checkEdge(
                graph,
                s.getGovernor().lemma().toLowerCase(),
                s.getDependent().lemma().toLowerCase());
          } else {
            checkVer(graph, s.getDependent().lemma().toLowerCase());
            checkEdge(graph, rootList.get(0), s.getDependent().lemma().toLowerCase());
          }
        }
      }
    }
    return graph;
  }

  protected DefaultDirectedGraph<String, DefaultEdge> getItems2(
      ArrayList<SemanticGraph> dependences, List<String> con) {
    DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    for (SemanticGraph semanticGraph : dependences) {
      String sents = semanticGraph.toRecoveredSentenceString();
      //      System.out.println(sents);
      Pattern p =
          Pattern.compile(
              "(.*)\\[(.*)((.+).)+(.+):(.*)run(.*)](.*)(Running)(.*)|(.*)\\[(.*)((.+).)+(.+):(.*)run(.*)]");
      Matcher m = p.matcher(sents.trim());
      if (m.matches()) {
        con.add(processPackage(sents));
      }
      List<String> rootList = new ArrayList<>();
      for (IndexedWord root : semanticGraph.getRoots()) {
        rootList.add(root.lemma().toLowerCase());
        checkVer(graph, root.lemma().toLowerCase());
      }
      Pattern p1 = Pattern.compile("[a-z]+[A-Z](.*)");
      for (SemanticGraphEdge s : semanticGraph.edgeListSorted()) {
        if (s.getDependent().tag().startsWith("NN")) {
          Matcher m1 = p1.matcher(s.getDependent().word());
          System.out.println(s.getDependent());
          if (s.getDependent().tag().equals("NNP") || m1.matches()) {
            con.add(s.getDependent().lemma().toLowerCase());
          }
          if (s.getGovernor().tag().startsWith("NN")
              || rootList.contains(s.getGovernor().lemma().toLowerCase())) {
            checkVer(graph, s.getGovernor().lemma().toLowerCase());
            checkVer(graph, s.getDependent().lemma().toLowerCase());
            checkEdge(
                graph,
                s.getGovernor().lemma().toLowerCase(),
                s.getDependent().lemma().toLowerCase());
          } else {
            checkVer(graph, s.getDependent().lemma().toLowerCase());
            checkEdge(graph, rootList.get(0), s.getDependent().lemma().toLowerCase());
          }
        }
      }
    }
    return graph;
  }

  protected String processPackage(String s) {
    String s1 = s.replace("[", "");
    String s2 = s1.replace("]", "");
    String s3 = s2.trim().split(":")[0].trim();
    String s4 = s3.split("\\.")[s3.split("\\.").length - 1];
    return s4;
  }

  protected List<String> getItems3(ArrayList<SemanticGraph> dependences, List<String> con) {
    List<SemanticGraph> selected = new ArrayList<>();
    Set<String> re = new HashSet<>();
    Pattern p = Pattern.compile("[a-z]*[A-Z][a-z]+(.*)");
    for (SemanticGraph semanticGraph : dependences) {
      for (IndexedWord indexedWord : semanticGraph.vertexListSorted()) {
        if (ResConfig.CON_KW.contains(indexedWord.lemma())) {
          selected.add(semanticGraph);
          break;
        }
      }
    }
    for (SemanticGraph semanticGraph1 : selected) {
      for (IndexedWord indexedWord1 : semanticGraph1.vertexListSorted()) {
        Matcher m = p.matcher(indexedWord1.word());
        if (m.matches() && indexedWord1.tag().startsWith("NN")) {
          if (!ResConfig.CON_KW1.contains(indexedWord1.lemma().toLowerCase())) {
            re.add(indexedWord1.word());
          }
        }
      }
    }
    return new ArrayList<>(re);
  }

  public static void main(String[] args) {
    KG1 conc = new KG1();
    DepManager depManager = new DepManager();
    String repository = DatasetConfig.TOMCAT;
    String bench = DatasetConfig.BLIZZARD;
    int id = 53624;
    String brPath = DatasetConfig.DATASET_DIR + bench + "/" + repository + "/BR/" + id + ".txt";
    List<String> con = BRLoader.loadBugReportList(brPath);
    List<String> conList = new ArrayList<>();
    TextNormalizer tn = new TextNormalizer(con);
    ArrayList<SemanticGraph> dependencies = depManager.getDependencies(tn.removeHttp());
    DefaultDirectedGraph<String, DefaultEdge> graph = conc.getItems2(dependencies, conList);
    for (String s : graph.vertexSet()) {
      for (String s1 : ConCorpus.CONCURRENCY) {
        if (s.toLowerCase().matches("(.*)" + s1 + "(.*)")) {
          conList.add(s);
        }
      }
    }
    for (String s2 : conList) {
      if (!graph.containsVertex(s2)) {
        conc.checkVer(graph, s2);
      }
      for (String s3 : conList) {
        if (!graph.containsVertex(s3)) {
          conc.checkVer(graph, s3);
        }
        if (!Objects.equals(s2, s3)) conc.checkEdge(graph, s2, s3);
      }
    }
    System.out.println(conList);
    List<String> graph1 = conc.getItems3(dependencies, conList);
    GraphParser gp = new GraphParser(graph);
    String searchQuery = "";
    if (conList.size()>2){
      searchQuery = gp.getQuery1(10);
    }else {
      searchQuery = gp.getQuery1(30);
    }
    String searchQuery1 = MiscUtility.list2Str(graph1);
    System.out.println(id + "\t" + searchQuery);
    System.out.println(id + "\t" + searchQuery1);
    FaultLocalizationRunner faultLocalizationRunner =
        new FaultLocalizationRunner(bench, repository, id, searchQuery);
    FaultLocalizationRunner faultLocalizationRunner1 =
        new FaultLocalizationRunner(bench, repository, id, searchQuery1);
    System.out.println("First found index:" + faultLocalizationRunner.getFirstRank());
    System.out.println("First found index:" + faultLocalizationRunner1.getFirstRank());
  }
}
