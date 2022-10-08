package uc.eecs.br.classification;

import utils.BRLoader;
import utils.ContentLoader;
import utils.config.DatasetConfig;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BRClassification {
  ArrayList<String> nl_content;
  ArrayList<String> st_content;

  public BRClassification() {
    nl_content = new ArrayList<>();
    st_content = new ArrayList<>();
  }

  public ArrayList<String> getNl_content() {
    return nl_content;
  }

  public ArrayList<String> getSt_content() {
    return st_content;
  }

  /**
   * If the
   * @param content has stack traces
   * @return ture
   */
  public boolean classification(String content) {
    Pattern p = Pattern.compile(DatasetConfig.Stack_Regex);
    Matcher m = p.matcher(content);
    return m.find();
  }

  public static void main(String[] args) {

    // TODO Auto-generated method stub
    String repoName = DatasetConfig.ECF;
    int bugID = 146622;
    String brFile = DatasetConfig.HOME_DIR + "/BR-Raw/" + repoName + "/" + bugID + ".txt";
    String title = BRLoader.loadBRTitle(repoName, bugID);
    String bugReportContent = ContentLoader.loadFileContent(brFile);
    BRClassification brc = new BRClassification();
    Boolean trace = brc.classification(bugReportContent);
    System.out.println(trace);
//    for (String t :trace){
//      System.out.println(t);
//    }
//    BLIZZARDQueryProvider blizzardProvider =
//        new BLIZZARDQueryProvider(repoName, bugID, title, bugReportContent);
//    System.out.println(blizzardProvider.provideBLIZZARDQuery());
  }
}
