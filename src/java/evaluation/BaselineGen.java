package evaluation;

import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.semgraph.SemanticGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import uc.eecs.br.loader.BRLoader;
import uc.eecs.core.query.GraphParser;
import uc.eecs.nlp.DepManager;
import uc.eecs.nlp.TextNormalizer;
import utils.ResUtils;
import utils.config.BugReportsID;
import utils.config.DatasetConfig;
import utils.config.EvaluationConfig;
import utils.config.QueryConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaselineGen {

  public void genBaseline_nl(List<Integer> idList, String rep, String project) throws IOException {
    String path = DatasetConfig.QUERY_DIR + rep + "/" + project + "/baseline_nl.txt";
    FileWriter fileWritter = new FileWriter(path, true);
    int cus = 0;
    for (int id : idList) {
      cus++;
      String brPath = DatasetConfig.DATASET_DIR + rep + "/" + project + "/BR/" + id + ".txt";
      List<String> con = BRLoader.loadBugContentList(brPath);
      String tile = BRLoader.loadBRTitle(brPath);
      String[] s = tile.split(" ");
      StringBuilder sb = new StringBuilder();
      int i = 0;
      String[] sym = {
        "(", ")", ":", "[", "]", "}", "{", "#", "-", "|", "%", "@", ".", "+", "=", "\\", "/", "#",
        "*", "?", "^", "$", "\"", "'", "<", ">", ",", ";"
      };
      for (String v : s) {
        for (String s1 : sym) {
          v = v.replace(s1, " ");
        }
        sb.append(v).append(" ");
      }
      for (String c : con) {
        Pattern num = Pattern.compile(QueryConfig.STACK_REGEX);
        Matcher m = num.matcher(c);
        if (!m.matches()) {
          for (String s1 : sym) {
            c = c.replace(s1, " ");
          }
          sb.append(c).append(" ");
        }
      }
      fileWritter.write(id + "\t" + sb.toString().trim());
      if (cus != idList.size()) {
        fileWritter.write("\n");
      }
      fileWritter.flush();
    }
    fileWritter.close();
  }

  public void genBaseline_nlst(List<Integer> idList, String rep, String project)
      throws IOException {
    String path = DatasetConfig.QUERY_DIR + rep + "/" + project + "/baseline_nlst.txt";
    FileWriter fileWritter = new FileWriter(path, true);
    int cus = 0;
    for (int id : idList) {
      cus++;
      String brPath = DatasetConfig.DATASET_DIR + rep + "/" + project + "/BR/" + id + ".txt";
      List<String> con = BRLoader.loadBugContentList(brPath);
      String tile = BRLoader.loadBRTitle(brPath);
      String[] s = tile.split(" ");
      StringBuilder sb = new StringBuilder();
      int i = 0;
      String[] sym = {
        "(", ")", ":", "[", "]", "}", "{", "#", "-", "|", "%", "@", ".", "+", "=", "\\", "/", "#",
        "*", "?", "^", "$", "\"", "'", "<", ">", ",", ";"
      };
      for (String v : s) {
        for (String s1 : sym) {
          v = v.replace(s1, " ");
        }
        sb.append(v).append(" ");
      }
      for (String c : con) {
        for (String s1 : sym) {
          c = c.replace(s1, " ");
        }
        sb.append(c).append(" ");
      }
      fileWritter.write(id + "\t" + sb.toString().trim());
      if (cus != idList.size()) {
        fileWritter.write("\n");
      }
      fileWritter.flush();
    }
    fileWritter.close();
  }

  public void genBaseline_title(List<Integer> idList, String rep, String project)
      throws IOException {
    String path = DatasetConfig.QUERY_DIR + rep + "/" + project + "/baseline_title.txt";
    FileWriter fileWritter = new FileWriter(path, true);
    int cus = 0;
    for (int id : idList) {
      cus++;
      String brPath = DatasetConfig.DATASET_DIR + rep + "/" + project + "/BR/" + id + ".txt";
      String tile = BRLoader.loadBRTitle(brPath);
      String[] s = tile.split(" ");
      StringBuilder sb = new StringBuilder();
      String[] sym = {
        "(", ")", ":", "[", "]", "}", "{", "#", "-", "|", "%", "@", ".", "+", "=", "\\", "/", "#",
        "*", "?", "^", "$", "\"", "'", "<", ">", ","
      };
      for (String v : s) {
        for (String s1 : sym) {
          v = v.replace(s1, " ");
        }
        sb.append(v).append(" ");
      }
      fileWritter.write(id + "\t" + sb.toString().trim());
      if (cus != idList.size()) {
        fileWritter.write("\n");
      }
      fileWritter.flush();
    }
    fileWritter.close();
  }

  public void genBaseline_entities(List<Integer> idList, String rep, String project)
      throws IOException {
    DepManager depManager = new DepManager();
    String path = DatasetConfig.QUERY_DIR + rep + "/" + project + "/baseline_entity.txt";
    FileWriter fileWritter = new FileWriter(path, true);
    int cus = 0;
    for (int id : idList) {
      cus++;
      String brPath = DatasetConfig.DATASET_DIR + rep + "/" + project + "/BR/" + id + ".txt";
      List<String> con = BRLoader.loadBugReportList(brPath);
      TextNormalizer tn = new TextNormalizer(con);
      ArrayList<SemanticGraph> dependencies = depManager.getDependencies(tn.removeHttp());
      StringBuilder sb = new StringBuilder();
      for (SemanticGraph semanticGraph : dependencies) {
        for (IndexedWord indexedWord : semanticGraph.vertexListSorted()) {
          if (indexedWord.tag().startsWith("NN") || indexedWord.tag().startsWith("VB")) {
            sb.append(indexedWord.originalText()).append(" ");
          }
        }
      }
      String[] s = sb.toString().split(" ");
      StringBuilder sb1 = new StringBuilder();
      String[] sym = {
        "(", ")", ":", "[", "]", "}", "{", "#", "-", "|", "%", "@", ".", "+", "=", "\\", "/", "#",
        "*", "?", "^", "$", "\"", "'", "<", ">", ","
      };
      for (String v : s) {
        for (String s1 : sym) {
          v = v.replace(s1, " ");
        }
        sb1.append(v).append(" ");
      }
      fileWritter.write(id + "\t" + sb1.toString().trim());
      if (cus != idList.size()) {
        fileWritter.write("\n");
      }
      fileWritter.flush();
    }
    fileWritter.close();
  }

  public void genBaseline_graph(List<Integer> idList, String rep, String project)
          throws IOException {
    DepManager depManager = new DepManager();
    String path = DatasetConfig.QUERY_DIR + rep + "/" + project + "/baseline_entity.txt";
    FileWriter fileWritter = new FileWriter(path, true);
    int cus = 0;
    for (int id : idList) {
      cus++;
      String brPath = DatasetConfig.DATASET_DIR + rep + "/" + project + "/BR/" + id + ".txt";
      List<String> con = BRLoader.loadBugReportList(brPath);
      TextNormalizer tn = new TextNormalizer(con);
      ArrayList<SemanticGraph> dependencies = depManager.getDependencies(tn.removeHttp());
      StringBuilder sb = new StringBuilder();
      for (SemanticGraph semanticGraph : dependencies) {
        for (IndexedWord indexedWord : semanticGraph.vertexListSorted()) {
          if (indexedWord.tag().startsWith("NN") || indexedWord.tag().startsWith("VB")) {
            sb.append(indexedWord.originalText()).append(" ");
          }
        }
      }
      String[] s = sb.toString().split(" ");
      StringBuilder sb1 = new StringBuilder();
      String[] sym = {
              "(", ")", ":", "[", "]", "}", "{", "#", "-", "|", "%", "@", ".", "+", "=", "\\", "/", "#",
              "*", "?", "^", "$", "\"", "'", "<", ">", ","
      };
      for (String v : s) {
        for (String s1 : sym) {
          v = v.replace(s1, " ");
        }
        sb1.append(v).append(" ");
      }
      fileWritter.write(id + "\t" + sb1.toString().trim());
      if (cus != idList.size()) {
        fileWritter.write("\n");
      }
      fileWritter.flush();
    }
    fileWritter.close();
  }

  public void genG(List<Integer> idList, String rep, String project) throws IOException {
    DepManager depManager = new DepManager();
    String path = DatasetConfig.QUERY_DIR + rep + "/" + project + "/baseline_entity.txt";
    FileWriter fileWritter = new FileWriter(path, true);
    int cus = 0;
    for (int id : idList) {
      cus++;
      DefaultDirectedGraph<String, DefaultEdge> graph = new DefaultDirectedGraph<>(DefaultEdge.class);
      String brPath = DatasetConfig.DATASET_DIR + rep + "/" + project + "/BR/" + id + ".txt";
      List<String> con = BRLoader.loadBugReportList(brPath);
      TextNormalizer tn = new TextNormalizer(con);
      ArrayList<SemanticGraph> dependencies = depManager.getDependencies(tn.removeHttp());
      for (SemanticGraph semanticGraph : dependencies) {
        Set<String> noun = new HashSet<>();
        for (IndexedWord root : semanticGraph.getRoots()) {
          for (IndexedWord child : semanticGraph.getChildList(root)) {
            checkVer(graph, root.word());
            checkEdge(graph, root.word(), child.word());
            getChildren(child, semanticGraph, graph);
          }
        }
      }
      GraphParser gp = new GraphParser(graph);
      System.out.println(id + "\t" + gp.getQuery());
    }
  }
  protected boolean getChildren(IndexedWord child, SemanticGraph semanticGraph, DefaultDirectedGraph<String, DefaultEdge> graph) {
    if (semanticGraph.getChildList(child).isEmpty()) return false;
    for (IndexedWord c1 : semanticGraph.getChildList(child)) {
      checkVer(graph, child.word());
      checkEdge(graph, child.word(), c1.word());
      getChildren(c1, semanticGraph, graph);
    }
    return true;
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
  public static void main(String[] args) throws IOException {
    int size = EvaluationConfig.BENCH4BL.length;
    for (int i = 0; i < size; i++)
      new BaselineGen()
          .genBaseline_entities(
              EvaluationConfig.BENCH4BL_ID.get(i), DatasetConfig.BENCH4BL, EvaluationConfig.BENCH4BL[i]);
    //    new BaselineGen().genBaseline_nl(BugReportsID.ECF, DatasetConfig.BLIZZARD,
    // DatasetConfig.ECF);
  }
}
