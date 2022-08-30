package uc.eecs.br.processing;

import utils.MiscUtility;

import java.util.ArrayList;
import java.util.Arrays;

public class TextNormalizer {
    String content;
    final int MAX_KEYWORD_COUNT = 1024;

    public TextNormalizer(String content) {
        this.content = content;
    }

    public TextNormalizer() {
        // default constructor
    }
    public String normalizeSimple() {
        String[] words = this.content.split("\\p{Punct}+|\\d+|\\s+");
        ArrayList<String> wordList = new ArrayList<>(Arrays.asList(words));
        return MiscUtility.list2Str(wordList);
    }

}
