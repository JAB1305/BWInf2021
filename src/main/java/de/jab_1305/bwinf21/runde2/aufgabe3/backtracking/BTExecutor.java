package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking;

import de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects.BTDigit;
import de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects.BTSolution;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;

import java.util.ArrayList;

public class BTExecutor {

    public static void main(String[] args) {
        ArrayList<BTDigit> digits = new ArrayList<>();
        ArrayList<Num> nums = Num.valueOfMultipleDigitString("632B29B38F11849015A3BCAEE2CDA0BD496919F8");

        int i = 0;
        for (Num num : nums) {
            digits.add(new BTDigit(num, i));
            i++;
        }

        BTSolution solution = new BTSolution(digits, 37);
    }
}
