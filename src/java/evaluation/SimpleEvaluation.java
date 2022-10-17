package evaluation;

import org.apache.lucene.queryparser.classic.ParseException;
import uc.eecs.core.FaultLocalizationRunner;
import utils.DatasetCollection;
import utils.config.DatasetConfig;

import java.io.IOException;
import java.util.ArrayList;

public class SimpleEvaluation {

  public ArrayList<Integer> simpleResults(String rep, String name, String index_name) throws IOException, ParseException {
    DatasetCollection dc = new DatasetCollection();
    ArrayList<Integer> tmp = new ArrayList<>();
    ArrayList<Integer> bugID_list;
    ArrayList<String> query_list;
    bugID_list = dc.bugIDCollect(rep, index_name);
    query_list = dc.queryCollect(rep, name);
    for (int i = 0; i < bugID_list.size(); i++) {
      int bugID = bugID_list.get(i);
      String searchQuery = query_list.get(i);
      //System.out.println(searchQuery);
//      FaultLocalizationRunner searcher =
//              new FaultLocalizationRunner(bugID, "ss", rep, searchQuery);
//      tmp.add(searcher.getFirstGoldRankClass());
    }
    return tmp;
  }

  public static void main(String[] args) throws IOException, ParseException {
    ArrayList<Integer> tmp = new SimpleEvaluation().simpleResults(DatasetConfig.TOMCAT, "_noun", "_index");
    System.out.println(tmp);
  }
}
