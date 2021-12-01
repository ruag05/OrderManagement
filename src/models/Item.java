package models;

public class Item {

    private String name;
    private int quantity;
    private double price;

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Item(String itemName, int itemQuantity, double itemPrice){
        this.name = itemName;
        this.quantity = itemQuantity;
        this.price = itemPrice;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPrice() {
        return price;
    }
}
