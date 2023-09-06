package uc.eecs.stacktrace;

import org.ejml.FancyPrint;
import utils.ContentLoader;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StackTraceAnalyzer {
  protected ArrayList<String> extractTraces(String bugReport) {
    ArrayList<String> traces = new ArrayList<>();
    String stackRegex =
        "(.*)?(.+)\\.(.+)(\\((.+)\\.java:\\d+\\)|\\(Unknown Source\\)|\\(Native Method\\))";
    Pattern p = Pattern.compile(stackRegex);
    Matcher m = p.matcher(bugReport);
    while (m.find()) {
      String entry = bugReport.substring(m.start(), m.end());
      entry = cleanTheEntry(entry);
      traces.add(entry);
    }
    return traces;
  }

  protected String cleanTheEntry(String entry) {
    if (entry.indexOf("at ") >= 0) {
      int atIndex = entry.indexOf("at");
      entry = entry.substring(atIndex + 2).trim();
    }
    if (entry.contains("(")) {
      int leftBraceIndex = entry.indexOf("(");
      entry = entry.substring(0, leftBraceIndex);
    }
    return entry;
  }

  public static HashSet<String> getExceptionMessages(String reportDesc) {
    HashSet<String> exceptions = new HashSet<>();
    String excepRegex = "(.)+Exception";
    Pattern p = Pattern.compile(excepRegex);
    Matcher m = p.matcher(reportDesc);
    while (m.find()) {
      String exception = reportDesc.substring(m.start(), m.end());
      String[] parts = exception.split("\\p{Punct}+|\\d+|\\s+");
      // System.out.println(exception);
      for (String part : parts) {
        if (part.endsWith("Exception") || part.endsWith("Error")) {
          if (part.equals("Exception") || part.equals("Error")) {
            // avoid the generic exception
          } else {
            exceptions.add(part);
          }
        }
      }
    }
    return exceptions;
  }

  public static void main(String[] args) {
    String content = ContentLoader.loadFileContent("Dataset/Bench4BL/Apache/CAMEL/BR/715.txt");
    StackTraceAnalyzer stackTraceAnalyzer = new StackTraceAnalyzer();
    List<String> traces;
    traces = stackTraceAnalyzer.extractTraces(content);
    for (String s : traces){
      System.out.println(s);
    }
  }
}
