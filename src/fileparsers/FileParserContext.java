package fileparsers;

import fileparsers.FileParser;

import java.util.List;

public class FileParserContext {
    private FileParser fileParser;

    public FileParserContext(FileParser parser){
        this.fileParser = parser;
    }

    public List<String[]> implementStrategy(String path) {
        return fileParser.parseFile(path);
    }
}
