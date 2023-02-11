package uc.eecs.core.process;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.AbstractBaseGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import uc.eecs.br.loader.StackTraceLoader;
import uc.eecs.nlp.StopWordManager;
import utils.ContentLoader;
import utils.MiscUtility;
import utils.config.BugReportsID;
import utils.config.DatasetConfig;

import java.util.*;

public class TraceElemExtractor {
  ArrayList<String> traces;
  HashSet<String> packages;
  HashSet<String> methods;
  HashSet<String> classes;

  // version of jgrapht
  AbstractBaseGraph<String, DefaultEdge> methodGraph = null;
  DefaultDirectedGraph<String, DefaultEdge> classGraph = null;

  public TraceElemExtractor(ArrayList<String> traces) {
    this.traces = refineTraces(traces);
    this.packages = new HashSet<>();
    this.methods = new HashSet<>();
    this.classes = new HashSet<>();

    // initialize the graph
    this.methodGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
    this.classGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
  }

  public ArrayList<String> getTraces() {
    return traces;
  }

  public HashSet<String> getPackages() {
    return packages;
  }

  public HashSet<String> getMethods() {
    return methods;
  }

  public HashSet<String> getClasses() {
    return classes;
  }

  public AbstractBaseGraph<String, DefaultEdge> getMethodGraph() {
    return methodGraph;
  }

  public DefaultDirectedGraph<String, DefaultEdge> getClassGraph() {
    return classGraph;
  }

  /**
   * clean the
   *
   * @param traces if it contains file name
   * @return
   */
  protected ArrayList<String> refineTraces(ArrayList<String> traces) {
    ArrayList<String> refined = new ArrayList<>();
    for (String trace : traces) {
      if (trace.indexOf('(') > 0) {
        int leftParenIndex = trace.indexOf('(');
        String line = trace.substring(0, leftParenIndex);
        refined.add(line);
      } else {
        refined.add(trace);
      }
    }
    return refined;
  }

  protected String cleanEntity(String itemName) {
    String[] parts = itemName.split("\\p{Punct}+|\\s+");
    return MiscUtility.list2Str(parts);
  }

  protected void decodeTraces(boolean canonical) {
    String prevClass = "";
    String prevMethod = "";
    int tcount = 0;
    for (String line : this.traces) {
      String[] parts = line.split("\\.");
      int length = parts.length;

      String methodName = "";
      String className = "";
      String packageName = "";

      if (canonical) {
        for (String part : parts) {
          methodName += "." + part;
        }

        for (int i = 0; i < length - 1; i++) {
          className += "." + parts[i];
        }

        for (int i = 0; i < length - 2; i++) {
          packageName += "." + parts[i];
        }

        // removing unexpected dots
        if (!methodName.trim().isEmpty()) methodName = methodName.substring(1);
        if (!className.trim().isEmpty()) className = className.substring(1);
        if (!packageName.trim().isEmpty()) packageName = packageName.substring(1);
      } else {
        if (length >= 2) {
          methodName = parts[length - 1];
          methodName = cleanEntity(methodName);
          className = parts[length - 2];
          className = cleanEntity(className);
          // package is always canonical
          for (int i = 0; i < length - 2; i++) {
            packageName += "." + parts[i];
          }
          packageName = cleanEntity(packageName.trim());
        }
      }
      this.methods.add(methodName);
      this.classes.add(className);
      this.packages.add(packageName);

      // record the dependencies
      /*
       * if (!methodGraph.containsVertex(methodName)) {
       * methodGraph.addVertex(methodName); if (!prevMethod.isEmpty()) {
       * if (!methodGraph.containsEdge(methodName, prevMethod)) {
       * this.methodGraph.addEdge(methodName, prevMethod); } } prevMethod
       * = methodName; }
       */

      if (!classGraph.containsVertex(className)) {
        classGraph.addVertex(className);
        if (!prevClass.isEmpty()) {
          if (!classGraph.containsEdge(className, prevClass)) {
            this.classGraph.addEdge(className, prevClass);
          }
        }
        prevClass = className;
      }

      // added extra module
      if (!classGraph.containsVertex(methodName)) {
        classGraph.addVertex(methodName);
      }
      // new
      if (!classGraph.containsEdge(methodName, className)) {
        this.classGraph.addEdge(methodName, className);
      }
      if (!classGraph.containsEdge(className, methodName)) {
        this.classGraph.addEdge(className, methodName);
      }
      // new
      if (!prevMethod.isEmpty()) {
        if (!classGraph.containsEdge(methodName, prevMethod)) {
          this.classGraph.addEdge(methodName, prevMethod);
        }
        // doesn't it improve?
        /*
         * if (!classGraph.containsEdge(methodName, prevClass)) {
         * this.classGraph.addEdge(methodName, prevClass); }
         */
      }
      prevMethod = methodName;

      tcount++;
      if (tcount == DatasetConfig.MAX_ST_ENTRY_LEN) {
        break;
      }
    }
  }

