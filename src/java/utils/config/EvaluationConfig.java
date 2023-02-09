package utils.config;

import java.util.List;

public class EvaluationConfig {
  public static String[] BENCH4BL = {
    DatasetConfig.A_CAMEL,
    DatasetConfig.A_HBASE,
    DatasetConfig.A_HIVE,
    DatasetConfig.C_CODEC,
    DatasetConfig.C_COLLECTIONS,
    DatasetConfig.C_COMPRESS,
    DatasetConfig.C_CONFIGURATION,
    DatasetConfig.C_IO,
    DatasetConfig.C_LANG,
    DatasetConfig.C_MATH,
    DatasetConfig.SP_AMQP,
    DatasetConfig.SP_BATCH,
    DatasetConfig.SP_DATACMNS,
    DatasetConfig.SP_DATAGRAPH,
    DatasetConfig.SP_DATAJPA,
    DatasetConfig.SP_DATAMONGO,
    DatasetConfig.SP_DATAREDIS,
    DatasetConfig.SP_SEC,
    DatasetConfig.SP_SECOAUTH,
    DatasetConfig.SP_SGF,
    DatasetConfig.SP_SHDP,
    DatasetConfig.SP_SPR,
    DatasetConfig.SP_SWF,
    DatasetConfig.SP_SWS,
    DatasetConfig.W_WFCORE,
    DatasetConfig.W_WFLY
  };
  public static String[] COMMONS = {
    DatasetConfig.C_CODEC,
    DatasetConfig.C_COLLECTIONS,
    DatasetConfig.C_COMPRESS,
    DatasetConfig.C_CONFIGURATION,
    DatasetConfig.C_IO,
    DatasetConfig.C_LANG,
    DatasetConfig.C_MATH,
  };
  public static String[] SPRING = {
    DatasetConfig.SP_AMQP,
    DatasetConfig.SP_BATCH,
    DatasetConfig.SP_DATACMNS,
    DatasetConfig.SP_DATAGRAPH,
    DatasetConfig.SP_DATAJPA,
    DatasetConfig.SP_DATAMONGO,
    DatasetConfig.SP_DATAREDIS,
    DatasetConfig.SP_SEC,
    DatasetConfig.SP_SECOAUTH,
    DatasetConfig.SP_SGF,
    DatasetConfig.SP_SHDP,
    DatasetConfig.SP_SPR,
    DatasetConfig.SP_SWF,
    DatasetConfig.SP_SWS,
  };
  public static String[] JBOSS = {DatasetConfig.W_WFCORE, DatasetConfig.W_WFLY};
  public static String[] BLIZZARD = {
    DatasetConfig.ECF,
    DatasetConfig.CORE,
    DatasetConfig.J_UI,
    DatasetConfig.DEBUG,
    DatasetConfig.P_UI,
    DatasetConfig.TOMCAT
  };
  public static String[] GITHUB = {
    DatasetConfig.DRUID,
    DatasetConfig.GRPC,
    DatasetConfig.PRESTO,
    DatasetConfig.PULSAR,
    DatasetConfig.REDISSON,
    DatasetConfig.ROCKETMQ,
    DatasetConfig.TRINO,
    DatasetConfig.VERTX
  };
  public static String[] LTR = {
    DatasetConfig.ASPECTJ, DatasetConfig.BIRT, DatasetConfig.PLATFORM, DatasetConfig.SWT
  };

