package utils.config;

public class QueryConfig {

  public static int MAX_ST_ENTRY_LEN = 10;
  public static int TOKEN_LEN_THRESHOLD = 3;
  public static int MAX_ST_SUGGESTED_QUERY_LEN = 11;
  public static int MAX_NL_SUGGESTED_QUERY_LEN = 8;
  public static int MAX_PE_SUGGESTED_QUERY_LEN = 30;

  public static String STACK_REGEX =
      "(.*)?(.+)\\.(.+)(\\((.+)\\.java:\\d+\\)|\\(Unknown Source\\)|\\(Native Method\\))";
  public static String CODE_REGEX = "(.*)\\((.*)\\);|(.*)=(.*);|public(.*)\\((.*)\\)(.*);";
  public static String LOG_REGEX = "\\w+(\\.\\w+)+|(.*)line: \\d+|(.*)\\[DEBUG\\](.*)";
  public static String LOG_REGEX1 = "(.*)\\w+\\.\\w+(\\.\\w+)+(.*)";
  public static String CAMEL_CASE = "(.*)[A-Z](.*)";
}
