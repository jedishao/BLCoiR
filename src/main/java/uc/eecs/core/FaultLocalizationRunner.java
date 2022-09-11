package uc.eecs.core;

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
import utils.ContentLoader;
import utils.config.DatasetConfig;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class FaultLocalizationRunner {
  int bugID;
  String repository;
  String query;
  // setter getter
  String field = "contents";
  int MAX_TOP = 100000;
  int TOP_K = 10;
  IndexReader reader = null;
  IndexSearcher searcher = null;
  Analyzer analyzer = null;
  ArrayList<String> results;
  public ArrayList<String> goldset;

  public FaultLocalizationRunner(
      int bugID,
      String repository,
      String query,
      IndexReader reader,
      IndexSearcher searcher,
      Analyzer analyzer) {
    this.bugID = bugID;
    this.repository = repository;
    this.query = query;
    this.reader = reader;
    this.searcher = searcher;
    this.analyzer = analyzer;
  }

  public FaultLocalizationRunner(int bugID, String repository, String query) {
    this.bugID = bugID;
    this.repository = repository;
    this.query = query;
    this.results = new ArrayList<>();
    this.goldset = new ArrayList<>();
  }

  public ArrayList<String> VSMBasedSearchList(boolean all) throws IOException, ParseException {
    // lucene index path
    boolean validcase = false;
    ArrayList<String> searchResults = new ArrayList<>();
    String index_path = DatasetConfig.INDEX_DIR + '/' + repository;
    if (reader == null)
      reader = DirectoryReader.open(FSDirectory.open(new File(index_path).toPath()));
    if (searcher == null) searcher = new IndexSearcher(reader);
    if (analyzer == null) analyzer = new StandardAnalyzer();
    QueryParser parser = new QueryParser(field, analyzer);

    if (!query.isEmpty()) {
      Query query_ = parser.parse(query);
      TopDocs results = searcher.search(query_, MAX_TOP);
      ScoreDoc[] hits = results.scoreDocs;
      if (all) {
        for (ScoreDoc item : hits) {
          Document doc = searcher.doc(item.doc);
          String fileURL = doc.get("path");
          fileURL = fileURL.replace('\\', '/');
          searchResults.add(fileURL);
        }
      } else {
        int len = Math.min(hits.length, TOP_K);
        for (int i = 0; i < len; i++) {
          ScoreDoc item = hits[i];
          Document doc = searcher.doc(item.doc);
          String fileURL = doc.get("path");
          fileURL = fileURL.replace('\\', '/');
          searchResults.add(fileURL);
          // System.out.print(item.score+", ");
        }
      }
    }
    try {
      validcase = getGoldSet();
    } catch (Exception exc) {
      // handle the exception
    }
    return searchResults;
  }

  public int getFirstGoldRankClass() throws IOException, ParseException {
    results = VSMBasedSearchList(true);
    int foundIndex = -1;
    try {
      // System.out.println(results);
      ResultRankManager rankFinder = new ResultRankManager(repository, results, goldset);
      foundIndex = rankFinder.getFirstGoldRank();
    } catch (Exception exc) {
      // handle the exception
    }
    return foundIndex;
  }

  protected boolean getGoldSet() {
    // collecting gold set results
    boolean gsfound = true;
    String goldFile = DatasetConfig.GOLDSET_DIR + "/" + repository + "/" + bugID + ".txt";
    // clear the old values
    if (!this.goldset.isEmpty()) this.goldset.clear();

    File f = new File(goldFile);
    if (f.exists()) { // if the solution file exists
      String content = ContentLoader.loadFileContent(goldFile);
      String[] items = content.split("\n");
      for (String item : items) {
        if (!item.trim().isEmpty()) this.goldset.add(item);
      }
    } else {
      gsfound = false;
      // System.out.println("Solution not listed");
    }
    return gsfound;
  }

  public static void main(String[] args) throws IOException, ParseException {
    int bugID = 51467;
    String repository = "tomcat70";
    //    String searchQuery =
    //        "authenticator digest digestauthenticator helper initialisation lazy incorrect method
    // field instructions object processor " +
    //                "compiler reorder initialization threads multiple initialized called
    // guaranteed unsynchronized correct completely";
    String searchQuery =
        "StandardContext Standard Context run runnable start completion remove";
    FaultLocalizationRunner searcher = new FaultLocalizationRunner(bugID, repository, searchQuery);
    System.out.println("First found index:" + searcher.getFirstGoldRankClass());
  }
}
