package de.jab_1305.bwinf21.runde2.aufgabe3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;

@AllArgsConstructor
@Getter
public class Digit {
    private final Num num;
    private final int pos;


    public ArrayList<Move> getAllPossibleMoves(int remainingN) {
        ArrayList<Move> moves = new ArrayList<>();
        for (Num allBiggerOne : num.getAllBiggerOnes()) {
            Move e = new Move(this.num, allBiggerOne, this);
            if (e.getN() <= remainingN) {
                moves.add(e);
            }
        }
        return moves;
    }

    @Override
    public String toString() {
        return "Digit{" +
                "num=" + num +
                ", pos=" + pos +
                '}';
    }
}
