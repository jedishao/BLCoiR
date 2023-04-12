package uc.eecs.core;

import org.apache.lucene.queryparser.classic.ParseException;
import uc.eecs.irmodel.blizzard.LuceneSearch;
import utils.config.BugReportsID;
import utils.config.ClassificationID;
import utils.config.DatasetConfig;

import java.io.IOException;
import java.util.ArrayList;

public class FaultLocalizationRunner {

  String benchName;
  String repository;
  int bugID;
  String query;
  ArrayList<String> changeset;

  public FaultLocalizationRunner(String benchName, String repository, int bugID, String query) {
    this.benchName = benchName;
    this.repository = repository;
    this.bugID = bugID;
    this.query = query;
    this.changeset = new ChangeSet().getChangeSet(benchName, repository, bugID);
  }

  public int getFirstRank() {
    LuceneSearch ls = new LuceneSearch(bugID, benchName, repository, query);
    int foundIndex = -1;
    try {
      ResultRankManager rankFinder =
          new ResultRankManager(benchName, repository, ls.SearchListAll(), changeset);
      foundIndex = rankFinder.getFirstGoldRank();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return foundIndex;
  }

  public ArrayList<Integer> getGoldFileIndicesClass() {
    ArrayList<Integer> foundIndices = new ArrayList<>();
    LuceneSearch ls = new LuceneSearch(bugID, benchName, repository, query);
    try {
      ResultRankManager rankFinder =
          new ResultRankManager(benchName, repository, ls.SearchListAll(), changeset);
      ArrayList<Integer> indices = rankFinder.getCorrectRanks();
      if (!indices.isEmpty()) {
        foundIndices.addAll(indices);
      }
    } catch (Exception exc) {
      exc.printStackTrace();
    }
    return foundIndices;
  }

  public static void main(String[] args) throws IOException, ParseException {
    //    int bugID = 39769;
    //    String repository = DatasetConfig.TOMCAT;
    //    String bench = DatasetConfig.BLIZZARD;
    //    String searchQuery =
    //        "class loader myServlet T5 shared directory Thread.currentThread getContextClassLoader
    // " +
    //                "StandardClassLoader StandardContext getLoader StandardWrapper unload servlet
    // destroy";
    //    FaultLocalizationRunner faultLocalizationRunner =
    //        new FaultLocalizationRunner(bench, repository, bugID, searchQuery);
    //    System.out.println("First found index:" + faultLocalizationRunner.getFirstRank());
    //    System.out.println("First found index:" + faultLocalizationRunner1.getFirstRank());
    System.out.println(ClassificationID.VERTX_NL.size());
    System.out.println(ClassificationID.VERTX_STN.size());
  }
}
