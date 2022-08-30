package utils;

import utils.config.DatasetConfig;

import java.util.ArrayList;

public class BRLoader {
  public static String loadBugReport(String repoName, int bugID) {
    String brFile = DatasetConfig.HOME_DIR + "/BR-Raw/" + repoName + "/" + bugID + ".txt";
    return ContentLoader.loadFileContent(brFile);
  }

  public static String loadBRTitle(String repoName, int bugID) {
    String brFile = DatasetConfig.HOME_DIR + "/BR-Raw/" + repoName + "/" + bugID + ".txt";
    ArrayList<String> lines = ContentLoader.getAllLinesOptList(brFile);
    if (lines.isEmpty()) {
      return new String();
    }
    return lines.get(0);
  }
}
