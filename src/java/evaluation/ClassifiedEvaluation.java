package evaluation;


import utils.ContentLoader;

import utils.MiscUtility;
import utils.config.DatasetConfig;
import utils.config.EvaluationConfig;

import java.util.*;
import static utils.config.ClassificationID.*;

public class ClassifiedEvaluation {

  public void collectResults(String path, List<Integer> id) {
    List<String> results = ContentLoader.getAllLinesList(path);
    for (String s1 : results) {
      if (s1.split("\t").length == 2) {
        String s2 = s1.split("\t")[0];
        if (id.contains(Integer.parseInt(s2))) {
          System.out.println(s2 + "\t" + Integer.parseInt(s1.split("\t")[1]));
        }
      }
    }
  }

  public Map<String, List<Integer>> mapping(String path) {
    List<String> results = ContentLoader.getAllLinesList(path);
    Map<String, List<Integer>> re = new HashMap<>();
    for (String s1 : results) {
      if (s1.split("\t").length > 1) {
        String s2 = s1.split("\t")[0];
        if (!re.containsKey(s2)) {
          List<Integer> list = new ArrayList<>();
          list.add(Integer.parseInt(s1.split("\t")[1]));
          re.put(s2, list);
        } else {
          if (!re.get(s2).contains(Integer.parseInt(s1.split("\t")[1]))) {
            re.get(s2).add(Integer.parseInt(s1.split("\t")[1]));
          }
        }
      }
    }
    for (String key : re.keySet()) {
      Collections.sort(re.get(key));
    }
    return re;
  }

  public Map<String, List<Integer>> mapping(String path, List<Integer> id) {
    List<String> results = ContentLoader.getAllLinesList(path);
    Map<String, List<Integer>> re = new HashMap<>();
    for (String s1 : results) {
      if (s1.split("\t").length > 1) {
        String s2 = s1.split("\t")[0];
        if (id.contains(Integer.parseInt(s2))) {
          if (!re.containsKey(s2)) {
            List<Integer> list = new ArrayList<>();
            list.add(Integer.parseInt(s1.split("\t")[1]));
            re.put(s2, list);
          } else {
            if (!re.get(s2).contains(Integer.parseInt(s1.split("\t")[1]))) {
              re.get(s2).add(Integer.parseInt(s1.split("\t")[1]));
            }
          }
        }
      }
    }
    for (String key : re.keySet()) {
      Collections.sort(re.get(key));
    }
    return re;
  }

  public void calculateTop(Map<String, List<Integer>> map, int size) {
    float top1 = 0;
    float top5 = 0;
    float top10 = 0;
    System.out.println(size);
    int a = 0;
    for (String key : map.keySet()) {
      a++;
      int rank = map.get(key).get(0);
      if (rank == 1) {
        top1++;
      } else if (rank < 6) {
        top5++;
      } else if (rank < 11) {
        top10++;
      }
    }
    System.out.println(a);
    System.out.println("Top 1: " + top1 / size);
    System.out.println("Top 5: " + (top5 + top1) / size);
    System.out.println("Top 10: " + (top10 + top5 + top1) / size);
  }

  public void calculateMAP() {
    int size = DatasetConfig.STN_SIZE;
    ClassifiedEvaluation ce = new ClassifiedEvaluation();
    float p = 0;
    //    String path = "IRBL-Evaluation/All/" + DatasetConfig.BLIZZARD + "_STP.txt";
    String path = "Query-Evaluation/All/our+_STN1.txt";
    Map<String, List<Integer>> map = ce.mapping(path);
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
  }

  public void calculateMRR() {
    int size = DatasetConfig.STN_SIZE;
    ClassifiedEvaluation ce = new ClassifiedEvaluation();
    float p = 0;
    //    String path = "IRBL-Evaluation/All/" + DatasetConfig.BLIZZARD+ "_STP.txt";
    String path = "Query-Evaluation/All/our+_STN1.txt";
    Map<String, List<Integer>> map = ce.mapping(path);
    for (String key : map.keySet()) {
      int rank = map.get(key).get(0);
      if (rank < 11) {
        p += (float) 1 / rank;
      }
    }
    System.out.println("MRR: " + p / size);
  }

  public static void main(String[] args) {
    ClassifiedEvaluation ce = new ClassifiedEvaluation();
    //    String te = DatasetConfig.LTR;
    //    String path = "Query-Evaluation/All/our_PE.txt";
    ce.calculateMAP();
    int i = 0;
    //    ce.collectResults(path, ClassificationID.LTR_PE);
//        for (String s : EvaluationConfig.DATA) {
//          String path1 = "Query-Evaluation/" + s + "/our_NL.txt";
//          ce.collectResults(path1, EvaluationConfig.STP_ID.get(i));
//          i++;
//        }
    String path1 = "Query-Evaluation/All/our+_st.txt";
    String path2 = "IRBL-Evaluation/All/" + DatasetConfig.BLIA + "_ST.txt";
    String path3 = "Query-Evaluation/All/our_st.txt";
    //    String path2 = "query/BLUiR_tomcat.txt";
    Map<String, List<Integer>> l1 = ce.mapping(path1);
    Map<String, List<Integer>> l2 = ce.mapping(path2);
//    Map<String, List<Integer>> l3 = ce.mapping(path3);

    List<Integer> list1 = CAMEL_STP;
//    for (String s : l1.keySet()) {
//      if (list1.contains(Integer.parseInt(s)))
//        System.out.println(s+":"+l1.get(s));
//    }
//    System.out.println("=====================================");
//    for (String s : l2.keySet()) {
//      if (list1.contains(Integer.parseInt(s)))
//        System.out.println(s+":"+l2.get(s));
//    }
    String p = "Query-Evaluation/All/our+_ST.txt";
//    List<String> ll = ContentLoader.getAllLinesList(p);
//    for (String s : ll) {
//      if (BENCH4BL_STN.contains(Integer.parseInt(s.split("\t")[0])))
//        System.out.println(s);
//    }

    String path = "Query-Evaluation/All/our+_STN1.txt";
//    ce.calculateTop(ce.mapping(path), DatasetConfig.STN_SIZE);
  }
}
