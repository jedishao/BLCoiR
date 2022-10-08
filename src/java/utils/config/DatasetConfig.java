package utils.config;

public class DatasetConfig {

  public static final String HOME_DIR = "./src/main/resources/";
  public static final String INDEX_DIR = "./dataset/Lucene-Index";
  public static final String QUERY_DIR = HOME_DIR + "/src/dataset/Query";
  public static final String QUERY_ST = "proposed-ST.txt";
  public static final String QUERY_NL = "proposed-NL.txt";
  public static final String QUERY_PE = "proposed-PE.txt";
  public static String STOPWORD_DIR = HOME_DIR + "/nlp/";
  public static String CORPUS_INDEX_KEY_MAPPING = HOME_DIR + "/src/dataset/Lucene-Index2File-Mapping";
  public static String GOLDSET_DIR = "C:/Java/BLIZZARD-Replication-Package-ESEC-FSE2018/Goldset";
  public static String CORPUS_DIR = HOME_DIR + "/src/dataset/Corpus";
  public static String TOMCAT = "tomcat70";
  public static String ECF = "ecf";
  public static String DEBUG = "eclipse.jdt.debug";
  public static String J_UI = "eclipse.jdt.ui";
  public static String P_UI = "eclipse.pde.ui";
  public static String CORE = "eclipse.jdt.core";
  public static int MAX_ST_ENTRY_LEN = 10;
  public static int TOKEN_LEN_THRESHOLD = 3;
  public static int MAX_ST_SUGGESTED_QUERY_LEN = 11;
  public static int MAX_NL_SUGGESTED_QUERY_LEN = 8;
  public static int MAX_PE_SUGGESTED_QUERY_LEN = 30;
  public static String Stack_Regex =
      "(.*)?(.+)\\.(.+)(\\((.+)\\.java:\\d+\\)|\\(Unknown Source\\)|\\(Native Method\\))";
}
