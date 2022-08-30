package evaluation;

import org.apache.lucene.queryparser.classic.ParseException;
import uc.eecs.core.FaultLocalizationRunner;
import utils.DatasetCollection;
import utils.config.DatasetConfig;

import java.io.IOException;
import java.util.ArrayList;

public class SimpleEvaluation {

  public ArrayList<Integer> ours(DatasetCollection dc, String rep) throws IOException, ParseException {
    ArrayList<Integer> tmp = new ArrayList<>();
    ArrayList<Integer> bugID_list;
    ArrayList<String> query_list;
    bugID_list = dc.bugIDCollect(rep);
    query_list = dc.queryCollect(rep);
    for (int i = 0; i < bugID_list.size(); i++) {
      int bugID = bugID_list.get(i);
      String searchQuery = query_list.get(i);
      FaultLocalizationRunner searcher =
              new FaultLocalizationRunner(bugID, rep, searchQuery);
      //System.out.println("First found index:" + searcher.getFirstGoldRankClass());
      tmp.add(searcher.getFirstGoldRankClass());
    }
    return tmp;
  }

  public static void main(String[] args) throws IOException, ParseException {
    DatasetCollection dc = new DatasetCollection();
    ArrayList<Integer> tmp = new ArrayList<>();
    ArrayList<Integer> bugID_list;
    ArrayList<String> query_list;
    bugID_list = dc.bugIDCollect(DatasetConfig.CORE);
    query_list = dc.queryCollect(DatasetConfig.CORE);
    for (int i = 0; i < bugID_list.size(); i++) {
      int bugID = bugID_list.get(i);
      String searchQuery = query_list.get(i);
      System.out.println(searchQuery);
      FaultLocalizationRunner searcher =
          new FaultLocalizationRunner(bugID, DatasetConfig.CORE, searchQuery);
      //System.out.println("First found index:" + searcher.getFirstGoldRankClass());
      tmp.add(searcher.getFirstGoldRankClass());
    }
    System.out.println(tmp);
  }
}
