package evaluation;

import uc.eecs.br.loader.BRLoader;
import utils.ResUtils;
import utils.config.BugReportsID;
import utils.config.DatasetConfig;
import utils.config.EvaluationConfig;
import utils.config.QueryConfig;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BaselineGen {

  public void genBaseline_nl(List<Integer> idList, String rep, String project) throws IOException {
    String path = DatasetConfig.QUERY_DIR + rep + "/" + project + "/baseline_nl.txt";
    FileWriter fileWritter = new FileWriter(path, true);
    int cus = 0;
    for (int id : idList) {
      cus++;
      String brPath = DatasetConfig.DATASET_DIR + rep + "/" + project + "/BR/" + id + ".txt";
      List<String> con = BRLoader.loadBugContentList(brPath);
      String tile = BRLoader.loadBRTitle(brPath);
      String[] s = tile.split(" ");
      StringBuilder sb = new StringBuilder();
      int i = 0;
      String[] sym = {
        "(", ")", ":", "[", "]", "}", "{", "#", "-", "|", "%", "@", ".", "+", "=", "\\", "/", "#",
        "*", "?", "^", "$", "\"", "'", "<", ">", ",", ";"
      };
      for (String v : s) {
        for (String s1 : sym) {
          v = v.replace(s1, " ");
        }
        sb.append(v).append(" ");
      }
      for (String c : con) {
        Pattern num = Pattern.compile(QueryConfig.STACK_REGEX);
        Matcher m = num.matcher(c);
        if (!m.matches()) {
          for (String s1 : sym) {
            c = c.replace(s1, " ");
          }
          sb.append(c).append(" ");
        }
      }
      fileWritter.write(id + "\t" + sb.toString().trim());
      if (cus != idList.size()) {
        fileWritter.write("\n");
      }
      fileWritter.flush();
    }
    fileWritter.close();
  }

  public void genBaseline_nlst(List<Integer> idList, String rep, String project) throws IOException {
    String path = DatasetConfig.QUERY_DIR + rep + "/" + project + "/baseline_nlst.txt";
    FileWriter fileWritter = new FileWriter(path, true);
    int cus = 0;
    for (int id : idList) {
      cus++;
      String brPath = DatasetConfig.DATASET_DIR + rep + "/" + project + "/BR/" + id + ".txt";
      List<String> con = BRLoader.loadBugContentList(brPath);
      String tile = BRLoader.loadBRTitle(brPath);
      String[] s = tile.split(" ");
      StringBuilder sb = new StringBuilder();
      int i = 0;
      String[] sym = {
              "(", ")", ":", "[", "]", "}", "{", "#", "-", "|", "%", "@", ".", "+", "=", "\\", "/", "#",
              "*", "?", "^", "$", "\"", "'", "<", ">", ",", ";"
      };
      for (String v : s) {
        for (String s1 : sym) {
          v = v.replace(s1, " ");
        }
        sb.append(v).append(" ");
      }
      for (String c : con) {
        for (String s1 : sym) {
          c = c.replace(s1, " ");
        }
        sb.append(c).append(" ");
      }
      fileWritter.write(id + "\t" + sb.toString().trim());
      if (cus != idList.size()) {
        fileWritter.write("\n");
      }
      fileWritter.flush();
    }
    fileWritter.close();
  }

  public void genBaseline_title(List<Integer> idList, String rep, String project)
      throws IOException {
    String path = DatasetConfig.QUERY_DIR + rep + "/" + project + "/baseline_title.txt";
    FileWriter fileWritter = new FileWriter(path, true);
    int cus = 0;
    for (int id : idList) {
      cus++;
      String brPath = DatasetConfig.DATASET_DIR + rep + "/" + project + "/BR/" + id + ".txt";
      String tile = BRLoader.loadBRTitle(brPath);
      String[] s = tile.split(" ");
      StringBuilder sb = new StringBuilder();
      String[] sym = {
        "(", ")", ":", "[", "]", "}", "{", "#", "-", "|", "%", "@", ".", "+", "=", "\\", "/", "#",
        "*", "?", "^", "$", "\"", "'", "<", ">", ","
      };
      for (String v : s) {
        for (String s1 : sym) {
          v = v.replace(s1, " ");
        }
        sb.append(v).append(" ");
      }
      fileWritter.write(id + "\t" + sb.toString().trim());
      if (cus != idList.size()) {
        fileWritter.write("\n");
      }
      fileWritter.flush();
    }
    fileWritter.close();
  }

  public static void main(String[] args) throws IOException {
    int size = EvaluationConfig.GITHUB.length;
    for (int i = 0; i < size; i++)
      new BaselineGen()
          .genBaseline_title(
              EvaluationConfig.GITHUB_ID.get(i),
              DatasetConfig.GITHUB,
              EvaluationConfig.GITHUB[i]);
    //    new BaselineGen().genBaseline_nl(BugReportsID.ECF, DatasetConfig.BLIZZARD,
    // DatasetConfig.ECF);
  }
}
