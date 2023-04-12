package uc.eecs.irmodel;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BRProcess {
  private final String workDir = Property.getInstance().WorkDir;
  private final String pathSeparator = Property.getInstance().Separator;
  private final String lineSeparator = Property.getInstance().LineSeparator;
  private final List<Integer> idList = Property.getInstance().IDList;

  public void create() throws IOException {
    // Create Corpus and Sort
    ArrayList<Bug> list = this.parseXML();
    Property.getInstance().BugReportCount = idList.size();
    // summarize corpus information.
    FileWriter writer = new FileWriter(workDir + pathSeparator + "SortedId.txt");
    FileWriter writerFix = new FileWriter(workDir + pathSeparator + "FixLink.txt");

    for (Bug bug : list) {
      writer.write(bug.getBugId() + "\t" + bug.getFixDate() + lineSeparator);
      writer.flush();
      for (String fixName : bug.set) {
        writerFix.write(bug.getBugId() + "\t" + fixName + lineSeparator);
        writerFix.flush();
      }
    }
    writer.close();
    writerFix.close();
  }

  private ArrayList<Bug> parseXML() {
    ArrayList<Bug> list = new ArrayList<>();
    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    try {
      DocumentBuilder domBuilder = domFactory.newDocumentBuilder();
      InputStream is = new FileInputStream(Property.getInstance().BugFilePath);
      Document doc = domBuilder.parse(is);
      Element root = doc.getDocumentElement();
      NodeList bugRepository = root.getChildNodes();
      if (bugRepository == null) return list;

      for (int i = 0; i < bugRepository.getLength(); i++) {
        Node bugNode = bugRepository.item(i);
        if (bugNode.getNodeType() != Node.ELEMENT_NODE) continue;

        String bugId = bugNode.getAttributes().getNamedItem("id").getNodeValue();
        if (idList.contains(Integer.parseInt(bugId.trim()))) {
          Bug bug = new Bug();
          bug.setBugId(bugId);
          for (Node node = bugNode.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() != Node.ELEMENT_NODE) continue;

            if (node.getNodeName().equals("fixedFiles")) {
              NodeList _l = node.getChildNodes();
              for (int j = 0; j < _l.getLength(); j++) {
                Node _n = _l.item(j);
                if (_n.getNodeName().equals("file")) {
                  String fileName = _n.getTextContent();
                  bug.addFixedFile(fileName);
                }
              }
            }
          }
          list.add(bug);
        }
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
    return list;
  }
}
