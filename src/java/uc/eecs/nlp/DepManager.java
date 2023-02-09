package uc.eecs.nlp;

import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;

import java.util.*;

public class DepManager {

  Properties properties;

  public Properties getProperties() {
    return properties;
  }

  public StanfordCoreNLP getPipeline() {
    return pipeline;
  }

  StanfordCoreNLP pipeline;

  public DepManager() {
    this.properties = setProperties();
    this.pipeline = new StanfordCoreNLP(properties);
  }

  private Properties setProperties() {
    Properties props = new Properties();
    // set the list of annotators to run
    // props.setProperty("annotators", "tokenize,pos,lemma,ner,parse,depparse,coref,kbp,quote");
    // props.setProperty("annotators", "tokenize,pos,lemma,ner,depparse");
    props.setProperty("annotators", "tokenize,pos,lemma,depparse");
    // set a property for an annotator, in this case the coref annotator is being set to use the
    // neural algorithm
    props.setProperty("coref.algorithm", "neural");
    return props;
  }

  public ArrayList<SemanticGraph> getDependencies(List<String> contents) {
    ArrayList<SemanticGraph> dependencyParse = new ArrayList<>();
    for (String sent : contents) {
      CoreDocument document = new CoreDocument(sent);
      pipeline.annotate(document);
      List<CoreSentence> sentences = document.sentences();
      assert sentences != null;
      for (CoreSentence sentence : sentences) {
        SemanticGraph semanticGraph = getDependency(sentence);
        dependencyParse.add(semanticGraph);
      }
    }
    return dependencyParse;
  }

  public ArrayList<SemanticGraph> getDependencies(String contents) {
    ArrayList<SemanticGraph> dependencyParse = new ArrayList<>();
    CoreDocument document = new CoreDocument(contents);
    pipeline.annotate(document);
    List<CoreSentence> sentences = document.sentences();
    assert sentences != null;
    for (CoreSentence sentence : sentences) {
      SemanticGraph semanticGraph = getDependency(sentence);
      dependencyParse.add(semanticGraph);
    }
    return dependencyParse;
  }

  public List<CoreSentence> getDep(String content) {
    CoreDocument document = new CoreDocument(content);
    pipeline.annotate(document);
    return document.sentences();
  }

  protected SemanticGraph getDependency(CoreSentence sentence) {
    return sentence.dependencyParse();
  }

  public ArrayList<SemanticGraph> getDependencyList(String sentence) {
    ArrayList<SemanticGraph> dependencyParse = new ArrayList<>();
    CoreDocument document = new CoreDocument(sentence);
    pipeline.annotate(document);
    List<CoreSentence> sentences = document.sentences();
    assert sentences != null;
    for (CoreSentence sent : sentences) {
      SemanticGraph semanticGraph = getDependency(sent);
      dependencyParse.add(semanticGraph);
    }
    return dependencyParse;
  }

  public static void main(String[] args) {
    DepManager dem = new DepManager();
    CoreDocument document =
        new CoreDocument(
            "The servlet have deadlock.");
    dem.getPipeline().annotate(document);
    List<CoreSentence> sentences = document.sentences();
    for (CoreSentence s : sentences) {
      SemanticGraph sg = dem.getDependency(s);
      Set<IndexedWord> iw = sg.vertexSet();
      System.out.println(sg);
      for (IndexedWord i : iw){
        System.out.println(i.word());
      }
//      for (SemanticGraphEdge se : sg.edgeListSorted()){
//        System.out.println(se);
//      }
      //      for (IndexedWord id :sg.getRoots()){
      //        System.out.println(id);
      //        for (IndexedWord j :sg.getChildList(id)){
      //          System.out.println(j);
      //          System.out.println(sg.getChildList(j));
      //        }
      //      }
      //      for (Iterator<SemanticGraphEdge> it = sg.edgeIterable().iterator(); it.hasNext(); ) {
      //        SemanticGraphEdge s1 = it.next();
      //        System.out.println(s1+"----->"+s1);
      //      }
      //      for (IndexedWord i : iw){
      //        System.out.println(i+"----->"+i.tag());
      //      }
      //      System.out.println(sg.toDotFormat());
    }
  }
}
