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
import java.nio.charset.StandardCharsets;

public class LuceneIndex {
  String repoName;
  String index;
  String docs;
  public int totalIndexed = 0;

  public LuceneIndex(String indexFolder, String docsFolder) {
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
          for (String s : files) {
            indexDocs(writer, new File(file, s));
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
              new TextField(
                  "contents",
                  new BufferedReader(new InputStreamReader(fis, StandardCharsets.UTF_8))));
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
    long start = System.currentTimeMillis();

    String docs = "/Users/shuai/Java-projects/trino/";
    String index = "src/resources/Lucene_Index/GitHub/trino/";

    LuceneIndex indexer = new LuceneIndex(index, docs);
    indexer.indexCorpusFiles();
    System.out.println("Files indexed:" + indexer.totalIndexed);
    long end = System.currentTimeMillis();
    System.out.println("Time needed:" + (end - start) / 1000 + " s");
  }
}
