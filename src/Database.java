import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import models.Item;

public final class Database {

    private static Database instance;
    public Map<String, List<Item>> inventory;

    public static Database getInstance(String datasetPath) throws IOException {
        if (instance == null) {
            instance = new Database(datasetPath);
        }
        return instance;
    }

    private Database(String datasetPath) throws IOException {
        this.inventory = buildInventory(datasetPath);
    }

    private Map<String, List<Item>> buildInventory(String datasetPath) throws IOException {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(datasetPath));
            inventory = new HashMap<>();

            //ignore csv header and table header lines
            String csvRow = reader.readLine();

            //iterate through the dataset and store as inventory
            while((csvRow = reader.readLine()) != null) {
                String[] rowData = csvRow.split(",");
                if(inventory.containsKey(rowData[0])){
                    inventory.get(rowData[0]).add(new Item(rowData[1], Integer.parseInt(rowData[2]),
                            Double.parseDouble(rowData[3])));
                } else{
                    inventory.put(rowData[0], new ArrayList<Item>(){
                        {
                            add(new Item(rowData[1], Integer.parseInt(rowData[2]), Double.parseDouble(rowData[3])));
                        }
                    });
                }
            }
        } catch (Exception e) {
            System.out.println("Database file could not read. Please check the database file path");
            //e.printStackTrace();
        }
        finally {
            assert reader != null;
            reader.close();
            return inventory;
        }
    }

    public void updateItemQuantity(String[] item, String itemCategory){
        List<Item> itemsList = inventory.get(itemCategory);
        if(!itemsList.isEmpty()){
            Optional<Item> myItem = itemsList.stream().filter(i ->i.getName().equals(item[0])).findFirst();
            myItem.ifPresent(value -> value.setQuantity(value.getQuantity() - Integer.parseInt(item[1])));
        }
    }
}
