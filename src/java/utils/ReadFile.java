package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class ReadFile {
  public ArrayList<ArrayList<ArrayList<String>>> readFormFile(String path) throws IOException {
    ArrayList<ArrayList<ArrayList<String>>> collectResults = new ArrayList<>();
    ArrayList<String> raw = new ArrayList<>();
    File file = new File(path);
    BufferedReader br = new BufferedReader(new FileReader(file));
    String st;
    while ((st = br.readLine()) != null) {
      raw.add(st);
    }
    for (String s1: raw){
      ArrayList<ArrayList<String>> inside1 = new ArrayList<>();
      for (String s2 : s1.split("],")){
        ArrayList<String> inside2 = new ArrayList<>(Arrays.asList(s2.split(",")));
        inside1.add(inside2);
      }
      collectResults.add(inside1);
    }
    return collectResults;
  }

  public static void main(String[] args) throws IOException {
    ReadFile rf = new ReadFile();
    ArrayList<ArrayList<ArrayList<String>>> results;
    results = rf.readFormFile("src/main/resources/concurrency/tomcat70_1.txt");
    ArrayList<ArrayList<String>> r1 = results.get(1);
    for (ArrayList<String> r2 : r1){
      for (String r3 : r2){
        System.out.println(r3);
      }
      System.out.println("==================================");
    }
  }
}
