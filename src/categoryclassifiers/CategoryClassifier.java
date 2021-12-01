package categoryclassifiers;

import java.util.List;
import models.Item;

public interface CategoryClassifier {
    public void setNext(CategoryClassifier nextClassifier);
    public void setInventory(List<Item> items);
    public String classifyItemCategory(String[] item);
}
