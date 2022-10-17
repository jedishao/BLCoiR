package uc.eecs.nlp;

import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.CoreSentence;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DepManager {

  Properties properties;
  StanfordCoreNLP pipeline;

  public DepManager() {
    this.properties = setProperties();
    this.pipeline = new StanfordCoreNLP(properties);
  }

  private Properties setProperties() {
    Properties props = new Properties();
    // set the list of annotators to run
    // props.setProperty("annotators", "tokenize,pos,lemma,ner,parse,depparse,coref,kbp,quote");
    props.setProperty("annotators", "tokenize,pos,lemma,depparse");
    // set a property for an annotator, in this case the coref annotator is being set to use the
    // neural algorithm
    props.setProperty("coref.algorithm", "neural");
    return props;
  }

  public ArrayList<SemanticGraph> getDependences(List<String> contents) {
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

  protected SemanticGraph getDependency(CoreSentence sentence) {
    return sentence.dependencyParse();
  }
}
