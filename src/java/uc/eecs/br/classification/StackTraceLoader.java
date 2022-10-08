package uc.eecs.br.classification;

import uc.eecs.core.selector.StackTraceSelector;
import utils.BRLoader;
import utils.ContentLoader;
import utils.config.DatasetConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackTraceLoader {

  public ArrayList<String> getStackTrace(String content) {
    ArrayList<String> traces = new ArrayList<>();
    Pattern p = Pattern.compile(DatasetConfig.Stack_Regex);
    Matcher m = p.matcher(content);
    while (m.find()) {
      String entry = content.substring(m.start(), m.end());
      entry = cleanTheEntry(entry);
      traces.add(entry);
    }
    return traces;
  }

  protected String cleanTheEntry(String entry) {
    if (entry.contains("at ")) {
      int atIndex = entry.indexOf("at");
      entry = entry.substring(atIndex + 2).trim();
    }
    if (entry.contains("(")) {
      int leftBraceIndex = entry.indexOf("(");
      entry = entry.substring(0, leftBraceIndex);
    }
    return entry;
  }

  public static void main(String[] args) {
    String repoName = DatasetConfig.TOMCAT;
    String brFile = "C:\\Java\\BLIZZARD-Replication-Package-ESEC-FSE2018\\BR-Raw\\tomcat70\\52208.txt";
    String title = BRLoader.loadBRTitle(repoName, 52208);
    String bugReportContent = ContentLoader.loadFileContent(brFile);
    StackTraceLoader stl = new StackTraceLoader();
    ArrayList<String> stack_trace = stl.getStackTrace(bugReportContent);
    for(String trace : stack_trace){
      System.out.println(trace);
    }
    System.out.println("==================================");
    StackTraceSelector sts = new StackTraceSelector(stack_trace);
    HashMap<String, Double> itemMapC = sts.getSalientClasses();
    //salientItems = sts.getSalientItemsFromST();
    for(String trace : itemMapC.keySet()){
      System.out.println(trace);
    }
    System.out.println("==================================");
    ArrayList<String> salientItems = sts.getSalientItemsFromST();
    for(String trace : salientItems){
      System.out.println(trace);
    }
  }
}
