package uc.eecs.core;

import uc.eecs.br.classification.BRClassification;
import uc.eecs.br.classification.ExceptionLoader;
import uc.eecs.br.classification.StackTraceLoader;
import uc.eecs.br.processing.TextNormalizer;
import uc.eecs.core.selector.PEKeywordSelector;
import uc.eecs.core.selector.StackTraceSelector;
import uc.eecs.core.selector.TextKeywordSelector;
import utils.BRLoader;
import utils.ContentLoader;
import utils.MiscUtility;
import utils.config.DatasetConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;

public class QueryGenerator {
  String repository;
  int bugID;
  Boolean stackTrace;
  String reportContent;
  String bugReportTitle;
  boolean hasException = false;

  public QueryGenerator(String repository, int bugID, String reportContent, String bugReportTitle) {
    this.repository = repository;
    this.bugID = bugID;
    this.reportContent = reportContent;
    this.bugReportTitle = bugReportTitle;
    this.stackTrace = new BRClassification().classification(reportContent);
  }

  // boolean hasException = false;
  public String generateQuery() {
    ArrayList<String> keywords = new ArrayList<>();
    if (this.stackTrace) {
      StackTraceLoader stl = new StackTraceLoader();
      ArrayList<String> stack_trace = stl.getStackTrace(this.reportContent);
      StackTraceSelector sts = new StackTraceSelector(stack_trace);
      // salient items after pagerank
      ArrayList<String> salientItems = sts.getSalientItemsFromST();
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

    } else {
      // invoke ACER (basic version without ML)
            TextKeywordSelector kwSelector = new TextKeywordSelector(repository, bugReportTitle,
       reportContent, DatasetConfig.MAX_NL_SUGGESTED_QUERY_LEN);
            String extended =
       kwSelector.getSearchTermsWithCR(DatasetConfig.MAX_NL_SUGGESTED_QUERY_LEN);
            ArrayList<String> salientItems = MiscUtility.str2List(extended);
            keywords.addAll(salientItems);
//      PEKeywordSelector peSelector =
//          new PEKeywordSelector(
//              bugReportTitle, reportContent, DatasetConfig.MAX_PE_SUGGESTED_QUERY_LEN);
//      ArrayList<String> salientItems = peSelector.getSearchTerms();
//      keywords.addAll(salientItems);
    }
    return MiscUtility.list2Str(keywords);
  }

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    String repoName = "eclipse.jdt.ui";
    int bugID = 140392;
    String brFile = DatasetConfig.HOME_DIR + "/dataset/BR-Raw/" + repoName + "/" + bugID + ".txt";
    System.out.println(new File(brFile).isFile());
    String title = BRLoader.loadBRTitle(repoName, bugID);
    String bugReportContent = ContentLoader.loadFileContent(brFile);
    System.out.println(title);
    System.out.println(bugReportContent);
    QueryGenerator qg = new QueryGenerator(repoName, bugID, bugReportContent, title);
    System.out.println(qg.generateQuery());
  }
}
