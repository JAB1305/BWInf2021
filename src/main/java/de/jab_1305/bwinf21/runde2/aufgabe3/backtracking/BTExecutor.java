package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking;

import de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects.BTDigit;
import de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects.BTSolution;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;

import java.util.ArrayList;

public class BTExecutor {

    public static void main(String[] args) {
        ArrayList<BTDigit> digits = new ArrayList<>();
        ArrayList<Num> nums = Num.valueOfMultipleDigitString("509C431B55");

        int i = 0;
        for (Num num : nums) {
            digits.add(new BTDigit(num, i));
            i++;
        }

        // FIXME: Random StackOverflowException, DEBUG DEBUG DEBUG

        BTSolution solution = new BTSolution(digits, 3);
    }
}
