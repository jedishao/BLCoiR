package uc.eecs.core;

import uc.eecs.indexing.buglocator.CodeCorpusCreator;
import uc.eecs.indexing.buglocator.Indexer;
import uc.eecs.irmodel.BRProcess;
import uc.eecs.irmodel.QueryProcess;

public class Indexing {

  // basic indexing method from BugLocator
  public void basic() throws Exception {
    // get fix link
    new BRProcess().create();
    new QueryProcess().create();
    // code corpus
    new CodeCorpusCreator().create();
    // indexing
    new Indexer().index();
  }
  // segmentation from BRTracer
  public void segment() {}

}