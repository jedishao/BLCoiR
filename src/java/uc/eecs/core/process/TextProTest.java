package uc.eecs.core.process;

import edu.stanford.nlp.semgraph.SemanticGraph;
import org.jgraph.graph.DefaultEdge;
import org.jgrapht.graph.DefaultDirectedGraph;
import uc.eecs.br.BRClassification;
import uc.eecs.br.loader.BRLoader;
import uc.eecs.br.loader.StackTraceLoader;
import uc.eecs.core.graph.KG4;
import uc.eecs.core.query.GraphParser;
import uc.eecs.nlp.DepManager;
import uc.eecs.nlp.TextNormalizer;
import utils.MiscUtility;
import utils.config.DatasetConfig;
import utils.config.QueryConfig;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProTest {

  public String getST(ArrayList<String> reportContentList) {
    ArrayList<String> keywords = new ArrayList<>();
    StackTraceLoader stl = new StackTraceLoader();
    ArrayList<String> stack_trace = stl.getStackTrace(reportContentList);
    StackTraceSelector sts = new StackTraceSelector(stack_trace);
    // salient items after pagerank
    ArrayList<String> salientItems = sts.getSalientItemsFromST();
    // new
    // HashMap<String, Double> itemMapC = sts.getSalientClasses();
    //    HashSet<String> exceptions = ExceptionLoader.getExceptionMessages(reportContent);
    //
    //    // adding the exception
    //    if (!exceptions.isEmpty()) {
    //      hasException = true;
    //      keywords.addAll(exceptions);
    //    }
    // adding the trace keywords
    keywords.addAll(salientItems);
    // new
    // keywords.addAll(itemMapC.keySet());
    return MiscUtility.list2Str(keywords);
  }

  private String[] splitCamelCase(String word) {
    Pattern pattern = Pattern.compile(QueryConfig.CAMEL_CASE);
    Matcher matcher = pattern.matcher(word);
    if (matcher.matches()) {
      String s = word.replaceAll("[A-Z]", "_$0");
      return s.split("_");
    }
    return null;
  }

  public ArrayList<Integer> testClassification(int[] list) {
    ArrayList<Integer> idList = new ArrayList<>();
    for (int id : list) {
      String brPath =
          DatasetConfig.DATASET_DIR
              + DatasetConfig.BLIZZARD
              + "/"
              + DatasetConfig.TOMCAT
              + "/BR/"
              + id
              + ".txt";
      ArrayList<String> content = BRLoader.loadBugContentList(brPath);
      BRClassification brClassification = new BRClassification();
      String flag = brClassification.classify(content);
      if (Objects.equals(flag, "ST")) {
        idList.add(id);
      }
    }
    return idList;
  }

  public static void main(String[] args) throws IOException {
    TextProTest tpt = new TextProTest();
    int[] ecf = {
      125572, 221053, 223484, 229237, 232530, 266262, 267035, 269091, 273357, 280601, 299849,
      303125, 313519, 319259, 323208, 326949, 327749, 337973, 341818, 348487, 349176, 425868,
      432662, 463008
    };
    int[] core = {
      29585, 58314, 135997, 177962, 292087, 362591, 375248, 375326, 381172, 382353, 400710, 401853,
      406175, 406388, 406584, 406641, 406773, 416885, 421536, 421620, 422515, 422516, 422800,
      422801, 424444, 424628, 425712, 427207, 427216, 427255, 427367, 428552, 429733, 429966,
      430035, 431275, 432531, 448043, 448112, 464570, 471034, 472178, 481564
    };
    int[] debug = {
      8683, 10009, 10096, 12740, 21769, 22842, 23328, 27679, 28195, 33297, 33712, 53247, 65107,
      65149, 66413, 76860, 77190, 78534, 79896, 80191, 84259, 89643, 89935, 99416, 101118, 105573,
      125868, 137513, 148006, 165174, 171075, 178912, 182237, 184862, 188339, 197282, 229394,
      239950, 261837, 274727, 275614, 312038, 434722, 442645, 446040, 478019, 499385
    };
    int[] jui = {
      29531, 75843, 125013, 133141, 184222, 199135, 245254, 258072, 263173, 267554, 272742, 291996,
      296794, 296998, 300536, 303313, 308898, 319689, 331115, 371987, 377141, 388724, 407759,
      411841, 417765, 424614, 425277, 427169, 471034
    };
    int[] pui = {133072, 158326, 186963, 220745, 304426};
    int[] tomcat = {
      37929, 39769, 39875, 40380, 41166, 42530, 42648, 42650, 43435, 43470, 44620, 45453, 48050,
      48399, 48421, 48454, 48498, 48648, 48790, 48843, 48870, 49144, 49150, 49170, 49905, 49985,
      49987, 50138, 50230, 50352, 50353, 50496, 50582, 50747, 50957, 51095, 51124, 51185, 51396,
      51445, 51644, 51653, 51860, 52042, 52055, 52671, 52719, 52858, 52998, 52999, 53173, 53225,
      53356, 53430, 53450, 53498, 53584, 53735, 53871, 54022, 54045, 54086, 54256, 54521, 55357,
      55521, 55524, 55582, 55799, 55960, 55996, 56042, 56082, 56172, 56472, 56518, 56577, 56653,
      56857, 56905, 57047, 57265, 57420, 57490, 57544, 57621, 57683, 57779, 57943, 57959, 57969,
      58179, 58946, 59015, 59849, 60043
    };
    //    System.out.println(tomcat.length);
    //    System.out.println(Arrays.toString(tomcat));
    //    System.out.println(tpt.testClassification(tomcat).size());
    //    System.out.println(tpt.testClassification(tomcat));
    int[] camel = {
            140, 510, 574, 702, 715, 803, 1199, 1270, 1401, 1510, 1565, 1583, 1604, 1641, 1650, 1670, 1795,
            1930, 1966, 2049, 2175, 2496, 2824, 2935, 2986, 2994, 3216, 3277, 3349, 3493, 3528, 7116,
            7161, 7239,
            7318, 7321, 7364, 7556, 7715, 7795, 7968, 7973, 7988, 8088, 8134, 8146, 8200, 8241, 8268,
            8353, 8673,
            8713, 8885, 8954, 9199, 9311, 9319, 9331, 9738, 9951, 9970, 10024, 10215, 10229, 10273
    };
    DepManager depManager = new DepManager();
    KG4 que = new KG4();
    for (int id : camel) {
      String brPath =
          DatasetConfig.DATASET_DIR
              + DatasetConfig.BENCH4BL
              + "/"
              + DatasetConfig.A_CAMEL
              + "/BR/"
              + id
              + ".txt";
      StringBuilder sb = new StringBuilder();
      String con = BRLoader.loadBugContent(brPath);
      ArrayList<String> content = BRLoader.loadBugContentList(brPath);
      BRClassification brClassification = new BRClassification();
      String flag = brClassification.classify(content);
      if (Objects.equals(flag, "ST")) {
        sb.append(tpt.getST(content));
      } else {
        TextNormalizer tn = new TextNormalizer(content);
        ArrayList<SemanticGraph> dependencies = depManager.getDependencies(tn.removeHttp());
        DefaultDirectedGraph<String, DefaultEdge> graph = que.getItems(dependencies);
        GraphParser gp = new GraphParser(graph);
        sb.append(gp.getQuery());
      }
      String tile = BRLoader.loadBRTitle(brPath);
      String[] s = tile.split(" ");
      int i = 0;
      String[] sym = {
        "(", ")", ":", "[", "]", "}", "{", "#", "-", "|", "%", "@", ".", "+", "=", "\\", "/", "#",
        "*", "?", "^", "$", "\"", "'", "<", ">", ","
      };
      for (String v : s) {
        if (i > 1) {
          Pattern num = Pattern.compile("(.*)\\d+(.*)");
          Matcher m = num.matcher(v);
          if (!m.matches()) {
            for (String s1 : sym) {
              v = v.replace(s1, " ");
            }
            if (v.length() > 2) sb.append(v).append(" ");
          }
        }
        i++;
      }
      System.out.println(sb);
    }
  }
}
