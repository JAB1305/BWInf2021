package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;

import java.util.ArrayList;

public class BTDigit {

    int position;
    Num num;
    ArrayList<BTMove> possibleMoves = new ArrayList<>();

    public BTDigit(Num n, int position) {
        this.position = position;
        for (Num n2 : n.getAllBiggerOnes()) {
            this.possibleMoves.add(new BTMove(n, n2, -1));
        }
        int priority = this.possibleMoves.size() - 1;
        for (BTMove possibleMove : this.possibleMoves) {
            possibleMove.setPriority(priority);
            priority--;
        }
        possibleMoves.forEach(btMove -> System.out.println(btMove.toString()));
        this.num = n;
    }

    public BTMove getMoveByHierarchy(int rank) {
        // possibleMove List is sorted, starting with the lowest priority move; ending with the best
        try {
            return this.possibleMoves.get(this.possibleMoves.size() - (rank + 1));
        } catch (IndexOutOfBoundsException ignored) {
            throw new IndexOutOfBoundsException("MovePriority out of bounds (" + rank + "/" + this.getMaxPriority() + ")");
        }
    }

    int getMaxPriority() {
        return this.possibleMoves.size();
    }
}
