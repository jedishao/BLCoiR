package utils.config;

public class DatasetConfig {

  public static final String DATASET_DIR = "dataset/";
  public static final String BRTEXT_DIR = "BRText/";
  public static final String QUERY_DIR = "query/";
  public static final String INDEX_DIR = "Lucene/Lucene_Index";

  // bench name
  public static String BLIZZARD = "BLIZZARD";
  public static String BENCH4BL = "Bench4BL";
  public static String LTR = "LtR";
  public static String GITHUB = "GitHub";

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
  public static String C_IO = "Commons/IO";
  public static String C_LANG = "Commons/LANG";
  public static String C_MATH = "Commons/MATH";

  // projects from Bench4BL (Spring)
  public static String SP_AMQP = "Spring/AMQP";
  public static String SP_BATCH = "Spring/BATCH";
  public static String SP_DATACMNS = "Spring/DATACMNS";
  public static String SP_DATAGRAPH = "Spring/DATAGRAPH";
  public static String SP_DATAJPA = "Spring/DATAJPA";
  public static String SP_DATAMONGO = "Spring/DATAMONGO";
  public static String SP_DATAREDIS = "Spring/DATAREDIS";
  public static String SP_SEC = "Spring/SEC";
  public static String SP_SECOAUTH = "Spring/SECOAUTH";
  public static String SP_SGF = "Spring/SGF";
  public static String SP_SHDP = "Spring/SHDP";
  public static String SP_SPR = "Spring/SPR";
  public static String SP_SWF = "Spring/SWF";
  public static String SP_SWS = "Spring/SWS";

  // projects from Bench4BL (Wildfly)
  public static String W_WFCORE = "Wildfly/WFCORE";
  public static String W_WFLY = "Wildfly/WFLY";

  // projects from LtR
  public static String ASPECTJ = "AspectJ";
  public static String BIRT = "Birt";
  public static String PLATFORM = "Platform";
  public static String SWT = "SWT";

  // projects from GitHub
  public static String DRUID = "druid";
  public static String GRPC = "grpc";
  public static String PRESTO = "presto";
  public static String PULSAR = "pulsar";
  public static String REDISSON = "redisson";
  public static String ROCKETMQ = "rocketmq";
  public static String TRINO = "trino";
  public static String VERTX = "vertx";

  public static String AMALGAM = "AmaLgam";
  public static String BLIA = "BLIA";
  public static String BLUIR = "BLUiR";
  public static String BUGLOCATOR = "BugLocator";
  public static String BRTRACER = "BRTracer";
  public static String LOCUS = "Locus";
  public static String BLCOIR = "BLCoiR";
  public static String BLCOIR_PLUS = "BLCoiR+";
  public static int MAX_ST_ENTRY_LEN = 10;
  public static int TOKEN_LEN_THRESHOLD = 3;
  public static int MAX_ST_SUGGESTED_QUERY_LEN = 11;
  public static int MAX_NL_SUGGESTED_QUERY_LEN = 8;
  public static int MAX_PE_SUGGESTED_QUERY_LEN = 30;
  public static int BENCH4BL_SIZE = 278;
  public static int BLIZZARD_SIZE = 163;
  public static int LTR_SIZE = 50;
  public static int GITHUB_SIZE = 201;
  public static int NL_SIZE = 83;
  public static int ST_SIZE = 206;
  public static int STP_SIZE = 147;
  public static int STN_SIZE = 59;
  public static int PE_SIZE = 403;
}
