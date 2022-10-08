package uc.eecs.Lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import utils.config.DatasetConfig;

import java.io.*;

public class FileIndex {
  String repoName;
  String index;
  String docs;
  public int totalIndexed = 0;

  public FileIndex(String repoName) {
    // initialization
    this.index = DatasetConfig.INDEX_DIR + "/" + repoName;
    this.docs = DatasetConfig.CORPUS_DIR + "/" + repoName;
    // this.makeIndexFolder(repoName);
    System.out.println("Index:" + this.index);
    System.out.println("Docs:" + this.docs);
  }

  public FileIndex(String indexFolder, String docsFolder) {
    this.index = indexFolder;
    this.docs = docsFolder;
  }

  public void indexCorpusFiles() {
    // index the files
    try {
      Directory dir = FSDirectory.open(new File(index).toPath());
      Analyzer analyzer = new StandardAnalyzer();
      // Analyzer analyzer=new EnglishAnalyzer(Version.LUCENE_44);
      IndexWriterConfig config = new IndexWriterConfig(analyzer);
      IndexWriter writer = new IndexWriter(dir, config);
      indexDocs(writer, new File(this.docs));
      writer.close();

    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  protected void indexDocs(IndexWriter writer, File file) {
    // writing to the index file
    if (file.canRead()) {
      if (file.isDirectory()) {
        String[] files = file.list();
        // an IO error could occur
        if (files != null) {
          for (int i = 0; i < files.length; i++) {
            indexDocs(writer, new File(file, files[i]));
          }
        }
      } else {
        FileInputStream fis;
        try {
          fis = new FileInputStream(file);
        } catch (FileNotFoundException fnfe) {
          return;
        }
        try {
          // make a new, empty document
          Document doc = new Document();

          Field pathField = new StringField("path", file.getPath(), Field.Store.YES);
          doc.add(pathField);

          doc.add(
              new TextField("contents", new BufferedReader(new InputStreamReader(fis, "UTF-8"))));
          // System.out.println("adding " + file);

          writer.addDocument(doc);

          totalIndexed++;

        } catch (IOException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        } finally {
          try {
            fis.close();
          } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
      }
    }
  }

  public static void main(String[] args) {
    long start=System.currentTimeMillis();
    String repoName="tomcat70";

    // String docs = DatasetConfig.HOME_DIR + "/Corpus/" + repoName;
    String docs = "C:/java-work/IDEA_workspace/ConBugLoc/dataset/BLIZZARD/ecf/code";
    String index =
        "C:/java-work/IDEA_workspace/ConBugLoc/src/resources/Lucene_Index/BLIZZARD/ecf";

    FileIndex indexer = new FileIndex(index, docs);
    indexer.indexCorpusFiles();
    System.out.println("Files indexed:" + indexer.totalIndexed);
    long end=System.currentTimeMillis();
    System.out.println("Time needed:"+(end-start)/1000+" s");
  }
}
