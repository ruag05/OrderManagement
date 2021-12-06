import models.Item;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class CardHolder {

    private static CardHolder instance;
    public Set<Card> cards;

    public static CardHolder getInstance(String cardsFilePath) throws IOException {
        if (instance == null) {
            instance = new CardHolder(cardsFilePath);
        }
        return instance;
    }

    private CardHolder(String cardsFilePath) throws IOException {
        this.cards = storeCards(cardsFilePath);
    }

    private Set<Card> storeCards(String cardsFilePath) throws IOException {
        BufferedReader reader = null;
        try {
            reader= new BufferedReader(new FileReader(cardsFilePath));
            cards = new HashSet<>();

            //ignore csv header and table header lines
            String csvRow = reader.readLine();

            //iterate through the dataset and store as inventory
            while((csvRow = reader.readLine()) != null) {

                String[] rowData = csvRow.split(",");
                Card card = new Card(Arrays.toString(rowData));
                cards.add(card);
            }
        } catch (Exception e) {
            System.out.println("Card file could not read. Please check the cards file path");
            //e.printStackTrace();
        }
        finally {
            assert reader != null;
            reader.close();
            return cards;
        }
    }
}
