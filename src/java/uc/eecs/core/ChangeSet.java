package uc.eecs.core;

import org.dom4j.Document;
import org.dom4j.Element;
import utils.XMLParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;

public class ChangeSet {

  public ArrayList<String> getChangeSet(String benchName, String repository, int bugId) {
    XMLParser xp = new XMLParser();
    Document dc = xp.readXml("dataset/" + benchName + "/" + repository + "/changeset.xml");
    String changes = null;
    ArrayList<String> changeset = new ArrayList<>();
    Element e = dc.getRootElement();
    Iterator<Element> it = e.elementIterator();
    while (it.hasNext()) {
      Element book = it.next();
      if (Objects.equals(book.getName(), "id")) {
        if (Objects.equals(book.getText().trim(), Integer.toString(bugId))) {
          changes = book.element("files").getText().trim();
        }
      }
    }
    assert changes != null;
    String[] set = changes.split("\n");
    for (String s : set) {
      changeset.add(s.trim());
    }
    return changeset;
  }

  public static void main(String[] args) {

  }
}