  public static List<List<Integer>> BENCH4BL_ID =
      List.of(
          BugReportsID.CAMEL,
          BugReportsID.HBASE,
          BugReportsID.HIVE,
          BugReportsID.CODEC,
          BugReportsID.COLLECTIONS,
          BugReportsID.COMPRESS,
          BugReportsID.CONFIGURATION,
          BugReportsID.IO,
          BugReportsID.LANG,
          BugReportsID.MATH,
          BugReportsID.AMQP,
          BugReportsID.BATCH,
          BugReportsID.DATACMNS,
          BugReportsID.DATAGRAPH,
          BugReportsID.DATAJPA,
          BugReportsID.DATAMONGO,
          BugReportsID.DATAREDIS,
          BugReportsID.SEC,
          BugReportsID.SECOAUTH,
          BugReportsID.SGF,
          BugReportsID.SHDP,
          BugReportsID.SPR,
          BugReportsID.SWF,
          BugReportsID.SWS,
          BugReportsID.WFCORE,
          BugReportsID.WFLY);
  public static List<List<Integer>> COMMONS_ID =
      List.of(
          BugReportsID.CODEC,
          BugReportsID.COLLECTIONS,
          BugReportsID.COMPRESS,
          BugReportsID.CONFIGURATION,
          BugReportsID.IO,
          BugReportsID.LANG,
          BugReportsID.MATH);
  public static List<List<Integer>> SPRING_ID =
      List.of(
          BugReportsID.AMQP,
          BugReportsID.BATCH,
          BugReportsID.DATACMNS,
          BugReportsID.DATAGRAPH,
          BugReportsID.DATAJPA,
          BugReportsID.DATAMONGO,
          BugReportsID.DATAREDIS,
          BugReportsID.SEC,
          BugReportsID.SECOAUTH,
          BugReportsID.SGF,
          BugReportsID.SHDP,
          BugReportsID.SPR,
          BugReportsID.SWF,
          BugReportsID.SWS);
  public static List<List<Integer>> JBOSS_ID = List.of(BugReportsID.WFCORE, BugReportsID.WFLY);
  public static List<List<Integer>> BLIZZARD_ID =
      List.of(
          BugReportsID.ECF,
          BugReportsID.JDT_CORE,
          BugReportsID.JDT_UI,
          BugReportsID.JDT_DEBUG,
          BugReportsID.PDE_UI,
          BugReportsID.TOMCAT70);
  public static List<List<Integer>> GITHUB_ID =
      List.of(
          BugReportsID.DRUID,
          BugReportsID.GRPC,
          BugReportsID.PRESTO,
          BugReportsID.PULSAR,
          BugReportsID.REDISSON,
          BugReportsID.ROCKETMQ,
          BugReportsID.TRINO,
          BugReportsID.VERTX);
  public static List<List<Integer>> LTR_ID =
      List.of(BugReportsID.ASPECTJ, BugReportsID.BIRT, BugReportsID.PLATFORM, BugReportsID.SWT);

  public static String[] DATA = {
    DatasetConfig.BENCH4BL, DatasetConfig.BLIZZARD, DatasetConfig.GITHUB, DatasetConfig.LTR
  };
  public static List<List<Integer>> NL_ID =
      List.of(
          ClassificationID.BENCH4BL_NL,
          ClassificationID.BLIZZARD_NL,
          ClassificationID.GIT_NL,
          ClassificationID.LTR_NL);
  public static List<List<Integer>> ST_ID =
      List.of(
          ClassificationID.BENCH4BL_ST,
          ClassificationID.BLIZZARD_ST,
          ClassificationID.GIT_ST,
          ClassificationID.LTR_ST);
  public static List<List<Integer>> STP_ID =
          List.of(
                  ClassificationID.BENCH4BL_STP,
                  ClassificationID.BLIZZARD_STP,
                  ClassificationID.GIT_STP,
                  ClassificationID.LTR_STP);
  public static List<List<Integer>> STN_ID =
          List.of(
                  ClassificationID.BENCH4BL_STN,
                  ClassificationID.BLIZZARD_STN,
                  ClassificationID.GIT_STN,
                  ClassificationID.LTR_STN);
  public static List<List<Integer>> PE_ID =
      List.of(
          ClassificationID.BENCH4BL_PE,
          ClassificationID.BLIZZARD_PE,
          ClassificationID.GIT_PE,
          ClassificationID.LTR_PE);

  public static String[] BENCH4BL_NL = {
    DatasetConfig.A_CAMEL,
    DatasetConfig.A_HBASE,
    DatasetConfig.A_HIVE,
    DatasetConfig.C_LANG,
    DatasetConfig.C_MATH,
    DatasetConfig.SP_AMQP,
    DatasetConfig.SP_BATCH,
    DatasetConfig.SP_SGF,
    DatasetConfig.W_WFLY
  };
  public static List<List<Integer>> BENCH4BL_NL_ID =
      List.of(
          ClassificationID.CAMEL_NL,
          ClassificationID.HBASE_NL,
          ClassificationID.HIVE_NL,
          ClassificationID.LANG_NL,
          ClassificationID.MATH_NL,
          ClassificationID.AMQP_NL,
          ClassificationID.BATCH_NL,
          ClassificationID.SGF_NL,
          ClassificationID.WFLY_NL);

