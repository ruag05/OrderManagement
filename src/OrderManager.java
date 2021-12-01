import categoryclassifiers.CategoryClassifier;
import categoryclassifiers.EssentialCategoryClassifier;
import categoryclassifiers.LuxuryCategoryClassifier;
import categoryclassifiers.MiscCategoryClassifier;
import java.util.*;
import fileparsers.CSVFileParser;
import fileparsers.FileParserContext;
import filerenders.CSVFileWriter;
import filerenders.FileRender;
import filerenders.TXTFileWriter;
import models.Item;

public class OrderManager {

    private Database database = null;
    private Map<String, Integer> categoryCaps = null;
    private double cartPrice = 0d;

    public void setDatabase(Database db) {
        this.database = db;
    }

    public void setCategoryCaps(int essQuantityCap, int luxQuantityCap, int miscQuantityCap){
        categoryCaps = new HashMap<>();
        categoryCaps.put("Essential", essQuantityCap);
        categoryCaps.put("Luxury", luxQuantityCap);
        categoryCaps.put("Misc", miscQuantityCap);
    }

    public void processOrder(String inputFilePath){
        FileParserContext fileParserContxt;

        try {

            //parse input file in CSV
            fileParserContxt = new FileParserContext(new CSVFileParser());
            List<String[]> items = fileParserContxt.implementStrategy(inputFilePath);

            String itemCategory = "";
            Map<String, Integer> reqQuantityCategoryWise = new HashMap<>();
            List<String> paymentCards = new ArrayList<>();
            boolean isItemPurchased = false;
            FileRender fileWriter;
            Validation validator = new Validation();

            for(String[] item : items){

                //get the category for each item
                itemCategory = getItemCategory(item);
                if(itemCategory != null){

                    //check if requested item quantity is available in database
                    if(validator.isReqQuantityValid(database, item, itemCategory)){

                        //check if requested quantity exceeds quantity cap for the category
                        if(validator.isCategoryCapSatisfied(reqQuantityCategoryWise, categoryCaps, item, itemCategory)){

                            //valid request -> add item to cart
                            addItemToCart(item, itemCategory);

                            //get & store card number
                            String cardNumber = item[2];
                            if(!paymentCards.contains(cardNumber))
                                paymentCards.add(cardNumber);

                            //reduce ordered item quantity from the database
                            database.updateItemQuantity(item, itemCategory);

                            isItemPurchased = true;
                        } else{

                            //incorrect request -> output to TXT file
                            fileWriter = new TXTFileWriter("Error Log");
                            fileWriter.writeToFile("Please correct quantities: " + Arrays.toString(item));
                        }
                    } else{
                        //incorrect request -> output to TXT file
                        fileWriter = new TXTFileWriter("Error Log");
                        fileWriter.writeToFile("Please correct quantities: " + Arrays.toString(item));
                    }
                } else{

                    //item not found in database
                    System.out.println("Item: '" + item[0] + "' not found in database");
                }
            }

            //print total amount paid only if at least one item was purchased and the total amount paid is > $0
            if(isItemPurchased && cartPrice > 0){
                fileWriter = new CSVFileWriter("Order Summary");
                fileWriter.writeToFile("Total amount paid is: " + cartPrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getItemCategory(String[] item){

        //[2]Applied Chain of Responsibility Pattern
        //to handle the different types of item categories
        CategoryClassifier essCategoryClassifier = new EssentialCategoryClassifier();
        CategoryClassifier luxCategoryClassifier = new LuxuryCategoryClassifier();
        CategoryClassifier miscCategoryClassifier = new MiscCategoryClassifier();

        essCategoryClassifier.setInventory(database.inventory.get("Essential"));
        essCategoryClassifier.setNext(luxCategoryClassifier);

        luxCategoryClassifier.setInventory(database.inventory.get("Luxury"));
        luxCategoryClassifier.setNext(miscCategoryClassifier);

        miscCategoryClassifier.setInventory(database.inventory.get("Misc"));
        miscCategoryClassifier.setNext(null);

        return essCategoryClassifier.classifyItemCategory(item);
    }

    public void addItemToCart(String[] item,  String itemCategory){
        String itemName = item[0];
        int itemQuantity = Integer.parseInt(item[1]);
        for(Map.Entry<String, List<Item>> entry : database.inventory.entrySet()){
            if(entry.getKey().equals(itemCategory)){
                Optional<Item> matchedItem = entry.getValue().stream().
                        filter(i -> i.getName().equals(itemName)).findFirst();
                matchedItem.ifPresent(value -> cartPrice += value.getPrice() * itemQuantity);
            }
        }
    }
}