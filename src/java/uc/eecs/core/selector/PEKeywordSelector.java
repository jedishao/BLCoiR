package uc.eecs.core.selector;

import strict.query.SearchTermProvider;
import utils.MiscUtility;
import utils.config.DatasetConfig;

import java.util.ArrayList;

public class PEKeywordSelector {
    String title;
    String bugReportContent;
    int TOPK;

    public PEKeywordSelector(String title, String bugReportContent, int TOPK) {
        this.title = title;
        this.bugReportContent = bugReportContent;
        this.TOPK = TOPK;
        this.loadSTRICTConfigs();
    }

    protected void loadSTRICTConfigs() {
        // loading the required variables for the strict library
        System.setProperty("HOME_DIR", DatasetConfig.HOME_DIR);
        System.setProperty("STOPWORD_DIR", "/src/dataset/data");
        System.setProperty("SAMURAI_DIR", "/tbdata");
        System.setProperty("MAX_ENT_MODELS_DIR", "/models");
    }
    public ArrayList<String> getSearchTerms() {
        SearchTermProvider keywordProvider = new SearchTermProvider("", 0, this.title, this.bugReportContent);
        String termStr = keywordProvider.provideSearchQuery("TPR");
        ArrayList<String> searchTerms = MiscUtility.str2List(termStr);
        ArrayList<String> keywords = new ArrayList<>();
        for (String sterm : searchTerms) {
            if (sterm.length() >= 3) {
                sterm = sterm.toLowerCase();
                keywords.add(sterm);
                if (keywords.size() == TOPK) {
                    break;
                }
            }
        }
        return keywords;
    }
}
