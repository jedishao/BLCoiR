package utils.config;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ClassificationID {
  public static List<Integer> CAMEL_ST =
      Arrays.asList(715, 1565, 2496, 2986, 2994, 3493, 7161, 8146, 10229);
  public static List<Integer> CAMEL_STP = Arrays.asList(715, 2496, 2986, 2994, 3493, 10229);
  public static List<Integer> CAMEL_STN = Arrays.asList(1565, 7161, 8146);
  public static List<Integer> CAMEL_PE =
      Arrays.asList(
          140, 510, 803, 1270, 1401, 1510, 1641, 1650, 1670, 1795, 1930, 1966, 2049, 2175, 2824,
          2935, 3216, 3277, 3528, 7116, 7239, 7318, 7321, 7364, 7715, 7795, 7968, 7973, 8088, 8134,
          8200, 8241, 8268, 8353, 8673, 8713, 8885, 8954, 9199, 9319, 9331, 9738, 9951, 9970, 10024,
          10215, 10273);
  public static List<Integer> CAMEL_NL =
      Arrays.asList(574, 1199, 1583, 1604, 3349, 7556, 7988, 9311);

  public static List<Integer> HBASE_ST =
      Arrays.asList(659, 818, 1569, 1840, 1869, 14178, 14555, 14812, 14926, 15030, 16304, 16429);
  public static List<Integer> HBASE_STP =
      Arrays.asList(659, 818, 1840, 1869, 14812, 14926, 15030, 16304, 16429);
  public static List<Integer> HBASE_STN = Arrays.asList(1569, 14178, 14555);
  public static List<Integer> HBASE_PE =
      Arrays.asList(
          543, 663, 699, 950, 6565, 8923, 13606, 13662, 14241, 14359, 14463, 15001, 15093, 15650,
          15957, 16081, 16144, 16367, 16699, 16721, 16788);
  public static List<Integer> HBASE_NL = Arrays.asList(1740, 2023, 13959, 13988, 15146);

  public static List<Integer> HIVE_ST =
      Arrays.asList(
          3408, 8440, 9598, 11616, 12238, 12248, 12797, 12904, 12926, 13599, 14463, 14924);
  public static List<Integer> HIVE_STP =
      Arrays.asList(3408, 8440, 9598, 11616, 12238, 12248, 14463);
  public static List<Integer> HIVE_STN = Arrays.asList(12797, 12904, 12926, 13599, 14924);
  public static List<Integer> HIVE_PE =
      Arrays.asList(
          3057, 7011, 7341, 9199, 10483, 11036, 12409, 12529, 12620, 12927, 13013, 13213, 13395,
          13458, 13699, 13753, 13858, 14357, 14739, 14778, 15090);
  public static List<Integer> HIVE_NL =
      Arrays.asList(545, 4000, 12266, 12768, 12996, 13512, 13725, 13833, 14400);

  public static List<Integer> CODEC_ST = Arrays.asList(96);
  public static List<Integer> CODEC_STP = Arrays.asList(96);

  public static List<Integer> COLLECTIONS_PE = Arrays.asList(384);

  public static List<Integer> COMPRESS_PE = Arrays.asList(302, 303, 357);

  public static List<Integer> CONFIGURATION_PE = Arrays.asList(344, 407);

  public static List<Integer> IO_PE = Arrays.asList(99, 136, 201, 207, 216);

  public static List<Integer> LANG_PE = Arrays.asList(367, 459, 584, 1230, 1251);
  public static List<Integer> LANG_NL = Arrays.asList(481);

  public static List<Integer> MATH_PE = Arrays.asList(899, 1134, 1294);
  public static List<Integer> MATH_NL = Arrays.asList(505);

  public static List<Integer> AMQP_ST = Arrays.asList(262, 532, 551);
  public static List<Integer> AMQP_STP = Arrays.asList(262, 532, 551);
  public static List<Integer> AMQP_PE =
      Arrays.asList(125, 190, 223, 243, 249, 260, 274, 355, 365, 469, 506, 648);
  public static List<Integer> AMQP_NL = Arrays.asList(275, 331);

  public static List<Integer> BATCH_ST = Arrays.asList(2156, 2313);
  public static List<Integer> BATCH_STP = Arrays.asList(2156, 2313);
  public static List<Integer> BATCH_PE =
      Arrays.asList(170, 449, 603, 782, 912, 1272, 1278, 1526, 1542, 1572, 1709, 1725, 2126, 2153);
  public static List<Integer> BATCH_NL = Arrays.asList(1362, 1724);

  public static List<Integer> DATACMNS_ST = Arrays.asList(390);
  public static List<Integer> DATACMNS_STP = Arrays.asList(390);
  public static List<Integer> DATACMNS_PE = Arrays.asList(65, 362, 831);

  public static List<Integer> DATAGRAPH_PE = Arrays.asList(876);

  public static List<Integer> DATAJPA_PE = Arrays.asList(268, 359, 620);

  public static List<Integer> DATAMONGO_ST = Arrays.asList(199, 737);
  public static List<Integer> DATAMONGO_STP = Arrays.asList(737);
  public static List<Integer> DATAMONGO_STN = Arrays.asList(199);
  public static List<Integer> DATAMONGO_PE = Arrays.asList(532, 622);

  public static List<Integer> DATAREDIS_PE = Arrays.asList(310);

  public static List<Integer> SEC_ST = Arrays.asList(444, 809);
  public static List<Integer> SEC_STP = Arrays.asList(444, 809);
  public static List<Integer> SEC_PE =
      Arrays.asList(188, 192, 197, 328, 356, 398, 423, 618, 670, 1396, 2067, 3108, 3109);

  public static List<Integer> SECOAUTH_PE = Arrays.asList(311);

  public static List<Integer> SGF_PE = Arrays.asList(280);
  public static List<Integer> SGF_NL = Arrays.asList(231);

  public static List<Integer> SHDP_ST = Arrays.asList(374);
  public static List<Integer> SHDP_STP = Arrays.asList(374);

  public static List<Integer> SPR_PE = Arrays.asList(7688);

  public static List<Integer> SWF_PE = Arrays.asList(976, 1437);

  public static List<Integer> SWS_ST = Arrays.asList(947);
  public static List<Integer> SWS_STN = Arrays.asList(947);
  public static List<Integer> SWS_PE = Arrays.asList(334, 613);

  public static List<Integer> WFCORE_ST = Arrays.asList(196, 202, 1002, 1025);
  public static List<Integer> WFCORE_STP = Arrays.asList(196, 1002, 1025);
  public static List<Integer> WFCORE_STN = Arrays.asList(202);
  public static List<Integer> WFCORE_PE = Arrays.asList(132, 553, 997);

  public static List<Integer> WFLY_ST =
      Arrays.asList(2458, 3259, 3396, 3727, 3731, 5186, 5770, 6877);
  public static List<Integer> WFLY_STP = Arrays.asList(3259, 3396, 3727, 3731, 5770, 6877);
  public static List<Integer> WFLY_STN = Arrays.asList(2458, 5186);
  public static List<Integer> WFLY_PE =
      Arrays.asList(
          1810, 2016, 2065, 2467, 2954, 3280, 3338, 3517, 3548, 3695, 3715, 4310, 4511, 4844, 5449,
          5596, 6127, 6215, 6405, 7282);
  public static List<Integer> WFLY_NL = Arrays.asList(3381, 3942, 4133, 6776);

  public static List<Integer> ECF_ST =
      Arrays.asList(232530, 267035, 323208, 337973, 348487, 432662);
  public static List<Integer> ECF_STP = Arrays.asList(232530, 267035, 337973, 432662);
  public static List<Integer> ECF_STN = Arrays.asList(323208, 348487);
  public static List<Integer> ECF_PE =
      Arrays.asList(
          125572, 223484, 229237, 265413, 265415, 273357, 299849, 321468, 321469, 326949, 341818);
  public static List<Integer> ECF_NL = Arrays.asList(273819);

  public static List<Integer> JDT_CORE_ST = Arrays.asList(29585, 58314, 431275, 464570);
  public static List<Integer> JDT_CORE_STP = Arrays.asList(431275, 464570);
  public static List<Integer> JDT_CORE_STN = Arrays.asList(29585, 58314);
  public static List<Integer> JDT_CORE_PE = Arrays.asList(327143, 421473);
  public static List<Integer> JDT_CORE_NL = Arrays.asList(291322);

  public static List<Integer> JDT_DEBUG_ST =
      Arrays.asList(
          8683, 33712, 65107, 65149, 66413, 78534, 101118, 125868, 148006, 178912, 182237, 229394,
          312038, 446040);
  public static List<Integer> JDT_DEBUG_STP =
      Arrays.asList(8683, 33712, 65149, 78534, 148006, 312038, 446040);
  public static List<Integer> JDT_DEBUG_STN =
      Arrays.asList(65107, 66413, 101118, 125868, 178912, 182237, 229394);
  public static List<Integer> JDT_DEBUG_PE =
      Arrays.asList(21801, 22005, 24579, 33297, 43197, 88923, 161781, 506865);
  public static List<Integer> JDT_DEBUG_NL =
      Arrays.asList(7392, 12695, 16930, 52474, 57467, 64951, 165174, 169542, 187000, 343039);

  public static List<Integer> JDT_UI_ST =
      Arrays.asList(29531, 125013, 272742, 291996, 300536, 319689, 411841, 417765);
  public static List<Integer> JDT_UI_STP =
      Arrays.asList(29531, 125013, 272742, 291996, 319689, 411841, 417765);
  public static List<Integer> JDT_UI_STN = Arrays.asList(300536);
  public static List<Integer> JDT_UI_PE =
      Arrays.asList(75843, 79793, 261198, 296794, 301242, 347599);
  public static List<Integer> JDT_UI_NL =
      Arrays.asList(12996, 103450, 179109, 297709, 336841, 411636);

  public static List<Integer> PDE_UI_ST = Arrays.asList(158326, 186963);
  public static List<Integer> PDE_UI_STN = Arrays.asList(158326, 186963);
  public static List<Integer> PDE_UI_PE = Arrays.asList(281923);
  public static List<Integer> PDE_UI_NL = Arrays.asList(149900, 291528);

  public static List<Integer> TOMCAT70_ST =
      Arrays.asList(
          42530, 45453, 49905, 50138, 53173, 53450, 54086, 55524, 55799, 55996, 56042, 56577, 56857,
          57265, 57420, 57683, 57779, 57943, 58946);
  public static List<Integer> TOMCAT70_STP =
      Arrays.asList(
          42530, 45453, 49905, 50138, 53173, 53450, 54086, 55524, 55799, 56042, 56577, 56857, 57420,
          57779, 57943, 58946);
  public static List<Integer> TOMCAT70_STN = Arrays.asList(55996, 57265, 57683);
  public static List<Integer> TOMCAT70_PE =
      Arrays.asList(
          39769, 40380, 47158, 47524, 48172, 48234, 48248, 48249, 48399, 48790, 48843, 48895, 49129,
          49144, 49730, 49985, 49987, 50293, 50352, 50353, 50459, 50547, 50554, 51185, 51197, 51467,
          51545, 51688, 52055, 52091, 52213, 52356, 52777, 52999, 53498, 53624, 53843, 54045, 54521,
          55309, 55521, 55582, 55684, 56082, 56518, 56653, 56746, 56907, 57338, 57340, 57959, 57977,
          58179, 58522, 59138);
  public static List<Integer> TOMCAT70_NL =
      Arrays.asList(49567, 50629, 51212, 52259, 55267, 56724, 57681);

  public static List<Integer> ASPECTJ_ST = Arrays.asList(155148, 269867, 320468, 408721);
  public static List<Integer> ASPECTJ_STP = Arrays.asList(269867, 320468);
  public static List<Integer> ASPECTJ_STN = Arrays.asList(155148, 408721);
  public static List<Integer> ASPECTJ_PE = Arrays.asList(59909, 340806);
  public static List<Integer> ASPECTJ_NL = Arrays.asList(59599, 318878);

  public static List<Integer> BIRT_PE =
      Arrays.asList(123840, 195335, 208512, 284367, 287102, 314645);

  public static List<Integer> PLATFORM_ST = Arrays.asList(6623, 46562, 59782, 113582, 118414);
  public static List<Integer> PLATFORM_STP = Arrays.asList(6623, 59782, 118414);
  public static List<Integer> PLATFORM_STN = Arrays.asList(46562, 113582);
  public static List<Integer> PLATFORM_PE =
      Arrays.asList(
          15379, 33942, 54450, 67053, 74185, 76378, 84141, 108162, 114527, 114813, 136855, 164361,
          168806, 248908, 258352, 262032, 273752, 296056, 296822, 302536, 306738, 307405, 312063);
  public static List<Integer> PLATFORM_NL = Arrays.asList(48382, 171452, 230472);

  public static List<Integer> SWT_PE = Arrays.asList(176792, 183830, 188320);
  public static List<Integer> SWT_NL = Arrays.asList(264399, 338965);

  // GitHub
  public static List<Integer> DRUID_ST =
      Arrays.asList(
          1210, 2793, 2842, 3393, 3459, 3593, 3600, 4226, 4296, 6020, 6028, 6139, 6201, 6867);
  public static List<Integer> DRUID_STP =
      Arrays.asList(2793, 3393, 4296, 6020, 6028, 6139, 6201, 6867);
  public static List<Integer> DRUID_STN = Arrays.asList(1210, 2842, 3459, 3593, 3600, 4226);
  public static List<Integer> DRUID_PE =
      Arrays.asList(582, 812, 2991, 3608, 3772, 4984, 5338, 6287, 6826, 7400, 8622, 9292, 10005);
  public static List<Integer> DRUID_NL = Arrays.asList(1014, 1360, 1715, 3063);

  public static List<Integer> GRPC_ST =
      Arrays.asList(696, 887, 1253, 2152, 2246, 2388, 3207, 6601, 8190, 8565, 8914);
  public static List<Integer> GRPC_STP =
      Arrays.asList(887, 1253, 2152, 2246, 2388, 3207, 6601, 8190, 8565, 8914);
  public static List<Integer> GRPC_STN = Arrays.asList(696);
  public static List<Integer> GRPC_PE =
      Arrays.asList(
          17, 120, 238, 330, 583, 605, 999, 1343, 1408, 1510, 1981, 2453, 2562, 2865, 3084, 5015,
          5450, 6641, 8536, 8642);

  public static List<Integer> PRESTO_ST = Arrays.asList(1689, 3629, 5110, 5116, 6196, 9881, 13142);
  public static List<Integer> PRESTO_STP = Arrays.asList(3629, 5116, 9881, 13142);
  public static List<Integer> PRESTO_STN = Arrays.asList(1689, 5110, 6196);
  public static List<Integer> PRESTO_PE =
      Arrays.asList(1614, 3647, 5005, 5735, 6319, 6755, 6923, 7667, 7689, 8716, 11253);
  public static List<Integer> PRESTO_NL = Arrays.asList(4869);

  public static List<Integer> PULSAR_ST =
      Arrays.asList(
          1117, 3768, 4447, 4707, 9109, 10767, 11379, 11690, 11966, 12929, 13004, 13923, 13964,
          14438, 14633, 17913, 18988);
  public static List<Integer> PULSAR_STP =
      Arrays.asList(
          3768, 4447, 4707, 9109, 10767, 11690, 12929, 13004, 13923, 13964, 14438, 14633, 17913);
  public static List<Integer> PULSAR_STN = Arrays.asList(1117, 11379, 11966, 18988);
  public static List<Integer> PULSAR_PE =
      Arrays.asList(
          2141, 5585, 8050, 8293, 10170, 10235, 11605, 11689, 12723, 12885, 13986, 14362, 14413,
          17446, 18196);
  public static List<Integer> PULSAR_NL = Arrays.asList(4635);

  public static List<Integer> REDISSON_ST =
      Arrays.asList(
          39, 83, 89, 199, 254, 455, 543, 775, 889, 1048, 1268, 1626, 1950, 2355, 2690, 2692, 4064);
  public static List<Integer> REDISSON_STP =
      Arrays.asList(89, 199, 254, 543, 1048, 1268, 1626, 1950, 2355, 2690, 2692, 4064);
  public static List<Integer> REDISSON_STN = Arrays.asList(39, 83, 455, 775, 889);
  public static List<Integer> REDISSON_PE =
      Arrays.asList(
          67, 106, 169, 491, 530, 533, 758, 828, 891, 1433, 1602, 2099, 2278, 2575, 2714, 2883,
          3484, 4033);
  public static List<Integer> REDISSON_NL = Arrays.asList(40, 486, 757, 1104, 1966);

  public static List<Integer> ROCKETMQ_ST = Arrays.asList(5039);
  public static List<Integer> ROCKETMQ_STP = Arrays.asList(5039);
  public static List<Integer> ROCKETMQ_PE = Arrays.asList(1986, 4143, 4686);

  public static List<Integer> TRINO_ST = Arrays.asList(2962, 6389, 7872, 9741, 14605);
  public static List<Integer> TRINO_STP = Arrays.asList(2962, 6389, 7872, 9741, 14605);
  public static List<Integer> TRINO_PE = Arrays.asList(6202, 7454, 13212, 14132);
  public static List<Integer> TRINO_NL = Arrays.asList(11798);

  public static List<Integer> VERTX_ST =
      Arrays.asList(
          1247, 1302, 1355, 1466, 1573, 1598, 2684, 2982, 3232, 3468, 3658, 4069, 4104, 4278);
  public static List<Integer> VERTX_STP =
      Arrays.asList(1247, 1302, 1355, 1466, 1573, 2684, 3232, 3468, 3658, 4069, 4104);
  public static List<Integer> VERTX_STN = Arrays.asList(1598, 2982, 4278);
  public static List<Integer> VERTX_PE =
      Arrays.asList(
          1469, 1633, 1742, 1834, 1892, 2012, 2370, 2418, 2670, 2773, 3064, 3140, 3335, 4024, 4275);
  public static List<Integer> VERTX_NL = Arrays.asList(2916, 3142, 3469, 4221);

  public static List<Integer> BLIZZARD_NL =
      Arrays.asList(
          273819, 291322, 7392, 12695, 16930, 52474, 57467, 64951, 165174, 169542, 187000, 343039,
          12996, 103450, 179109, 297709, 336841, 411636, 149900, 291528, 49567, 50629, 51212, 52259,
          55267, 56724, 57681);
  public static List<Integer> BLIZZARD_PE =
      Arrays.asList(
          125572, 223484, 229237, 265413, 265415, 273357, 299849, 321468, 321469, 326949, 341818,
          327143, 421473, 21801, 22005, 24579, 33297, 43197, 88923, 161781, 506865, 75843, 79793,
          261198, 296794, 301242, 347599, 281923, 39769, 40380, 47158, 47524, 48172, 48234, 48248,
          48249, 48399, 48790, 48843, 48895, 49129, 49144, 49730, 49985, 49987, 50293, 50352, 50353,
          50459, 50547, 50554, 51185, 51197, 51467, 51545, 51688, 52055, 52091, 52213, 52356, 52777,
          52999, 53498, 53624, 53843, 54045, 54521, 55309, 55521, 55582, 55684, 56082, 56518, 56653,
          56746, 56907, 57338, 57340, 57959, 57977, 58179, 58522, 59138);
  public static List<Integer> BLIZZARD_ST =
      Arrays.asList(
          232530, 267035, 323208, 337973, 348487, 432662, 29585, 58314, 431275, 464570, 8683, 33712,
          65107, 65149, 66413, 78534, 101118, 125868, 148006, 178912, 182237, 229394, 312038,
          446040, 29531, 125013, 272742, 291996, 300536, 319689, 411841, 417765, 158326, 186963,
          42530, 45453, 49905, 50138, 53173, 53450, 54086, 55524, 55799, 55996, 56042, 56577, 56857,
          57265, 57420, 57683, 57779, 57943, 58946);
  public static List<Integer> BLIZZARD_STP =
      Arrays.asList(
          232530, 267035, 337973, 432662, 431275, 464570, 8683, 33712, 65149, 78534, 148006, 312038,
          446040, 29531, 125013, 272742, 291996, 319689, 411841, 417765, 42530, 45453, 49905, 50138,
          53173, 53450, 54086, 55524, 55799, 56042, 56577, 56857, 57420, 57779, 57943, 58946);
  public static List<Integer> BLIZZARD_STN =
      Arrays.asList(
          323208, 348487, 29585, 58314, 65107, 66413, 101118, 125868, 178912, 182237, 229394,
          300536, 158326, 186963, 55996, 57265, 57683);

  public static List<Integer> BENCH4BL_NL =
      Arrays.asList(
          574, 1199, 1583, 1604, 3349, 7556, 7988, 9311, 1740, 2023, 13959, 13988, 15146, 545, 4000,
          12266, 12768, 12996, 13512, 13725, 13833, 14400, 481, 505, 275, 331, 1362, 1724, 231,
          3381, 3942, 4133, 6776);
  public static List<Integer> BENCH4BL_ST =
      Arrays.asList(
          715, 1565, 2496, 2986, 2994, 3493, 7161, 8146, 10229, 659, 818, 1569, 1840, 1869, 14178,
          14555, 14812, 14926, 15030, 16304, 16429, 3408, 8440, 9598, 11616, 12238, 12248, 12797,
          12904, 12926, 13599, 14463, 14924, 96, 262, 532, 551, 2156, 2313, 390, 199, 737, 444, 809,
          374, 947, 196, 202, 1002, 1025, 2458, 3259, 3396, 3727, 3731, 5186, 5770, 6877);
  public static List<Integer> BENCH4BL_STP =
      Arrays.asList(
          715, 2496, 2986, 2994, 3493, 10229, 659, 818, 1840, 1869, 14812, 14926, 15030, 16304,
          16429, 3408, 8440, 9598, 11616, 12238, 12248, 14463, 96, 262, 532, 551, 2156, 2313, 390,
          737, 444, 809, 374, 196, 1002, 1025, 3259, 3396, 3727, 3731, 5770, 6877);
  public static List<Integer> BENCH4BL_STN =
      Arrays.asList(
          1565, 7161, 8146, 1569, 14178, 14555, 12797, 12904, 12926, 13599, 14924, 199, 947, 202,
          2458, 5186);
  public static List<Integer> BENCH4BL_PE =
      Arrays.asList(
          140, 510, 803, 1270, 1401, 1510, 1641, 1650, 1670, 1795, 1930, 1966, 2049, 2175, 2824,
          2935, 3216, 3277, 3528, 7116, 7239, 7318, 7321, 7364, 7715, 7795, 7968, 7973, 8088, 8134,
          8200, 8241, 8268, 8353, 8673, 8713, 8885, 8954, 9199, 9319, 9331, 9738, 9951, 9970, 10024,
          10215, 10273, 543, 663, 699, 950, 6565, 8923, 13606, 13662, 14241, 14359, 14463, 15001,
          15093, 15650, 15957, 16081, 16144, 16367, 16699, 16721, 16788, 3057, 7011, 7341, 9199,
          10483, 11036, 12409, 12529, 12620, 12927, 13013, 13213, 13395, 13458, 13699, 13753, 13858,
          14357, 14739, 14778, 15090, 384, 302, 303, 357, 344, 407, 99, 136, 201, 207, 216, 367,
          459, 584, 1230, 1251, 899, 1134, 1294, 125, 190, 223, 243, 249, 260, 274, 355, 365, 469,
          506, 648, 170, 449, 603, 782, 912, 1272, 1278, 1526, 1542, 1572, 1709, 1725, 2126, 2153,
          65, 362, 831, 876, 268, 359, 620, 310, 532, 622, 188, 192, 197, 328, 356, 398, 423, 618,
          670, 1396, 2067, 3108, 3109, 311, 280, 7688, 976, 1437, 334, 613, 132, 553, 997, 1810,
          2016, 2065, 2467, 2954, 3280, 3338, 3517, 3548, 3695, 3715, 4310, 4511, 4844, 5449, 5596,
          6127, 6215, 6405, 7282);

  public static List<Integer> LTR_NL =
      Arrays.asList(59599, 318878, 264399, 338965, 48382, 171452, 230472);
  public static List<Integer> LTR_ST =
      Arrays.asList(155148, 269867, 320468, 408721, 6623, 46562, 59782, 113582, 118414);
  public static List<Integer> LTR_STP =
          Arrays.asList(269867, 320468, 6623, 59782, 118414);
  public static List<Integer> LTR_STN =
          Arrays.asList(155148, 408721, 46562, 113582);
  public static List<Integer> LTR_PE =
      Arrays.asList(
          59909, 340806, 123840, 195335, 208512, 284367, 287102, 314645, 15379, 33942, 54450, 67053,
          74185, 76378, 84141, 108162, 114527, 114813, 136855, 164361, 168806, 248908, 258352,
          262032, 273752, 296056, 296822, 302536, 306738, 307405, 312063, 176792, 183830, 188320);

  public static List<Integer> GIT_NL =
      Arrays.asList(
          1014, 1360, 1715, 3063, 4869, 4635, 40, 486, 757, 1104, 1966, 11798, 2916, 3142, 3469,
          4221);
  public static List<Integer> GIT_ST =
      Arrays.asList(
          1210, 2793, 2842, 3393, 3459, 3593, 3600, 4226, 4296, 6020, 6028, 6139, 6201, 6867, 696,
          887, 1253, 2152, 2246, 2388, 3207, 6601, 8190, 8565, 8914, 1689, 3629, 5110, 5116, 6196,
          9881, 13142, 1117, 3768, 4447, 4707, 9109, 10767, 11379, 11690, 11966, 12929, 13004,
          13923, 13964, 14438, 14633, 17913, 18988, 39, 83, 89, 199, 254, 455, 543, 775, 889, 1048,
          1268, 1626, 1950, 2355, 2690, 2692, 4064, 5039, 2962, 6389, 7872, 9741, 14605, 1247, 1302,
          1355, 1466, 1573, 1598, 2684, 2982, 3232, 3468, 3658, 4069, 4104, 4278);
  public static List<Integer> GIT_STP =
      Arrays.asList(
          2793, 3393, 4296, 6020, 6028, 6139, 6201, 6867, 887, 1253, 2152, 2246, 2388, 3207, 6601,
          8190, 8565, 8914, 3629, 5116, 9881, 13142, 3768, 4447, 4707, 9109, 10767, 11690, 12929,
          13004, 13923, 13964, 14438, 14633, 17913, 89, 199, 254, 543, 1048, 1268, 1626, 1950, 2355,
          2690, 2692, 4064, 5039, 2962, 6389, 7872, 9741, 14605, 1247, 1302, 1355, 1466, 1573, 2684,
          3232, 3468, 3658, 4069, 4104);
  public static List<Integer> GIT_STN =
      Arrays.asList(
          1210, 2842, 3459, 3593, 3600, 4226, 696, 1689, 5110, 6196, 1117, 11379, 11966, 18988, 39,
          83, 455, 775, 889, 1598, 2982, 4278);
  public static List<Integer> GIT_PE =
      Arrays.asList(
          582, 812, 2991, 3608, 3772, 4984, 5338, 6287, 6826, 7400, 8622, 9292, 10005, 17, 120, 238,
          330, 583, 605, 999, 1343, 1408, 1510, 1981, 2453, 2562, 2865, 3084, 5015, 5450, 6641,
          8536, 8642, 1614, 3647, 5005, 5735, 6319, 6755, 6923, 7667, 7689, 8716, 11253, 2141, 5585,
          8050, 8293, 10170, 10235, 11605, 11689, 12723, 12885, 13986, 14362, 14413, 17446, 18196,
          67, 106, 169, 491, 530, 533, 758, 828, 891, 1433, 1602, 2099, 2278, 2575, 2714, 2883,
          3484, 4033, 1986, 4143, 4686, 6202, 7454, 13212, 14132, 1469, 1633, 1742, 1834, 1892,
          2012, 2370, 2418, 2670, 2773, 3064, 3140, 3335, 4024, 4275);

  private static void copyFileUsingApacheCommonsIO(File source, File dest) throws IOException {
    FileUtils.copyFile(source, dest);
  }

  public static void main(String[] args) throws IOException {
    //    String re = DatasetConfig.LTR;
    //    String pr = DatasetConfig.PLATFORM;
    //    for (int id : PLATFORM_ST) {
    //      String f = DatasetConfig.DATASET_DIR + re + "/" + pr + "/BR/" + id + ".txt";
    //      String t = DatasetConfig.DATASET_DIR + re + "/" + pr + "/ST/" + id + ".txt";
    //      File s1 = new File(f);
    //      File s2 = new File(t);
    //      copyFileUsingApacheCommonsIO(s1, s2);
    //    }
    List<Integer> temp = new ArrayList<>();
    for (int id : LTR_ST) {
      if (!LTR_STP.contains(id)) temp.add(id);
    }
    System.out.println(temp);
    int i =
        ECF_STP.size()
            + JDT_CORE_STP.size()
            + JDT_DEBUG_STP.size()
            + JDT_UI_STP.size()
            + TOMCAT70_STP.size()
            + ASPECTJ_STP.size()
            + PLATFORM_STP.size()
            + CAMEL_STP.size()
            + HBASE_STP.size()
            + HIVE_STP.size()
            + CODEC_STP.size()
            + AMQP_STP.size()
            + BATCH_STP.size()
            + DATACMNS_STP.size()
            + DATAMONGO_STP.size()
            + SEC_STP.size()
            + SHDP_STP.size()
            + WFCORE_STP.size()
            + WFLY_STP.size()
            + DRUID_STP.size()
            + GRPC_STP.size()
            + PRESTO_STP.size()
            + PULSAR_STP.size()
            + REDISSON_STP.size()
            + ROCKETMQ_STP.size()
            + TRINO_STP.size()
            + VERTX_STP.size();
    System.out.println(i);

    List<Integer> L = new ArrayList<>();
    L.addAll(ASPECTJ_STP);
    L.addAll(PLATFORM_STP);
    System.out.println(L);
    //        int bli_nl =
    //            ECF_NL.size()
    //                + JDT_CORE_NL.size()
    //                + JDT_DEBUG_NL.size()
    //                + JDT_UI_NL.size()
    //                + PDE_UI_NL.size()
    //                + TOMCAT70_NL.size();
    //    int lr_nl = ASPECTJ_NL.size() + PLATFORM_NL.size() + SWT_NL.size();
    //    int ben_nl =
    //        CAMEL_NL.size()
    //            + HBASE_NL.size()
    //            + HIVE_NL.size()
    //            + LANG_NL.size()
    //            + MATH_NL.size()
    //            + AMQP_NL.size()
    //            + BATCH_NL.size()
    //            + SGF_NL.size()
    //            + WFLY_NL.size();
    //    int git_nl =
    //        DRUID_NL.size()
    //            + PRESTO_NL.size()
    //            + PULSAR_NL.size()
    //            + REDISSON_NL.size()
    //            + TRINO_NL.size()
    //            + VERTX_NL.size();
    //    System.out.println(bli_nl + lr_nl + ben_nl + git_nl);
    //
    //    int bli_st =
    //        ECF_ST.size()
    //            + JDT_CORE_ST.size()
    //            + JDT_DEBUG_ST.size()
    //            + JDT_UI_ST.size()
    //            + PDE_UI_ST.size()
    //            + TOMCAT70_ST.size();
    //    int lr_st = ASPECTJ_ST.size() + PLATFORM_ST.size();
    //    int ben_st =
    //        CAMEL_ST.size()
    //            + HBASE_ST.size()
    //            + HIVE_ST.size()
    //            + CODEC_ST.size()
    //            + AMQP_ST.size()
    //            + BATCH_ST.size()
    //            + DATACMNS_ST.size()
    //            + DATAMONGO_ST.size()
    //            + SEC_ST.size()
    //            + SHDP_ST.size()
    //            + SWS_ST.size()
    //            + WFCORE_ST.size()
    //            + WFLY_ST.size();
    //    int git_st =
    //        DRUID_ST.size()
    //            + GRPC_ST.size()
    //            + PRESTO_ST.size()
    //            + PULSAR_ST.size()
    //            + REDISSON_ST.size()
    //            + ROCKETMQ_ST.size()
    //            + TRINO_ST.size()
    //            + VERTX_ST.size();
    //    System.out.println(bli_st + ben_st + lr_st + git_st);
    //
    //    int bli_pe =
    //        ECF_PE.size()
    //            + JDT_CORE_PE.size()
    //            + JDT_DEBUG_PE.size()
    //            + JDT_UI_PE.size()
    //            + PDE_UI_PE.size()
    //            + TOMCAT70_PE.size();
    //    int lr_pe = ASPECTJ_PE.size() + BIRT_PE.size() + PLATFORM_PE.size() + SWT_PE.size();
    //    int ben_pe =
    //        CAMEL_PE.size()
    //            + HBASE_PE.size()
    //            + HIVE_PE.size()
    //            + COLLECTIONS_PE.size()
    //            + COMPRESS_PE.size()
    //            + CONFIGURATION_PE.size()
    //            + IO_PE.size()
    //            + LANG_PE.size()
    //            + MATH_PE.size()
    //            + AMQP_PE.size()
    //            + BATCH_PE.size()
    //            + DATACMNS_PE.size()
    //            + DATAGRAPH_PE.size()
    //            + DATAJPA_PE.size()
    //            + DATAREDIS_PE.size()
    //            + DATAMONGO_PE.size()
    //            + SECOAUTH_PE.size()
    //            + SEC_PE.size()
    //            + SGF_PE.size()
    //            + SPR_PE.size()
    //            + SWF_PE.size()
    //            + SWS_PE.size()
    //            + WFCORE_PE.size()
    //            + WFLY_PE.size();
    //    int git_pe =
    //        DRUID_PE.size()
    //            + GRPC_PE.size()
    //            + PRESTO_PE.size()
    //            + PULSAR_PE.size()
    //            + REDISSON_PE.size()
    //            + ROCKETMQ_PE.size()
    //            + TRINO_PE.size()
    //            + VERTX_PE.size();
    //    System.out.println(bli_pe + ben_pe + lr_pe + git_pe);
    //    System.out.println(BENCH4BL_PE.size() + BLIZZARD_PE.size() + GIT_PE.size() +
    // LTR_PE.size());
  }
}
