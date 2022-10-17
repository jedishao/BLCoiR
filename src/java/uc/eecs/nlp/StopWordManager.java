package uc.eecs.nlp;

import utils.ContentLoader;
import utils.config.DatasetConfig;
import utils.config.ResConfig;

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
      Scanner scanner = new Scanner(new File(ResConfig.STOP_WORD));
      while (scanner.hasNext()) {
        String word = scanner.nextLine().trim();
        this.stopList.add(word);
      }
      scanner.close();

      // now add the programming keywords
      ArrayList<String> keywords = ContentLoader.getAllLinesOptList(ResConfig.JAVA_KEYWORDS);
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

  public ArrayList<String> getRefinedList(ArrayList<String> words) {
    ArrayList<String> refined = new ArrayList<>();
    for (String word : words) {
      if (!this.stopList.contains(word.toLowerCase())) {
        refined.add(word);
      }
    }
    return refined;
  }

  protected String removeSpecialChars(String sentence) {
    // removing special characters
    String regex = "\\p{Punct}+|\\d+|\\s+";
    String[] parts = sentence.split(regex);
    StringBuilder refined = new StringBuilder();
    for (String str : parts) {
      refined.append(str.trim()).append(" ");
    }
    // if(modifiedWord.isEmpty())modifiedWord=word;
    return refined.toString();
  }
}
