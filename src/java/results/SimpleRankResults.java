package results;

import evaluation.SimpleEvaluation;
import org.apache.lucene.queryparser.classic.ParseException;
import utils.DatasetCollection;
import utils.config.DatasetConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleRankResults {

  public void simpleRank(int len, List<Integer> blizzard, List<Integer> our) throws IOException {
    int j = 0;
    int k = 0;
    int l = 0;
    DatasetCollection dc = new DatasetCollection();
//    ArrayList<String> query_list1;
//    ArrayList<String> query_list2;
//    query_list1 = dc.queryCollect(DatasetConfig.TOMCAT, "_blizzard1_st");
//    query_list2 = dc.queryCollect(DatasetConfig.TOMCAT, "_CG_st");
    for (int i = 0; i < len; i++) {
      int r = blizzard.get(i) - our.get(i);
      if (r > 0) {
        j ++;
//        System.out.println(i + 1);
//        System.out.println(blizzard.get(i) + "------->" + our.get(i));
      } else if (r < 0){
        k++;
//        System.out.println(i + 1);
//        System.out.println(DatasetIndex.TOMCAT_NL[i]);
//        System.out.println(blizzard.get(i) + "------->" + our.get(i));
//        System.out.println(query_list1.get(i));
//        System.out.println(query_list2.get(i));
//        System.out.println();
      } else {
//        System.out.println(i + 1);
//        System.out.println(DatasetIndex.TOMCAT_NL[i]);
//        System.out.println(blizzard.get(i) + "------->" + our.get(i));
//        System.out.println(query_list1.get(i));
//        System.out.println(query_list2.get(i));
//        System.out.println();
        l++;
      }
    }
    System.out.println(j);
    System.out.println(k);
    System.out.println(l);
  }

  public void rankFirst(List<Integer> list){
    int j = 0;
    for (int l : list){
      if (l == 1)
        j++;
    }
    System.out.println(j);
  }

  public static void main(String[] args) throws IOException, ParseException {
    int len = RankResults.BLIZZARD_ECF.size();
    SimpleEvaluation se = new SimpleEvaluation();
//    new SimpleRankResults().simpleRank(len, RankResults.Blizzard1_Tomcat,
//            se.simpleResults(DatasetConfig.TOMCAT, "_test1", "_index_st"));
    new SimpleRankResults().simpleRank(len, RankResults.BLIZZARD_ECF, RankResults.OUR_ECF);
    //new SimpleRankResults().simpleRank(len, RankResults.Bugloc_Tomcat_ST, RankResults.Blizzard_Tomcat_ST);
    System.out.println("====================================================");
    new SimpleRankResults().rankFirst(RankResults.BLIZZARD_ECF);
    new SimpleRankResults().rankFirst(RankResults.OUR_ECF);
    //new SimpleRankResults().rankFirst(se.simpleResults(DatasetConfig.DEBUG, "_CG3", "_index_st"));
  }
}
