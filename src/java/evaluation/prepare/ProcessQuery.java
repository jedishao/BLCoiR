package evaluation.prepare;

import utils.ContentLoader;
import utils.config.BugReportsID;
import utils.config.DatasetConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ProcessQuery {

  public void writeFile() throws IOException {
    String bench = DatasetConfig.LTR;
    String repository = DatasetConfig.SWT;
    List<String> our =
        ContentLoader.getAllLinesList("query/" + bench + "/" + repository + "/our.txt");
    for (String s : our) {
      File file =
          new File("dataset/" + bench + "/" + repository + "/query/" + s.split("\t")[0] + ".txt");
      if (!file.exists()) {
        file.createNewFile();
      }
      FileWriter fileWritter = new FileWriter(file.getPath(), true);
      fileWritter.write(s.split("\t")[1]);
      fileWritter.flush();
      fileWritter.close();
    }
  }

  public void writeQuery() throws IOException {
    String bench = DatasetConfig.LTR;
    String repository = DatasetConfig.PLATFORM;
    List<Integer> idList = BugReportsID.PLATFORM;
    File file = new File("dataset/" + bench + "/" + repository + "/query.xml");
    if (!file.exists()) {
      file.createNewFile();
    }
    FileWriter fileWriter = new FileWriter(file.getPath(), true);
    fileWriter.write("<bugrepository name=\"Platform\">");
    fileWriter.write("\n");
    for (int id : idList) {
      String q =
          ContentLoader.loadFileContent(
              "dataset/" + bench + "/" + repository + "/query/" + id + ".txt");
      String op =
          ContentLoader.loadFileContent(
              "dataset/" + bench + "/" + repository + "/opendate/" + id + ".txt");
      String fix =
          ContentLoader.loadFileContent(
              "dataset/" + bench + "/" + repository + "/fixdate/" + id + ".txt");
      fileWriter.write(
          "<bug id=\"" + id + '"'
              + " opendate=\""
              + op
              + '"'
              + "fixdate=\""
              + fix
              + "\" resolution=\"Fixed\">");
      fileWriter.write("<buginformation>");
      fileWriter.write("<summary>" + q + "</summary>");
      fileWriter.write("<description>");
      fileWriter.write(q);
      fileWriter.write("</description>");
      fileWriter.write("<version>1.1.0</version>");
      fileWriter.write("<fixedVersion>1.1.0</fixedVersion>");
      fileWriter.write("<type>Bug</type>");
      fileWriter.write("</buginformation>");
      fileWriter.write("<fixedFiles>");
      List<String> change =
              ContentLoader.getAllLinesList(
                      "dataset/" + bench + "/" + repository + "/changeset/" + id + ".txt");
      for (String c : change){
        String c1 = c.replace("/", ".");
        fileWriter.write("<file type=\"M\">"+c1+"</file>");
      }
      fileWriter.write("</fixedFiles>");
      fileWriter.write("</bug>");
      fileWriter.flush();
    }
    fileWriter.write("</bugrepository>");
    fileWriter.flush();
    fileWriter.close();
  }

  public static void main(String[] args) throws IOException {
    new ProcessQuery().writeQuery();
  }
}
