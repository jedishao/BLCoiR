package uc.eecs.document;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SourceFileIndex {
  int i = 0;
  List<String> pathList = new ArrayList<>();

  public void getSource(String path, String path1) {
    File file = new File(path);
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      assert files != null;
      for (File file1 : files) {
        if (file1.isDirectory()) {
          getSource(file1.getAbsolutePath(), path1);
        } else {
          if (file1.getAbsolutePath().endsWith(".java")) {
            String[] s = file1.getAbsolutePath().split("/");
            StringBuilder s1 = new StringBuilder();
            int j = 0;
            for (String ss : s) {
              if (j > 5) s1.append("/").append(ss);
              j++;
            }
            if (!pathList.contains(s1.toString())) {
              //System.out.println(s1);
              pathList.add(s1.toString());
              i++;
              System.out.println(i + ":" + file1.getAbsolutePath());
              File newFile = new File(path1 + i + ".java");
              file1.renameTo(newFile);
            }
          }
        }
      }
    } else {
      if (file.getAbsolutePath().endsWith(".java")) {
        String[] s = file.getAbsolutePath().split("/");
        StringBuilder s1 = new StringBuilder();
        int j = 0;
        for (String ss : s) {
          if (j > 4) s1.append("/").append(ss);
          j++;
        }
        if (!pathList.contains(s1.toString())) {
          //System.out.println(s1);
          pathList.add(s1.toString());
          i++;
          System.out.println(i + ":" + file.getAbsolutePath());
//          File newFile = new File("dataset/Bench4BL/Wildfly/WFLY/code/" + i + ".java");
          File newFile = new File(path1 + i + ".java");
          file.renameTo(newFile);
        }
        //
      }
    }
  }

  public void renameFile() {}

  public static void main(String[] args) throws IOException {
    SourceFileIndex sf = new SourceFileIndex();
    String p = "/Users/shuai/Java-projects/h/";
    File f = new File(p);
    if (!f.isDirectory()){
      f.mkdirs();
    }
    sf.getSource("/Users/shuai/Java-projects/vertx/", p);
    //    String sp = "/Users/shuai/Java-projects/camel/camel-1.3.0/";
    //    String[] s = sp.split("/");
    //    for (String ss : s){
    //      System.out.println(ss);
    //    }
  }
}
