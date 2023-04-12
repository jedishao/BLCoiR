package uc.eecs.core;

import uc.eecs.irmodel.buglocator.CodeVectorCreator;
import uc.eecs.irmodel.buglocator.Similarity;

import java.io.IOException;

public class IRModel {

  // IR model from BugLocator
  public void rVSM() throws IOException {
    new CodeVectorCreator().create();
    new Similarity().compute();
  }
}
