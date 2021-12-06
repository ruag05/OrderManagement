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
    private int orderNumber = 0;
    private CardHolder cardHolder = null;

    public void setDatabase(Database db) { this.database = db;}

    public void setCards(CardHolder cdHdr){ this.cardHolder = cdHdr;}

    public void setCategoryCaps(int essQuantityCap, int luxQuantityCap, int miscQuantityCap){
        categoryCaps = new HashMap<>();
        categoryCaps.put("Essential", essQuantityCap);
        categoryCaps.put("Luxury", luxQuantityCap);
        categoryCaps.put("Misc", miscQuantityCap);
    }

    public void processOrder(String inputFilePath){
        orderNumber++;
        cartPrice = 0;
        FileParserContext fileParserContxt;

        try {
            //parse input file in CSV
            fileParserContxt = new FileParserContext(new CSVFileParser());
            List<String[]> items = fileParserContxt.implementStrategy(inputFilePath);
            if(!items.isEmpty()){

                String itemCategory = "";
                Map<String, Integer> reqQuantityCategoryWise = new HashMap<>();
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

                                if(cardHolder.cards.stream().noneMatch(c -> c.cardNumber.equals(cardNumber)))
                                    cardHolder.cards.add(new Card(cardNumber));

                                //reduce ordered item quantity from the database
                                database.updateItemQuantity(item, itemCategory);

                                isItemPurchased = true;
                            }
                            else{

                                //incorrect request -> output to TXT file
                                fileWriter = new TXTFileWriter("ErrorLog");
                                fileWriter.writeToFile("The cap for category: " + itemCategory + " is exceeded. " +
                                        "Please correct quantity of the item: " + Arrays.toString(item));
                            }
                        }
                        else{

                            //incorrect request -> output to TXT file
                            fileWriter = new TXTFileWriter("ErrorLog");
                            fileWriter.writeToFile("Sorry, database does not have enough quantity for item: " + item[0] +
                                    ". Please correct quantity of the item: " + Arrays.toString(item));
                        }
                    }
                    else{

                        //item not found in database
                        System.out.println("Item: '" + item[0] + "' not found in database. Please check");
                    }
                }

                //print total amount paid only if at least one item was purchased and the total amount paid is > $0
                if(isItemPurchased){
                    fileWriter = new CSVFileWriter("Order Summary");
                    fileWriter.writeToFile("Order: " + orderNumber + ". The total amount paid is: " + cartPrice);
                }
            }
            else{
                System.out.println("Input/Order file is empty. No item to process");
            }

            //print cards
            System.out.println("Following are the cards present in the application");
            for (Card card : cardHolder.cards){
                System.out.println(card.cardNumber);
            }
        }
        catch (Exception e) {
            System.out.println("Input/Order file could not be read. Please check the file path");
            //e.printStackTrace();
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
        if(item.length > 0) {
            String itemName = item[0];
            int itemQuantity = Integer.parseInt(item[1]);
            for (Map.Entry<String, List<Item>> entry : database.inventory.entrySet()) {
                if (entry.getKey().equals(itemCategory)) {
                    Optional<Item> matchedItem = entry.getValue().stream().
                            filter(i -> i.getName().equals(itemName)).findFirst();
                    matchedItem.ifPresent(value -> cartPrice += value.getPrice() * itemQuantity);
                }
            }
        }
    }
}