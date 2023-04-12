package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

public class XMLParser {

  public static void writeXml() {
    try {
      SAXReader reader = new SAXReader();
      Document dc = reader.read(new File(""));
      Element e = dc.getRootElement();

      Iterator<Element> it = e.elementIterator();
      System.out.println(it.hasNext());
      String fileName = "C:/Users/Shao/Desktop/index/BugLocator/data/zxing/";
      while (it.hasNext()) {
        Element book = (Element) it.next();
        String bugId = "";
        String title = "";
        String content = "";
        List<Attribute> atts = book.attributes();
        for (Attribute att : atts) {
          if (Objects.equals(att.getName(), "id")){
            bugId = att.getValue();
          }
        }
        Iterator<Element> itt = book.elementIterator();
        while (itt.hasNext()) {
          Element b = itt.next();
          //System.out.println(b.elements());
          for (Element element : b.elements()){
            if (Objects.equals(element.getName(), "summary")){
              title = element.getStringValue();
            }else if (Objects.equals(element.getName(), "description")) {
              content = element.getStringValue();
            }
          }
//          if (Objects.equals(b.attribute(b.attributes().size() - 1).getValue(), "bug_id")){
//            bugId = b.getText();
//            System.out.println(bugId);
//          }else if (Objects.equals(b.attribute(b.attributes().size() - 1).getValue(), "summary")){
//            title = b.getText();
//          }else if (Objects.equals(b.attribute(b.attributes().size() - 1).getValue(),"description")){
//            content = b.getText();
//          }
        }

//        File file = new File(fileName+bugId+".txt");
//        file.createNewFile();
//        Path path = Paths.get(fileName+bugId+".txt");
//        System.out.println(path);
//        BufferedWriter writer =
//                Files.newBufferedWriter(path,
//                        StandardCharsets.UTF_8,
//                        StandardOpenOption.APPEND);
//        writer.write(title);
//        writer.write(content);
//        writer.close();
      }
    } catch (Exception e) {
      // TODO: handle exception
    }
  }

  public Document readXml(String targetPath){
    SAXReader reader = new SAXReader();
    Document dc;
    try {
      dc = reader.read(new File(targetPath));
      return dc;
    } catch (DocumentException e) {
      e.printStackTrace();
    }
    return null;
  }

  public static void main(String[] args) {

  }
}
