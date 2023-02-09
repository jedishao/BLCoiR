package uc.eecs.Lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import utils.config.DatasetConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class LuceneSearch {
  int bugID;
  String benchName;
  String repository;
  String query;
  String field = "contents";
  int MAX_TOP = 100000;
  IndexReader reader = null;
  IndexSearcher searcher = null;
  Analyzer analyzer = null;
  ArrayList<String> results;

  public LuceneSearch(int bugID, String benchName, String repository, String query) {
    this.bugID = bugID;
    this.benchName = benchName;
    this.repository = repository;
    this.query = query;
    this.results = new ArrayList<>();
  }

  public ArrayList<String> SearchListAll() throws IOException, ParseException {

    ArrayList<String> searchResults = new ArrayList<>();
    String index_path = DatasetConfig.INDEX_DIR + '/' + benchName + "/" + repository;
    if (reader == null)
      reader = DirectoryReader.open(FSDirectory.open(new File(index_path).toPath()));
    if (searcher == null) searcher = new IndexSearcher(reader);
    if (analyzer == null) analyzer = new StandardAnalyzer();
    QueryParser parser = new QueryParser(field, analyzer);

    if (!query.isEmpty()) {
      Query query_ = parser.parse(query);
      TopDocs results = searcher.search(query_, MAX_TOP);
      ScoreDoc[] hits = results.scoreDocs;
      for (ScoreDoc item : hits) {
//        System.out.println(item.score);
        Document doc = searcher.doc(item.doc);
        String fileURL = doc.get("path");
        fileURL = fileURL.replace('\\', '/');
        searchResults.add(fileURL);
      }
    }
    return searchResults;
  }

  /**
   * @param TOP_K
   * @return TOP_K results
   * @throws IOException
   * @throws ParseException
   */
  public ArrayList<String> SearchListTopK(int TOP_K) throws IOException, ParseException {

    ArrayList<String> searchResults = new ArrayList<>();
    String index_path = DatasetConfig.INDEX_DIR + '/' + benchName + "/" + repository;
    if (reader == null)
      reader = DirectoryReader.open(FSDirectory.open(new File(index_path).toPath()));
    if (searcher == null) searcher = new IndexSearcher(reader);
    if (analyzer == null) analyzer = new StandardAnalyzer();
    QueryParser parser = new QueryParser(field, analyzer);

    if (!query.isEmpty()) {
      Query query_ = parser.parse(query);
      TopDocs results = searcher.search(query_, MAX_TOP);
      ScoreDoc[] hits = results.scoreDocs;

      int len = Math.min(hits.length, TOP_K);
      for (int i = 0; i < len; i++) {
        ScoreDoc item = hits[i];
        Document doc = searcher.doc(item.doc);
        String fileURL = doc.get("path");
        fileURL = fileURL.replace('\\', '/');
        searchResults.add(fileURL);
      }
    }
    return searchResults;
  }
}
