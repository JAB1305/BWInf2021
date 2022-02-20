package de.jab_1305.bwinf21.runde2.aufgabe3.model;

import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
public class Solution {
    private HashMap<Digit, Move> edits;
    private int totalB;
    private int totalN;
    private int maxN;

    public boolean add(Digit digit, Move move) {
        this.edits.put(digit, move);

        this.totalB += move.getB();
        this.totalN += move.getN();

        return this.totalN < this.maxN;
    }

    public boolean isValid() {
        return this.totalB == 0 && this.totalN <= this.maxN
                && this.totalN > 0;
    }

    @Override
    public String toString() {
        return "Solution{" +
                "edits=" + edits +
                ", totalB=" + totalB +
                ", totalN=" + totalN +
                ", maxN=" + maxN +
                '}';
    }
}
