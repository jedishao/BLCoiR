package uc.eecs.core.selector;

import acer.coderank.query.expansion.ACERQueryProviderMinimal;
import uc.eecs.br.processing.TextNormalizer;
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
        System.setProperty("HOME_DIR", DatasetConfig.HOME_DIR);
        System.setProperty("STOPWORD_DIR", "/nlp");
        System.setProperty("SAMURAI_DIR", "/tbdata");
        System.setProperty("MAX_ENT_MODELS_DIR", "/models");
        System.setProperty("REPOSITORY_SRC_DIRECTORY", "/Corpus");
        System.setProperty("CORPUS_DIR", "/Corpus");
        System.setProperty("INDEX_DIR", "/Lucene-Index");
    }

    public String getSearchTermsWithCR(int expansionSize) {

        String indexFolder = DatasetConfig.INDEX_DIR + "/" + repoName;
        String corpusFolder = DatasetConfig.CORPUS_DIR + "/" + repoName;

        String baselineQuery = new TextNormalizer(this.title).normalizeText();
        String normDesc = new TextNormalizer(this.bugDesc).normalizeText();

        ACERQueryProviderMinimal acer = new ACERQueryProviderMinimal(repoName, 0, baselineQuery, indexFolder,
                corpusFolder, null);
        String sourceTerms = acer.getExtendedQuery(expansionSize);

        return  sourceTerms+"\t"+normDesc;

    }
//

}
