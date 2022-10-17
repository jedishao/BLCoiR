package utils;

import utils.config.DatasetConfig;

public class FileURL {
  public static String brPathAppend(String benchName, String repoName, int bugID) {
    return DatasetConfig.HOME_DIR + benchName + "/" + repoName + "/BR/" + bugID + ".txt";
  }

  public static String brPath(String benchName, String repoName) {
    return DatasetConfig.HOME_DIR + benchName + "/" + repoName + "/BR/";
  }

  public static String queryPath(String benchName, String repoName) {
    return DatasetConfig.HOME_DIR + benchName + "/" + repoName + "/query.txt";
  }
}
