package uc.eecs.core.process;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import uc.eecs.br.loader.BRLoader;
import uc.eecs.nlp.DepManager;
import utils.FileURL;
import utils.config.DatasetConfig;
import utils.config.QueryConfig;
import utils.config.ResConfig;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcess {
  String content;
  ArrayList<String> contentList;
  ArrayList<String> conItems;
  DepManager depManager;

  public TextProcess(String content) {
    this.content = content;
    conItems = new ArrayList<>();
  }

  public TextProcess(ArrayList<String> content) {
    this.contentList = content;
    conItems = new ArrayList<>();
  }

  public TextProcess(String content, DepManager depManager) {
    this.content = content;
    this.depManager = depManager;
    conItems = new ArrayList<>();
  }

  public TextProcess(ArrayList<String> contentList, DepManager depManager) {
    this.contentList = contentList;
    this.depManager = depManager;
    conItems = new ArrayList<>();
  }

  public Map<String, List<List<String>>> getDomainItems() {
    //    TextNormalizer textNormalizer = new TextNormalizer(content);
    //    String normalizeText = textNormalizer.normalizeText();
    //    ArrayList<String> sentencesList = getSentencesList(normalizeText);
    if (depManager == null) depManager = new DepManager();
    ArrayList<SemanticGraph> dependencies = depManager.getDependencies(this.contentList);
    return getItemsMap(dependencies);
  }

  public List<String> getDomainItems1() {
    if (depManager == null) depManager = new DepManager();
    ArrayList<SemanticGraph> dependences = depManager.getDependencies(this.contentList);
    return getItemsMap1(dependences);
  }

  protected List<String> getItemsMap1(ArrayList<SemanticGraph> dependences) {
    List<String> itemsMap = new ArrayList<>();
    for (SemanticGraph semanticGraph : dependences) {
      // System.out.println(semanticGraph.getRoots());
      String items = "";
      for (SemanticGraphEdge semanticGraphEdge : semanticGraph.edgeIterable()) {
        String root = semanticGraphEdge.getGovernor().lemma();
        //                if (semanticGraphEdge.getGovernor().tag().startsWith("VB")) {
        //                    if (!itemsMap.contains(root))
        //                        itemsMap.add(root);
        //                }
        if (semanticGraphEdge.getDependent().toString().contains(".")) {
          String m = semanticGraphEdge.getDependent().lemma();
          String[] r1 = m.split("\\.");
          if (r1.length > 1) {
            for (String r2 : r1) {
              // System.out.println(r2);
              if (r2.length() > 1) itemsMap.add(r2);
            }
            //                        for (String r3 : r1) {
            //                            String[] spilt = splitCamelCase(r3);
            //                            if (spilt != null) {
            //                                for (String s2 : spilt) {
            //                                    //System.out.println(s2);
            //                                    if (s2.length() > 1)
            //                                        itemsMap.add(s2);
            //                                }
            //                            }
            //                        }
          }
          //                } else if
          // (semanticGraphEdge.getRelation().getShortName().startsWith("nsubj")) {
          //                    String parent = semanticGraphEdge.getDependent().lemma();
          //
          //                    itemsMap.add(parent);
          //                    String[] spilt = splitCamelCase(parent);
          //                    if (spilt != null) {
          //                        for (String s2 : spilt) {
          //                            if (s2.length() > 1)
          //                                itemsMap.add(s2);
          //                        }
          //                    }
        } else if (semanticGraphEdge.getDependent().tag().startsWith("NN")) {
          String child = semanticGraphEdge.getDependent().lemma();
          // System.out.println(child);
          itemsMap.add(child);
          String[] spilt = splitCamelCase(child);
          if (spilt != null) {
            for (String s2 : spilt) {
              if (s2.length() > 1) itemsMap.add(s2);
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

  protected Map<String, List<List<String>>> getItemsMap(ArrayList<SemanticGraph> dependences) {
    Map<String, List<List<String>>> itemsMap = new HashMap<>();
    for (SemanticGraph semanticGraph : dependences) {
      // System.out.println(semanticGraph.getRoots());
      String items = "";
      for (SemanticGraphEdge semanticGraphEdge : semanticGraph.edgeIterable()) {
        String root = semanticGraphEdge.getGovernor().lemma();
        // if (semanticGraphEdge.getGovernor().tag().startsWith("VB") &&
        // !itemsMap.containsKey(root)) {
        if (!itemsMap.containsKey(root)) {
          // System.out.println("---->"+root);
          List<List<String>> nodes = new LinkedList<>();
          List<String> parent = new ArrayList<>();
          List<String> child = new ArrayList<>();
          nodes.add(parent);
          nodes.add(child);
          itemsMap.put(root, nodes);
        }
        if (semanticGraphEdge.getDependent().toString().contains(".")) {
          String m = semanticGraphEdge.getDependent().lemma();
          // System.out.println(m);
          List<List<String>> node = itemsMap.get(semanticGraphEdge.getGovernor().lemma());
          if (node != null) {
            String[] r1 = m.split("\\.");
            if (r1.length > 1) {
              for (String r2 : r1) {
                // System.out.println(r2);
                if (r2.length() > 1) node.get(0).add(r2);
              }
              for (String r3 : r1) {
                String[] spilt = splitCamelCase(r3);
                if (spilt != null) {
                  for (String s2 : spilt) {
                    // System.out.println(s2);
                    if (s2.length() > 1) node.get(1).add(s2);
                  }
                }
              }
            }
          }
          //                } else if
          // (semanticGraphEdge.getRelation().getShortName().startsWith("nsubj")) {
          //                    String parent = semanticGraphEdge.getDependent().lemma();
          //                    // System.out.println(parent);
          //                    List<List<String>> node =
          // itemsMap.get(semanticGraphEdge.getGovernor().lemma());
          //                    if (node != null) {
          //                        node.get(0).add(parent);
          //                        String[] spilt = splitCamelCase(parent);
          //                        if (spilt != null) {
          //                            for (String s2 : spilt) {
          //                                if (s2.length() > 1)
          //                                    node.get(0).add(s2);
          //                            }
          //                        }
          //                    }
        } else if (semanticGraphEdge.getDependent().tag().startsWith("NN")) {
          String child = semanticGraphEdge.getDependent().lemma();
          // System.out.println(child);
          List<List<String>> node = itemsMap.get(semanticGraphEdge.getGovernor().lemma());
          if (node != null) {
            node.get(1).add(child);
            String[] spilt = splitCamelCase(child);
            if (spilt != null) {
              for (String s2 : spilt) {
                if (s2.length() > 1) node.get(1).add(s2);
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
    //    Pattern pattern = Pattern.compile("(.*)[A-Z](.*)");
    //    Matcher matcher = pattern.matcher("CacassdsCsas");
    //    System.out.println(matcher.matches());
    //    String r = "Caca.";
    //    String[] r1 = r.split("\\.");
    //    System.out.println(r.toLowerCase().contains("."));
    //    for (String r2 : r1){
    //      System.out.println(r2);
    //    }
    String benchName = DatasetConfig.BLIZZARD;
    String repoName = DatasetConfig.ECF;
    //    //    for (int bugID : DatasetIndex.DEBUG_ST) {
    DepManager depManager = new DepManager();
    String brFile = FileURL.brPathAppend(benchName, repoName, 125572);
    ArrayList<String> bugReportContent = BRLoader.loadBugContentList(brFile);
    TextProcess textProcess = new TextProcess(bugReportContent, depManager);
    textProcess.getDomainItems();
  }
}