  public static String[] BENCH4BL_ST = {
    DatasetConfig.A_CAMEL,
    DatasetConfig.A_HBASE,
    DatasetConfig.A_HIVE,
    DatasetConfig.C_CODEC,
    DatasetConfig.SP_AMQP,
    DatasetConfig.SP_BATCH,
    DatasetConfig.SP_DATACMNS,
    DatasetConfig.SP_DATAMONGO,
    DatasetConfig.SP_SEC,
    DatasetConfig.SP_SHDP,
    DatasetConfig.SP_SWS,
    DatasetConfig.W_WFCORE,
    DatasetConfig.W_WFLY
  };
  public static List<List<Integer>> BENCH4BL_ST_ID =
      List.of(
          ClassificationID.CAMEL_ST,
          ClassificationID.HBASE_ST,
          ClassificationID.HIVE_ST,
          ClassificationID.CODEC_ST,
          ClassificationID.AMQP_ST,
          ClassificationID.BATCH_ST,
          ClassificationID.DATACMNS_ST,
          ClassificationID.DATAMONGO_ST,
          ClassificationID.SEC_ST,
          ClassificationID.SHDP_ST,
          ClassificationID.SWS_ST,
          ClassificationID.WFCORE_ST,
          ClassificationID.WFLY_ST);
  public static String[] BENCH4BL_PE = {
    DatasetConfig.A_CAMEL,
    DatasetConfig.A_HBASE,
    DatasetConfig.A_HIVE,
    DatasetConfig.C_COLLECTIONS,
    DatasetConfig.C_COMPRESS,
    DatasetConfig.C_CONFIGURATION,
    DatasetConfig.C_IO,
    DatasetConfig.C_LANG,
    DatasetConfig.C_MATH,
    DatasetConfig.SP_AMQP,
    DatasetConfig.SP_BATCH,
    DatasetConfig.SP_DATACMNS,
    DatasetConfig.SP_DATAGRAPH,
    DatasetConfig.SP_DATAJPA,
    DatasetConfig.SP_DATAREDIS,
    DatasetConfig.SP_DATAMONGO,
    DatasetConfig.SP_SEC,
    DatasetConfig.SP_SECOAUTH,
    DatasetConfig.SP_SGF,
    DatasetConfig.SP_SPR,
    DatasetConfig.SP_SWF,
    DatasetConfig.SP_SWS,
    DatasetConfig.W_WFCORE,
    DatasetConfig.W_WFLY
  };
  public static List<List<Integer>> BENCH4BL_PE_ID =
      List.of(
          ClassificationID.CAMEL_PE,
          ClassificationID.HBASE_PE,
          ClassificationID.HIVE_PE,
          ClassificationID.COLLECTIONS_PE,
          ClassificationID.COMPRESS_PE,
          ClassificationID.CONFIGURATION_PE,
          ClassificationID.IO_PE,
          ClassificationID.LANG_PE,
          ClassificationID.MATH_PE,
          ClassificationID.AMQP_PE,
          ClassificationID.BATCH_PE,
          ClassificationID.DATACMNS_PE,
          ClassificationID.DATAGRAPH_PE,
          ClassificationID.DATAJPA_PE,
          ClassificationID.DATAREDIS_PE,
          ClassificationID.DATAMONGO_PE,
          ClassificationID.SEC_PE,
          ClassificationID.SECOAUTH_PE,
          ClassificationID.SGF_PE,
          ClassificationID.SPR_PE,
          ClassificationID.SWF_PE,
          ClassificationID.SWS_PE,
          ClassificationID.WFCORE_PE,
          ClassificationID.WFLY_PE);

