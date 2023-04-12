package uc.eecs.nlp;

import utils.MiscUtility;
import utils.config.QueryConfig;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextNormalizer {
  String content;
  List<String> contentList;

  public TextNormalizer(String content) {
    this.content = content;
  }
  public TextNormalizer(List<String> content) {
    this.contentList = content;
  }
  public String normalizeSimple() {
    String[] words = content.split("\\p{Punct}+|\\d+|\\s+");
    ArrayList<String> wordList = new ArrayList<>(Arrays.asList(words));
    return MiscUtility.list2Str(wordList);
  }

  public List<String> removeHttp(){
    ArrayList<String> results = new ArrayList<>();
    ArrayList<String> results1 = new ArrayList<>();
    Pattern p =
        Pattern.compile(
            "(.*)http://(.*)|(.*)https://(.*)|(.*)line:(.*)|(.*)}|(.*)\\{|(.*);|(.*)\\((.*).java:(.*)|Build ID:(.*)" +
                    "|Steps To Reproduce:|//(.*)|\\*(.*)|->(.*)|\\+-(.*)|User-Agent:(.*)|Build Identifier:(.*)");
    //Pattern p = Pattern.compile("(.*)http://(.*)|(.*)https://(.*)|(.*)\\[(.*)\\](.*)");
    Pattern p1 = Pattern.compile(QueryConfig.STACK_REGEX);
    Pattern p2 = Pattern.compile("(.*)([a-z]\\.)+(.+)(.*)");
    for (String s : contentList){
      Matcher m = p.matcher(s);
      Matcher m1 = p1.matcher(s);
      if (!m.matches() && !m1.matches())
        results.add(s);
    }
    List<String> index = new ArrayList<>();
    for (String s1 : results){
      Matcher m = p2.matcher(s1);
      if (m.matches()){
        index.add(s1);
        StringBuilder sb = new StringBuilder();
        for (String s2 : s1.split(" ")) {
          Matcher m1 = p2.matcher(s2);
          if (m1.matches()){
            sb.append(s2.split("\\.")[s2.split("\\.").length - 1]);
            sb.append(" ");
          } else {
            sb.append(s2);
            sb.append(" ");
          }
        }
        results1.add(sb.toString());
      }
    }
    for (String i : index){
      results.remove(i);
    }
    results.addAll(results1);
    return results;
  }

  public String normalizeText() {
    // normalize the content
    String[] sentences = content.split("\\.+|\\)+|\\(+");
    ArrayList<String> sentenceList = new ArrayList<>();
    for (String s1: sentences){
      sentenceList.add(s1.trim());
    }
    // discard the small tokens
    sentenceList = removeSmallWords(sentenceList);
    String modifiedContent = MiscUtility.list2StrSent(sentenceList);
    return modifiedContent;
  }

  protected ArrayList<String> removeSmallWords(ArrayList<String> items) {
    // discarding small tokens
    ArrayList<String> temp = new ArrayList<>();
    for (String item : items) {
      if (item.length() > 2) {
        temp.add(item);
      }
    }
    return temp;
  }

  public static void main(String[] args) {
    String s = "adada adada s 11";
    System.out.println(s.matches("\\w+\\s+[a-z]+\\s+\\w+"));
  }
}
