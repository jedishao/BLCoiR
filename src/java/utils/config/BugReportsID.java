package utils.config;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BugReportsID {
  // Bench4BL
  // Apache
  public static List<Integer> CAMEL =
      Arrays.asList(
          140, 510, 574, 715, 803, 1199, 1270, 1401, 1510, 1565, 1583, 1604, 1641, 1650, 1670, 1795,
          1930, 1966, 2049, 2175, 2496, 2824, 2935, 2986, 2994, 3216, 3277, 3349, 3493, 3528, 7116,
          7161, 7239, 7318, 7321, 7364, 7556, 7715, 7795, 7968, 7973, 7988, 8088, 8134, 8146, 8200,
          8241, 8268, 8353, 8673, 8713, 8885, 8954, 9199, 9311, 9319, 9331, 9738, 9951, 9970, 10024,
          10215, 10229, 10273);
  public static List<Integer> HBASE =
      Arrays.asList(
          543, 659, 663, 699, 818, 950, 1569, 1740, 1840, 1869, 2023, 6565, 8923, 13606, 13662,
          13959, 13988, 14178, 14241, 14359, 14463, 14555, 14812, 14926, 15001, 15030, 15093, 15146,
          15650, 15957, 16081, 16144, 16304, 16367, 16429, 16699, 16721, 16788);
  public static List<Integer> HIVE =
      Arrays.asList(
          545, 3057, 3408, 4000, 7011, 7341, 8440, 9199, 9598, 10483, 11036, 11616, 12238, 12248,
          12266, 12409, 12529, 12620, 12768, 12797, 12904, 12926, 12927, 12996, 13013, 13213, 13395,
          13458, 13512, 13599, 13699, 13725, 13753, 13833, 13858, 14357, 14400, 14463, 14739, 14778,
          14924, 15090);

  public static List<Integer> CODEC = Arrays.asList(96);
  public static List<Integer> COLLECTIONS = Arrays.asList(384);
  public static List<Integer> COMPRESS = Arrays.asList(302, 303, 357);
  public static List<Integer> CONFIGURATION = Arrays.asList(344, 407);
  public static List<Integer> IO = Arrays.asList(99, 136, 201, 207, 216);
  public static List<Integer> LANG = Arrays.asList(367, 459, 481, 584, 1230, 1251);
  public static List<Integer> MATH = Arrays.asList(505, 899, 1134, 1294);

  public static List<Integer> AMQP =
      Arrays.asList(
          125, 190, 223, 243, 249, 260, 262, 274, 275, 331, 355, 365, 469, 506, 532, 551, 648);
  public static List<Integer> BATCH =
      Arrays.asList(
          170, 449, 603, 782, 912, 1272, 1278, 1362, 1526, 1542, 1572, 1709, 1724, 1725, 2126, 2153,
          2156, 2313);
  public static List<Integer> DATACMNS = Arrays.asList(65, 362, 390, 831);
  public static List<Integer> DATAGRAPH = Arrays.asList(876);
  public static List<Integer> DATAJPA = Arrays.asList(268, 359, 620);
  public static List<Integer> DATAMONGO = Arrays.asList(199, 532, 622, 737);
  public static List<Integer> DATAREDIS = Arrays.asList(310);
  public static List<Integer> SEC =
      Arrays.asList(188, 192, 197, 328, 356, 398, 423, 444, 618, 670, 809, 1396, 2067, 3108, 3109);
  public static List<Integer> SECOAUTH = Arrays.asList(311);
  public static List<Integer> SGF = Arrays.asList(231, 280);
  public static List<Integer> SHDP = Arrays.asList(374);
  public static List<Integer> SPR = Arrays.asList(7688);
  public static List<Integer> SWF = Arrays.asList(976, 1437);
  public static List<Integer> SWS = Arrays.asList(334, 613, 947);

  public static List<Integer> WFCORE = Arrays.asList(132, 196, 202, 553, 997, 1002, 1025);
  public static List<Integer> WFLY =
      Arrays.asList(
          1810, 2016, 2065, 2458, 2467, 2954, 3259, 3280, 3338, 3381, 3396, 3517, 3548, 3695, 3715,
          3727, 3731, 3942, 4133, 4310, 4511, 4844, 5186, 5449, 5596, 5770, 6127, 6215, 6405, 6776,
          6877, 7282);

  public static List<Integer> ECF =
      Arrays.asList(
          125572, 223484, 229237, 232530, 265413, 265415, 267035, 273357, 273819, 299849, 321468,
          321469, 323208, 326949, 337973, 341818, 348487, 432662);
  public static List<Integer> JDT_CORE =
      Arrays.asList(29585, 58314, 291322, 327143, 421473, 431275, 464570);
  public static List<Integer> JDT_DEBUG =
      Arrays.asList(
          7392, 8683, 12695, 16930, 21801, 22005, 24579, 33297, 33712, 43197, 52474, 57467, 64951,
          65107, 65149, 66413, 78534, 88923, 101118, 125868, 148006, 161781, 165174, 169542, 178912,
          182237, 187000, 229394, 312038, 343039, 446040, 506865);
  public static List<Integer> JDT_UI =
      Arrays.asList(
          12996, 29531, 75843, 79793, 103450, 125013, 179109, 261198, 272742, 291996, 296794,
          297709, 300536, 301242, 319689, 336841, 347599, 411636, 411841, 417765);
  public static List<Integer> PDE_UI = Arrays.asList(149900, 158326, 186963, 281923, 291528);
  public static List<Integer> TOMCAT70 =
      Arrays.asList(
          39769, 40380, 42530, 45453, 47158, 47524, 48172, 48234, 48248, 48249, 48399, 48790, 48843,
          48895, 49129, 49144, 49567, 49730, 49905, 49985, 49987, 50138, 50293, 50352, 50353, 50459,
          50547, 50554, 50629, 51185, 51197, 51212, 51467, 51545, 51688, 52055, 52091, 52213, 52259,
          52356, 52777, 52999, 53173, 53450, 53498, 53624, 53843, 54045, 54086, 54521, 55267, 55309,
          55521, 55524, 55582, 55684, 55799, 55996, 56042, 56082, 56518, 56577, 56653, 56724, 56746,
          56857, 56907, 57265, 57338, 57340, 57420, 57681, 57683, 57779, 57943, 57959, 57977, 58179,
          58522, 58946, 59138);

  public static List<Integer> ASPECTJ =
      Arrays.asList(59599, 59909, 155148, 269867, 318878, 320468, 340806, 408721);
  public static List<Integer> BIRT = Arrays.asList(123840, 195335, 208512, 284367, 287102, 314645);
  public static List<Integer> PLATFORM =
      Arrays.asList(
          6623, 15379, 33942, 46562, 48382, 54450, 59782, 67053, 74185, 76378, 84141, 108162,
          113582, 114527, 114813, 118414, 136855, 164361, 168806, 171452, 230472, 248908, 258352,
          262032, 273752, 296056, 296822, 302536, 306738, 307405, 312063);
  public static List<Integer> SWT = Arrays.asList(176792, 183830, 188320, 264399, 338965);

  // GitHub
  public static List<Integer> DRUID =
      Arrays.asList(
          582, 812, 1014, 1210, 1360, 1715, 2793, 2842, 2991, 3063, 3393, 3459, 3593, 3600, 3608,
          3772, 4226, 4296, 4984, 5338, 6020, 6028, 6139, 6201, 6287, 6826, 6867, 7400, 8622, 9292,
          10005);

  public static List<Integer> GRPC =
      Arrays.asList(
          17, 120, 238, 330, 583, 605, 696, 887, 999, 1253, 1343, 1408, 1510, 1981, 2152, 2246,
          2388, 2453, 2562, 2865, 3084, 3207, 5015, 5450, 6601, 6641, 8190, 8536, 8565, 8642, 8914);
  public static List<Integer> PRESTO =
      Arrays.asList(
          1614, 1689, 3629, 3647, 4869, 5005, 5110, 5116, 5735, 6196, 6319, 6755, 6923, 7667, 7689,
          8716, 9881, 11253, 13142);
  public static List<Integer> PULSAR =
      Arrays.asList(
          1117, 2141, 3768, 4447, 4635, 4707, 5585, 8050, 8293, 9109, 10170, 10235, 10767, 11379,
          11605, 11689, 11690, 11966, 12723, 12885, 12929, 13004, 13923, 13964, 13986, 14362, 14413,
          14438, 14633, 17446, 17913, 18196, 18988);
  public static List<Integer> REDISSON =
      Arrays.asList(
          39, 40, 67, 83, 89, 106, 169, 199, 254, 455, 486, 491, 530, 533, 543, 757, 758, 775, 828,
          889, 891, 1048, 1104, 1268, 1433, 1602, 1626, 1950, 1966, 2099, 2278, 2355, 2575, 2690,
          2692, 2714, 2883, 3484, 4033, 4064);
  public static List<Integer> ROCKETMQ = Arrays.asList(1986, 4143, 4686, 5039);
  public static List<Integer> TRINO =
      Arrays.asList(2962, 6202, 6389, 7454, 7872, 9741, 11798, 13212, 14132, 14605);
  public static List<Integer> VERTX =
      Arrays.asList(
          1247, 1302, 1355, 1466, 1469, 1573, 1598, 1633, 1742, 1834, 1892, 2012, 2370, 2418, 2670,
          2684, 2773, 2916, 2982, 3064, 3140, 3142, 3232, 3335, 3468, 3469, 3658, 4024, 4069, 4104,
          4221, 4275, 4278);

  public static void main(String[] args) {
    BugReportsID br = new BugReportsID();
//    br.getID("dataset/LtR/SWT/BR/");
//    int i = 0;
//    for (List<Integer> l : EvaluationConfig.LTR_ID){
//      System.out.println(l.size());
//      i += l.size();
//    }
    System.out.println(DRUID.size());
  }

  void getID(String path) {
    File file = new File(path);
    List<Integer> list = new ArrayList<>();
    if (file.isDirectory()) {
      File[] files = file.listFiles();
      assert files != null;
      for (File file1 : files) {
        if (file1.getName().equals(".DS_Store")) {
          file1.delete();
        } else if (file1.getName().split(".txt").length > 0) {
          list.add(Integer.valueOf(file1.getName().split(".txt")[0]));
        }
      }
    }
    System.out.println(list);
  }
}
