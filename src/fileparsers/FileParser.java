package fileparsers;

import java.util.List;

public interface FileParser {
    List<String[]> parseFile(String filePath);
}
