package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;

import java.util.ArrayList;

public class BTDigit {

    int position;
    Num num;
    ArrayList<BTMove> possibleMoves = new ArrayList<>();

    public BTDigit(Num n, int position) {
        this.position = position;
        // TODO Generate all possible moves, based on the number given as the constructor parameter
        for (Num n2 : n.getAllBiggerOnes()) {
            this.possibleMoves.add(new BTMove(n, n2));
        }
        this.num = n;
    }

    BTMove getMoveByHierarchy(int rank) {
        return this.possibleMoves.get(rank - 1);
    }

    int getMaxPriority() {
        return -2;
    }
}
