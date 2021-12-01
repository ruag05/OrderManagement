package categoryclassifiers;

import categoryclassifiers.CategoryClassifier;
import java.util.List;
import models.Item;

public class MiscCategoryClassifier implements CategoryClassifier {
    CategoryClassifier nextClassifier = null;
    List<Item> items = null;

    @Override
    public void setNext(CategoryClassifier nextClassifier) {
        this.nextClassifier = nextClassifier;
    }

    @Override
    public void setInventory(List<Item> items){ this.items = items; }

    @Override
    public String classifyItemCategory(String[] orderItem) {
        String itemName = orderItem[0];
        for(Item item : items){
            if(item.getName().equals(itemName)) return "Misc";
        }
        if(nextClassifier != null)
            return nextClassifier.classifyItemCategory(orderItem);

        return null;
    }
}
