package utils;

import utils.config.DatasetConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class ResUtils {

  public static List<Integer> getId(String rep, String project) {
    String brPath = DatasetConfig.DATASET_DIR + rep + "/" + project + "/BR/";
    File dir = new File(brPath);
    File[] br = dir.listFiles();
    assert br != null;
    List<Integer> fn = new ArrayList<>();
    for (File f : br) {
      String[] ff = f.getName().split("\\.");
      if (!Objects.equals(ff[0], "")) {
        fn.add(Integer.parseInt(ff[0]));
      }
    }
    Collections.sort(fn);
    return fn;
  }

  public static List<Integer> getId(String rep, String project, String subProject) {
    String brPath = DatasetConfig.DATASET_DIR + rep + "/" + project + "/" + subProject + "/BR/";
    File dir = new File(brPath);
    File[] br = dir.listFiles();
    assert br != null;
    List<Integer> fn = new ArrayList<>();
    for (File f : br) {
      String[] ff = f.getName().split("\\.");
      if (!Objects.equals(ff[0], "")) {
        fn.add(Integer.parseInt(ff[0]));
      }
    }
    Collections.sort(fn);
    return fn;
  }
}
