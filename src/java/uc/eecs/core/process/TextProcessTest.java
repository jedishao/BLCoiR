package uc.eecs.core.process;

import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import uc.eecs.br.BRClassification;
import uc.eecs.br.loader.BRLoader;
import uc.eecs.core.FaultLocalizationRunner;
import uc.eecs.nlp.DepManager;
import utils.ContentLoader;
import utils.config.DatasetConfig;
import utils.config.QueryConfig;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TextProcessTest {

  public static List<Integer> idList =
      Arrays.asList(
          125572, 199938, 207530, 218584, 221053, 223484, 229237, 232530, 244908, 257017, 265174,
          265312, 265413, 265415, 266262, 267035, 267782, 269091, 270644, 271323, 273357, 273819,
          280601, 299849, 303125, 313519, 319259, 321468, 321469, 323208, 326949, 327749, 337973,
          341818, 348487, 349176, 397877, 425868, 432662, 463008);

  public static List<Integer> idList_ST =
      Arrays.asList(
          232530, 266262, 267035, 303125, 313519, 319259, 323208, 337973, 348487, 349176, 425868,
          432662);
  public static List<Integer> idList_CODE =
      Arrays.asList(229237, 273357, 280601, 299849, 326949, 327749, 341818, 463008);

  public static List<Integer> idList_LOG = Arrays.asList(125572, 221053, 223484, 269091);

  public static List<Integer> idList_NL =
      Arrays.asList(
          199938, 207530, 218584, 244908, 257017, 265174, 265312, 265413, 265415, 267782, 270644,
          271323, 273819, 321468, 321469, 397877);

  public ArrayList<Integer> getId() {
    String brPath =
        DatasetConfig.DATASET_DIR + DatasetConfig.BENCH4BL + "/" + DatasetConfig.A_CAMEL + "/BR/";
    File dir = new File(brPath);
    File[] br = dir.listFiles();
    assert br != null;
    ArrayList<Integer> fn = new ArrayList<>();
    for (File f : br) {
      String[] ff = f.getName().split("\\.");
      if (!Objects.equals(ff[0], "")) {
        fn.add(Integer.parseInt(ff[0]));
      }
      //      ArrayList<String> bugReportList = BRLoader.loadBugReportList(f.getPath());
      //      for (String brr : bugReportList){
      //        System.out.println(brr);
      //      }
    }
    Collections.sort(fn);
    System.out.println(fn);
    return fn;
  }

  public void test(int id) {
    String brPath =
        DatasetConfig.DATASET_DIR
            + DatasetConfig.BLIZZARD
            + "/"
            + DatasetConfig.TOMCAT
            + "/BR/"
            + id
            + ".txt";
    ArrayList<String> content = BRLoader.loadBugContentList(brPath);
    int i = 0;
    for (String s : content) {
      System.out.println(i);
      System.out.println(s);
      i++;
    }
    System.out.println(content.size());
    DepManager depManager = new DepManager();
    System.out.println(depManager.getDependencies(content).size());
    int j = 0;
    for (SemanticGraph sg : depManager.getDependencies(content)) {
      System.out.println(j);
      System.out.println(sg);
      j++;
    }
  }

  public ArrayList<Integer> testClassification(List<Integer> list) {
    ArrayList<Integer> idList = new ArrayList<>();
    for (int id : list) {
      String brPath =
          DatasetConfig.DATASET_DIR
              + DatasetConfig.BLIZZARD
              + "/"
              + DatasetConfig.ECF
              + "/BR/"
              + id
              + ".txt";
      ArrayList<String> content = BRLoader.loadBugContentList(brPath);
      BRClassification brClassification = new BRClassification();
      String flag = brClassification.classify(content);
      if (!Objects.equals(flag, "NL")) {
        idList.add(id);
      }
    }
    return idList;
  }

  public Map<String, List<List<String>>> getDomainItems(List<String> list) {
    //    TextNormalizer textNormalizer = new TextNormalizer(content);
    //    String normalizeText = textNormalizer.normalizeText();
    //    ArrayList<String> sentencesList = getSentencesList(normalizeText);
    DepManager depManager = new DepManager();
    ArrayList<SemanticGraph> dependencies = depManager.getDependencies(list);
    return getItemsMap(dependencies);
  }

  public List<String> getItems(String list, DepManager depManager) {
    //    TextNormalizer textNormalizer = new TextNormalizer(content);
    //    String normalizeText = textNormalizer.normalizeText();
    //    ArrayList<String> sentencesList = getSentencesList(normalizeText);
    ArrayList<SemanticGraph> dependencies = depManager.getDependencies(list);
    return getItemsMap1(dependencies);
  }

  protected List<String> getItemsMap1(ArrayList<SemanticGraph> dependences) {
    List<String> itemsMap = new ArrayList<>();
    for (SemanticGraph semanticGraph : dependences) {
      System.out.println(semanticGraph);
      // System.out.println(semanticGraph.getRoots());
      String items = "";
      for (SemanticGraphEdge semanticGraphEdge : semanticGraph.edgeIterable()) {
        String root = semanticGraphEdge.getGovernor().lemma();
        // if (semanticGraphEdge.getDependent().tag().startsWith("NN"))
        // System.out.println(semanticGraphEdge);
        //        System.out.println(semanticGraphEdge.getDependent().tag().startsWith("NN"));
        if (semanticGraphEdge.getDependent().toString().contains(".")) {
          String m = semanticGraphEdge.getDependent().lemma();
          // System.out.println(m);
          String[] r1 = m.split("\\.");
          if (r1.length > 1) {
            for (String r2 : r1) {
              // System.out.println(r2);
              if (r2.length() > 1) itemsMap.add(r2);
            }
            for (String r3 : r1) {
              String[] spilt = splitCamelCase(r3);
              if (spilt != null) {
                for (String s2 : spilt) {
                  // System.out.println(s2);
                  if (s2.length() > 1) itemsMap.add(s2);
                }
              }
            }
          }
        } else if (semanticGraphEdge.getDependent().tag().startsWith("NN")) {
          String child = semanticGraphEdge.getDependent().lemma();
          itemsMap.add(child);
          String[] spilt = splitCamelCase(child);
          if (spilt != null) {
            for (String s2 : spilt) {
              if (s2.length() > 1) itemsMap.add(s2);
            }
          }
        } else if (semanticGraphEdge.getDependent().tag().startsWith("VB")) {
          String child = semanticGraphEdge.getDependent().lemma();
          itemsMap.add(child);
        }
      }
    }
    Set<String> set = new HashSet<>(itemsMap);
    return new ArrayList<>(set);
  }

  protected Map<String, List<List<String>>> getItemsMap(ArrayList<SemanticGraph> dependences) {
    Map<String, List<List<String>>> itemsMap = new HashMap<>();
    for (SemanticGraph semanticGraph : dependences) {
      //      System.out.println(semanticGraph);
      // System.out.println(semanticGraph.getRoots());
      String items = "";
      for (SemanticGraphEdge semanticGraphEdge : semanticGraph.edgeIterable()) {
        String root = semanticGraphEdge.getGovernor().lemma();
        System.out.println(semanticGraphEdge.getDependent().tag().startsWith("NN"));
        // if (semanticGraphEdge.getGovernor().tag().startsWith("VB") &&
        // !itemsMap.containsKey(root)) {
        if (!itemsMap.containsKey(root)) {
          // System.out.println("---->"+root);
          List<List<String>> nodes = new LinkedList<>();
          List<String> parent = new ArrayList<>();
          List<String> child = new ArrayList<>();
          nodes.add(parent);
          nodes.add(child);
          itemsMap.put(root, nodes);
        }
        if (semanticGraphEdge.getDependent().toString().contains(".")) {
          String m = semanticGraphEdge.getDependent().lemma();
          // System.out.println(m);
          List<List<String>> node = itemsMap.get(semanticGraphEdge.getGovernor().lemma());
          if (node != null) {
            String[] r1 = m.split("\\.");
            if (r1.length > 1) {
              for (String r2 : r1) {
                // System.out.println(r2);
                if (r2.length() > 1) node.get(0).add(r2);
              }
              for (String r3 : r1) {
                String[] spilt = splitCamelCase(r3);
                if (spilt != null) {
                  for (String s2 : spilt) {
                    // System.out.println(s2);
                    if (s2.length() > 1) node.get(1).add(s2);
                  }
                }
              }
            }
          }
          //                } else if
          // (semanticGraphEdge.getRelation().getShortName().startsWith("nsubj")) {
          //                    String parent = semanticGraphEdge.getDependent().lemma();
          //                    // System.out.println(parent);
          //                    List<List<String>> node =
          // itemsMap.get(semanticGraphEdge.getGovernor().lemma());
          //                    if (node != null) {
          //                        node.get(0).add(parent);
          //                        String[] spilt = splitCamelCase(parent);
          //                        if (spilt != null) {
          //                            for (String s2 : spilt) {
          //                                if (s2.length() > 1)
          //                                    node.get(0).add(s2);
          //                            }
          //                        }
          //                    }
        } else if (semanticGraphEdge.getDependent().tag().startsWith("NN")) {
          String child = semanticGraphEdge.getDependent().lemma();
          // System.out.println(child);
          List<List<String>> node = itemsMap.get(semanticGraphEdge.getGovernor().lemma());
          if (node != null) {
            node.get(1).add(child);
            String[] spilt = splitCamelCase(child);
            if (spilt != null) {
              for (String s2 : spilt) {
                if (s2.length() > 1) node.get(1).add(s2);
              }
            }
          }
        }
      }
    }
    return itemsMap;
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

  public void copyFile(int[] l) throws IOException {
    InputStream in = null;
    OutputStream out = null;
    for (int id : l) {
      String fromPath = "dataset/BLIZZARD/" + DatasetConfig.P_UI + "/BR/" + id + ".txt";
      File fromFile = new File(fromPath);

      // 如果文件不存在则创建文件
      if (!fromFile.exists()) {
        fromFile.createNewFile();
      }

      String goPath = "src/java/test/pui/st/" + id + ".txt";
      File goFile = new File(goPath);

      // 如果文件不存在则创建文件
      if (!goFile.exists()) {
        goFile.createNewFile();
      }

      // 创建输入流，读取fromFile
      in = new FileInputStream(fromFile);

      // 创建输入流，写入到goFile
      out = new FileOutputStream(goFile);
      int i = 0;
      while ((i = in.read()) != -1) {
        char j = (char) i;
        out.write(j);
      }
    }
    assert out != null;
    out.flush();
    out.close();
    in.close();
    System.out.println("文件复制结束！！！");
  }

  public void results(int[] l, String repository, String name) {
    String bench = DatasetConfig.BLIZZARD;
    System.out.println(name);
    System.out.println(l.length);
    List<String> our =
        ContentLoader.getAllLinesList("src/java/test/" + name + "/our_title.txt");
    List<String> blizzard =
        ContentLoader.getAllLinesList("src/java/test/" + name + "/blizzard.txt");
    ArrayList<Integer> o = new ArrayList<>();
    ArrayList<Integer> b = new ArrayList<>();
    int index = 0;
    for (int id : l) {
      FaultLocalizationRunner fr =
          new FaultLocalizationRunner(bench, repository, id, our.get(index));
      o.add(fr.getFirstRank());
      FaultLocalizationRunner fr1 =
          new FaultLocalizationRunner(bench, repository, id, blizzard.get(index));
      b.add(fr1.getFirstRank());
      index++;
    }
    int k = 0;
    int kk = 0;
    for (int j = 0; j < o.size(); j++) {
      if (o.get(j) - b.get(j) > 0) {
        System.out.println(l[j] + "-->" + (j + 1) + "----->" + o.get(j) + "-----" + b.get(j));
        k++;
      } else if (o.get(j) - b.get(j) < 0) {
        kk++;
      }
    }
    float k1 = 0;
    float k2 = 0;
    for (int j = 0; j < o.size(); j++) {
      if (o.get(j) <= 10 && o.get(j) > 0) {
        k1 += 1 / (float) o.get(j);
      }
      if (b.get(j) <= 10 && b.get(j) > 0) {
        k2 += 1 / (float) b.get(j);
      }
    }
    System.out.println("bad:" + k);
    System.out.println("good:" + kk);
    System.out.println(o);
    System.out.println(b);
    System.out.println(k1);
    System.out.println(k2);
  }

  public static void main(String[] args) throws IOException {
    TextProcessTest tpt = new TextProcessTest();
    System.out.println(tpt.getId());
    int[] debug = {
      1695, 7392, 10154, 12695, 13015, 16930, 21801, 22005, 24579, 30344, 30837, 43197, 52474,
      57467, 64951, 72169, 74885, 77125, 81249, 88923, 90805, 108473, 112670, 138377, 142476,
      161364, 161781, 164650, 169542, 183206, 187000, 287834, 341232, 343039, 372030, 412786, 506865
    };

    int[] jui = {
      12996, 46332, 58817, 79793, 83697, 103450, 125296, 179109, 202245, 216707, 244514, 251327,
      251824, 256087, 261198, 263935, 276744, 283427, 296229, 297709, 301242, 332421, 335249,
      336841, 347599, 391265, 391927, 411636, 416401, 435279, 440815, 466252, 479109
    };

    int[] tom = {
      41059, 43150, 47158, 47330, 47524, 47774, 48117, 48172, 48234, 48248, 48249, 48895, 49082,
      49110, 49129, 49159, 49567, 49730, 50127, 50183, 50293, 50306, 50459, 50460, 50547, 50554,
      50556, 50629, 50642, 51197, 51212, 51467, 51503, 51545, 51688, 51712, 51741, 51905, 52091,
      52213, 52259, 52443, 52607, 52724, 52726, 52727, 52729, 52731, 52732, 52777, 52850, 52954,
      53063, 53337, 53342, 53623, 53624, 53843, 54141, 54458, 54624, 55071, 55267, 55309, 55639,
      55684, 56213, 56724, 56746, 56902, 56907, 57323, 57338, 57340, 57681, 57708, 57977, 58275,
      58522, 59138
    };

    int[] pui = {
      61894, 66069, 96828, 117094, 126796, 129093, 143293, 149900, 172374, 197906, 199169, 203126,
      212745, 212755, 217333, 250340, 281923, 291528, 311979, 312495, 426528, 461083
    };

    int[] core = {
      135328, 170843, 291322, 327143, 380112, 380194, 380313, 383968, 383970, 384666, 384667,
      399780, 399979, 421473, 472815, 484957
    };

    int[] ecf = {
      199938, 207530, 218584, 244908, 257017, 265174, 265312, 265413, 265415, 267782, 270644,
      271323, 273819, 321468, 321469, 397877
    };
//    tpt.results(core, DatasetConfig.CORE, "core");
//        tpt.results(debug, DatasetConfig.DEBUG, "debug");
//         tpt.results(ecf, DatasetConfig.ECF, "ecf");
//            tpt.results(jui, DatasetConfig.J_UI, "jui");
//            tpt.results(pui, DatasetConfig.P_UI, "pui");
//         tpt.results(tom, DatasetConfig.TOMCAT, "tomcat70");

    // tpt.copyFile(pui);
    // System.out.println(tpt.testClassification(tpt.getId()));
    //    String content = "Client.isConnected() line: 213";
    //    Pattern p = Pattern.compile("(.*)line: \\d+");
    //    Matcher m = p.matcher(content);
    //    System.out.println(m.matches());
    // tpt.test(idList.get(0));
    //    ArrayList<Integer> st;
    //    st = tpt.testClassification(idList);
    //    System.out.println(st);
    //    System.out.println(idList_NL);
    //    int[] l = {
    //      199938, 207530, 218584, 244908, 257017, 265174, 265312, 265413, 265415, 267782, 270644,
    //      271323, 273819, 321468, 321469, 397877
    //    };
    //    DepManager depManager = new DepManager();
    //    String brPath =
    //        DatasetConfig.HOME_DIR
    //            + DatasetConfig.BLIZZARD
    //            + "/"
    //            + DatasetConfig.TOMCAT
    //            + "/BR/"
    //            + nl[0]
    //            + ".txt";
    //    ArrayList<String> report = BRLoader.loadBugReportList(brPath);
    //    String con = BRLoader.loadBugContent(brPath);
    //    System.out.println(con);
    //    for (String s : report) {
    //      System.out.println(s);
    //    }
    // System.out.println(tpt.getItems(report, depManager));
    //    ArrayList<SemanticGraph> dependencies = depManager.getDependencies(con);
    //    for (SemanticGraph sg : dependencies) {
    //      System.out.println(sg.edgeListSorted());
    //    }
    //    for (CoreSentence c : depManager.getDep(con)) {
    //      System.out.println(c);
    //    }
    // tpt.getItems(con, depManager);

    //    for (int id : nl) {
    //      String brPath =
    //          DatasetConfig.HOME_DIR
    //              + DatasetConfig.BLIZZARD
    //              + "/"
    //              + DatasetConfig.TOMCAT
    //              + "/BR/"
    //              + id
    //              + ".txt";
    //      ArrayList<String> report = BRLoader.loadBugReportList(brPath);
    //      //System.out.println(tpt.getItems(report, depManager));
    //      tpt.getItems(report, depManager);
    //    }

    // for (int i : idList_NL) {}

    //    for (String s : report) {
    //      System.out.println(s);
    //    }

  }
}
