package uc.eecs.irmodel;

import java.io.*;
import java.util.Hashtable;
import java.util.Map;

public class QueryProcess {
  private final String queryDir =
      Property.getInstance().WorkDir + Property.getInstance().Separator + "query/";
  private final String path = Property.getInstance().QueryDir;

  public QueryProcess() {
  }

  public void create() throws IOException {
    BufferedReader reader = new BufferedReader(new FileReader(path));
    String line;
    File f = new File(queryDir);
    if (!f.isDirectory())
      f.mkdir();
    while ((line = reader.readLine()) != null) {
      String[] values = line.split("\t");
      FileWriter writeCorpus = null;
      try{
        writeCorpus = new FileWriter(queryDir + values[0] + ".txt");
        writeCorpus.write(values[1]);
      }catch (IOException e){
        e.printStackTrace();
      } finally{
        assert writeCorpus != null;
        writeCorpus.flush();
        writeCorpus.close();
      }
    }
  }

  public static void main(String[] args) throws IOException {
    String path = "/Users/shuai/Java-projects/ConBugLoc-Dataset/Evaluation/ConBugLoc/GitHub/druid/query/";
    File f = new File(path);
    if (!f.isDirectory())
      f.mkdir();
  }
}
