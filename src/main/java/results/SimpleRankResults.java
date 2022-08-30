package results;

import evaluation.SimpleEvaluation;
import org.apache.lucene.queryparser.classic.ParseException;
import utils.DatasetCollection;
import utils.config.DatasetConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleRankResults {
  static List<Integer> Blizzard_Tomcat =
      Arrays.asList(
          2, 1, 3, 1, 3, 1, 1, 1, 2, 1, 2, 3, 7, 1, 2, 1, 4, 1, 5, 1, 2, 1, 1, 15, 22, 1, 1, 5, 4,
          2, 1, 2, 1, 1, 14, 1, 1, 2, 1, 1, 1, 3, 8, 2, 59, 1, 2, 2, 4, 9, 2, 4, 1, 1, 13, 14, 2, 1,
          10, 1, 1, 3, 1, 1, 1, 2, 1, 1, 1, 36, 2, 5, 1, 1, 1, 1, 1, 1, 2, 65, 1, 33, 13, 4, 1, 1,
          40, 3, 1, 1, 1, 1, 1, 2, 3, 1, 1, 2, 1, 2, 1, 1, 1, 3, 1, 1, 1, 2, 1, 265, 2, 1, 5, 1, 1,
          1, 1, 4, 1, 1, 27, 1, 1, 1, 2, 5, 2, 1, 3);
  static List<Integer> Blizzard_Ecf =
      Arrays.asList(
          3, 2, 2, 2, 1, 1, 2, 5, 1, 2, 1, 29, 6, 1, 37, 461, 1, 1, 13, 11, 1, 1, 1, 1, 1, 1, 2, 1,
          2, 40, 1, 2, 2, 473, 508, 5, 104, 2);
  static List<Integer> Blizzard_CORE =
      Arrays.asList(
          2, 1, 16, 2, 116, 1, 1, 5, 4, 4, 1, 14, 2, 1, 3, 1, 1, 1, 1, 4, 1, 9, 1, 1, 1, 17, 1, 1,
          33, 2, 3, 16, 131, 8);

  public static void main(String[] args) throws IOException, ParseException {
    int len = Blizzard_CORE.size();
    SimpleEvaluation se = new SimpleEvaluation();
    DatasetCollection dc = new DatasetCollection();
    ArrayList<Integer> ours = se.ours(dc, DatasetConfig.CORE);
    System.out.println(len);
    int j = 0, k = 0;
    for (int i = 0; i < len; i++) {
      int r = Blizzard_CORE.get(i) - ours.get(i);
      if (r > 0) {
        j += 1;
        System.out.println(i + 1);
        System.out.println(Blizzard_CORE.get(i) + "------->" + ours.get(i));
      } else {
        k++;
      }
    }
    System.out.println(j);
    System.out.println(k);
  }
}
