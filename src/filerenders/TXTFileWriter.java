package filerenders;

import java.io.FileWriter;
import java.io.IOException;

public class TXTFileWriter implements FileRender{
    private String fileName = "";

    public TXTFileWriter(String fileName){
        this.fileName = fileName;
    }
    @Override
    public void writeToFile(String message) throws IOException {
        FileWriter txtWriter = new FileWriter("files/" + fileName + ".txt", true);
        txtWriter.append("\n").append(message);
        txtWriter.flush();
        txtWriter.close();
    }
}
