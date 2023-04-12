package uc.eecs.core.process;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
  public static void main(String[] args) {
    String s = "getoLgervice";
    Pattern p = Pattern.compile("[a-z]+[A-Z](.*)");
    Matcher m = p.matcher(s);
    System.out.println(m.matches());
    List<Integer> list = new ArrayList<>();
    list.add(1);
    list.add(2);
    System.out.println(list.indexOf(1));
    for (String s1 : s.split(" ")) {
      Matcher m1 = p.matcher(s1);
      if (m1.matches()){
        System.out.println(s1.split("\\.")[s1.split("\\.").length-1]);
      }
    }
  }
}
