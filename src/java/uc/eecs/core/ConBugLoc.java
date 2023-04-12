package uc.eecs.core;

import evaluation.Evaluation;
import uc.eecs.irmodel.Property;
import utils.config.BugReportsID;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ConBugLoc {
  public static void main(String[] args) throws Exception {
    parse("druid", "druid-druid-24.0.2", BugReportsID.DRUID);
    Indexing indexing = new Indexing();
    indexing.basic();
    IRModel irModel = new IRModel();
    irModel.rVSM();
    new Evaluation().evaluate();
  }

  private static void parse(String name, String code, List<Integer> idlist) {
    // -n CAMEL_CAMEL_2_15_6
    // -s /Users/shuai/Java-projects/Bench4BL-master/_archives/Apache/CAMEL/sources/CAMEL_2_15_6
    // -b
    // /Users/shuai/Java-projects/Bench4BL-master/_archives/Apache/CAMEL/bugrepo/repository/CAMEL_2_15_6.xml
    // -w /Users/shuai/Java-projects/Bench4BL-master/expresults/Exp_first/Apache/CAMEL/
    // -a 0.2
    int i = 0;
    String bugFilePath = "";
    String sourceCodeDir = "";
    String outputFile = "";
    String workingPath = "";
    String projectStr = "";
    String queryPath = "";

    bugFilePath = "/Users/shuai/Java-projects/ConBugLoc-Dataset/Dataset/GitHub/" + name + "/repository.xml";
    sourceCodeDir = "/Users/shuai/Java-projects/ConBugLoc-Dataset/Dataset/GitHub/" + name + "/" + code + "/";
    projectStr = "/Users/shuai/Java-projects/ConBugLoc-Dataset/Evaluation/ConBugLoc/GitHub/" + name + "/";
    queryPath = "/Users/shuai/Java-projects/ConBugLoc-Dataset/query/GitHub/" + name + "/blizzard.txt";

    // File System check (minimum 2GB)
    File file = new File(System.getProperty("user.dir"));
    if (file.getFreeSpace() / 1024 / 1024 / 1024 < 2) {
      System.out.println(
          "Not enough free disk space, please ensure your current disk space are bigger than 2G.");
    }
    // prepare working directory and create properties.
    // make workingPath
    workingPath = projectStr + "/" + "Model_1";

    // make outputFile path.
    File dir = new File(workingPath);
    if (!dir.exists()) dir.mkdirs();
    outputFile = workingPath + name + ".txt";

    Property.createInstance(
        projectStr.toUpperCase(), bugFilePath, sourceCodeDir, workingPath, outputFile, queryPath, idlist);
  }
}
