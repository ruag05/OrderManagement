import models.Item;
import java.util.List;
import java.util.Map;

public class Validation {

    public boolean isReqQuantityValid(Database db, String[] item, String itemCategory){
        String itemName = item[0];
        int itemQuantity = Integer.parseInt(item[1]);
        for(Map.Entry<String, List<Item>> entry : db.inventory.entrySet()){
            if(entry.getKey().equals(itemCategory)){
                return entry.getValue().stream()
                        .filter(i -> i.getName().equals(itemName))
                        .anyMatch(i -> i.getQuantity() >= itemQuantity);
            }
        }
        return true;
    }

    public boolean isCategoryCapSatisfied(Map<String, Integer> reqQuantityCategoryWise, Map<String, Integer> categoryCaps,
                                          String[] item, String itemCategory){

        if(reqQuantityCategoryWise.getOrDefault(itemCategory, 0) + Integer.parseInt(item[1])
                <= categoryCaps.get(itemCategory)){

            //add requested item quantity to the total quantity per category
            reqQuantityCategoryWise.put(itemCategory,
                    reqQuantityCategoryWise.getOrDefault(itemCategory, 0) + Integer.parseInt(item[1]));

            return true;
        } else return false;
    }
}
