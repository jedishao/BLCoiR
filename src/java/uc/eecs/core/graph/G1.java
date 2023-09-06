package uc.eecs.core.graph;

import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import uc.eecs.br.loader.BRLoader;
import uc.eecs.core.query.GraphParser;
import uc.eecs.nlp.CorefManager;
import uc.eecs.nlp.DepManager;
import utils.ConCorpus;
import utils.config.BugReportsID;
import utils.config.DatasetConfig;

public class G1 {
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

  protected DefaultDirectedGraph<String, DefaultEdge> getItems2(
      ArrayList<SemanticGraph> dependencies, List<String> con) {
    DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    for (SemanticGraph semanticGraph : dependencies) {
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

  protected DefaultDirectedGraph<String, DefaultEdge> depGraph(
      ArrayList<SemanticGraph> dependencies) {
    DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
    for (SemanticGraph semanticGraph : dependencies) {
      boolean flag = false;
      List<String> rootList = new ArrayList<>();
      for (IndexedWord root : semanticGraph.getRoots()) {
        rootList.add(root.lemma().toLowerCase());
        checkVer(graph, root.lemma().toLowerCase());
      }
      for (SemanticGraphEdge s : semanticGraph.edgeListSorted()) {
        if (s.getDependent().tag().startsWith("NN")) {
          if (s.getDependent().tag().length() > 2) {
            flag = true;
            break;
          } else if (ConCorpus.CONCURRENCY.contains(s.getDependent().lemma().toLowerCase())) {
            flag = true;
            break;
          } else if (s.getDependent().word().matches("(.*)[A-Z](.*)")) {
            flag = true;
            break;
          }
        }
      }
      if (flag) {
        //        System.out.println(semanticGraph.toRecoveredSentenceString());
        for (SemanticGraphEdge s : semanticGraph.edgeListSorted()) {
          if (s.getDependent().tag().startsWith("NN") || s.getDependent().tag().startsWith("VB")) {
            //
            // System.out.println(s.getDependent().word()+"--------->"+s.getDependent().tag());
            if (s.getGovernor().tag().startsWith("NN")
                || s.getGovernor().tag().startsWith("VB")
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
          //          } else if
          // (ConCorpus.CONCURRENCY.contains(s.getDependent().lemma().toLowerCase())){
          //            checkVer(graph, s.getDependent().lemma().toLowerCase());
          //            checkEdge(graph, rootList.get(0), s.getDependent().lemma().toLowerCase());
          //          }
        }
      }
    }
    return graph;
  }

  protected DefaultDirectedGraph<String, DefaultEdge> knowledgeGraph(
      DefaultDirectedGraph<String, DefaultEdge> graph, List<String> chain, List<String> relation) {
    for (String s1 : chain) {
      if (graph.containsVertex(s1)) {
        for (String s2 : chain) {
          if (graph.containsVertex(s2) && !Objects.equals(s1, s2)) {
            checkEdge(graph, s1, s2);
            checkEdge(graph, s2, s1);
          }
        }
      }
    }
    for (String s3 : relation) {
      if (graph.containsVertex(s3)) {
        for (String s4 : relation) {
          if (graph.containsVertex(s4) && !Objects.equals(s3, s4)) {
            checkEdge(graph, s3, s4);
            checkEdge(graph, s4, s3);
          }
        }
      }
    }
    return graph;
  }

  public List<String> conceptRelation(ArrayList<SemanticGraph> dependencies) {
    List<String> relations = new ArrayList<>();
    for (SemanticGraph semanticGraph : dependencies) {
      List<String> con = new ArrayList<>();
      List<String> api = new ArrayList<>();
      for (IndexedWord word : semanticGraph.vertexListSorted()) {
        if (word.word().split("\\.").length > 1 && word.tag().startsWith("NN")) {
          api.add(word.lemma().toLowerCase());
        } else if (Objects.equals(word.tag(), "NNP")) {
          api.add(word.lemma().toLowerCase());
        } else if (ConCorpus.CONCURRENCY.contains(word.lemma().toLowerCase())) {
          con.add(word.lemma().toLowerCase());
        }
      }
      if (con.size() > 0) {
        for (String s1 : con) {
          if (!relations.contains(s1)) relations.add(s1);
        }
        for (String s2 : con) {
          if (!relations.contains(s2)) relations.add(s2);
        }
      }
    }
    return relations;
  }

  protected String processPackage(String s) {
    String s1 = s.replace("[", "");
    String s2 = s1.replace("]", "");
    String s3 = s2.trim().split(":")[0].trim();
    String s4 = s3.split("\\.")[s3.split("\\.").length - 1];
    return s4;
  }

  public void one(int id) {
    G1 g = new G1();
    DepManager depManager = new DepManager();
    CorefManager corefManager = new CorefManager();
    String brPath =
        DatasetConfig.BRTEXT_DIR
            + DatasetConfig.BENCH4BL
            + "/"
            + DatasetConfig.A_CAMEL
            + "/"
            + id
            + ".txt";
    List<String> con = BRLoader.loadBugContentList(brPath);
    String text = BRLoader.loadBugContent(brPath);
    ArrayList<SemanticGraph> dependencies = depManager.getDependencies(con);
    DefaultDirectedGraph<String, DefaultEdge> depGraph = g.depGraph(dependencies);
    List<String> corefChain = corefManager.corefChain(text);
    List<String> conceptRelation = g.conceptRelation(dependencies);
    DefaultDirectedGraph<String, DefaultEdge> knowledgeGraph =
        g.knowledgeGraph(depGraph, corefChain, conceptRelation);
    GraphParser gp = new GraphParser(knowledgeGraph);
    System.out.println(id + "\t" + gp.getQuery());
  }

  public void all() {
    G1 g = new G1();
    DepManager depManager = new DepManager();
    CorefManager corefManager = new CorefManager();
    for (int id : BugReportsID.SWT) {
      String brPath =
          DatasetConfig.BRTEXT_DIR
              + DatasetConfig.LTR
              + "/"
              + DatasetConfig.SWT
              + "/"
              + id
              + ".txt";
      List<String> con = BRLoader.loadBugContentList(brPath);
      String text = BRLoader.loadBugContent(brPath);
      ArrayList<SemanticGraph> dependencies = depManager.getDependencies(con);
      DefaultDirectedGraph<String, DefaultEdge> depGraph = g.depGraph(dependencies);
      List<String> corefChain = corefManager.corefChain(text);
      List<String> conceptRelation = g.conceptRelation(dependencies);
      DefaultDirectedGraph<String, DefaultEdge> knowledgeGraph =
          g.knowledgeGraph(depGraph, corefChain, conceptRelation);
      GraphParser gp = new GraphParser(knowledgeGraph);
      System.out.println(id + "\t" + gp.getQuery());
    }
  }

  public static void main(String[] args) {
    G1 g = new G1();
    g.one(803);
//    g.all();
  }
}
