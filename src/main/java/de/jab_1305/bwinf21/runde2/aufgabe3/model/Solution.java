package de.jab_1305.bwinf21.runde2.aufgabe3.model;

import lombok.AllArgsConstructor;

import java.util.HashMap;

@AllArgsConstructor
public class Solution {
    private HashMap<Digit, Move> edits;

    public void add(Digit digit, Move move) {
        this.edits.put(digit, move);
    }

    public void remove(Digit digit, Move move) {
        this.edits.put(digit, move);
    }

    @Override
    public String toString() {
        return "Solution{" +
                "edits=" + edits +
                '}';
    }
}
