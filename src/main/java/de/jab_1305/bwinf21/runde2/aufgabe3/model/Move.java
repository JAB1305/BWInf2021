package de.jab_1305.bwinf21.runde2.aufgabe3.model;

import lombok.Data;

import java.util.Objects;

@Data
public class Move {

    Num num1;
    Num num2;
    int B; // Bilanz des Zuges -> +3 - Zug "gibt drei Bars ab" oder -2 Zug "brauch 2 Bars"
    int N; // Required moves ! NOT in addition to B. Moving Bars out of the pool (positive B) is not counted here.
    int absoluteDiff; // Increase on that digit that is achieved by using this operation.

    public Move(Num num1, Num num2) {
        this.num1 = num1;
        this.num2 = num2;

        this.absoluteDiff = Math.abs(num1.getAbsolute() - num2.getAbsolute());
        int requiredMoves = 0;
        boolean[] num1Bars = this.num1.getBars();

        for (int i = 0; i < num1Bars.length; i++) {
            if (num1Bars[i] != this.num2.getBars()[i]) requiredMoves++;
        }

        int barCount1 = this.num1.countBars();
        int barCount2 = this.num2.countBars();
        this.B = barCount1 - barCount2;
        this.N = requiredMoves;
        if (this.B > 0) {
            this.N = (this.N - this.B) / 2;
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Move move = (Move) o;
        return num1 == move.num1 && num2 == move.num2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(num1, num2);
    }

    @Override
    public String toString() {
        return "Move{" +
                "num1=" + num1.getAbsolute() +
                ", num2=" + num2.getAbsolute() +
                ", B=" + B +
                ", N=" + N +
                '}';
    }
}
