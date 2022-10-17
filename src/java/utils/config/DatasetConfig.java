package utils.config;

public class DatasetConfig {

  public static final String HOME_DIR = "dataset/";
  public static final String INDEX_DIR = "src/resources/Lucene_Index";

  public static final String QUERY_DIR = HOME_DIR + "/src/dataset/Query";
  public static final String QUERY_ST = "proposed-ST.txt";
  public static final String QUERY_NL = "proposed-NL.txt";
  public static final String QUERY_PE = "proposed-PE.txt";
  public static String STOPWORD_DIR = HOME_DIR + "/nlp/";
  public static String CORPUS_INDEX_KEY_MAPPING =
      HOME_DIR + "/src/dataset/Lucene-Index2File-Mapping";
  public static String GOLDSET_DIR = "C:/Java/BLIZZARD-Replication-Package-ESEC-FSE2018/Goldset";
  public static String CORPUS_DIR = HOME_DIR + "/src/dataset/Corpus";

  // bench name
  public static String BLIZZARD = "BLIZZARD";
  public static String BENCH4BL = "Bench4BL";
  public static String BUGLOCATOR = "BugLocator";
  public static String LTR = "LtR";

  // projects from BLIZZARD
  public static String TOMCAT = "tomcat70";
  public static String ECF = "ecf";
  public static String DEBUG = "eclipse.jdt.debug";
  public static String J_UI = "eclipse.jdt.ui";
  public static String P_UI = "eclipse.pde.ui";
  public static String CORE = "eclipse.jdt.core";

  // projects from Bench4BL (Apache)
  public static String A_CAMEL = "Apache/CAMEL";
  public static String A_HBASE = "Apache/HBASE";
  public static String A_HIVE = "Apache/HIVE";

  // projects from Bench4BL (Commons)
  public static String C_CODEC = "Commons/CODEC";
  public static String C_COLLECTIONS = "Commons/COLLECTIONS";
  public static String C_COMPRESS = "Commons/COMPRESS";
  public static String C_CONFIGURATION = "Commons/CONFIGURATION";
  public static String C_CRYPTO = "Commons/CRYPTO";
  public static String C_CSV = "Commons/CSV";
  public static String C_IO = "Commons/IO";
  public static String C_LANG = "Commons/LANG";
  public static String C_MATH = "Commons/MATH";

  // projects from Bench4BL (JBoss)
  public static String JB_ENTESB = "JBoss/ENTESB";
  public static String JB_JBMETA = "JBoss/JBMETA";

  // projects from Bench4BL (Spring)
  public static String SP_AMQP = "Spring/AMQP";
  public static String SP_ANDROID = "Spring/ANDROID";
  public static String SP_BATCH = "Spring/BATCH";
  public static String SP_BATCHADM = "Spring/BATCHADM";
  public static String SP_DATACMNS = "Spring/DATACMNS";
  public static String SP_DATAGRAPH = "Spring/DATAGRAPH";
  public static String SP_DATAJPA = "Spring/DATAJPA";
  public static String SP_DATAMONGO = "Spring/DATAMONGO";
  public static String SP_DATAREDIS = "Spring/DATAREDIS";
  public static String SP_DATAREST = "Spring/DATAREST";
  public static String SP_LDAP = "Spring/LDAP";
  public static String SP_MOBILE = "Spring/MOBILE";
  public static String SP_ROO = "Spring/ROO";
  public static String SP_SEC = "Spring/SEC";
  public static String SP_SECOAUTH = "Spring/SECOAUTH";
  public static String SP_SGF = "Spring/SGF";
  public static String SP_SHDP = "Spring/SHDP";
  public static String SP_SHL = "Spring/SHL";
  public static String SP_SOCIAL = "Spring/SOCIAL";
  public static String SP_SOCIALFB = "Spring/SOCIALFB";
  public static String SP_SOCIALLI = "Spring/SOCIALLI";
  public static String SP_SOCIALTW = "Spring/SOCIALTW";
  public static String SP_SPR = "Spring/SPR";
  public static String SP_SWF = "Spring/SWF";
  public static String SP_SWS = "Spring/SWS";

  // projects from Bench4BL (Wildfly)
  public static String W_ELF = "Wildfly/ELF";
  public static String W_SWARM = "Wildfly/SWARM";
  public static String W_WFARQ = "Wildfly/WFARQ";
  public static String W_WFCORE = "Wildfly/WFCORE";
  public static String W_WFLY = "Wildfly/WFLY";
  public static String W_WFMP = "Wildfly/WFMP";

  // projects from BugLocator
  public static String ZXING = "ZXing";

  // projects from LtR
  public static String ASPECTJ = "AspectJ";
  public static String BIRT = "Birt";
  public static String PLATFORM = "Platform";
  public static String SWT = "SWT";

  public static int MAX_ST_ENTRY_LEN = 10;
  public static int TOKEN_LEN_THRESHOLD = 3;
  public static int MAX_ST_SUGGESTED_QUERY_LEN = 11;
  public static int MAX_NL_SUGGESTED_QUERY_LEN = 8;
  public static int MAX_PE_SUGGESTED_QUERY_LEN = 30;

}
