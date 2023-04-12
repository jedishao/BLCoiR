package evaluation;

import utils.config.ClassificationID;
import utils.config.DatasetConfig;
import java.util.List;
import java.util.Map;

public class LengthEvaluation {

  public void calculateMRR() {
    int size = ClassificationID.BLIZZARD_PE.size() + ClassificationID.BLIZZARD_STP.size();
    String te = DatasetConfig.BLIZZARD;
    BLCoiREvaluation se = new BLCoiREvaluation();

    //    String path = "Query-Evaluation/" + te + "/" + project + "/BLCoiR.txt";
    int jj = 1;
    while (jj < 41) {
      float p = 0;
      String path = "Query-Length/classification/evaluation/PE/" + te + "/" + jj + ".txt";
      Map<String, List<Integer>> map = se.mapping(path);
      for (String key : map.keySet()) {
        int rank = map.get(key).get(0);
        if (rank < 11) {
          p += (float) 1 / rank;
        }
      }
      System.out.println("MRR: " + p / size);
      jj++;
    }
  }

  public void calculateMAP() {
    int size = ClassificationID.BLIZZARD_NL.size() + ClassificationID.BLIZZARD_STN.size();
    String te = DatasetConfig.BLIZZARD;
    BLCoiREvaluation se = new BLCoiREvaluation();
    int jj = 1;
    while (jj < 41) {
      float p = 0;
      String path = "Query-Length/classification/evaluation/NL/" + te + "/" + jj + ".txt";
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
      System.out.println("MAP: " + p / size);
      jj++;
    }
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
    LengthEvaluation pe = new LengthEvaluation();
    //    pe.calculateMRR1("ecf");
    pe.calculateMRR();
  }
}
