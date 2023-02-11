package evaluation;

import org.apache.lucene.queryparser.classic.ParseException;
import org.dom4j.Document;
import org.dom4j.Element;
import utils.ContentLoader;
import utils.XMLParser;
import utils.config.BugReportsID;
import utils.config.ClassificationID;
import utils.config.DatasetConfig;
import utils.config.EvaluationConfig;

import java.io.IOException;
import java.util.*;

public class SimpleEvaluation {

  public void readResults(String path, List<Integer> id, String re) {
    List<String> results = ContentLoader.getAllLinesList(path);
    for (String s1 : results) {
      String s2 = s1.split("\t")[0];
      if (id.contains(Integer.parseInt(s2))) {
        if (Objects.equals(re, "AmaLgam")
            || Objects.equals(re, "BLUiR")
            || Objects.equals(re, "BRTracer")) {
          //            || Objects.equals(re, "BLIA")) {
          System.out.println(s2 + "\t" + (Integer.parseInt(s1.split("\t")[2]) + 1));
        } else {
          System.out.println(s2 + "\t" + (Integer.parseInt(s1.split("\t")[1]) + 1));
        }
      }
    }
  }

  public Map<String, List<String>> getChanges(String path) {
    Map<String, List<String>> map = new HashMap<>();
    XMLParser xp = new XMLParser();
    Document dc = xp.readXml(path);
    Element e = dc.getRootElement();
    Iterator<Element> it = e.elementIterator();
    while (it.hasNext()) {
      Element book = it.next();
      if (!map.containsKey(book.getText().trim())) {
        List<String> list = new ArrayList<>();
        map.put(book.getText().trim(), list);
      }
      for (Element e1 : book.elements()) {
        for (String s1 : e1.getText().split("\n")) {
          if (s1.trim().length() > 2) {
            map.get(book.getText().trim()).add(s1.trim());
          }
        }
      }
    }
    //    for (String key : map.keySet()) {
    //      System.out.println(key + ":" + map.get(key) + map.get(key).size());
    //    }
    return map;
  }

  public void collectResults(String path, List<Integer> id) {
    List<String> results = ContentLoader.getAllLinesList(path);
    //    System.out.println(id.size());
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
    //    System.out.println(re);
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
    int size = DatasetConfig.BENCH4BL_SIZE;
    String te = DatasetConfig.BENCH4BL;
    SimpleEvaluation se = new SimpleEvaluation();
    float p = 0;
    for (String s1 : EvaluationConfig.BENCH4BL) {
      String path =
          "IRBL-Evaluation/" + te + "/" + s1 + "/" + DatasetConfig.BUGLOCATOR + "_" + s1.split("/")[1] + ".txt";
      //      String path = "Query-Evaluation/" + te + "/" + s1 + "/our.txt";
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
    }
    //    String path = "Query-Evaluation/bench4bl/jboss.txt";
    //    Map<String, List<Integer>> map = se.mapping(path);
    //    for (String key : map.keySet()) {
    //      int j = 0;
    //      float k = 0;
    //      List<Integer> rank = map.get(key);
    //      for (int r : rank) {
    //        if (r < 11) {
    //          j++;
    //        }
    //        if (j != 0) {
    //          k += (float) j / r;
    //        }
    //      }
    //      p += k / map.get(key).size();
    //    }
    System.out.println("MAP: " + p / size);
  }

  public void calculateMRR() {
    int size = DatasetConfig.BENCH4BL_SIZE;
    String te = DatasetConfig.BENCH4BL;
    SimpleEvaluation se = new SimpleEvaluation();
    float p = 0;
    for (String s1 : EvaluationConfig.BENCH4BL) {
      String path =
          "IRBL-Evaluation/" + te + "/" + s1 + "/" + DatasetConfig.AMALGAM + "_" + s1.split("/")[1] + ".txt";
      //      String path = "Query-Evaluation/" + te + "/" + s1 + "/our.txt";
      Map<String, List<Integer>> map = se.mapping(path);
      for (String key : map.keySet()) {
        int rank = map.get(key).get(0);
        if (rank < 11) {
          p += (float) 1 / rank;
        }
      }
    }
    //    int size = BugReportsID.VERTX.size();
    //    String path = "Query-Evaluation/bench4bl/jboss.txt";
    //    Map<String, List<Integer>> map = se.mapping(path);
    //    for (String key : map.keySet()) {
    //      int rank = map.get(key).get(0);
    //      if (rank < 11) {
    //        p += (float) 1 / rank;
    //      }
    //    }
    System.out.println("MRR: " + p / size);
  }

  public static void main(String[] args) throws IOException, ParseException {

    String te = DatasetConfig.BENCH4BL;
    int size = DatasetConfig.BENCH4BL_SIZE;
    String path = "IRBL-Evaluation/" + te + "/" + DatasetConfig.BUGLOCATOR + ".txt";
    //    String path = "Query-Evaluation/GitHub/our+.txt";
    //    String path = "Query-Evaluation/" + te + "/our+.txt";
    String change = "dataset/" + te + "/" + "/changeset.xml";
    SimpleEvaluation se = new SimpleEvaluation();
//    se.calculateTop(se.mapping(path), size);
//                se.calculateMRR();
//                se.calculateMAP();
                se.readResults("query/BLUiR_camel.txt", ClassificationID.CAMEL_NL, "BLUiR");
    //    int size = 0;
    //
    //    String da = DatasetConfig.BIRT;
    //    String rePath = "Query-Evaluation/LtR/" + da + "/our+.txt";
    ////    String rePath = "IRBL-Evaluation/LtR/" + da + "/" + DatasetConfig.BRTRACER + "_" + da +
    // ".txt";
    //    Map<String, List<Integer>> map = se.mapping(rePath);
    //    for (String s : map.keySet()) {
    //      System.out.println(s + ":" + map.get(s));
    //    }

    //    for (List<Integer> l : EvaluationConfig.GITHUB_ID) {
    //      size += l.size();
    //    }
    //    System.out.println(size);
    int i = 0;
    //    String s2 = DatasetConfig.VERTX;
    //    String s3 = DatasetConfig.BLUIR;
    //    String path = "IRBL-Evaluation/GitHub/" + s2 + "/" + s3 + "_" + s2 + ".txt";
    //    new SimpleEvaluation().readResults(path, BugReportsID.VERTX, s3);
    // get all results
    //    for (String s4 : EvaluationConfig.BENCH4BL) {
    //      //          String path1 = "Query-Evaluation/LtR/" + s4 + "/our+.txt";
    //      String path1 =
    //          "IRBL-Evaluation/Bench4BL/" + s4 + "/" + DatasetConfig.AMALGAM + "_" +
    // s4.split("/")[1] + ".txt";
    //      new SimpleEvaluation().collectResults(path1, EvaluationConfig.BENCH4BL_ID.get(i));
    //      i++;
    //    }
    //            String path1 = "Query-Evaluation/LtR/" + DatasetConfig.SWT + "/our+.txt";
    //    String da = DatasetConfig.SP_BATCH;
    //    String path1 = "IRBL-Evaluation/Bench4BL/" + da + "/" + DatasetConfig.AMALGAM + "_" +
    // da.split("/")[1] + ".txt";
    //    new SimpleEvaluation().collectResults(path1, BugReportsID.BATCH);
  }
}
