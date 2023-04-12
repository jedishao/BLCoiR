package evaluation;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import utils.config.BugReportsID;
import utils.config.ClassificationID;
import utils.config.DatasetConfig;
import utils.config.EvaluationConfig;

public class ProjectEvaluation {

  public void calculateMRR() {
    String te = DatasetConfig.BLIZZARD;
    int i = 0;
    for (String project : EvaluationConfig.BLIZZARD) {
      //      String path =
      //          "IRBL-Evaluation/"
      //              + te
      //              + "/"
      //              + project
      //              + "/"
      //              + DatasetConfig.BLIA
      //              + "_"
      //              + project
      //              + ".txt";
      String path = "Query-Evaluation/" + te + "/" + project + "/our+.txt";
      MRR(path, EvaluationConfig.BLIZZARD_ID.get(i).size());
      i++;
    }
  }

  public void MRR(String path, int size) {
    BLCoiREvaluation se = new BLCoiREvaluation();
    float p = 0;
    Map<String, List<Integer>> map = se.mapping(path);
    for (String key : map.keySet()) {
      int rank = map.get(key).get(0);
      if (rank < 11) {
        p += (float) 1 / rank;
      }
    }
    System.out.println(p / size);
  }

  public void calculateMAP() {
    String te = DatasetConfig.BLIZZARD;
    int i = 0;
    for (String project : EvaluationConfig.BLIZZARD) {
      //      String path =
      //          "IRBL-Evaluation/"
      //              + te
      //              + "/"
      //              + project
      //              + "/"
      //              + DatasetConfig.BLIZZARD
      //              + "_"
      //              + project
      //              + ".txt";
      String path = "Query-Evaluation/" + te + "/" + project + "/our.txt";
      MAP(path, EvaluationConfig.BLIZZARD_ID.get(i).size());
      i++;
    }
  }

  public void MAP(String path, int size) {
    BLCoiREvaluation se = new BLCoiREvaluation();
    float p = 0;
    Map<String, List<Integer>> map = se.mapping(path);
    for (String key : map.keySet()) {
      int j = 0;
      float k = 0;
      List<Integer> rank = map.get(key);
      for (int r : rank) {
        if (r < 11) {
          j++;
        }
        if (j != 0) {
          k += (float) j / r;
        }
      }
      p += k / map.get(key).size();
    }
    System.out.println(p / size);
  }

  public void calculateMRR1(String project) {
    int size = 18;
    String te = DatasetConfig.BLIZZARD;
    BLCoiREvaluation se = new BLCoiREvaluation();
    float p = 0;
    //    String path = "Query-Evaluation/" + te + "/" + project + "/BLCoiR.txt";
    String path = "Query-Evaluation/" + te + "/" + project + "/BLCoiR.txt";
    Map<String, List<Integer>> map = se.mapping(path);
    for (String key : map.keySet()) {
      int rank = map.get(key).get(0);
      if (rank < 11) {
        p += (float) 1 / rank;
      }
    }
    System.out.println("MRR: " + p / size);
  }

  public static void main(String[] args) {
    ProjectEvaluation pe = new ProjectEvaluation();
    //    pe.calculateMRR1("ecf");
    pe.calculateMAP();
  }
}
