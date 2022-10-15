package uc.eecs.br;

import utils.ContentLoader;
import utils.config.DatasetConfig;

import java.util.ArrayList;

public class BRLoader {
  public static String loadBugReport(String benchName, int bugID) {
    String brFile = DatasetConfig.HOME_DIR + benchName + "/" + bugID + ".txt";
    return ContentLoader.loadFileContent(brFile);
  }

  public static String loadBugReport(String benchName, String repoName, int bugID) {
    String brFile = DatasetConfig.HOME_DIR + benchName + "/" + repoName + "/" + bugID + ".txt";
    return ContentLoader.loadFileContent(brFile);
  }

  public static String loadBugReport(String brFile) {
    return ContentLoader.loadFileContent(brFile);
  }

  public static String loadBRTitle(String repoName, int bugID) {
    String brFile =
        DatasetConfig.HOME_DIR + "/src/dataset/BR-Raw/" + repoName + "/" + bugID + ".txt";
    ArrayList<String> lines = ContentLoader.getAllLinesOptList(brFile);
    if (lines.isEmpty()) {
      return "";
    }
    return lines.get(0);
  }

  public static String loadBRTitle(String benchName, String repoName, int bugID) {
    String brFile = DatasetConfig.HOME_DIR + benchName + "/" + repoName + "/" + bugID + ".txt";
    ArrayList<String> lines = ContentLoader.getAllLinesOptList(brFile);
    if (lines.isEmpty()) {
      return "";
    }
    return lines.get(0);
  }

  public static String loadBRTitle(String brFile) {
    ArrayList<String> lines = ContentLoader.getAllLinesOptList(brFile);
    if (lines.isEmpty()) {
      return "";
    }
    return lines.get(0);
  }
}
