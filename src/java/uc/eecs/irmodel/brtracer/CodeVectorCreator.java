package uc.eecs.irmodel.brtracer;

import uc.eecs.irmodel.Property;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CodeVectorCreator {
  private final String workDir = Property.getInstance().WorkDir + Property.getInstance().Separator;
  private final String lineSeparator = Property.getInstance().LineSeparator;

  public int fileCount = Property.getInstance().FileCount;
  public int codeTermCount = Property.getInstance().WordCount;

  public void create() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(workDir + "TermInfo.txt"));
    String line = null;
    FileWriter writer = new FileWriter(workDir + "CodeVector.txt");
    FileWriter errorWriter = new FileWriter(workDir + "CodeVector_noTerms.txt");
    int errorCount = 0;

    while ((line = reader.readLine()) != null) {
      String[] values = line.split(";");

      String name = values[0].substring(0, values[0].indexOf("\t"));
      if (values.length == 1) {
        errorWriter.write("Warnning:: This file has no terms: " + name);
        errorCount++;
        continue;
      }
      int totalTermCount = Integer.parseInt(values[0].substring(values[0].indexOf("\t") + 1));
      String[] termInfos = values[1].split("\t");
      float[] vector = new float[codeTermCount];
      for (String str : termInfos) {
        String[] strs = str.split(":");
        int termId = Integer.parseInt(strs[0]);
        int termCount = Integer.parseInt(strs[1].substring(0, strs[1].indexOf(" ")));
        int documentCount = Integer.parseInt(strs[1].substring(strs[1].indexOf(" ") + 1));

        float tf = this.getTfValue(termCount, totalTermCount);
        float idf = this.getIdfValue(documentCount, fileCount);
        vector[termId] = tf * idf;
      }
      double norm = 0.0f;
      for (float v : vector) {
        norm += v * v;
      }
      norm = Math.sqrt(norm);

      StringBuilder buf = new StringBuilder();
      buf.append(name).append(";");
      for (int i = 0; i < vector.length; i++) {
        if (vector[i] != 0.0f) {
          vector[i] = vector[i] / (float) norm;
          buf.append(i).append(":").append(vector[i]).append(" ");
        }
      }
      writer.write(buf.toString() + lineSeparator);
      writer.flush();
    }
    reader.close();
    writer.close();
    errorWriter.close();

    if (errorCount > 0)
      System.err.println("CodeVectorCreator :: " + errorCount + " files has no terms.");
  }

  private float getTfValue(int freq, int totalTermCount) {
    return (float) Math.log(freq) + 1;
  }

  private float getIdfValue(double docCount, double totalCount) {
    return (float) Math.log(totalCount / docCount);
  }
}
