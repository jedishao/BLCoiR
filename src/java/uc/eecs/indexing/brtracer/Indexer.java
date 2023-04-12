package uc.eecs.indexing.brtracer;

import uc.eecs.irmodel.Property;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;
import java.util.TreeSet;

public class Indexer {
  private final String workDir = Property.getInstance().WorkDir + Property.getInstance().Separator;
  private final String lineSeparator = Property.getInstance().LineSeparator;

  public void index() throws IOException {
    // countTable: count how many times a word occurs in all files
    Hashtable<String, Integer> countTable = countDoc(); // ���� ��ü���� �����ϴ� word�� ī��Ʈ
    Hashtable<String, Integer> idSet = new Hashtable<String, Integer>(); // �� word�� id  <word, id>

    // Store <index, word> sets
    int id = 0;
    FileWriter writerWord = new FileWriter(workDir + "Wordlist.txt");
    int wordCount = 0;
    for (String key : countTable.keySet()) {
      idSet.put(key, id);
      writerWord.write(key + "\t" + id + lineSeparator);
      writerWord.flush();
      id++;
      wordCount++;
    }
    Property.getInstance().WordCount = wordCount;
    writerWord.close();

    // IDC.txt tells how many times a word occurs in all files
    // Store <word, count> sets
    FileWriter writerDoc = new FileWriter(workDir + "IDC.txt");
    for (String key : countTable.keySet()) {
      writerDoc.write(key + "\t" + countTable.get(key) + lineSeparator);
      writerDoc.flush();
    }
    writerDoc.close();

    // CodeCorpus (segmented)
    BufferedReader reader = new BufferedReader(new FileReader(workDir + "CodeCorpus.txt"));
    String line = null;
    FileWriter writer = new FileWriter(workDir + "TermInfo.txt");
    while ((line = reader.readLine()) != null) {
      String[] values = line.split("\t");
      String[] words;
      if (values.length == 2) words = values[1].split(" ");
      else {
        words = new String[0];
      }
      int totalCount = 0;

      Hashtable<Integer, Integer> termTable = new Hashtable<Integer, Integer>();
      for (String word : words) {
        if (!word.trim().equals("")) {
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
      }
      StringBuilder output = new StringBuilder();
      output.append(values[0]).append("\t").append(totalCount).append(";");
      TreeSet<Integer> tmp = new TreeSet<>();
      for (String word : words) {
        if (!word.trim().equals("")) {
          Integer termId = idSet.get(word);
          if (!tmp.contains(termId)) {
            tmp.add(termId);
            int termCount = termTable.get(termId);
            // documentCount means how many times a word occurs in
            // all files
            int documentCount = countTable.get(word);
            output
                .append(termId)
                .append(":")
                .append(termCount)
                .append(" ")
                .append(documentCount)
                .append("\t");
          }
        }
      }
      writer.write(output.toString() + lineSeparator);
      writer.flush();
    }
    reader.close();
    writer.close();
  }

  public Hashtable<String, Integer> countDoc() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(workDir + "CodeCorpus.txt"));
    String line;

    Hashtable<String, Integer> countTable = new Hashtable<>();

    while ((line = reader.readLine()) != null) {
      String[] values = line.split("\t");
      if (values.length <= 1) continue;

      String[] words = values[1].split(" ");

      TreeSet<String> wordSet = new TreeSet<>();
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
