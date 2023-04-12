package uc.eecs.irmodel.buglocator;

import uc.eecs.irmodel.Property;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Hashtable;

/*
Similarity from BugLocator
 */
public class Similarity {
  Hashtable<String, Integer> fileIdTable = null;
  private String workDir = Property.getInstance().WorkDir + Property.getInstance().Separator;
  private String lineSeparator = Property.getInstance().LineSeparator;
  private String Separator = Property.getInstance().Separator;
  public int fileCount = Property.getInstance().FileCount;
  public int codeTermCount = Property.getInstance().WordCount;

  public void compute() throws IOException {
    fileIdTable = getFileId();
    Hashtable<String, Integer> wordIdTable = getWordId();
    Hashtable<String, Integer> idcTable = getIDCTable();

    FileWriter writer = new FileWriter(workDir + "VSMScore.txt");
    BufferedReader readerId = new BufferedReader(new FileReader(workDir + "SortedId.txt"));
    String idLine = null;
    while ((idLine = readerId.readLine()) != null) {
      Integer bugId = Integer.parseInt(idLine.substring(0, idLine.indexOf("\t")));

      // load bug word
      BufferedReader readerBug =
          new BufferedReader(new FileReader(workDir + "Query" + "/" + bugId + ".txt"));
      String line = readerBug.readLine();
      readerBug.close();

      // count bug word
      String[] words = line.split(" ");
      Hashtable<String, Integer> wordTable = new Hashtable<>();
      for (String word : words) {
        if (!word.trim().equals("")) {
          if (wordTable.containsKey(word)) {
            Integer count = wordTable.get(word);
            count++;
            wordTable.remove(word);
            wordTable.put(word, count);
          } else {
            wordTable.put(word, 1);
          }
        }
      }

      // calculate total word count
      int totalTermCount = 0;
      for (String word : wordTable.keySet()) {
        Integer id = wordIdTable.get(word);
        if (id != null) {
          totalTermCount += wordTable.get(word);
        }
      }

      // create word vector
      float[] bugVector = new float[codeTermCount];
      for (String word : wordTable.keySet()) {
        Integer id = wordIdTable.get(word);
        if (id == null) continue;

        Integer idc = idcTable.get(word);
        Integer count = wordTable.get(word);
        float tf = getTfValue(count, totalTermCount);
        float idf = getIdfValue(idc, fileCount);
        bugVector[id] = tf * idf;
      }
      double norm = 0.0f;
      for (int i = 0; i < bugVector.length; i++) {
        norm += bugVector[i] * bugVector[i];
      }
      norm = Math.sqrt(norm);
      for (int i = 0; i < bugVector.length; i++) {
        bugVector[i] = bugVector[i] / (float) norm;
      }

      float[] simValues = computeSimilarity(bugVector);

      StringBuilder buf = new StringBuilder();
      buf.append(bugId).append(";");
      for (int i = 0; i < simValues.length; i++) {
        if (simValues[i] != 0.0f) buf.append(i).append(":").append(simValues[i]).append(" ");
      }
      writer.write(buf.toString().trim() + lineSeparator);
      writer.flush();
    }
    readerId.close();
    writer.close();
  }

  private Hashtable<String, Integer> getFileId() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(workDir + "ClassName.txt"));
    String line;
    Hashtable<String, Integer> table = new Hashtable<>();
    while ((line = reader.readLine()) != null) {
      String[] values = line.split("\t");
      Integer idInteger = Integer.parseInt(values[0]);
      String nameString = values[1].trim();
      table.put(nameString, idInteger);
    }
    reader.close();
    return table;
  }

  private Hashtable<String, Integer> getWordId() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(workDir + "Wordlist.txt"));
    String line = null;
    Hashtable<String, Integer> wordIdTable = new Hashtable<>();
    while ((line = reader.readLine()) != null) {
      String[] values = line.split("\t");
      wordIdTable.put(values[0], Integer.parseInt(values[1]));
    }
    reader.close();
    return wordIdTable;
  }

  private Hashtable<String, Integer> getIDCTable() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(workDir + "IDC.txt"));
    String line;
    Hashtable<String, Integer> idcTable = new Hashtable<String, Integer>();
    while ((line = reader.readLine()) != null) {
      String[] values = line.split("\t");
      idcTable.put(values[0], Integer.parseInt(values[1]));
    }
    reader.close();
    return idcTable;
  }

  private float[] computeSimilarity(float[] bugVector) throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(workDir + "CodeVector.txt"));
    String line = null;
    float[] simValues = new float[fileCount];
    while ((line = reader.readLine()) != null) {
      String[] values = line.split(";");
      String name = values[0];
      Integer fileId = fileIdTable.get(name);
      if (fileId == null) {
        System.out.println("similarity error: " + name);
      }
      float[] codeVector = null;
      if (values.length != 1) codeVector = this.getVector(values[1]);
      else codeVector = this.getVector(null);
      float sim = 0.0f;
      for (int i = 0; i < codeVector.length; i++) {
        sim += bugVector[i] * codeVector[i];
      }
      simValues[fileId] = sim;
    }
    reader.close();
    return simValues;
  }

  private float[] getVector(String vecStr) {
    float[] vector = new float[codeTermCount];
    if (vecStr == null) return vector;
    String[] values = vecStr.split(" ");
    for (String str : values) {
      Integer id = Integer.parseInt(str.substring(0, str.indexOf(":")));
      float w = Float.parseFloat(str.substring(str.indexOf(":") + 1));
      vector[id] = w;
    }
    return vector;
  }

  private float getTfValue(int freq, int totalTermCount) {
    return (float) Math.log(freq) + 1;
  }

  private float getIdfValue(double docCount, double totalCount) {
    return (float) Math.log(totalCount / docCount);
  }
}
