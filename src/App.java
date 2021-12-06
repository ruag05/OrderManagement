import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

// src/files/Dataset - Sheet1.csv
// src/files/Cards - Sheet1.csv
// src/files/Input1 - Sheet1.csv
// src/files/Input2 - Sheet1.csv
// src/files/Input3 - Sheet1.csv
public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Application started");

        Scanner scanner = new Scanner(System.in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter the path of Dataset file: ");
        String dbPath = reader.readLine();

        //create and initialize OrderManager to manage processing the item order
        OrderManager orderManager = new OrderManager();

        //build items inventory and pass it to OrderManager instance
        Database db = Database.getInstance(dbPath);
        orderManager.setDatabase(db);

        System.out.print("Enter the path of Cards file: ");
        String cardsPath = reader.readLine();

        CardHolder cards = CardHolder.getInstance(cardsPath);
        orderManager.setCards(cards);

        //define category wise quantity caps
        orderManager.setCategoryCaps(5,3,6);

        int userInput = 1;
        String orderPath = null;
        int orderNumber = 1;

        do{

            System.out.print("Enter the path of Order/Input File: ");
            orderPath = reader.readLine();

            //process the item order by parsing the input item file
            System.out.println("Processing Order: " + orderNumber);
            orderManager.processOrder(orderPath);
            System.out.println("Order processed successfully");
            orderNumber++;

            System.out.println("Would you like to enter more input files");
            System.out.println("1. To enter another input file.\n2. To exit");
            userInput = scanner.nextInt();
        } while(userInput == 1);

        System.out.println("Order processed");
        System.out.println("Please check for 'Order Summary.csv' and 'Error Log.txt' files" +
                "under application's 'src/files/output' folder");
    }
}
