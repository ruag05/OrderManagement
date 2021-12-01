package filerenders;

import java.io.FileWriter;
import java.io.IOException;

public class CSVFileWriter implements FileRender{
    private String fileName = "";

    public CSVFileWriter(String fileName){
        this.fileName = fileName;
    }
    @Override
    public void writeToFile(String message) throws IOException {
        FileWriter csvWriter = new FileWriter("src/files/" + fileName + ".csv", true);
        csvWriter.append("\n").append(message);
        csvWriter.flush();
        csvWriter.close();
    }

}
