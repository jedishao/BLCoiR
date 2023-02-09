package evaluation.prepare;

import utils.ContentLoader;
import utils.config.DatasetConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class ProcessQuery {

  public void writeFile() throws IOException {
    String bench = DatasetConfig.LTR;
    String repository = DatasetConfig.PLATFORM;
    ArrayList<String> our =
            ContentLoader.getAllLinesList("query/" + bench + "/" + repository + "/our+.txt");
    for (String s : our){
      File file = new File("query/" + bench + "/" + repository + "/query/" + s.split("\t")[0] +".txt");
      if (!file.exists()) {
        file.createNewFile();
      }
      FileWriter fileWritter = new FileWriter(file.getPath(), true);
      fileWritter.write(s.split("\t")[1]);
      fileWritter.flush();
      fileWritter.close();
    }
  }

  public static void main(String[] args) throws IOException {
    new ProcessQuery().writeFile();
  }
}
