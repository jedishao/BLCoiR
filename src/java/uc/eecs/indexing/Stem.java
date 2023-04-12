package uc.eecs.indexing;

import uc.eecs.indexing.buglocator.PorterStemmer;

/*
Stem from BugLocator
*/
public class Stem {
  private static PorterStemmer stemmer = new PorterStemmer();

  public static String stem(String word) {
    stemmer.reset();
    stemmer.stem(word);
    return stemmer.toString();
  }
}
