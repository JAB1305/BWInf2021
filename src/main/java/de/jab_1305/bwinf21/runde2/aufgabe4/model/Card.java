package de.jab_1305.bwinf21.runde2.aufgabe4.model;

import lombok.Getter;

import java.util.Arrays;

public class Card {
    private String binary;
    private int oneCount;
    @Getter
    private boolean[] boolArray;

    public Card(String binary, int length) {
        this.oneCount = 0;
        this.binary = binary;
        int pos = 0;
        boolArray = new boolean[length];
        for (char c : this.binary.toCharArray()) {
            try {
                boolArray[pos] = false;
            } catch (ArrayIndexOutOfBoundsException ignored) {
                throw new ArrayIndexOutOfBoundsException("Invalid card length");
            }
            if (c == '1') {
                this.oneCount++;
                boolArray[pos] = true;
            }
            pos++;
        }
    }

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        for (boolean b : this.boolArray) {
            message.append(b ? "1" : "0");
            message.append(" ");
        }
        return message.toString();
    }

    public boolean getBoolAt(int index) {
        return this.boolArray[index];
    }
}
