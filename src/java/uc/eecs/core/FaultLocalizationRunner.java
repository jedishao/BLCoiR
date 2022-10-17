package uc.eecs.core;

import org.apache.lucene.queryparser.classic.ParseException;
import uc.eecs.Lucene.LuceneSearch;
import uc.eecs.nlp.DepManager;

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
          new ResultRankManager(repository, ls.SearchListAll(), changeset);
      foundIndex = rankFinder.getFirstGoldRank();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return foundIndex;
  }

  public static void main(String[] args) throws IOException, ParseException {
    int bugID = 39769;
    String repository = "tomcat70";
    String bench = "BLIZZARD";
    String searchQuery =
        "myservlet servlet directory loader destroy shared wrong called loader thread "
            + "context standard system servlet currentthread classloader problem getcontextclassloader "
            + "instance code web method classloader securityutil stream event capture standardcontext instance destroy";
    FaultLocalizationRunner faultLocalizationRunner =
        new FaultLocalizationRunner(bench, repository, bugID, searchQuery);
    System.out.println("First found index:" + faultLocalizationRunner.getFirstRank());
  }
}
