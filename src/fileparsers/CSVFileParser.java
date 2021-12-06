package fileparsers;

import fileparsers.FileParser;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CSVFileParser implements FileParser {

    @Override
    public List<String[]> parseFile(String filePath) {
        List<String[]> csvData = null;
        BufferedReader reader = null;
        String csvRow = "";

        try{
            reader = new BufferedReader(new FileReader(filePath));
            csvData = new ArrayList<>();

            //ignore csv header and table header lines
            csvRow = reader.readLine();
           // csvRow = reader.readLine();

            //iterate through the csv content
            while((csvRow = reader.readLine()) != null) {
                String[] rowData = csvRow.split(",");
                csvData.add(rowData);
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return csvData;
        }
    }
}
