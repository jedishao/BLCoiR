package uc.eecs.core.process;


import utils.config.DatasetConfig;

public class TextKeywordSelector {
    String title;
    String bugDesc;
    int TOPK;
    String repoName;

    public TextKeywordSelector(String repoName, String title, String bugDesc, int TOPK, boolean otherMethod) {
        this.repoName = repoName;
        this.title = title;
        this.bugDesc = bugDesc;
        this.TOPK = TOPK;
    }

    public TextKeywordSelector(String repoName, String title, String bugDesc, int TOPK) {
        this.repoName = repoName;
        this.title = title;
        this.bugDesc = bugDesc;
        this.TOPK = TOPK;
        this.loadACERConfigs();
    }
    protected void loadACERConfigs() {
        // loading the required variables for the strict
        System.setProperty("HOME_DIR", DatasetConfig.DATASET_DIR);
        System.setProperty("STOPWORD_DIR", "/nlp");
        System.setProperty("SAMURAI_DIR", "/tbdata");
        System.setProperty("MAX_ENT_MODELS_DIR", "/models");
        System.setProperty("REPOSITORY_SRC_DIRECTORY", "/Corpus");
        System.setProperty("CORPUS_DIR", "/Corpus");
        System.setProperty("INDEX_DIR", "/Lucene-Index");
    }

//

}
