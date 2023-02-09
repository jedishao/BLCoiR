package evaluation.prepare;

import java.io.IOException;
import java.util.List;

public interface Prepare {
    void collectBR(String path, String brPath) throws IOException;
    void getVersion(String path, List<Integer> con) throws IOException;
    void getChanges(String path, String brPath, List<Integer> con) throws IOException;
}
