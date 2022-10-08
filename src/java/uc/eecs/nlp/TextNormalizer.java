package uc.eecs.nlp;

import utils.MiscUtility;

import java.util.ArrayList;
import java.util.Arrays;

public class TextNormalizer {
  String content;

  public TextNormalizer(String content) {
    this.content = content;
  }

  public String normalizeText() {
    // normalize the content
    String[] words = this.content.split("\\p{Punct}+|\\d+|\\s+");
    ArrayList<String> wordList = new ArrayList<>(Arrays.asList(words));
    // discard the small tokens
    wordList = discardSmallTokens(wordList);
    String modifiedContent = MiscUtility.list2Str(wordList);
    StopWordManager stopManager = new StopWordManager();
    this.content = stopManager.getRefinedSentence(modifiedContent);
    return this.content;
  }

  protected ArrayList<String> discardSmallTokens(ArrayList<String> items) {
    // discarding small tokens
    ArrayList<String> temp = new ArrayList<>();
    for (String item : items) {
      if (item.length() > 2) {
        temp.add(item);
      }
    }
    return temp;
  }
}
