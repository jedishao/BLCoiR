package uc.eecs.indexing.buglocator;

import uc.eecs.indexing.*;
import uc.eecs.irmodel.Property;

import java.io.File;
import java.io.FileWriter;
import java.util.TreeSet;

/*
Code Corpus Creator from BugLocator
 */
public class CodeCorpusCreator {
  private final String workDir = Property.getInstance().WorkDir;
  private final String codePath = Property.getInstance().SourceCodeDir;
  private final String pathSeparator = Property.getInstance().Separator;
  private final String lineSeparator = Property.getInstance().LineSeparator;
  private final String projectName = Property.getInstance().ProjectName;

  public CodeCorpusCreator() {}

  public void create() throws Exception {
    int count = 0;
    TreeSet<String> nameSet = new TreeSet<>();

    // File listing
    FileDetector detector = new FileDetector("java"); // java file Filter
    File[] files = detector.detect(codePath);

    // preparing output File.
    FileWriter writeCorpus = new FileWriter(workDir + "/CodeCorpus.txt");
    FileWriter writer = new FileWriter(workDir + "/ClassName.txt");

    // make corpus each file
    for (File file : files) {
      Corpus corpus = this.create(file);
      if (corpus == null) continue;

      // file filtering
      String FullClassName = corpus.getJavaFileFullClassName();
      if (projectName.startsWith("ASPECTJ")) {
        FullClassName = file.getPath().substring(codePath.length());
        FullClassName = FullClassName.replace("\\", "/");
        if (FullClassName.startsWith("/")) FullClassName = FullClassName.substring(1);
      }
      if (nameSet.contains(FullClassName)) continue;

      // Write File.
      if (!FullClassName.endsWith(".java")) FullClassName += ".java";
      writer.write(count + "\t" + FullClassName + this.lineSeparator);
      writeCorpus.write(FullClassName + "\t" + corpus.getContent() + this.lineSeparator);
      writer.flush();
      writeCorpus.flush();

      // Update Filter
      nameSet.add(FullClassName); // corpus.getJavaFileFullClassName());
      count++;
    }
    Property.getInstance().FileCount = count;
    writeCorpus.close();
    writer.close();
  }

  public Corpus create(File file) {
    FileParser parser = new FileParser(file);

    String fileName = parser.getPackageName();
    if (fileName.trim().equals("")) {
      fileName = file.getName();
    } else {
      fileName = fileName + "." + file.getName();
    }
    fileName = fileName.substring(0, fileName.lastIndexOf("."));

    String[] content = parser.getContent();
    StringBuilder contentBuf = new StringBuilder();
    for (String word : content) { // camel case �и� tokenize�� content����.
      String stemWord = Stem.stem(word.toLowerCase());
      if ((!StopWord.isKeyword(word)) && (!StopWord.isEnglishStopword(word))) {
        contentBuf.append(stemWord);
        contentBuf.append(" ");
      }
    }
    String sourceCodeContent = contentBuf.toString();

    String[] classNameAndMethodName = parser.getClassNameAndMethodName();
    StringBuilder nameBuf = new StringBuilder();

    for (String word : classNameAndMethodName) {
      String stemWord = Stem.stem(word.toLowerCase());
      nameBuf.append(stemWord);
      nameBuf.append(" ");
    }
    String names = nameBuf.toString();

    Corpus corpus = new Corpus();
    corpus.setJavaFilePath(file.getAbsolutePath());
    corpus.setJavaFileFullClassName(fileName);
    corpus.setContent(sourceCodeContent + " " + names);
    return corpus;
  }
}
