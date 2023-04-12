package evaluation;

import uc.eecs.irmodel.Property;

import java.io.*;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.TreeSet;

public class Evaluation {
  private final String workDir = Property.getInstance().WorkDir + Property.getInstance().Separator;
  private final String outputFile = Property.getInstance().OutputFile;
  private final int fileCount = Property.getInstance().FileCount;
  private final int bugCount = Property.getInstance().BugReportCount;
  private final String lineSeparator = Property.getInstance().LineSeparator;
  private final String recommendedPath =
      Property.getInstance().WorkDir
          + Property.getInstance().Separator
          + "recommended"
          + Property.getInstance().Separator;

  Hashtable<String, Integer> idTable = null;
  Hashtable<Integer, String> nameTable = null;
  Hashtable<Integer, TreeSet<String>> fixTable = null;

  FileWriter outputWriter;
  public FileWriter errorWriter = null;

  public Evaluation() {
    try {
      this.idTable = getFileId();
      this.fixTable = getFixLinkTable();
      this.nameTable = getClassNames();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void evaluate() throws IOException {
    BufferedReader VSMReader = new BufferedReader(new FileReader(this.workDir + "VSMScore.txt"));

    errorWriter = new FileWriter(this.workDir + "Evaluator-NoMatch.txt");
    outputWriter = new FileWriter(this.outputFile);
    File resultDir = new File(recommendedPath);
    if (!resultDir.exists()) resultDir.mkdirs();

    int ErrorCount = 0;
    int ErrorBugCount = 0;
    for (int count = 0; count < bugCount; count++) {
      System.out.println("ssss");
      String vsmLine = VSMReader.readLine();
      String vsmIdStr = vsmLine.substring(0, vsmLine.indexOf(";"));
      Integer bugID = Integer.parseInt(vsmIdStr);
      String vsmVectorStr = vsmLine.substring(vsmLine.indexOf(";") + 1);
      float[] vsmVector = getVector(vsmVectorStr);

      // Calculate final Ranking  (This is recommendation process)
      float[] finalR = combine(normalize(vsmVector));

      // Print Evaluation Result

      int ret = this.printEvaluationResult(bugID, finalR);
      if (ret != 0) {
        ErrorCount += ret;
        ErrorBugCount++;
      }
    }
    VSMReader.close();
    errorWriter.close();
    outputWriter.close();

    if (ErrorCount != 0)
      System.err.println(
          "There are " + ErrorCount + " no match files in " + ErrorBugCount + " Bug Reports");
  }

  public int printEvaluationResult(Integer _bugID, float[] _finalscore) throws IOException {
    Rank[] sortedRank = getSortedRank(_finalscore);

    int ErrorCount = 0;

    TreeSet<String> fileSet = fixTable.get(_bugID);
    Iterator<String> fileIt = fileSet.iterator();
    Hashtable<Integer, String> answerIdTable = new Hashtable<Integer, String>();
    while (fileIt.hasNext()) {
      String fileName = fileIt.next();
      Integer fileId = idTable.get(fileName);

      if (fileId == null) {
        errorWriter.write(
            _bugID
                + ": This version of source code has no "
                + fileName
                + ".... Please check it!\n");
        errorWriter.flush();
        ErrorCount++;
        continue;
      }
      answerIdTable.put(fileId, fileName);
    }
    FileWriter writer = new FileWriter(recommendedPath + _bugID + ".txt");
    for (int i = 0; i < sortedRank.length; i++) {
      Rank rank = sortedRank[i];
      if (nameTable.containsKey(rank.id)) {
        writer.write(i + "\t" + rank.rank + "\t" + nameTable.get(rank.id) + this.lineSeparator);
      }
      if ((!answerIdTable.isEmpty()) && (answerIdTable.containsKey(rank.id))) {
        outputWriter.write(
            _bugID
                + "\t"
                + i
                + "\t"
                + rank.rank
                + "\t"
                + answerIdTable.get(rank.id)
                + this.lineSeparator);
        outputWriter.flush();
      }
    }
    writer.close();

    return ErrorCount;
  }

  private float[] getVector(String vectorStr) {
    float[] vector = new float[this.fileCount];
    String[] values = vectorStr.split(" ");

    for (String value : values) {
      String[] singleValues = value.split(":");
      if (singleValues.length == 2) {
        int index = Integer.parseInt(singleValues[0]);

        float sim = Float.parseFloat(singleValues[1]);
        vector[index] = sim;
      }
    }
    return vector;
  }

  private float[] normalize(float[] array) {
    float max = Float.MIN_VALUE;
    float min = Float.MAX_VALUE;
    for (int i = 0; i < array.length; i++) {
      if (max < array[i]) max = array[i];
      if (min > array[i]) min = array[i];
    }
    float span = max - min;
    for (int i = 0; i < array.length; i++) {
      array[i] = ((array[i] - min) / span);
    }
    return array;
  }

  public float[] combine(float[] vsmVector) {
    float[] results = new float[this.fileCount];
    for (int i = 0; i < this.fileCount; i++) {
      results[i] = vsmVector[i];
    }
    return results;
  }
  private Rank[] getSortedRank(float[] finalR) {
    Rank[] R = new Rank[finalR.length];
    for (int i = 0; i < R.length; i++) {
      R[i] = new Rank();
      R[i].rank = finalR[i];
      R[i].id = i;
    }
    R = insertionSort(R);
    return R;
  }

  private Rank[] insertionSort(Rank[] R) {
    for (int i = 0; i < R.length; i++) {
      int maxIndex = i;
      for (int j = i; j < R.length; j++) {
        if (R[j].rank > R[maxIndex].rank) {
          maxIndex = j;
        }
      }
      Rank tmpRank = R[i];
      R[i] = R[maxIndex];
      R[maxIndex] = tmpRank;
    }
    return R;
  }
  public Hashtable<String, Integer> getFileId() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(this.workDir + "ClassName.txt"));
    String line = null;
    Hashtable<String, Integer> table = new Hashtable<String, Integer>();
    while ((line = reader.readLine()) != null) {
      String[] values = line.split("\t");
      Integer idInteger = Integer.valueOf(Integer.parseInt(values[0]));
      String nameString = values[1].trim();
      table.put(nameString, idInteger);
    }
    reader.close();
    return table;
  }

  public Hashtable<Integer, TreeSet<String>> getFixLinkTable() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(this.workDir + "FixLink.txt"));
    String line = null;
    Hashtable<Integer, TreeSet<String>> table = new Hashtable<Integer, TreeSet<String>>();
    while ((line = reader.readLine()) != null) {
      String[] valueStrings = line.split("\t");
      Integer id = Integer.parseInt(valueStrings[0]);
      String fileName = valueStrings[1].trim();
      if (!table.containsKey(id)) {
        table.put(id, new TreeSet<String>());
      }
      table.get(id).add(fileName);
    }
    reader.close();
    return table;
  }

  public Hashtable<Integer, String> getClassNames() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(this.workDir + "ClassName.txt"));
    String line = null;
    Hashtable<Integer, String> table = new Hashtable<Integer, String>();
    while ((line = reader.readLine()) != null) {
      String[] values = line.split("\t");
      Integer idInteger = Integer.valueOf(Integer.parseInt(values[0]));
      String nameString = values[1].trim();
      table.put(idInteger, nameString);
    }
    reader.close();
    return table;
  }
}
