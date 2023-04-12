package uc.eecs.indexing;
/*
Corpus from BugLocator
 */
public class Corpus {
  private String javaFileFullClassName;
  private String javaFilePath;
  private String content;

  private String nameContent; // added feild

  public String getJavaFileFullClassName() {
    return this.javaFileFullClassName;
  }

  public void setJavaFileFullClassName(String javaFileFullClassName) {
    this.javaFileFullClassName = javaFileFullClassName;
  }

  public String getJavaFilePath() {
    return this.javaFilePath;
  }

  public void setJavaFilePath(String javaFilePath) {
    this.javaFilePath = javaFilePath;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getNameContent() {
    return nameContent;
  }

  public void setNameContent(String content) {
    this.nameContent = content;
  }
}