  public static String[] BLIZZARD_NL = {
    DatasetConfig.ECF,
    DatasetConfig.CORE,
    DatasetConfig.DEBUG,
    DatasetConfig.J_UI,
    DatasetConfig.P_UI,
    DatasetConfig.TOMCAT
  };
  public static List<List<Integer>> BLIZZARD_NL_ID =
      List.of(
          ClassificationID.ECF_NL,
          ClassificationID.JDT_CORE_NL,
          ClassificationID.JDT_DEBUG_NL,
          ClassificationID.JDT_UI_NL,
          ClassificationID.PDE_UI_NL,
          ClassificationID.TOMCAT70_NL);
  public static String[] BLIZZARD_ST = {
    DatasetConfig.ECF,
    DatasetConfig.CORE,
    DatasetConfig.DEBUG,
    DatasetConfig.J_UI,
    DatasetConfig.P_UI,
    DatasetConfig.TOMCAT
  };
  public static List<List<Integer>> BLIZZARD_ST_ID =
      List.of(
          ClassificationID.ECF_ST,
          ClassificationID.JDT_CORE_ST,
          ClassificationID.JDT_DEBUG_ST,
          ClassificationID.JDT_UI_ST,
          ClassificationID.PDE_UI_ST,
          ClassificationID.TOMCAT70_ST);
  public static String[] BLIZZARD_PE = {
    DatasetConfig.ECF,
    DatasetConfig.CORE,
    DatasetConfig.DEBUG,
    DatasetConfig.J_UI,
    DatasetConfig.P_UI,
    DatasetConfig.TOMCAT
  };
  public static List<List<Integer>> BLIZZARD_PE_ID =
      List.of(
          ClassificationID.ECF_PE,
          ClassificationID.JDT_CORE_PE,
          ClassificationID.JDT_DEBUG_PE,
          ClassificationID.JDT_UI_PE,
          ClassificationID.PDE_UI_PE,
          ClassificationID.TOMCAT70_PE);

  public static String[] LTR_NL = {
    DatasetConfig.ASPECTJ, DatasetConfig.PLATFORM, DatasetConfig.SWT
  };
  public static List<List<Integer>> LTR_NL_ID =
      List.of(ClassificationID.ASPECTJ_NL, ClassificationID.PLATFORM_NL, ClassificationID.SWT_NL);
  public static String[] LTR_ST = {
    DatasetConfig.ASPECTJ, DatasetConfig.PLATFORM,
  };
  public static List<List<Integer>> LTR_ST_ID =
      List.of(ClassificationID.ASPECTJ_ST, ClassificationID.PLATFORM_ST);
  public static String[] LTR_PE = {
    DatasetConfig.ASPECTJ, DatasetConfig.BLIA, DatasetConfig.PLATFORM, DatasetConfig.SWT
  };
  public static List<List<Integer>> LTR_PE_ID =
      List.of(
          ClassificationID.ASPECTJ_PE,
          ClassificationID.BIRT_PE,
          ClassificationID.PLATFORM_PE,
          ClassificationID.SWT_PE);

  public static String[] GIT_NL = {
    DatasetConfig.DRUID,
    DatasetConfig.PRESTO,
    DatasetConfig.PULSAR,
    DatasetConfig.REDISSON,
    DatasetConfig.TRINO,
    DatasetConfig.VERTX
  };
  public static List<List<Integer>> GIT_NL_ID =
      List.of(
          ClassificationID.DRUID_NL,
          ClassificationID.PRESTO_NL,
          ClassificationID.PULSAR_NL,
          ClassificationID.REDISSON_NL,
          ClassificationID.TRINO_NL,
          ClassificationID.VERTX_NL);
  public static String[] GIT_ST = {
          DatasetConfig.DRUID,
          DatasetConfig.GRPC,
          DatasetConfig.PRESTO,
          DatasetConfig.PULSAR,
          DatasetConfig.REDISSON,
          DatasetConfig.TRINO,
          DatasetConfig.VERTX
  };
  public static List<List<Integer>> GIT_ST_ID =
      List.of(
          ClassificationID.DRUID_ST,
          ClassificationID.GRPC_ST,
          ClassificationID.PRESTO_ST,
          ClassificationID.PULSAR_ST,
          ClassificationID.REDISSON_ST,
          ClassificationID.ROCKETMQ_ST,
          ClassificationID.TRINO_ST,
          ClassificationID.VERTX_ST);
  public static String[] GIT_PE = {
          DatasetConfig.DRUID,
          DatasetConfig.GRPC,
          DatasetConfig.PRESTO,
          DatasetConfig.PULSAR,
          DatasetConfig.REDISSON,
          DatasetConfig.ROCKETMQ,
          DatasetConfig.TRINO,
          DatasetConfig.VERTX
  };
  public static List<List<Integer>> GIT_PE_ID =
      List.of(
          ClassificationID.DRUID_PE,
          ClassificationID.GRPC_PE,
          ClassificationID.PRESTO_PE,
          ClassificationID.PULSAR_PE,
          ClassificationID.REDISSON_PE,
          ClassificationID.ROCKETMQ_PE,
          ClassificationID.TRINO_PE,
          ClassificationID.VERTX_PE);
}
