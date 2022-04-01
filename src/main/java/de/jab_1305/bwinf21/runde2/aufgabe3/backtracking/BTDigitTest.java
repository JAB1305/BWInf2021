package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking;

import de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects.BTDigit;
import de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects.BTMove;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;

public class BTDigitTest {
    public static void main(String[] args) {
        BTDigit btDigit = new BTDigit(Num.THREE, 2);
        BTMove moveByHierarchy = btDigit.getMoveByHierarchy(1);
        System.out.println(moveByHierarchy.getNum2().toString());
        System.out.println(moveByHierarchy.getN());
    }
}
