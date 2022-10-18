package results;

import uc.eecs.br.BRLoader;
import uc.eecs.core.FaultLocalizationRunner;
import uc.eecs.core.query.QueryGenerator;
import uc.eecs.nlp.DepManager;
import utils.ContentLoader;
import utils.FileURL;

import java.io.File;
import java.util.ArrayList;

public class ConBugLocResults {
  String benchName;
  String repository;

  public ConBugLocResults() {}

  public ConBugLocResults(String benchName, String repository) {
    this.benchName = benchName;
    this.repository = repository;
  }


  public ArrayList<Integer> rankCollection(ArrayList<String> bugId) {
      ArrayList<Integer> rankList = new ArrayList<>();
      String queryFile = FileURL.queryPath(benchName, repository);
      ArrayList<String> queryList = ContentLoader.getAllLinesOptList(queryFile);
      int index = 0;
      for (String query : queryList){
        FaultLocalizationRunner faultLocalizationRunner =
                new FaultLocalizationRunner(benchName, repository, Integer.parseInt(bugId.get(index)), query);
        rankList.add(faultLocalizationRunner.getFirstRank());
        index ++;
      }

      // System.out.println("First found index:" + );
      return rankList;
  }

  public void rankCollection(String bench, String repository) {
    int bugID = 39769;
    String searchQuery =
        "myservlet servlet directory loader destroy shared wrong called loader thread "
            + "context standard system servlet currentthread classloader problem getcontextclassloader "
            + "instance code web method classloader securityutil stream event capture standardcontext instance destroy";
    FaultLocalizationRunner faultLocalizationRunner =
        new FaultLocalizationRunner(bench, repository, bugID, searchQuery);
    System.out.println("First found index:" + faultLocalizationRunner.getFirstRank());
  }

  public ArrayList<String> queryCollection(ArrayList<String> bugId) {
    ArrayList<String> queryList = new ArrayList<>();
    DepManager depManager = new DepManager();
    for (String id : bugId) {
      System.out.println(id);
      String brFile = FileURL.brPathAppend(benchName, repository, Integer.parseInt(id));
      String title = BRLoader.loadBRTitle(brFile);
      ArrayList<String> bugReportContent = ContentLoader.getAllLinesOptList(brFile);
      QueryGenerator queryGenerator = new QueryGenerator(title, bugReportContent, depManager);
      String query = queryGenerator.generateQuery();
      queryList.add(query);
    }
    return queryList;
  }

  public ArrayList<String> bugIdCollection() {
    String path = FileURL.brPath(benchName, repository);
    ArrayList<String> bugIdList = new ArrayList<>();
    File file = new File(path);
    File[] tempList = file.listFiles();
    assert tempList != null;
    for (File f : tempList) {
      bugIdList.add(f.getName().split("\\.")[0]);
    }
    return bugIdList;
  }

  public void bugIdCollection(String benchName, String repoName) {
    String path = FileURL.brPath(benchName, repoName);
    ArrayList<Integer> bugIdList = new ArrayList<>();
    File file = new File(path);
    File[] tempList = file.listFiles();
    assert tempList != null;
    for (File f : tempList) {
      System.out.println(f.getName().split("\\.")[0]);
    }
  }

  public static void main(String[] args) {
    String benchName = "BLIZZARD";
    String repoName = "ecf";
    ConBugLocResults results = new ConBugLocResults(benchName, repoName);
    ArrayList<String> list = results.bugIdCollection();
//    ArrayList<Integer> rankList = results.rankCollection(list);
//    System.out.println(rankList.toString());
    ArrayList<String> queryList = results.queryCollection(list);
    for (String s : queryList){
      System.out.println(s);
    }
    //System.out.println(list);

  }
}
