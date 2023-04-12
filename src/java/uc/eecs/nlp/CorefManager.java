package uc.eecs.nlp;

import edu.stanford.nlp.coref.CorefCoreAnnotations;
import edu.stanford.nlp.coref.data.CorefChain;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class CorefManager {
  Properties properties;
  StanfordCoreNLP pipeline;

  public CorefManager() {
    this.properties = setProperties();
    this.pipeline = new StanfordCoreNLP(properties);
  }

  private Properties setProperties() {
    Properties props = new Properties();
    props.setProperty("annotators", "tokenize,pos,lemma,ner,parse,coref");
    props.setProperty("coref.algorithm", "neural");
    return props;
  }

  public List<String> corefChain(String text) {
    Annotation document = new Annotation(text);
    List<String> chain = new ArrayList<>();
    pipeline.annotate(document);
    for (CorefChain cc : document.get(CorefCoreAnnotations.CorefChainAnnotation.class).values()) {
      for (CorefChain.CorefMention c : cc.getMentionsInTextualOrder()) {
        chain.add(c.mentionSpan.toLowerCase());
      }
    }
    return chain;
  }
}
