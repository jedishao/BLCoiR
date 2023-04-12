package results;

import uc.eecs.br.loader.BRLoader;
import uc.eecs.core.FaultLocalizationRunner;
import uc.eecs.core.query.QueryGenerator;
import uc.eecs.nlp.DepManager;
import utils.ContentLoader;
import utils.FileURL;
import utils.config.DatasetConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConBugLocResults {
    String benchName;
    String repository;

    public ConBugLocResults() {
    }

    public ConBugLocResults(String benchName, String repository) {
        this.benchName = benchName;
        this.repository = repository;
    }


    public List<Integer> rankCollection(List<Integer> bugId, String tech) {
        ArrayList<Integer> rankList = new ArrayList<>();
        String queryFile = FileURL.queryPath(benchName, repository, tech);
        ArrayList<String> queryList = ContentLoader.getAllLinesOptList(queryFile);
        int index = 0;
        for (String query : queryList) {
            FaultLocalizationRunner faultLocalizationRunner =
                    new FaultLocalizationRunner(benchName, repository, bugId.get(index), query);
            rankList.add(faultLocalizationRunner.getFirstRank());
            index++;
        }

        // System.out.println("First found index:" + );
        return rankList;
    }

    public void rankCollection(String bench, String repository) {
        int bugID = 39769;
        String searchQuery =
                "myservlet servlet directory loader destroy shared wrong called loader thread "
                        + "context standard system servlet currentthread classloader problem getcontextclassloader "
                        + "instance code web method classloader securityutil stream event capture standardcontext " +
                        "instance destroy";
        FaultLocalizationRunner faultLocalizationRunner =
                new FaultLocalizationRunner(bench, repository, bugID, searchQuery);
        System.out.println("First found index:" + faultLocalizationRunner.getFirstRank());
    }

    public ArrayList<String> queryCollection(ArrayList<Integer> bugId) {
        ArrayList<String> queryList = new ArrayList<>();
        DepManager depManager = new DepManager();
        for (int id : bugId) {
            //System.out.println(id);
            String brFile = FileURL.brPathAppend(benchName, repository, id);
            String title = BRLoader.loadBRTitle(brFile);
            ArrayList<String> bugReportContent = ContentLoader.getAllLinesOptList(brFile);
            QueryGenerator queryGenerator = new QueryGenerator(id, title, bugReportContent, depManager);
            String query = queryGenerator.generateQuery();
            queryList.add(query);
        }
        return queryList;
    }

    public ArrayList<Integer> bugIdCollection() {
        String path = FileURL.brPath(benchName, repository);
        ArrayList<Integer> bugIdList = new ArrayList<>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        assert tempList != null;
        for (File f : tempList) {
            bugIdList.add(Integer.parseInt(f.getName().split("\\.")[0]));
        }
        Collections.sort(bugIdList);
        return bugIdList;
    }

    public void bugIdCollection(String benchName, String repoName) {
        String path = FileURL.brPath(benchName, repoName);
        ArrayList<Integer> bugIdList = new ArrayList<>();
        File file = new File(path);
        File[] tempList = file.listFiles();
        assert tempList != null;
        for (File f : tempList) {
            System.out.println(f.getName().split("\\.")[0]);
        }
    }

    public static void main(String[] args) {
        String benchName = "BLIZZARD";
        String repoName = DatasetConfig.TOMCAT;
        ConBugLocResults results = new ConBugLocResults(benchName, repoName);
        ArrayList<Integer> list = results.bugIdCollection();
        System.out.println(list);
        //   int i = 0;
//    ArrayList<Integer> list1 = new ArrayList<>();
//    for (int j : list){
//      if (RankResults.OUR_TOMCAT.contains(j))
//        list1.add(i);
//      i ++;
//    }
//    System.out.println(list1);
        //System.out.println(list);
//    List<Integer> rankList = results.rankCollection(list);
//    System.out.println(rankList.toString());

//    int i = 0;
//    for (String id:list){
//      System.out.println(id);
//      System.out.println(queryList.get(i));
//      i++;
//    }
        String[] sym = {"(", ")", ":", "[", "]", "}", "{", "#", "-", "|", "%", "@", ".", "+", "=",
                "\\", "/", "#", "*", "?", "^", "$", "\"", "'", "<", ">"};
        ArrayList<String> queryList = results.queryCollection(list);
        for (String s : queryList) {
            StringBuilder stringBuilder = new StringBuilder();
            String s2 = s;
            for (String s1 : sym) {
                s2 = s2.replace(s1, " ");
            }
            String[] s3 = s2.split(" ");
            for (String s4 : s3){
                if (s4.trim().length() > 2){
                    Pattern p = Pattern.compile("(.)*\\d(.)*");
                    Matcher m = p.matcher(s4);
                    if (! m.matches() && ! s4.contains("_"))
                        stringBuilder.append(s4.trim()).append(" ");
                }
            }
            System.out.println(stringBuilder);
        }
        //System.out.println(list);

    }
}
