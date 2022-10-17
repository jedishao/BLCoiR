package results;

import org.apache.lucene.queryparser.classic.ParseException;
import uc.eecs.core.FaultLocalizationRunner;
import utils.DatasetCollection;
import utils.config.DatasetConfig;

import java.io.IOException;
import java.util.ArrayList;

public class BlizzardResults {
    public void blizzardResults(String rep, String name, String index_name) throws IOException, ParseException {
        DatasetCollection dc = new DatasetCollection();
        ArrayList<Integer> tmp = new ArrayList<>();
        ArrayList<Integer> bugID_list;
        ArrayList<String> query_list;
        bugID_list = dc.bugIDCollect(rep, index_name);
        query_list = dc.queryCollect(rep, name);
        for (int i = 0; i < bugID_list.size(); i++) {
            int bugID = bugID_list.get(i);
            String searchQuery = query_list.get(i);
            System.out.println(searchQuery);
//            FaultLocalizationRunner searcher =
//                    new FaultLocalizationRunner(bugID, "ss",rep, searchQuery);
//            //System.out.println("First found index:" + searcher.getFirstGoldRankClass());
//            tmp.add(searcher.getFirstGoldRankClass());
        }
        System.out.println(tmp);
    }
  public static void main(String[] args) throws IOException, ParseException {
      String origin_name = "_blizzard_st";
      String new_name = "_blizzard2";
      new BlizzardResults().blizzardResults(DatasetConfig.DEBUG, origin_name, "_index_st");
      //new BlizzardResults().blizzardResults(DatasetConfig.TOMCAT, new_name);
  }
}
