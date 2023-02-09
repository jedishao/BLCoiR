package evaluation;

import org.dom4j.Document;
import org.dom4j.Element;
import utils.ContentLoader;
import utils.XMLParser;
import utils.config.ClassificationID;
import utils.config.DatasetConfig;
import utils.config.EvaluationConfig;

import java.util.*;

public class ClassifiedEvaluation {

  public void collectResults(String path, List<Integer> id) {
    ArrayList<String> results = ContentLoader.getAllLinesList(path);
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
    ArrayList<String> results = ContentLoader.getAllLinesList(path);
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
    ArrayList<String> results = ContentLoader.getAllLinesList(path);
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
    int size = DatasetConfig.NL_SIZE;
    ClassifiedEvaluation ce = new ClassifiedEvaluation();
    float p = 0;
//    String path = "IRBL-Evaluation/All/" + DatasetConfig.BUGLOCATOR + "_ST.txt";
    String path = "Query-Evaluation/All/our_NL.txt";
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
    int size = DatasetConfig.NL_SIZE;
    ClassifiedEvaluation ce = new ClassifiedEvaluation();
    float p = 0;
//    String path = "IRBL-Evaluation/All/" + DatasetConfig.BLIZZARD + "_ST.txt";
    String path = "Query-Evaluation/All/our_NL.txt";
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
//    ce.calculateMAP();
    int i = 0;
    //    ce.collectResults(path, ClassificationID.LTR_PE);
    for (String s : EvaluationConfig.DATA) {
      String path1 = "IRBL-Evaluation/" + s + "/"+ DatasetConfig.BUGLOCATOR + "_ST.txt";
      ce.collectResults(path1, EvaluationConfig.STP_ID.get(i));
      i++;
    }
    //    ce.calculateTop(ce.mapping(path), DatasetConfig.PE_SIZE);
  }
}
