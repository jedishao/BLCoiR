package uc.eecs.core.process;

import utils.config.BugReportsID;
import uc.eecs.core.FaultLocalizationRunner;
import utils.ContentLoader;
import utils.config.ClassificationID;
import utils.config.DatasetConfig;

import java.util.ArrayList;
import java.util.List;

public class ResultTest {
  public void results(List<Integer> l, String repository) {
    String bench = DatasetConfig.BENCH4BL;
    List<String> our =
        ContentLoader.getAllLinesList("query/" + bench + "/" + repository + "/our.txt");
    List<String> blizzard =
        ContentLoader.getAllLinesList("query/" + bench + "/" + repository + "/blizzard.txt");
    ArrayList<Integer> o = new ArrayList<>();
    ArrayList<Integer> b = new ArrayList<>();
    int index = 0;
    for (int id : l) {
      FaultLocalizationRunner fr =
          new FaultLocalizationRunner(bench, repository, id, our.get(index).split("\t")[1]);
      o.add(fr.getFirstRank());
      FaultLocalizationRunner fr1 =
          new FaultLocalizationRunner(bench, repository, id, blizzard.get(index).split("\t")[1]);
      b.add(fr1.getFirstRank());
      index++;
    }
    int k = 0;
    int kk = 0;
    for (int j = 0; j < b.size(); j++) {
//      System.out.println((j + 1) + "-->" + l.get(j) + "----->" + o.get(j) + "----->" + b.get(j));
      if (o.get(j) - b.get(j) > 0) {
//        System.out.println(l.get(j) + "-->" + (j + 1) + "----->" + o.get(j) + "----->" + b.get(j));
        k++;
      } else if (o.get(j) - b.get(j) < 0) {
        System.out.println(l.get(j) + "-->" + (j + 1) + "----->" + o.get(j) + "----->" + b.get(j));
        kk++;
      }
    }
    float k1 = 0;
    float k2 = 0;
    for (int j = 0; j < b.size(); j++) {
      if (o.get(j) <= 10 && o.get(j) > 0) {
        //        k1 += 1;
        k1 += 1 / (float) o.get(j);
      }
      if (b.get(j) <= 10 && b.get(j) > 0) {
        //        k2 += 1;
        k2 += 1 / (float) b.get(j);
      }
    }
    System.out.println("bad:" + k);
    System.out.println("good:" + kk);
    System.out.println(o);
    System.out.println(b);
    System.out.println(k1);
    System.out.println(k2);
  }

  public static void main(String[] args) {
    new ResultTest().results(BugReportsID.CAMEL, DatasetConfig.A_CAMEL);
  }
}
