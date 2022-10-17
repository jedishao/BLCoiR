package uc.eecs.br;

import utils.ContentLoader;
import utils.config.DatasetConfig;
import utils.config.QueryConfig;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BRClassification {
  ArrayList<String> st_content;

  public BRClassification() {
    st_content = new ArrayList<>();
  }

  public ArrayList<String> getSt_content() {
    return st_content;
  }

  /**
   * If the
   *
   * @param content has stack traces
   * @return ture
   */
  public boolean classification(ArrayList<String> content) {
    Pattern p = Pattern.compile(QueryConfig.STACK_REGEX);
    for (String line : content){
      Matcher m = p.matcher(line);
      if (m.matches()) return true;
    }
    return false;
  }

  public static void main(String[] args) {

    // TODO Auto-generated method stub
//    String repoName = DatasetConfig.ECF;
//    int bugID = 146622;
//    String brFile = DatasetConfig.HOME_DIR + "/BR-Raw/" + repoName + "/" + bugID + ".txt";
//    String title = BRLoader.loadBRTitle(repoName, bugID);
//    String bugReportContent = ContentLoader.loadFileContent(brFile);
//    BRClassification brc = new BRClassification();
//    Boolean trace = brc.classification(bugReportContent);
//    System.out.println(trace);
    String content = "Bug 223484 – Several threads remain though disconnect was done from the XMPP server\n" +
            "Build ID: 2.0.0.v20080310-1643\n" +
            "Steps To Reproduce:\n" +
            "1.\n" +
            "Connect the XMPP server\n" +
            "2.\n" +
            "Discoonect\n" +
            "5 threads do not dispose;\n" +
            "[org.eclipse.ecf.docshare.DocShare:run]\n" +
            "[org.eclipse.ecf.presence.collab.ui.url.URLShare:run]\n" +
            "[org.eclipse.ecf.presence.collab.ui.view.ViewShare:run]\n" +
            "[org.eclipse.ecf.presence.collab.ui.console.ConsoleShare:run]\n" +
            "[org.eclipse.ecf.presence.collab.ui.screencapture.ScreenCaptureShare:run]\n" +
            "More information:\n" +
            "Repeat 3 time ( 1, 2, 1, 2, 1 and 2),\n" +
            "the following threads remained.\n" +
            "Thread [main] (Running)\n" +
            "Daemon Thread [State Data Manager] (Running)\n" +
            "Daemon Thread [Framework Event Dispatcher] (Running)\n" +
            "Daemon Thread [Start Level Event Dispatcher] (Running)\n" +
            "Daemon Thread [Bundle File Closer] (Running)\n" +
            "Thread [Worker-0] (Running)\n" +
            "Thread [Worker-4] (Running)\n" +
            "Thread [JMDNS Discovery Thread] (Running)\n" +
            "Thread [Timer-0] (Running)\n" +
            "Thread [JmDNS.SocketListener] (Running)\n" +
            "Thread [org.eclipse.ecf.datashare.IChannelContainerAdapter:run] (Running)\n" +
            "Daemon Thread [Thread-5] (Running)\n" +
            "Thread [org.eclipse.ecf.docshare.DocShare:run] (Running)\n" +
            "Thread [org.eclipse.ecf.presence.collab.ui.url.URLShare:run] (Running)\n" +
            "Thread [org.eclipse.ecf.presence.collab.ui.view.ViewShare:run] (Running)\n" +
            "Thread [org.eclipse.ecf.presence.collab.ui.console.ConsoleShare:run] (Running)\n" +
            "Thread [org.eclipse.ecf.presence.collab.ui.screencapture.ScreenCaptureShare:run] (Running)\n" +
            "Thread [org.eclipse.ecf.datashare.IChannelContainerAdapter:run] (Running)\n" +
            "Thread [org.eclipse.ecf.docshare.DocShare:run] (Running)\n" +
            "Thread [org.eclipse.ecf.presence.collab.ui.url.URLShare:run] (Running)\n" +
            "Thread [org.eclipse.ecf.presence.collab.ui.view.ViewShare:run] (Running)\n" +
            "Thread [org.eclipse.ecf.presence.collab.ui.console.ConsoleShare:run] (Running)\n" +
            "Thread [org.eclipse.ecf.presence.collab.ui.screencapture.ScreenCaptureShare:run] (Running)\n" +
            "Thread [org.eclipse.ecf.datashare.IChannelContainerAdapter:run] (Running)\n" +
            "Thread [org.eclipse.ecf.docshare.DocShare:run] (Running)\n" +
            "Thread [org.eclipse.ecf.presence.collab.ui.url.URLShare:run] (Running)\n" +
            "Thread [org.eclipse.ecf.presence.collab.ui.view.ViewShare:run] (Running)\n" +
            "Thread [org.eclipse.ecf.presence.collab.ui.console.ConsoleShare:run] (Running)\n" +
            "Thread [org.eclipse.ecf.presence.collab.ui.screencapture.ScreenCaptureShare:run] (Running)";
    Pattern p = Pattern.compile(QueryConfig.STACK_REGEX);
    Matcher m = p.matcher(content);
    System.out.println(m.find());
    //    for (String t :trace){
    //      System.out.println(t);
    //    }
    //    BLIZZARDQueryProvider blizzardProvider =
    //        new BLIZZARDQueryProvider(repoName, bugID, title, bugReportContent);
    //    System.out.println(blizzardProvider.provideBLIZZARDQuery());
  }
}
