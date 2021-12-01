import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Application started");

        Scanner scanner = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter the path of Dataset file: ");
        String dbPath = reader.readLine(); //"src/files/Dataset.csv"

        //create and initialize OrderManager to manage processing the item order
        OrderManager orderManager = new OrderManager();

        //build items inventory and pass it to OrderManager instance
        Database db = Database.getInstance(dbPath);
        orderManager.setDatabase(db);

        //define category wise quantity caps
        orderManager.setCategoryCaps(3,4,6);

        System.out.print("Enter the path of Order/Input File:");
        String orderPath = reader.readLine(); //"src/files/Sample Input File.csv"

        //process the item order by parsing the input item file
        System.out.println("Processing Order");
        orderManager.processOrder(orderPath);

        System.out.println("Order processed");
        System.out.println("Please check for 'Order Summary.csv' and 'Error Log.txt' files under 'src/files' folder");
    }
}
