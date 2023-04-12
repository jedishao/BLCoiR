package uc.eecs.indexing;

import java.io.File;
import java.util.LinkedList;

/*
File detector from BugLocator
 */
public class FileDetector {

  private LinkedList<File> fileList = new LinkedList<File>();
  private String fileType = null;

  public FileDetector(String fileType) {
    this.fileType = fileType;
  }

  public File[] detect(String absoluteFilePath) {
    File[] files = listFiles(absoluteFilePath);
    if (files != null) classifyFileAndDirectory(files);
    return fileList.toArray(new File[fileList.size()]);
  }

  private File[] listFiles(String absoluteFilePath) {
    File dir = new File(absoluteFilePath);
    return dir.listFiles();
  }

  private void classifyFileAndDirectory(File[] files) {
    for (File file : files) {
      if (file.isDirectory())
        detect(file.getAbsolutePath());
      else
        addFile(file);
    }
  }

  private void addFile(File file) {
    if (this.fileType == null) {
      this.fileList.add(file);
    } else
      addFileBySuffix(file);
  }

  private void addFileBySuffix(File file) {
    if (file.getName().endsWith(fileType)) {
      fileList.addLast(file);
    }
  }
}