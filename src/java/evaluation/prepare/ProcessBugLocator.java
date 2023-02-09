package evaluation.prepare;

import utils.config.BugReportsID;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import utils.XMLParser;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public class ProcessBugLocator implements Prepare {
  @Override
  public void collectBR(String path, String brPath) throws IOException {
    XMLParser xp = new XMLParser();
    Document dc = xp.readXml(path);
    Element e = dc.getRootElement();
    Iterator<Element> it = e.elementIterator();
    while (it.hasNext()) {
      Element book = it.next();
      Attribute attribute = book.attribute(0);
      System.out.println(attribute.getText());
      File file = new File(brPath + attribute.getText() + ".txt");
      if (!file.exists()) {
        file.createNewFile();
      }
      FileWriter fileWritter = new FileWriter(file.getPath(), true);
      Iterator<Element> itt = book.elementIterator();
      while (itt.hasNext()) {
        Element book1 = itt.next();
        if (Objects.equals(book1.getName(), "buginformation")) {
          Iterator<Element> itt1 = book1.elementIterator();
          while (itt1.hasNext()) {
            Element book2 = itt1.next();
            if (Objects.equals(book2.getName(), "summary")) {
              fileWritter.write(book2.getText());
            } else if (Objects.equals(book2.getName(), "description")) {
              fileWritter.write(book2.getText());
            }
          }
        }
      }
      fileWritter.flush();
      fileWritter.close();
    }
  }

  @Override
  public void getVersion(String path, List<Integer> con) throws IOException {}

  @Override
  public void getChanges(String path, String brPath, List<Integer> con) throws IOException {
    XMLParser xp = new XMLParser();
    Document dc = xp.readXml(path);
    Element e = dc.getRootElement();
    Iterator<Element> itt = e.elementIterator();
    while (itt.hasNext()) {
      Element book = itt.next();
      Iterator<Element> it = book.elementIterator();
      boolean flag = false;
      File file = null;
      while (it.hasNext()) {
        Element book1 = it.next();
        if (Objects.equals(book1.attribute(0).getText(), "bug_id")) {
          if (con.contains(Integer.parseInt(book1.getText()))) {
            file = new File(brPath + book1.getText() + ".txt");
            if (!file.exists()) {
              file.createNewFile();
            }
            flag = true;
          }
        } else if (flag && Objects.equals(book1.attribute(0).getText(), "files")) {
          FileWriter fileWritter = new FileWriter(file.getPath(), true);
          System.out.println(book1.getText());
          fileWritter.write(book1.getText());
          fileWritter.write("\n");
          fileWritter.flush();
          fileWritter.close();
        }
      }
    }
  }

  public static void main(String[] args) throws IOException {
    ProcessBugLocator pb = new ProcessBugLocator();
    String path = "dataset/LtR/AspectJ/repository.xml";
//    String brPath = "dataset/BugLocator&LtR/AspectJ/BR/";
    String changePath = "dataset/LtR/AspectJ/changeset/";
    //List<Integer> con = ResUtils.getId("BugLocator&LtR", "SWT");
    // System.out.println(con.size());
    // pb.collectBR(path, brPath);
    pb.getChanges(path, changePath, BugReportsID.ASPECTJ);
  }
}
