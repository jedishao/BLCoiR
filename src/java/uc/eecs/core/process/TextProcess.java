package uc.eecs.core.process;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import nu.xom.Nodes;
import uc.eecs.nlp.DepManager;
import uc.eecs.nlp.TextNormalizer;
import utils.config.QueryConfig;
import utils.config.ResConfig;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcess {
  String content;
  ArrayList<String> conItems;
  DepManager depManager;

  public TextProcess(String content) {
    this.content = content;
    conItems = new ArrayList<>();
  }

  public TextProcess(String content, DepManager depManager) {
    this.content = content;
    this.depManager = depManager;
    conItems = new ArrayList<>();
  }

  public Map<String, List<List<String>>> getDomainItems() {
    TextNormalizer textNormalizer = new TextNormalizer(content);
    String normalizeText = textNormalizer.normalizeText();
    ArrayList<String> sentencesList = getSentencesList(normalizeText);
    if (depManager == null) depManager = new DepManager();
    ArrayList<SemanticGraph> dependences = depManager.getDependences(sentencesList);
    return getItemsMap(dependences);
  }

  protected Map<String, List<List<String>>> getItemsMap(ArrayList<SemanticGraph> dependences) {
    Map<String, List<List<String>>> itemsMap = new HashMap<>();
    for (SemanticGraph semanticGraph : dependences) {
      String items = "";
      for (SemanticGraphEdge semanticGraphEdge : semanticGraph.edgeIterable()) {
        String root = semanticGraphEdge.getGovernor().lemma();
        // if (semanticGraphEdge.getGovernor().tag().startsWith("VB") &&
        // !itemsMap.containsKey(root)) {
        if (semanticGraphEdge.getGovernor().tag().startsWith("VB") && !itemsMap.containsKey(root)) {
          System.out.println(root);
          List<List<String>> nodes = new LinkedList<>();
          List<String> parent = new ArrayList<>();
          List<String> child = new ArrayList<>();
          nodes.add(parent);
          nodes.add(child);
          itemsMap.put(root, nodes);
        }
        if (semanticGraphEdge.getRelation().getShortName().startsWith("nsubj")) {
          String parent = semanticGraphEdge.getDependent().lemma();
          List<List<String>> node = itemsMap.get(semanticGraphEdge.getGovernor().lemma());
          if (node != null) {
            node.get(0).add(parent);
            String[] spilt = splitCamelCase(parent);
            if (spilt != null) {
              for (String s2 : spilt) {
                if (s2.length() > 1)
                  node.get(0).add(s2);
              }
            }
          }
        } else if (semanticGraphEdge.getDependent().tag().startsWith("NN")) {
          String child = semanticGraphEdge.getDependent().lemma();
          List<List<String>> node = itemsMap.get(semanticGraphEdge.getGovernor().lemma());
          if (node != null) {
            node.get(1).add(child);
            String[] spilt = splitCamelCase(child);
            if (spilt != null) {
              for (String s2 : spilt) {
                if (s2.length() > 1)
                  node.get(1).add(s2);
              }
            }
          }
        }
        if (ResConfig.CON_KW.contains(semanticGraphEdge.getDependent().lemma())) {
          conItems.add(semanticGraphEdge.getDependent().lemma());
        }
      }
    }
    return itemsMap;
  }

  public ArrayList<String> getConcurrencyItems() {
    return this.conItems;
  }

  protected ArrayList<String> getSentencesList(String normalizeText) {
    ArrayList<String> sentencesList = new ArrayList<>();
    String[] sentences = normalizeText.split("\\.+");
    for (String s1 : sentences) {
      sentencesList.add(s1.trim() + ".");
    }
    return sentencesList;
  }

  private String[] splitCamelCase(String word) {
    Pattern pattern = Pattern.compile(QueryConfig.CAMEL_CASE);
    Matcher matcher = pattern.matcher(word);
    if (matcher.matches()) {
      String s = word.replaceAll("[A-Z]", "_$0");
      return s.split("_");
    }
    return null;
  }

  public static void main(String[] args) {
    Pattern pattern = Pattern.compile("(.*)[A-Z](.*)");
    Matcher matcher = pattern.matcher("CacassdsCsas");
    System.out.println(matcher.matches());
    String r = "CacassdsCsas";
  }
}