  protected List<String> traces() {
    List<String> classes = new ArrayList<>();
    for (String line : this.traces) {
      String[] parts = line.split("\\.");
      int length = parts.length;

      String methodName = "";
      String className = "";
      String packageName = "";

      if (length >= 2) {
        methodName = parts[length - 1];
        methodName = cleanEntity(methodName);
        className = parts[length - 2];
        className = cleanEntity(className);
        // package is always canonical
        for (int i = 0; i < length - 2; i++) {
          packageName += "." + parts[i];
        }
        packageName = cleanEntity(packageName.trim());
      }
      classes.add(className);
    }
    return classes;
  }

  protected void expandTraceNodes() {
    // expand trace nodes with individual nodes
    HashSet<String> nodeSet = new HashSet<>(this.classGraph.vertexSet());
    for (String key : nodeSet) {
      ArrayList<String> tokens = MiscUtility.decomposeCamelCase(key);
      if (tokens.size() > 1) {
        StopWordManager stopManager = new StopWordManager();
        ArrayList<String> refinedTokens = stopManager.getRefinedList(tokens);
        for (String refToken : refinedTokens) {
          if (!this.classGraph.containsVertex(refToken)) {
            this.classGraph.addVertex(refToken);
          }
          if (!this.classGraph.containsEdge(refToken, key)) {
            this.classGraph.addEdge(refToken, key);
          }
          if (!this.classGraph.containsEdge(key, refToken)) {
            this.classGraph.addEdge(key, refToken);
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    String repoName = DatasetConfig.W_WFCORE;
    String path = DatasetConfig.QUERY_DIR + "Bench4BL/" + repoName + "/our.txt";
    int j = 0;
    List<String> results = ContentLoader.getAllLinesList(path);
    for (int bugID : BugReportsID.WFCORE) {
      //    String brFile = DatasetConfig.DATASET_DIR + "/BR-Raw/" + repoName + "/" + bugID +
      // ".txt";
      String brPath =
          DatasetConfig.DATASET_DIR
              + DatasetConfig.BENCH4BL
              + "/"
              + repoName
              + "/BR/"
              + bugID
              + ".txt";
      // String title = BRLoader.loadBRTitle(repoName, bugID);
      String reportContent = ContentLoader.loadFileContent(brPath);
      StackTraceLoader stl = new StackTraceLoader();
      ArrayList<String> stack_trace = stl.getStackTrace(reportContent);
      //      for (String st : stack_trace) {
      //        System.out.println(st);
      //      }
      //      System.out.println("============================");
      TraceElemExtractor tee = new TraceElemExtractor(stack_trace);
      List<String> l = new ArrayList<>();
      int index = 1;
      StringBuilder sb = new StringBuilder();
      if (!tee.traces().isEmpty()) {
        for (String m : tee.traces()) {
          if (index < 11) {
            if (!l.contains(m)) {
              l.add(m);
              sb.append(m).append("^").append((float) 1 / index).append(" ");
            }
            index++;
          }
        }
      }
      System.out.println(results.get(j) + " " + sb);
      j++;
    }
  }
}
