package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking;

import de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects.BTDigit;
import de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects.BTSolution;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;

import java.util.ArrayList;

public class BTExecutor {

    public static void main(String[] args) {
        ArrayList<BTDigit> digits = new ArrayList<>();
        ArrayList<Num> nums = Num.valueOfMultipleDigitString("D24");
        int i = 0;
        for (Num num : nums) {
            digits.add(new BTDigit(num, i));
            i++;
        }

        BTSolution solution = new BTSolution(digits, 6);
        System.out.println(solution.compile());
    }
}
