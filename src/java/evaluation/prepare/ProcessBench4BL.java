package evaluation.prepare;

import utils.config.BugReportsID;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import utils.XMLParser;
import utils.config.DatasetConfig;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class ProcessBench4BL implements Prepare {

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
  public void getVersion(String path, List<Integer> con) throws IOException {
    XMLParser xp = new XMLParser();
    Document dc = xp.readXml(path);
    Element e = dc.getRootElement();
    Iterator<Element> it = e.elementIterator();
    Map<String, List<String>> ver = new HashMap<>();
    while (it.hasNext()) {
      Element book = it.next();
      Attribute attribute = book.attribute(0);
      if (con.contains(Integer.parseInt(attribute.getText()))) {
        Iterator<Element> itt = book.elementIterator();
        while (itt.hasNext()) {
          Element book1 = itt.next();
          if (Objects.equals(book1.getName(), "buginformation")) {
            Iterator<Element> itt1 = book1.elementIterator();
            while (itt1.hasNext()) {
              Element book2 = itt1.next();
              if (Objects.equals(book2.getName(), "version")) {
                if (!ver.containsKey(book2.getText())) {
                  List<String> id = new ArrayList<>();
                  id.add(attribute.getText());
                  ver.put(book2.getText(), id);
                } else {
                  ver.get(book2.getText()).add(attribute.getText());
                }
              }
            }
          }
        }
      }
    }
    for (String s : ver.keySet()) {
      System.out.println(s + ":" + ver.get(s));
    }
  }

  @Override
  public void getChanges(String path, String brPath, List<Integer> con) throws IOException {
    XMLParser xp = new XMLParser();
    Document dc = xp.readXml(path);
    Element e = dc.getRootElement();
    Iterator<Element> it = e.elementIterator();
    while (it.hasNext()) {
      Element book = it.next();
      Attribute attribute = book.attribute(0);
      if (con.contains(Integer.parseInt(attribute.getText()))) {
        File file = new File(brPath + attribute.getText() + ".txt");
        if (!file.exists()) {
          file.createNewFile();
        }
        FileWriter fileWritter = new FileWriter(file.getPath(), true);
        Iterator<Element> itt = book.elementIterator();
        while (itt.hasNext()) {
          Element book1 = itt.next();
          if (Objects.equals(book1.getName(), "fixedFiles")) {
            Iterator<Element> itt1 = book1.elementIterator();
            while (itt1.hasNext()) {
              Element book2 = itt1.next();
              if (Objects.equals(book2.getName(), "file")) {
                // System.out.println(book2.getText());
                fileWritter.write(book2.getText());
                fileWritter.write("\n");
              }
            }
          }
        }
        fileWritter.flush();
        fileWritter.close();
      }
    }
  }

  public void getChanges1(String path, String brPath) throws IOException {
    XMLParser xp = new XMLParser();
    Document dc = xp.readXml(path);
    Element e = dc.getRootElement();
    Iterator<Element> it = e.elementIterator();
    while (it.hasNext()) {
      Element book = it.next();
      File file = new File(brPath + book.getText().trim() + ".txt");
      if (!file.exists()) {
        file.createNewFile();
      }
      FileWriter fileWritter = new FileWriter(file.getPath(), true);
      for (Element e1 : book.elements()) {
        for (String s1 : e1.getText().split("\n")) {
          fileWritter.write(s1.trim());
          fileWritter.write("\n");
        }
      }
      fileWritter.flush();
      fileWritter.close();
    }
  }

  public void getChanges2(String path) {
    Map<String, List<String>> map = new HashMap<>();
    XMLParser xp = new XMLParser();
    Document dc = xp.readXml(path);
    Element e = dc.getRootElement();
    Iterator<Element> it = e.elementIterator();
    while (it.hasNext()) {
      Element book = it.next();
      if (!map.containsKey(book.getText().trim())) {
        List<String> list = new ArrayList<>();
        map.put(book.getText().trim(), list);
      }
      for (Element e1 : book.elements()) {
        for (String s1 : e1.getText().split("\n")) {
          if (s1.trim().length() > 2) {
            map.get(book.getText().trim()).add(s1.trim());
          }
        }
      }
    }
    for (String key : map.keySet()) {
      System.out.println(key + ":" + map.get(key) + map.get(key).size());
    }
  }

  public void collectBRbyID(String path, String brPath, int[] id) throws IOException {
    XMLParser xp = new XMLParser();
    Document dc = xp.readXml(path);
    Element e = dc.getRootElement();
    Iterator<Element> it = e.elementIterator();
    while (it.hasNext()) {
      Element book = it.next();
      //      for (Attribute aa : book.attributes()) {
      //        if (!aa.toString().equals("\n")){
      //          System.out.println(aa);
      //        }
      //      }

      //      File file = new File(brPath + attribute.getText() + ".txt");
      //      if (!file.exists()) {
      //        file.createNewFile();
      //      }
      //      FileWriter fileWritter = new FileWriter(file.getPath(), true);
      //      Iterator<Element> itt = book.elementIterator();
      //      while (itt.hasNext()) {
      //        Element book1 = itt.next();
      //        if (Objects.equals(book1.getName(), "buginformation")) {
      //          Iterator<Element> itt1 = book1.elementIterator();
      //          while (itt1.hasNext()) {
      //            Element book2 = itt1.next();
      //            if (Objects.equals(book2.getName(), "summary")) {
      //              fileWritter.write(book2.getText());
      //            } else if (Objects.equals(book2.getName(), "description")) {
      //              fileWritter.write(book2.getText());
      //            }
      //          }
      //        }
      //      }
      //      fileWritter.flush();
      //      fileWritter.close();
    }
  }

  public void getDate(String path, String opPath, String fixPath, List<Integer> list) throws IOException {
    XMLParser xp = new XMLParser();
    Document dc = xp.readXml(path);
    Element e = dc.getRootElement();
    Iterator<Element> it = e.elementIterator();
    while (it.hasNext()) {
      Element book = it.next();
      Attribute attribute = book.attribute(0);
      Attribute attribute1 = book.attribute(1);
      Attribute attribute2 = book.attribute(2);
      if (list.contains(Integer.parseInt(attribute.getText().trim()))) {
        File file = new File(opPath + attribute.getText() + ".txt");
        File file1 = new File(fixPath + attribute.getText() + ".txt");
        if (!file.exists()) {
          file.createNewFile();
        }
        if (!file1.exists()) {
          file1.createNewFile();
        }
        FileWriter fileWriter = new FileWriter(file.getPath(), true);
        FileWriter fileWriter1 = new FileWriter(file1.getPath(), true);
        fileWriter.write(attribute1.getText());
        fileWriter1.write(attribute2.getText());
        fileWriter.flush();
        fileWriter1.flush();
        fileWriter.close();
        fileWriter1.close();
      }
    }
  }

  public static void main(String[] args) throws IOException {
    ProcessBench4BL pb = new ProcessBench4BL();
    String r = DatasetConfig.TOMCAT;
    String path = "dataset/BLIZZARD/" + r + "/repository.xml";
    String cpath = "dataset/BLIZZARD/" + r + "/changeset/";
    String opPath = "dataset/LtR/" + r + "/opendate/";
    String fixPath = "dataset/LtR/" + r + "/fixdate/";
//    pb.getDate(path, opPath, fixPath, BugReportsID.SWT);
    //    List<Integer> con = ResUtils.getId("Bench4BL", "Apache", "CAMEL");
    // System.out.println(con);
    //    pb.getChanges1(path, brPath);
        pb.getChanges(path, cpath, BugReportsID.TOMCAT70);
    //    pb.getChanges(path, brPath, BugReportsID.WFLY);
    //    pb.collectBRbyID(path, brPath, id);
  }
}
