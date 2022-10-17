package uc.eecs.core.query;

import org.jgraph.graph.DefaultEdge;
import org.jgrapht.DirectedGraph;
import uc.eecs.br.BRClassification;
import uc.eecs.br.classification.ExceptionLoader;
import uc.eecs.br.classification.StackTraceLoader;
import uc.eecs.core.graph.ConceptualGraph;
import uc.eecs.core.process.StackTraceSelector;
import uc.eecs.br.BRLoader;
import uc.eecs.core.process.TextProcess;
import uc.eecs.nlp.DepManager;
import uc.eecs.nlp.TextNormalizer;
import utils.FileURL;
import utils.MiscUtility;
import utils.config.DatasetConfig;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class QueryGenerator {

  Boolean stackTrace;
  String reportContent;
  ArrayList<String> reportContentList;
  String bugReportTitle;
  DepManager depManager;
  boolean hasException = false;
  Logger logger;

  public Boolean getStackTrace() {
    return stackTrace;
  }

  public QueryGenerator(String bugReportTitle, ArrayList<String> reportContentList) {
    this.bugReportTitle = bugReportTitle;
    this.reportContent = BRLoader.loadBugContent(reportContentList);
    this.reportContentList = reportContentList;
    this.stackTrace = new BRClassification().classification(reportContentList);
  }

  public QueryGenerator(String bugReportTitle, ArrayList<String> reportContentList, DepManager depManager) {
    this.bugReportTitle = bugReportTitle;
    this.reportContent = BRLoader.loadBugContent(reportContentList);
    this.depManager = depManager;
    this.reportContentList = reportContentList;
    this.stackTrace = new BRClassification().classification(reportContentList);;
  }

  // boolean hasException = false;
  public String generateQuery() {
    ArrayList<String> keywords = new ArrayList<>();
    if (this.stackTrace) {
      System.out.println("ST");
      StackTraceLoader stl = new StackTraceLoader();
      ArrayList<String> stack_trace = stl.getStackTrace(this.reportContentList);
      StackTraceSelector sts = new StackTraceSelector(stack_trace);
      // salient items after pagerank
      ArrayList<String> salientItems = sts.getSalientItemsFromST();
      // new
      // HashMap<String, Double> itemMapC = sts.getSalientClasses();
      HashSet<String> exceptions = ExceptionLoader.getExceptionMessages(this.reportContent);

      if (!bugReportTitle.isEmpty()) {
        String normTitle = new TextNormalizer(this.bugReportTitle).normalizeSimple();
        keywords.add(normTitle);
      }
      // adding the exception
      if (!exceptions.isEmpty()) {
        hasException = true;
        keywords.addAll(exceptions);
      }
      // adding the trace keywords
      keywords.addAll(salientItems);
      // new
      // keywords.addAll(itemMapC.keySet());
      return MiscUtility.list2Str(keywords);
    } else {
      // logger.info("NL");
      System.out.println("NL");
      TextProcess textProcess;
      if (depManager == null){
        textProcess = new TextProcess(reportContent);
      } else {
        textProcess = new TextProcess(reportContent, depManager);
      }
      // get items
      Map<String, List<List<String>>> itemsMap = textProcess.getDomainItems();
      ArrayList<String> conItems = textProcess.getConcurrencyItems();
      // using items to build conceptual graph
      ConceptualGraph cg = new ConceptualGraph(itemsMap, conItems);
      DirectedGraph<String, DefaultEdge> graph = cg.buildGraph();
      // if too long, using pageRank
      GraphParser graphParser = new GraphParser(graph);
      return graphParser.getQuery();
    }
  }

  public static void main(String[] args) {
//    String benchName = DatasetConfig.BLIZZARD;
//    String repoName = DatasetConfig.DEBUG;
//    //    for (int bugID : DatasetIndex.DEBUG_ST) {
//    String brFile = FileURL.brPathAppend(benchName, repoName, 1695);
//
//    String title = BRLoader.loadBRTitle(brFile);
//    String bugReport = BRLoader.loadBugReport(brFile);
//    String bugReportContent = BRLoader.loadBugContent(brFile);
//    QueryGenerator queryGenerator = new QueryGenerator(title, bugReportContent);
//    String query = queryGenerator.generateQuery();
//    System.out.println(query);
    // System.out.println(bugReportContent.replace("\n", " "));
    //    String baselineQuery = new TextNormalizer(title).normalizeText();
    //    String normDesc = new TextNormalizer(bugReportContent).normalizeText();
    //    System.out.println("q---->"+baselineQuery);
    //    System.out.println("s---->"+normDesc);
    //    QueryGenerator qg = new QueryGenerator(repoName, bugReportContent, title);
    //      System.out.println(qg.generateQuery());
    //    if(qg.getStackTrace()){
    //       System.out.println(bugID);
    //    }
  }
  // }
}
