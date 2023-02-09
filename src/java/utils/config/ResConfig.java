package utils.config;

import java.util.Arrays;
import java.util.List;

public class ResConfig {

  public static String HOME_DIR = "src/resources/";
  public static String NLP_DIR = HOME_DIR + "nlp/";
  public static String STOP_WORD = NLP_DIR + "stop-words-english-total.txt";
  public static String JAVA_KEYWORDS = NLP_DIR + "java-keywords.txt";

  public static List<String> CON_KW =
      Arrays.asList(
          "throw",
          "atomic",
          "thread",
          "lock",
          "race",
          "deadlock",
          "concurrent",
          "concurrency",
          "synchronized",
          "synchronize",
          "synchronous",
          "synchronization",
          "starvation",
          "suspension",
          "livelock",
          "threads",
          "block",
          "starvation",
          "suspension",
          "livelock");
  public static List<String> CON_KW1 =
      Arrays.asList(
          "atomic",
          "thread",
          "race",
          "deadlock",
          "concurrent",
          "concurrency",
          "synchronized",
          "synchronize",
          "synchronous",
          "synchronization",
          "starvation",
          "suspension",
          "livelock",
          "threads",
          "block",
          "starvation",
          "suspension",
          "livelock");
}
