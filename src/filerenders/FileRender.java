package filerenders;

import java.io.IOException;

public interface FileRender {
    public void writeToFile(String message) throws IOException;
}
