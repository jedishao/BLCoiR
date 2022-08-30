package utils;

import java.io.*;
import java.util.ArrayList;

public class DatasetCollection {

  public ArrayList<String> queryCollect(String repository) throws IOException {
    ArrayList<String> collectResults = new ArrayList<>();
    File file = new File("C:\\Java\\ConBugLoc-dataset\\dataset\\" + repository + "_.txt");

    BufferedReader br = new BufferedReader(new FileReader(file));

    String st;

    while ((st = br.readLine()) != null) collectResults.add(st);

    return collectResults;
  }

  public ArrayList<Integer> bugIDCollect(String repository) throws IOException {
    ArrayList<Integer> bugID = new ArrayList<>();
    File file = new File("C:\\Java\\ConBugLoc-dataset\\dataset\\" + repository + "_index.txt");

    BufferedReader br = new BufferedReader(new FileReader(file));

    String st;

    while ((st = br.readLine()) != null) bugID .add(Integer.parseInt(st));

    return bugID;
  }

}
