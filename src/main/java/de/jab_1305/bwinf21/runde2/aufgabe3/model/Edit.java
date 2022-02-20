package de.jab_1305.bwinf21.runde2.aufgabe3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

//@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Edit implements Cloneable, Comparable {

    //TODO: Besseren Klassennamen für den Chonker

    private final Stack<Move> movesDone;
    private Double totalDiff = 0.0;

    private int totalN;
    private int totalB;

    public Edit() {
        this.movesDone = new Stack<>();
    }

    public void add(Move move) {
        this.movesDone.add(move);
        this.totalDiff += move.getAbsoluteDiff() * Math.pow(16, move.getDigit().getPos());
        this.totalN += move.getN();
        this.totalB += move.getB();
    }

    public boolean isValid(int maxN) {
        return this.totalB == 0 && this.totalN <= maxN
                && this.totalN > 0;
    }


    @Override
    public Edit clone() {
        try {
            Edit clone = (Edit) super.clone();
            Object clonedStack = clone.getMovesDone().clone();
            Stack<Move> movesDone = (Stack<Move>) clonedStack;
            return new Edit(movesDone, clone.getTotalDiff(), clone.getTotalN(), clone.getTotalB());
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "\nPath:" +
                "\n   -> movesDone=" + movesDone.toString() +
                "\n   -> total=" + totalDiff +
                " | N:" + totalN +
                " | B:" + totalB;
    }

    @Override
    public int compareTo(Object o) {
        if (!(o instanceof Edit edit)) {
            return 0;
        }
        if (edit.getTotalDiff() > this.getTotalDiff()) {
            return 0;
        }
        return 1;
    }
}
