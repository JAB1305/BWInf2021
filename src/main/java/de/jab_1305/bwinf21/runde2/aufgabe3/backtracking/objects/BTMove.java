package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BTMove {
    private int n;
    private int b;

    private final Num num1;
    private final Num num2;

    private final BTDigit digit;
    private final int priority;

    public BTMove(Num n1, Num n2) {
        n = 0;
        b = 0;

        digit = null;
        priority = 0;

        this.num1 = n1;
        this.num2 = n2;

        int requiredMoves = 0;
        boolean[] num1Bars = this.num1.getBars();

        for (int i = 0; i < num1Bars.length; i++) {
            if (num1Bars[i] != this.num2.getBars()[i]) requiredMoves++;
        }

        int barCount1 = this.num1.countBars();
        int barCount2 = this.num2.countBars();
        this.b = barCount1 - barCount2;
        this.n = requiredMoves;
        if (this.b > 0) {
            this.n = (this.n - this.b) / 2;
        }
        if (this.b == 0) {
            this.n = requiredMoves / 2;
        }
    }
}
