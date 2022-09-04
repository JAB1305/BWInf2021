package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking;

import de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects.BTDigit;
import de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects.BTIterativeSolution;
import de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects.BTRecursiveSolution;
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


        long startTime = System.nanoTime();
        BTIterativeSolution solution = new BTIterativeSolution(digits, 8);
        System.out.println(solution.toString());

        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println(duration / 1_000_000_000.0);
    }
}
