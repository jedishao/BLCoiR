package evaluation;

import org.apache.bcel.generic.FADD;
import org.apache.xpath.operations.Lt;
import org.jheaps.monotone.BigIntegerRadixHeap;
import utils.ContentLoader;

import utils.MiscUtility;
import utils.config.BugReportsID;
import utils.config.DatasetConfig;
import utils.config.EvaluationConfig;

import java.io.FileWriter;
import java.io.IOException;
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
    System.out.println("Top 1: " + top1);
    System.out.println("Top 5: " + (top5 + top1));
    System.out.println("Top 10: " + (top10 + top5 + top1));
    System.out.println("Top 1: " + top1 / size);
    System.out.println("Top 5: " + (top5 + top1) / size);
    System.out.println("Top 10: " + (top10 + top5 + top1) / size);
  }

  public void calculateMAP() {
    int size = DatasetConfig.NL_SIZE;
    ClassifiedEvaluation ce = new ClassifiedEvaluation();
    //    String path = "IRBL-Evaluation/All/" + DatasetConfig.BLIZZARD + "_STP.txt";
    String path = "Query-Evaluation/All/BLCoiR_nl.txt";
    Map<String, List<Integer>> map = ce.mapping(path);
    int index = 1;
//    while (index < 16) {
      float p = 0;
      for (String key : map.keySet()) {
        int j = 0;
        float k = 0;
        List<Integer> rank = map.get(key);
        for (int r : rank) {
          if (r <= 10) {
            j++;
          }
          if (j != 0) {
            k += (float) j / r;
          }
        }
        p += k / map.get(key).size();
      }
      System.out.println(p / size);
      index++;
//    }
  }

  public void calculateMRR() {
    int size = DatasetConfig.STP_SIZE;
    ClassifiedEvaluation ce = new ClassifiedEvaluation();
    //    String path = "IRBL-Evaluation/All/" + DatasetConfig.BLIZZARD+ "_STP.txt";
    String path = "Query-Evaluation/All/BLCoiR_stp.txt";
    Map<String, List<Integer>> map = ce.mapping(path);
    //    for (String key : map.keySet()) {
    //      int rank = map.get(key).get(0);
    //      if (rank <= 10) {
    //        p += (float) 1 / rank;
    //      }
    //    }
    //    System.out.println("MRR: " + p / size);
    int index = 1;
//    while (index < 16) {
      float p = 0;
      for (String key : map.keySet()) {
        int rank = map.get(key).get(0);
        if (rank <= 10) {
          p += (float) 1 / rank;
        }
      }
      System.out.println(p / size);
      index++;

  }

  public static void main(String[] args) throws IOException {
    ClassifiedEvaluation ce = new ClassifiedEvaluation();
    //    String te = DatasetConfig.LTR;
    //    String path = "Query-Evaluation/All/our_PE.txt";
            ce.calculateMRR();
//    ce.calculateMAP();
    int i = 0;
    //    ce.collectResults(path, ClassificationID.LTR_PE);
    //        for (String s : EvaluationConfig.DATA) {
    //          String path1 = "Query-Evaluation/" + s + "/our_NL.txt";
    //          ce.collectResults(path1, EvaluationConfig.STP_ID.get(i));
    //          i++;
    //        }
    String path1 = "Query-Evaluation/All/our+_ST1.txt";
    String path2 = "IRBL-Evaluation/All/" + DatasetConfig.BLIA + "_ST.txt";
    String path3 = "Query-Evaluation/All/our_.txt";
    String path4 = "Query-Evaluation/" + DatasetConfig.BLIZZARD + "/gpt-3.5.txt";
    //    String path5 = "Query-Evaluation/" + DatasetConfig.BENCH4BL + "/our_PE1.txt";
    //    String path2 = "query/BLUiR_tomcat.txt";
    Map<String, List<Integer>> l1 = ce.mapping(path4);
    //    Map<String, List<Integer>> l2 = ce.mapping(path5);
    //    System.out.println(l1.keySet().size());
    //    Map<String, List<Integer>> l3 = ce.smapping(path3);
    //    for (String s : l1.keySet()){
    //      System.out.println(s+":"+l1.get(s));
    //    }
    //    for (String s1 : l1.keySet()) {
    //      System.out.println(s1 );
    //      if (l1.get(s1).size() != l2.get(s1).size()){
    //        System.out.println(s1);
    //      }
    //    }
    List<String> c1 = ContentLoader.getAllLinesList(path4);
    //    List<String> c2 = ContentLoader.getAllLinesList(path5);
    List<String> c3 = new ArrayList<>();

    //    for (String ss: c1){
    //      if (! c3.contains(ss)){
    //        c3.add(ss);
    //      }else {
    //        System.out.println(ss);
    //      }
    //    }
    //    List<Integer> list1 = BugReportsID.WFLY;
    //    List<Integer> list1 = BLIZZARD_PE;
    //    for (String s : l1.keySet()) {
    //      if (list1.contains(Integer.parseInt(s))) {
    //        for (int rank : l1.get(s)) {
    //          System.out.println(s + "\t" + rank);
    //        }
    //      }
    //    }
    //    for (int i1 : list1){
    //      int j = 0;
    //      for (int i2 : list1){
    //        if (i1 == i2){
    //          j ++;
    //        }
    //      }
    //      if (j > 1){
    //        System.out.println(i1);
    //      }
    //    }
    //    System.out.println("=====================================");
    //    for (String s : l2.keySet()) {
    //      if (list1.contains(Integer.parseInt(s))) System.out.println(s + ":" + l2.get(s));
    //    }
    //    for (String repository : EvaluationConfig.LTR) {
    //        String p = "query/" + DatasetConfig.LTR + "/" + repository + "/BLCoiR.txt";
    int length = 40;
    //    String te = DatasetConfig.BLIZZARD;
//    String p = "Query-Evaluation/" + DatasetConfig.BENCH4BL + "/BLCoiR.txt";
//    //    FileWriter fileWriter = new FileWriter(path, false);
//    //    String p = "Query-Length/evaluation/" + te + "/" + length + ".txt";
//    List<String> ll = ContentLoader.getAllLinesList(p);
//    for (String s : ll) {
//      if (BENCH4BL_NL.contains(Integer.parseInt(s.split("\t")[0]))) {
//        System.out.println(s);
//      }
//    }
    //        fileWriter.write(s);
    //        fileWriter.write("\n");
    //        fileWriter.flush();
    //    fileWriter.close();
    //        String path = "Query-Evaluation/"+DatasetConfig.BENCH4BL+"/our_final.txt";
    String path = "Query-Evaluation/All/blcoir_STN.txt";
    //        ce.calculateTop(ce.mapping(path), DatasetConfig.STN_SIZE + DatasetConfig.NL_SIZE);
//    ce.calculateTop(ce.mapping(path), DatasetConfig.STN_SIZE);
  }
}
