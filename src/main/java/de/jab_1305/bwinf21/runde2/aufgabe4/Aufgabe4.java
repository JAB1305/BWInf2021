package de.jab_1305.bwinf21.runde2.aufgabe4;

import de.jab_1305.bwinf21.runde2.aufgabe4.model.Card;
import de.jab_1305.bwinf21.runde2.aufgabe4.model.CardStack;

import java.util.ArrayList;
import java.util.Arrays;

public class Aufgabe4 {
    public static void main(String[] args) {

        Card card = new Card("10101001", 8);
        Card card2 = new Card("01100100", 8);
        Card card3 = new Card("01010101", 8);
        Card card4 = new Card("11110000", 8);

        ArrayList<Card> cards = new ArrayList<>();
        cards.add(card);
        cards.add(card2);
        cards.add(card3);
        cards.add(card4);

        CardStack cardStack = new CardStack(cards, 8);
        System.out.println(cardStack.toString());
        Card xor = cardStack.genXOR();
        System.out.println(xor.toString());

        cards.add(xor);

        System.out.println("\n-\n");

        CardStack lowScaleTest = new CardStack(cards, 8);

        lowScaleTest.findXOR();

    }
}
