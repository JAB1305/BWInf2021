package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BTMove {
    private final int n;
    private final int b;

    private final BTDigit digit;
    private final int priority;

    public BTMove(Num n1, Num n2) {
        n = 0;
        b = 0;

        digit = null;
        priority = 0;
    }
}
