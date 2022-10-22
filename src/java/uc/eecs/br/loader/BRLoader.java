package uc.eecs.br.loader;

import utils.ContentLoader;
import utils.FileURL;
import utils.MiscUtility;
import utils.config.DatasetConfig;

import java.util.ArrayList;

public class BRLoader {
  public static String loadBugReport(String benchName, int bugID) {
    String brFile = DatasetConfig.HOME_DIR + benchName + "/" + bugID + ".txt";
    return ContentLoader.loadFileContent(brFile);
  }

  public static String loadBugReport(String benchName, String repoName, int bugID) {
    String brFile = FileURL.brPathAppend(benchName, repoName, bugID);
    return ContentLoader.loadFileContent(brFile);
  }

  public static String loadBugReport(String brFile) {
    return ContentLoader.loadFileContent(brFile);
  }

  public static ArrayList<String> loadBugReportList(String brFile) {
    return ContentLoader.getAllLinesOptList(brFile);
  }

  public static String loadBugContent(String brFile) {
    ArrayList<String> lines = ContentLoader.getAllLinesOptList(brFile);
    if (lines.isEmpty()) {
      return "";
    }
    lines.remove(0);
    return MiscUtility.list2Str(lines);
  }

  public static ArrayList<String> loadBugContentList(String brFile) {
    ArrayList<String> lines = ContentLoader.getAllLinesOptList(brFile);
    return lines;
  }
  public static String loadBugContent(ArrayList<String> lines) {
    if (lines.isEmpty()) {
      return "";
    }
    lines.remove(0);
    return MiscUtility.list2Str(lines);
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
