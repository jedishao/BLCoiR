package uc.eecs.nlp;

import utils.ContentLoader;
import utils.config.DatasetConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class StopWordManager {
  public ArrayList<String> stopList;
  String stopDir = DatasetConfig.STOPWORD_DIR + "/stop-words-english-total.txt";
  String javaKeywordFile = DatasetConfig.STOPWORD_DIR + "/java-keywords.txt";
  String CppKeywordFile = DatasetConfig.STOPWORD_DIR + "/cpp-keywords.txt";

  public StopWordManager() {
    // initialize the Hash set
    this.stopList = new ArrayList<>();
    this.loadStopWords();
  }

  protected void loadStopWords() {
    // loading stop words
    try {
      Scanner scanner = new Scanner(new File(this.stopDir));
      while (scanner.hasNext()) {
        String word = scanner.nextLine().trim();
        this.stopList.add(word);
      }
      scanner.close();

      // now add the programming keywords
      ArrayList<String> keywords = ContentLoader.getAllLinesOptList(javaKeywordFile);
      this.stopList.addAll(keywords);

    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public String getRefinedSentence(String sentence) {
    // get refined sentence
    StringBuilder refined = new StringBuilder();
    String temp = removeSpecialChars(sentence);
    String[] tokens = temp.split("\\s+");
    for (String token : tokens) {
      if (!this.stopList.contains(token.toLowerCase())) {
        refined.append(token).append(" ");
      }
    }
    return refined.toString().trim();
  }

  protected String removeSpecialChars(String sentence) {
    // removing special characters
    String regex = "\\p{Punct}+|\\d+|\\s+";
    String[] parts = sentence.split(regex);
    String refined = new String();
    for (String str : parts) {
      refined += str.trim() + " ";
    }
    // if(modifiedWord.isEmpty())modifiedWord=word;
    return refined;
  }
}
