package uc.eecs.irmodel.buglocator;

import uc.eecs.irmodel.Property;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CodeVectorCreator {

  public int fileCount = Property.getInstance().FileCount;
  public int codeTermCount = Property.getInstance().WordCount;

  private final String workDir = Property.getInstance().WorkDir + Property.getInstance().Separator;
  private final String lineSeparator = Property.getInstance().LineSeparator;

  public CodeVectorCreator() {}

  public void create() throws IOException {
    String line = null;
    BufferedReader reader = new BufferedReader(new FileReader(this.workDir + "TermInfo.txt"));
    FileWriter writer = new FileWriter(this.workDir + "CodeVector.txt");

    while ((line = reader.readLine()) != null) {
      String[] values = line.split(";");

      String name = values[0].substring(0, values[0].indexOf("\t"));
      //			if (values.length == 1) {
      //				System.out.println("This file has no terms : "+ name);
      //				continue;
      //			}

      int totalTermCount = Integer.parseInt(values[0].substring(values[0].indexOf("\t") + 1));
      String[] termInfos = values[1].split("\t");
      float[] vector = new float[codeTermCount];

      for (String str : termInfos) {
        String[] strs = str.split(":");
        int termId = Integer.parseInt(strs[0]);
        int termCount = Integer.parseInt(strs[1].substring(0, strs[1].indexOf(" ")));
        int documentCount = Integer.parseInt(strs[1].substring(strs[1].indexOf(" ") + 1));

        float tf = getTfValue(termCount, totalTermCount);
        float idf = getIdfValue(documentCount, this.fileCount);
        vector[termId] = (tf * idf);
      }

      double norm = 0.0D;
      for (float v : vector) {
        norm += v * v;
      }
      norm = Math.sqrt(norm);

      StringBuilder buf = new StringBuilder();
      buf.append(name).append(";");
      for (int i = 0; i < vector.length; i++) {
        if (vector[i] != 0.0F) {
          vector[i] /= (float) norm;
          buf.append(i).append(":").append(vector[i]).append(" ");
        }
      }
      writer.write(buf.toString() + this.lineSeparator);
      writer.flush();
    }
    reader.close();
    writer.close();
  }

  private float getTfValue(int freq, int totalTermCount) {
    return (float) Math.log(freq) + 1.0F;
  }

  private float getIdfValue(double docCount, double totalCount) {
    return (float) Math.log(totalCount / docCount);
  }
}
