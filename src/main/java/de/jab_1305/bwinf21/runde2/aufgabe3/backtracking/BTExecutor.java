package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking;

import de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects.BTDigit;
import de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects.BTSolution;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;

import java.util.ArrayList;

public class BTExecutor {

    public static void main(String[] args) {
        BTDigit digitLeft = new BTDigit(Num.D, 0);
        BTDigit digitMid = new BTDigit(Num.TWO, 1);
        BTDigit digitRight = new BTDigit(Num.FOUR, 2);

        ArrayList<BTDigit> digits = new ArrayList<>();

        digits.add(digitLeft);
        digits.add(digitMid);
        digits.add(digitRight);

        BTSolution solution = new BTSolution(digits, 3);
        System.out.println(solution);
    }
}
