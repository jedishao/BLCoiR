package uc.eecs.core.process;

import utils.config.DatasetConfig;

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
        System.setProperty("HOME_DIR", DatasetConfig.DATASET_DIR);
        System.setProperty("STOPWORD_DIR", "/src/dataset/data");
        System.setProperty("SAMURAI_DIR", "/tbdata");
        System.setProperty("MAX_ENT_MODELS_DIR", "/models");
    }
}
