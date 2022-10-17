package uc.eecs.nlp;

import utils.MiscUtility;

import java.util.ArrayList;
import java.util.Arrays;

public class TextNormalizer {
  String content;

  public TextNormalizer(String content) {
    this.content = content;
  }

  public String normalizeSimple() {
    String[] words = content.split("\\p{Punct}+|\\d+|\\s+");
    ArrayList<String> wordList = new ArrayList<>(Arrays.asList(words));
    return MiscUtility.list2Str(wordList);
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
