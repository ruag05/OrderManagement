package filerenders;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class TXTFileWriter implements FileRender{
    private String fileName = "";

    public TXTFileWriter(String fileName){
        this.fileName = fileName;
    }
    @Override
    public void writeToFile(String message) throws IOException {
        String filePath = "src/files/output/" + fileName + ".txt";
//        FileWriter txtWriter = new FileWriter(filePath, true);
        FileWriter txtWriter = new FileWriter(new File(filePath), true);
        txtWriter.append("\n").append(message);
        txtWriter.flush();
        txtWriter.close();
    }
}
