package uc.eecs.core;

import utils.ContentLoader;
import utils.config.DatasetConfig;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ResultRankManager {
    String repoName;
    ArrayList<String> results;
    ArrayList<String> goldset;
    public static HashMap<String, String> keyMap = new HashMap<>();
    String keyfile;

    public ResultRankManager(String repoName, ArrayList<String> results, ArrayList<String> goldset) {
        this.repoName = repoName;
        this.results = results;
        this.goldset = goldset;
        this.keyfile = DatasetConfig.CORPUS_INDEX_KEY_MAPPING + "/" + this.repoName + ".ckeys";
        if (keyMap.isEmpty()) {
            // load only the HashMap is empty
            loadKeys();
            // System.out.println("Keys loaded." + repoName);
        }
    }
    public void loadKeys() {
        // loading file name keys
        ArrayList<String> lines = ContentLoader.getAllLinesOptList(this.keyfile);
        for (String line : lines) {
            String[] parts = line.split(":");
            String key = parts[0] + ".java";
            keyMap.put(key, parts[2].trim()); // startled me
        }
    }

    public int getFirstGoldRank() {
        int foundIndex = -1;
        int index = 0;
        ArrayList<String> results = translateResults();
        outer: for (String elem : results) {
            index++;
            for (String item : goldset) {
                if (elem.endsWith(item)) {
                    // found = true;
                    foundIndex = index;
                    break outer;
                }
            }
        }
        return foundIndex;
    }

    protected ArrayList<String> translateResults() {
        // translating the results
        ArrayList<String> translated = new ArrayList<>();
        for (String fileURL : results) {
            String key = new File(fileURL).getName();
            if (keyMap.containsKey(key)) {
                String orgFileName = keyMap.get(key);
                // replacing back slashes
                orgFileName = orgFileName.replace('\\', '/');
                translated.add(orgFileName);
            }
        }
        return translated;
    }
}
