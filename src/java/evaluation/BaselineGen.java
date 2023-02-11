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
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaselineGen {

  public void genBaseline_nl(List<Integer> idList, String rep, String project) throws IOException {
    String path = DatasetConfig.QUERY_DIR + rep + "/" + project + "/baseline_nl.txt";
    FileWriter fileWriter = new FileWriter(path, true);
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
      fileWriter.write(id + "\t" + sb.toString().trim());
      if (cus != idList.size()) {
        fileWriter.write("\n");
      }
      fileWriter.flush();
    }
    fileWriter.close();
  }

  public void genBaseline_nlst(List<Integer> idList, String rep, String project)
      throws IOException {
    String path = DatasetConfig.QUERY_DIR + rep + "/" + project + "/baseline_nlst.txt";
    FileWriter fileWriter = new FileWriter(path, true);
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
      fileWriter.write(id + "\t" + sb.toString().trim());
      if (cus != idList.size()) {
        fileWriter.write("\n");
      }
      fileWriter.flush();
    }
    fileWriter.close();
  }

  public void genBaseline_title(List<Integer> idList, String rep, String project)
      throws IOException {
    String path = DatasetConfig.QUERY_DIR + rep + "/" + project + "/baseline_title.txt";
    FileWriter fileWriter = new FileWriter(path, true);
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
      fileWriter.write(id + "\t" + sb.toString().trim());
      if (cus != idList.size()) {
        fileWriter.write("\n");
      }
      fileWriter.flush();
    }
    fileWriter.close();
  }

  public void genBaseline_entities(List<Integer> idList, String rep, String project)
      throws IOException {
    DepManager depManager = new DepManager();
    String path = DatasetConfig.QUERY_DIR + rep + "/" + project + "/baseline_entity.txt";
    FileWriter fileWriter = new FileWriter(path, true);
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
      fileWriter.write(id + "\t" + sb1.toString().trim());
      if (cus != idList.size()) {
        fileWriter.write("\n");
      }
      fileWriter.flush();
    }
    fileWriter.close();
  }

  public void genBaseline_graph(List<Integer> idList, String rep, String project)
      throws IOException {
    DepManager depManager = new DepManager();
    String path = DatasetConfig.QUERY_DIR + rep + "/" + project + "/baseline_graph.txt";
    FileWriter fileWriter = new FileWriter(path, true);
    String[] sym = {
      "(", ")", ":", "[", "]", "}", "{", "#", "-", "|", "%", "@", ".", "+", "=", "\\", "/", "#",
      "*", "?", "^", "$", "\"", "'", "<", ">", ","
    };
    List<String> list = Arrays.asList(sym);
    int cus = 0;
    for (int id : idList) {
      cus++;
      DefaultDirectedGraph<String, DefaultEdge> graph =
          new DefaultDirectedGraph<>(DefaultEdge.class);
      String brPath = DatasetConfig.DATASET_DIR + rep + "/" + project + "/BR/" + id + ".txt";
      List<String> con = BRLoader.loadBugReportList(brPath);
      TextNormalizer tn = new TextNormalizer(con);
      ArrayList<SemanticGraph> dependencies = depManager.getDependencies(tn.removeHttp());
      for (SemanticGraph semanticGraph : dependencies) {
        for (IndexedWord root : semanticGraph.getRoots()) {
          for (IndexedWord child : semanticGraph.getChildList(root)) {
            if (!list.contains(child.word())) {
              checkVer(graph, root.word());
              checkVer(graph, child.word());
              checkEdge(graph, root.word(), child.word());
              getChildren(child, semanticGraph, graph, 0);
            }
          }
        }
      }
      GraphParser gp = new GraphParser(graph);
      fileWriter.write(id + "\t" + gp.getQuery());
      if (cus != idList.size()) {
        fileWriter.write("\n");
      }
      fileWriter.flush();
    }
    fileWriter.close();
  }

  protected boolean getChildren(
      IndexedWord child,
      SemanticGraph semanticGraph,
      DefaultDirectedGraph<String, DefaultEdge> graph,
      int count) {
    if (count > 5) {
      return false;
    } else {
      count++;
    }
    if (semanticGraph.getChildList(child).isEmpty()) return false;
    for (IndexedWord c1 : semanticGraph.getChildList(child)) {
      checkVer(graph, child.word());
      checkVer(graph, c1.word());
      checkEdge(graph, child.word(), c1.word());
      getChildren(c1, semanticGraph, graph, count);
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
    int size = EvaluationConfig.LTR.length;
    for (int i = 0; i < size; i++)
      new BaselineGen()
          .genBaseline_graph(
              EvaluationConfig.LTR_ID.get(i),
              DatasetConfig.LTR,
              EvaluationConfig.LTR[i]);
  }
}
