package uc.eecs.indexing.buglocator;

import uc.eecs.irmodel.Property;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.TreeSet;

public class Indexer {
  private final String lineSeparator = System.getProperty("line.separator");
  private final String workDir = Property.getInstance().WorkDir + Property.getInstance().Separator;

  public Indexer() {}

  public void index() throws IOException {
    // countTable: count how many times a word occurs in all files
    Hashtable<String, Integer> countTable = countDoc();
    Hashtable<String, Integer> idSet = new Hashtable<>();
    int id = 0;
    int errorCount = 0;
    FileWriter writerWord = new FileWriter(workDir + "Wordlist.txt");
    for (String key : countTable.keySet()) {
      idSet.put(key, id);
      writerWord.write(key + "\t" + id + lineSeparator);
      writerWord.flush();
      id++;
    }
    writerWord.close();
    Property.getInstance().WordCount = id;

    // IDC.txt tells how many times a word occurs in all files
    FileWriter writerDoc = new FileWriter(workDir + "IDC.txt");
    for (String key : countTable.keySet()) {
      writerDoc.write(key + "\t" + countTable.get(key) + lineSeparator);
      writerDoc.flush();
    }
    writerDoc.close();

    FileWriter errorList = new FileWriter(workDir + "CodeTerm-NoTermList.txt");
    BufferedReader reader = new BufferedReader(new FileReader(workDir + "CodeCorpus.txt"));
    String line = null;
    FileWriter writer = new FileWriter(workDir + "TermInfo.txt");
    while ((line = reader.readLine()) != null) {
      String[] values = line.split("\t");
      String[] words = values[1].split(" ");
      int totalCount = 0;

      Hashtable<Integer, Integer> termTable = new Hashtable<>();
      for (String word : words) {
        if (word.trim().equals("")) continue;

        totalCount++;
        Integer termId = idSet.get(word);
        if (termTable.containsKey(termId)) {
          Integer count = termTable.get(termId);
          count++;
          termTable.remove(termId);
          termTable.put(termId, count);
        } else {
          termTable.put(termId, 1);
        }
      }
      if (totalCount == 0) {
        // System.err.println("Warning::This file has no term: "+values[0]);
        errorCount++;
        errorList.write(values[0] + "\n");
        errorList.flush();
        continue;
      }
      StringBuffer output = new StringBuffer();
      output.append(values[0] + "\t" + totalCount + ";");
      TreeSet<Integer> tmp = new TreeSet<>();
      for (String word : words) {
        if (word.trim().equals("")) continue;

        Integer termId = idSet.get(word);
        if (tmp.contains(termId)) continue;

        tmp.add(termId);
        int termCount = termTable.get(termId);
        // documentCount means how many times a word occurs in
        // all files
        int documentCount = countTable.get(word);
        output.append(termId + ":" + termCount + " " + documentCount + "\t");
      }
      writer.write(output.toString() + lineSeparator);
      writer.flush();
    }
    reader.close();
    writer.close();
    errorList.close();

    if (errorCount > 0) {
      System.err.println(
          "Warning:: This project has "
              + errorCount
              + " empty term files. Check the CodeTerm-NoTermList.txt!");
    }
  }

  public Hashtable<String, Integer> countDoc() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(workDir + "CodeCorpus.txt"));
    String line = null;

    Hashtable<String, Integer> countTable = new Hashtable<String, Integer>();

    while ((line = reader.readLine()) != null) {
      String[] values = line.split("\t");
      String[] words = values[1].split(" ");

      TreeSet<String> wordSet = new TreeSet<String>();
      for (String word : words) {
        if (!word.trim().equals("") && !wordSet.contains(word)) {
          wordSet.add(word);
        }
      }

      for (String word : wordSet) {
        if (countTable.containsKey(word)) {
          Integer count = countTable.get(word);
          count++;
          countTable.remove(word);
          countTable.put(word, count);
        } else {
          countTable.put(word, 1);
        }
      }
    }
    reader.close();
    return countTable;
  }
}
