package evaluation;

import uc.eecs.core.FaultLocalizationRunner;
import utils.ContentLoader;
import utils.config.BugReportsID;
import utils.config.ClassificationID;
import utils.config.DatasetConfig;
import utils.config.EvaluationConfig;

import java.util.ArrayList;
import java.util.List;

public class QueryEvaluation {
  public void queryResults() {
    String bench = DatasetConfig.BENCH4BL;
    String repository = DatasetConfig.A_HBASE;
    List<String> our_ =
            ContentLoader.getAllLinesList("query/" + bench + "/" + repository + "/baseline_entity.txt");
    int index = 0;
    for (int id : BugReportsID.HBASE) {
      FaultLocalizationRunner fr1 =
          new FaultLocalizationRunner(bench, repository, id, our_.get(index).split("\t")[1]);
      for (int r : fr1.getGoldFileIndicesClass()) {
        System.out.println(id + "\t" + r);
      }
      index ++;
    }
//    index = 0;
//    System.out.println("acer_st");
//    for (int id : BugReportsID.JDT_UI) {
//      FaultLocalizationRunner fr2 =
//          new FaultLocalizationRunner(bench, repository, id, acer_st.get(index).split("\t")[1]);
//      for (int r : fr2.getGoldFileIndicesClass()) {
//        System.out.println(id + "\t" + r);
//      }
//      index ++;
//    }
//    index = 0;
//    System.out.println("baseline_nl");
//    for (int id : BugReportsID.JDT_UI) {
//      FaultLocalizationRunner fr3 =
//          new FaultLocalizationRunner(bench, repository, id, baseline_nl.get(index).split("\t")[1]);
//      for (int r : fr3.getGoldFileIndicesClass()) {
//        System.out.println(id + "\t" + r);
//      }
//      index ++;
//    }
//    index = 0;
//    System.out.println("baseline_nlst");
//    for (int id : BugReportsID.JDT_UI) {
//      FaultLocalizationRunner fr4 =
//          new FaultLocalizationRunner(
//              bench, repository, id, baseline_nlst.get(index).split("\t")[1]);
//      for (int r : fr4.getGoldFileIndicesClass()) {
//        System.out.println(id + "\t" + r);
//      }
//      index ++;
//    }
//    index = 0;
//    System.out.println("baseline_title");
//    for (int id : BugReportsID.JDT_UI) {
//      FaultLocalizationRunner fr5 =
//          new FaultLocalizationRunner(
//              bench, repository, id, baseline_title.get(index).split("\t")[1]);
//      for (int r : fr5.getGoldFileIndicesClass()) {
//        System.out.println(id + "\t" + r);
//      }
//      index ++;
//    }
//    index = 0;
//    System.out.println("our");
//    for (int id : BugReportsID.JDT_UI) {
//      FaultLocalizationRunner fr6 =
//          new FaultLocalizationRunner(bench, repository, id, our.get(index).split("\t")[1]);
//      for (int r : fr6.getGoldFileIndicesClass()) {
//        System.out.println(id + "\t" + r);
//      }
//      index ++;
//    }
  }

  public static void main(String[] args) {
    new QueryEvaluation().queryResults();
    //
  }
}
