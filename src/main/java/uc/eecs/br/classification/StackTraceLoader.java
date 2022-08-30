package uc.eecs.br.classification;

import utils.config.DatasetConfig;

import java.util.ArrayList;
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

}
