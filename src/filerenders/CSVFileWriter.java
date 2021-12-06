package filerenders;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CSVFileWriter implements FileRender{
    private String fileName = "";

    public CSVFileWriter(String fileName){
        this.fileName = fileName;
    }
    @Override
    public void writeToFile(String message) throws IOException {
        String filePath = "src/files/output/" + fileName + ".csv";
        //FileWriter csvWriter = new FileWriter(filePath, true);
        FileWriter csvWriter = new FileWriter(new File(filePath), true);
        csvWriter.append("\n").append(message);
        csvWriter.flush();
        csvWriter.close();
    }
}
