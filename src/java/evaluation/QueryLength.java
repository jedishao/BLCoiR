package evaluation;

import uc.eecs.core.FaultLocalizationRunner;
import utils.ContentLoader;
import utils.config.BugReportsID;
import utils.config.DatasetConfig;
import utils.config.EvaluationConfig;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryLength {
  public void cut() throws IOException {
    int length = 39;
    String path = "Query-Length/query/" + DatasetConfig.LTR + "/" + length + ".txt";
    FileWriter fileWriter = new FileWriter(path, false);
    for (String repository : EvaluationConfig.LTR) {
      List<String> query =
          ContentLoader.getAllLinesList(
              "query/" + DatasetConfig.LTR + "/" + repository + "/BLCoiR.txt");
      for (String s1 : query) {
        int i = 0;
        String id = s1.split("\t")[0];
        String s2 = s1.split("\t")[1];
        String[] s3 = s2.split(" ");
        StringBuilder sb = new StringBuilder();
        while (i < length) {
          if (s3.length > i) {
            if (s3[i].length() != 0) sb.append(s3[i]).append(" ");
          }
          i++;
        }
        System.out.println(id + "\t" + sb);
        fileWriter.write(id + "\t" + sb);
        fileWriter.write("\n");
        fileWriter.flush();
      }
    }
    fileWriter.close();
  }

  public void cut1() throws IOException {
    int length = 20;
    List<String> query =
        ContentLoader.getAllLinesList("query/" + DatasetConfig.BLIZZARD + "/tomcat70/BLCoiR.txt");
    for (String s1 : query) {
      int i = 0;
      String id = s1.split("\t")[0];
      String s2 = s1.split("\t")[1];
      String[] s3 = s2.split(" ");
      StringBuilder sb = new StringBuilder();
      while (i < length) {
        if (s3.length > i) {
          if (s3[i].length() != 0) sb.append(s3[i]).append(" ");
        }
        i++;
      }
      System.out.println(id + "\t" + sb);
    }
  }

  public void queryResults() throws IOException, InterruptedException {
    String ben = DatasetConfig.BENCH4BL;
    int length = 40;
    String path = "Query-Length/evaluation/" + ben + "/" + length + ".txt";

    FileWriter fileWriter = new FileWriter(path, false);
    List<String> query =
        ContentLoader.getAllLinesList("Query-Length/query/" + ben + "/" + length + ".txt");
    int index = 0;
    Map<String, List<Integer>> map = new HashMap<>();
    //    map.put(DatasetConfig.DRUID, BugReportsID.DRUID);
    //    map.put(DatasetConfig.GRPC, BugReportsID.GRPC);
    //    map.put(DatasetConfig.PRESTO, BugReportsID.PRESTO);
    //    map.put(DatasetConfig.PULSAR, BugReportsID.PULSAR);
    //    map.put(DatasetConfig.REDISSON, BugReportsID.REDISSON);
    //    map.put(DatasetConfig.ROCKETMQ, BugReportsID.ROCKETMQ);
    //    map.put(DatasetConfig.TRINO, BugReportsID.TRINO);
    //    map.put(DatasetConfig.VERTX, BugReportsID.VERTX);
//        map.put(DatasetConfig.ECF,BugReportsID.ECF);
//        map.put(DatasetConfig.CORE,BugReportsID.JDT_CORE);
//        map.put(DatasetConfig.J_UI,BugReportsID.JDT_UI);
//        map.put(DatasetConfig.DEBUG,BugReportsID.JDT_DEBUG);
//        map.put(DatasetConfig.P_UI,BugReportsID.PDE_UI);
//        map.put(DatasetConfig.TOMCAT, BugReportsID.TOMCAT70);
        map.put(DatasetConfig.A_CAMEL, BugReportsID.CAMEL);
        map.put(DatasetConfig.A_HBASE, BugReportsID.HBASE);
        map.put(DatasetConfig.A_HIVE, BugReportsID.HIVE);
        map.put(DatasetConfig.C_CODEC, BugReportsID.CODEC);
        map.put(DatasetConfig.C_COLLECTIONS, BugReportsID.COLLECTIONS);
        map.put(DatasetConfig.C_COMPRESS, BugReportsID.COMPRESS);
        map.put(DatasetConfig.C_CONFIGURATION, BugReportsID.CONFIGURATION);
        map.put(DatasetConfig.C_IO, BugReportsID.IO);
        map.put(DatasetConfig.C_LANG, BugReportsID.LANG);
        map.put(DatasetConfig.C_MATH, BugReportsID.MATH);
        map.put(DatasetConfig.SP_AMQP, BugReportsID.AMQP);
        map.put(DatasetConfig.SP_BATCH, BugReportsID.BATCH);
        map.put(DatasetConfig.SP_DATACMNS, BugReportsID.DATACMNS);
        map.put(DatasetConfig.SP_DATAGRAPH, BugReportsID.DATAGRAPH);
        map.put(DatasetConfig.SP_DATAJPA, BugReportsID.DATAJPA);
        map.put(DatasetConfig.SP_DATAMONGO, BugReportsID.DATAMONGO);
        map.put(DatasetConfig.SP_DATAREDIS, BugReportsID.DATAREDIS);
        map.put(DatasetConfig.SP_SEC, BugReportsID.SEC);
        map.put(DatasetConfig.SP_SECOAUTH, BugReportsID.SECOAUTH);
        map.put(DatasetConfig.SP_SGF, BugReportsID.SGF);
        map.put(DatasetConfig.SP_SHDP, BugReportsID.SHDP);
        map.put(DatasetConfig.SP_SPR, BugReportsID.SPR);
        map.put(DatasetConfig.SP_SWF, BugReportsID.SWF);
        map.put(DatasetConfig.SP_SWS, BugReportsID.SWS);
        map.put(DatasetConfig.W_WFCORE, BugReportsID.WFCORE);
        map.put(DatasetConfig.W_WFLY, BugReportsID.WFLY);
//    map.put(DatasetConfig.ASPECTJ, BugReportsID.ASPECTJ);
//    map.put(DatasetConfig.BIRT, BugReportsID.BIRT);
//    map.put(DatasetConfig.PLATFORM, BugReportsID.PLATFORM);
//    map.put(DatasetConfig.SWT, BugReportsID.SWT);
    for (String repository : EvaluationConfig.BENCH4BL) {
      for (int id : map.get(repository)) {
        int idd = Integer.parseInt(query.get(index).split("\t")[0]);
        String q = query.get(index).split("\t")[1];
        FaultLocalizationRunner fr1 = new FaultLocalizationRunner(ben, repository, idd, q);
        for (int r : fr1.getGoldFileIndicesClass()) {
          System.out.println(id + "\t" + r);
          fileWriter.write(id + "\t" + r + "\n");
        }
        index++;
      }
    }
    //    System.out.println(index);
    fileWriter.close();
  }

  public void test() {
    String s =
        "container xmppremoteserviceadapterfactory invoke  handleaddregistrationcan  xmppregistrysharedobject get remoteserverid getlocalcontainerid connection  handleaddregistration situation receive npe occur packet message throw registrysharedobject  getlocalcontainerid";
    String[] s3 = s.trim().split(" ");
    for (String s1 : s3) {
      System.out.println(s1.length() == 0);
    }
  }

  public static void main(String[] args) throws IOException, InterruptedException {
    QueryLength ql = new QueryLength();
    ql.queryResults();
    //    ql.cut();
  }
}
