package uc.eecs.br;

import uc.eecs.br.loader.BRLoader;
import utils.config.BugReportsID;
import utils.config.DatasetConfig;
import utils.config.QueryConfig;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BRClassification {
  ArrayList<String> st_content;

  public BRClassification() {
    st_content = new ArrayList<>();
  }

  public ArrayList<String> getSt_content() {
    return st_content;
  }

  /**
   * If the
   *
   * @param content has stack traces
   * @return ture
   */
  public boolean classification(List<String> content) {
    Pattern p = Pattern.compile(QueryConfig.STACK_REGEX);
    for (String line : content) {
      Matcher m = p.matcher(line);
      if (m.matches()) {
        //        System.out.println(line);
        return true;
      }
    }
    return false;
  }

  public String classify(List<String> content) {
    Pattern stack = Pattern.compile(QueryConfig.STACK_REGEX);
    // Pattern p1 = Pattern.compile("(.*)line: \\d+|(.*)=(.*);");
    Pattern code = Pattern.compile(QueryConfig.CODE_REGEX);
    Pattern log = Pattern.compile(QueryConfig.LOG_REGEX);
    Pattern log1 = Pattern.compile(QueryConfig.LOG_REGEX1);
    for (String line : content) {
      Matcher m1 = stack.matcher(line);
      if (m1.matches()) {
        return "ST";
      }
    }
    //    for (String line : content) {
    //      Matcher m2 = code.matcher(line);
    //      if (m2.matches()) {
    //        return "CODE";
    //      }
    //    }
    //    int j = 0;
    //    for (String line : content) {
    //      Matcher m3 = log.matcher(line);
    //      Matcher m4 = log1.matcher(line);
    //      if (m3.matches()) {
    //        return "LOG";
    //      } else if (m4.matches()) {
    //        j++;
    //      }
    //      if (j > 5) return "LOG";
    //    }
    return "NL";
  }

  public static void main(String[] args) {
    //    String repoName = DatasetConfig.ECF;
    //    int bugID = 146622;
    //    String brFile = DatasetConfig.HOME_DIR + "/BR-Raw/" + repoName + "/" + bugID + ".txt";
    //    String title = BRLoader.loadBRTitle(repoName, bugID);
    //    String bugReportContent = ContentLoader.loadFileContent(brFile);
    //    BRClassification brc = new BRClassification();
    //    Boolean trace = brc.classification(bugReportContent);
    //    System.out.println(trace);
    BRClassification bc = new BRClassification();
    List<Integer> nl = new ArrayList<>();
    List<Integer> st = new ArrayList<>();
    List<Integer> pe = new ArrayList<>();
    for (int id : BugReportsID.VERTX) {
      String brPath =
          DatasetConfig.DATASET_DIR
              + DatasetConfig.GITHUB
              + "/"
              + DatasetConfig.VERTX
              + "/BR/"
              + id
              + ".txt";
      List<String> con = BRLoader.loadBugReportList(brPath);
      if (Objects.equals(bc.classify(con), "ST")) {
        st.add(id);
      }
      if (Objects.equals(bc.classify(con), "NL")) {
        nl.add(id);
      }
      bc.classification(con);
    }
    System.out.println(st);
    System.out.println(nl);
    //    for (String t :trace){
    //      System.out.println(t);
    //    }
    //    BLIZZARDQueryProvider blizzardProvider =
    //        new BLIZZARDQueryProvider(repoName, bugID, title, bugReportContent);
    //    System.out.println(blizzardProvider.provideBLIZZARDQuery());
  }
}
