package uc.eecs.indexing.brtracer;

import uc.eecs.indexing.*;
import uc.eecs.irmodel.Property;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.TreeSet;

public class CodeCorpusCreator {

  private final String workDir = Property.getInstance().WorkDir;
  private final String srcDir = Property.getInstance().SourceCodeDir;
  private final String pathSeparator = Property.getInstance().Separator;
  private final String lineSeparator = Property.getInstance().LineSeparator;
  private final String project = Property.getInstance().ProjectName;
  public static int spiltclass = 800;

  public void create() throws Exception {

    // java file listing
    FileDetector detector = new FileDetector("java");
    File[] files = detector.detect(srcDir);

    // prepare file writer
    FileWriter writer = new FileWriter(workDir + pathSeparator + "ClassName.txt");
    FileWriter writeCorpus = new FileWriter(workDir + pathSeparator + "CodeCorpus_OriginClass.txt");
    FileWriter writeImport = new FileWriter(workDir + pathSeparator + "Import.txt");

    FileWriter writeSegCorpus =
        new FileWriter(workDir + pathSeparator + "CodeCorpus.txt"); // for segment
    FileWriter writerSegName =
        new FileWriter(workDir + pathSeparator + "MethodName.txt"); // for segment

    // create corpus each file.
    int segIndex = 0;
    int fileIndex = 0;
    TreeSet<String> nameSet = new TreeSet<>();

    for (File file : files) {
      // file�� corpus �м�.
      Corpus corpus = this.create(file, writeImport);
      if (corpus == null) continue;

      // get full filename
      String fullFileName = corpus.getJavaFileFullClassName();
      if (!fullFileName.endsWith(".java")) fullFileName += ".java";

      // Classs name
      if (project.startsWith("ASPECTJ")) {
        fullFileName = file.getPath().substring(srcDir.length()); // ��θ��� ���� �ν�.
        fullFileName = fullFileName.replace("\\", "/");
        if (fullFileName.startsWith("/"))
          fullFileName = fullFileName.substring(1); // ��θ��� ���� �ν�.
      }
      if (nameSet.contains(fullFileName)) continue;
      nameSet.add(fullFileName);

      // store segment code corpus
      String srccontent = corpus.getContent();
      String[] src = srccontent.split(" ");
      int methodCount = 0;
      while (methodCount == 0 || methodCount * spiltclass < src.length) {
        StringBuilder content = new StringBuilder();
        int i = methodCount * spiltclass;
        while (i < src.length && i < (methodCount + 1) * spiltclass) {
          content.append(src[i]).append(" ");
          i++;
        }
        content.append(corpus.getNameContent());

        int tmp = segIndex + methodCount;
        writerSegName.write(
            tmp + "\t" + fullFileName + "@" + methodCount + ".java" + lineSeparator);
        writeSegCorpus.write(
            fullFileName + "@" + methodCount + ".java" + "\t" + content.toString() + lineSeparator);

        methodCount++;
      }
      writerSegName.flush();
      writeSegCorpus.flush();
      segIndex += methodCount;

      // store in files.
      writer.write(fileIndex + "\t" + fullFileName + lineSeparator);
      writer.flush();
      // writeNames.write(fullFileName + "\t" + corpus.getNameContent() + lineSeparator);
      // writeNames.flush();
      writeCorpus.write(fullFileName + "\t" + corpus.getContent() + lineSeparator);
      writeCorpus.flush();

      fileIndex++;
    }
    Property.getInstance().OriginFileCount = fileIndex;
    Property.getInstance().FileCount = segIndex;
    writerSegName.close();
    writeSegCorpus.close();
    writeCorpus.close();
    writeImport.close();
    // writeNames.close();
    writer.close();
  }

  public Corpus create(File file, FileWriter writeImport) throws IOException {

    FileParser parser = new FileParser(file);

    // make file full name.
    String fileName = parser.getPackageName();
    if (fileName.trim().equals("")) {
      fileName = file.getName();
    } else {
      fileName += "." + file.getName();
    }

    writeImport.write(fileName + "\t");
    parser.getImport(writeImport);
    writeImport.write(lineSeparator);

    fileName = fileName.substring(0, fileName.lastIndexOf("."));

    String[] content = parser.getContent();
    StringBuilder contentBuf = new StringBuilder();
    for (String word : content) {
      String stemWord = Stem.stem(word.toLowerCase());
      if (!(StopWord.isKeyword(word) || StopWord.isEnglishStopword(word))) {

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
    corpus.setNameContent(names);
    return corpus;
  }
}
