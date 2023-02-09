package evaluation;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

public class ReadOutputFile {
  public void readLineFile(String filename) {
    List<Integer> CAMEL =
        Arrays.asList(
            7973, 7556, 7795, 10024, 7318, 7321, 9319, 9331, 9738, 7715, 8268, 8241, 10229, 3277,
            7161, 9951, 7968, 9199);
    try {
      FileInputStream in = new FileInputStream(filename);
      InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
      BufferedReader bufReader = new BufferedReader(inReader);
      String line;
      int i = 1;
      while ((line = bufReader.readLine()) != null) {
        String[] id = line.split("\t");
        if (CAMEL.contains(Integer.valueOf(id[0]))) {
          System.out.println(line);
        }
        i++;
      }
      bufReader.close();
      inReader.close();
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("read: " + filename + " failed");
    }
  }

  public void readL(String filename) {
    try {
      FileInputStream in = new FileInputStream(filename);
      InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
      BufferedReader bufReader = new BufferedReader(inReader);
      String line;
      int i = 1;
      while ((line = bufReader.readLine()) != null) {
        System.out.println(line);
      }
      bufReader.close();
      inReader.close();
      in.close();
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("read: " + filename + " failed");
    }
  }

  public Map<Integer, List<Integer>> readL1(String filename) {
    try {
      FileInputStream in = new FileInputStream(filename);
      InputStreamReader inReader = new InputStreamReader(in, "UTF-8");
      BufferedReader bufReader = new BufferedReader(inReader);
      Set<Integer> set = new HashSet<>();
      Map<Integer, List<Integer>> map = new HashMap<>();
      String line;
      int i = 1;
      while ((line = bufReader.readLine()) != null) {
        String[] id = line.split("\t");
        if (!map.containsKey(Integer.valueOf(id[0]))) {
          List<Integer> list = new ArrayList<>();
          list.add(Integer.parseInt(id[1]) + 1);
          map.put(Integer.valueOf(id[0]), list);
        } else {
          map.get(Integer.valueOf(id[0])).add(Integer.parseInt(id[1]) + 1);
        }
      }
      bufReader.close();
      inReader.close();
      in.close();
      //      System.out.println(set);
      //      for (Integer i1 : BugReportsID.CAMEL){
      //        if (!set.contains(i1)){
      //          System.out.println(i1);
      //        }
      //      }
      for (int key : map.keySet()) {
        System.out.println(key + ":" + map.get(key));
      }
      return map;
    } catch (Exception e) {
      e.printStackTrace();
      System.out.println("read: " + filename + " failed");
    }
    return null;
  }

  void compute1(Map<Integer, List<Integer>> listMap) {
    int top1 = 0;
    int top5 = 0;
    int top10 = 0;
    int size = 65;
    float MRR = 0;
    for (int k1 : listMap.keySet()) {
      if (listMap.get(k1).get(0) == 1) {
        top1++;
      }
    }

    for (int k2 : listMap.keySet()) {
      if (listMap.get(k2).get(0) < 6) {
        top5++;
      }
    }

    for (int k3 : listMap.keySet()) {
      if (listMap.get(k3).get(0) < 11) {
//        top10++;
        top10 += 1 / (float) listMap.get(k3).get(0);
      }
    }

    for (int k3 : listMap.keySet()) {
      if (listMap.get(k3).get(0) < 11) {
//        top10++;
        MRR += 1 / (float) listMap.get(k3).get(0);
      }
    }

    System.out.println("Top1:" + (float) top1 / size);
    System.out.println("Top5:" + (float) top5 / size);
    System.out.println("Top10:" + (float) top10 / size);
    System.out.println("MRR:" + MRR / size);
  }

  public static void main(String[] args) {
    ReadOutputFile rof = new ReadOutputFile();
    //    for (String s : DatasetVersion.CAMEL) {
    //      rof.readL("evaluation/BugLocator/Bench4BL/Apache/CAMEL/results/CAMEL_" + s + ".txt");
    //    }
    rof.compute1(rof.readL1("evaluation/BugLocator/Bench4BL/Apache/CAMEL/results/ALL.txt"));
    //    rof.readL1("evaluation/BugLocator/Bench4BL/Apache/CAMEL/results/ALL.txt");
    //    System.out.println(BugReportsID.CAMEL.size());
  }
}
