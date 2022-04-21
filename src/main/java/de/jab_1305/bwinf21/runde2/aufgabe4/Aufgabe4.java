package de.jab_1305.bwinf21.runde2.aufgabe4;

import de.jab_1305.bwinf21.runde2.aufgabe4.model.Card;
import de.jab_1305.bwinf21.runde2.aufgabe4.model.CardStack;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Aufgabe4 {
    public static void main(String[] args) throws FileNotFoundException {

        Card card = new Card("10101001", 8);
        Card card2 = new Card("01100100", 8);
        Card card3 = new Card("01010101", 8);
        Card card4 = new Card("11110000", 8);
        Card card5 = new Card("1110100", 8);

        Card xor = new Card("10010101", 8);

        ArrayList<Card> cards = loadFromFile("src/main/java/de/jab_1305/bwinf21/runde2/aufgabe4/examples/stapel0.txt");
        ArrayList<Card> lessCards = new ArrayList<>(Arrays.asList(xor, card, card2, card3, card4, card5));

        CardStack lowScaleTest = new CardStack(cards, 32, 10);
        lowScaleTest.findXOR();
    }


    static ArrayList<Card> loadFromFile(String path) throws FileNotFoundException {
        Scanner s = new Scanner(new File(path));
        ArrayList<String> list = new ArrayList<>();
        while (s.hasNext()) {
            list.add(s.next());
        }
        s.close();

        int total = Integer.parseInt(list.get(0));
        int cardCount = Integer.parseInt(list.get(1));
        int cardLength = Integer.parseInt(list.get(2));

        System.out.println("cardCount = " + cardCount);
        System.out.println("cardLength = " + cardLength);
        System.out.println("total = " + total);

        ArrayList<Card> cards = new ArrayList<>();

        int index = 3;
        while (index < list.size()) {
            cards.add(new Card(list.get(index), cardLength));
            index++;
        }

        System.out.println(cards);
        return cards;
    }
}
