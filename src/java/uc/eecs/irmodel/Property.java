package uc.eecs.irmodel;

import java.util.List;

public class Property {
  private static Property p = null;
  public final String Separator;
  public final String LineSeparator;

  public final String BugFilePath;
  public final String SourceCodeDir;
  public final String WorkDir;
  public final String QueryDir;
  public final List<Integer> IDList;
  public int FileCount;
  public int WordCount;
  public int BugReportCount;
  public int BugTermCount;
  public final String OutputFile;
  public int OriginFileCount;
  public int Offset; // for AspectJ    //old  Aspectj_filename_offset;
  public String ProjectName;

  public static void createInstance(
      String projectName,
      String bugFilePath,
      String sourceCodeDir,
      String workDir,
      String outputFile,
      String queryDir,
      List<Integer> IDList) {
    if (p == null)
      p = new Property(projectName, bugFilePath, sourceCodeDir, workDir, outputFile, queryDir, IDList);
  }

  public static Property getInstance() {
    return p;
  }

  private Property(
      String projectName,
      String bugFilePath,
      String sourceCodeDir,
      String workDir,
      String outputFile,
      String queryDir,
      List<Integer> IDList) {
    this.BugFilePath = bugFilePath;
    this.SourceCodeDir = sourceCodeDir;
    this.WorkDir = workDir;
    this.QueryDir = queryDir;
    this.OutputFile = outputFile;
    this.ProjectName = projectName;
    this.IDList = IDList;
    this.Offset = sourceCodeDir.length();

    this.Separator = System.getProperty("file.separator");
    this.LineSeparator = System.getProperty("line.separator");
  }
}
