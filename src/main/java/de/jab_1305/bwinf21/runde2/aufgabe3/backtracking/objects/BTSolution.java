package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects;

import lombok.RequiredArgsConstructor;

import java.util.*;

public class BTSolution {
    private final LinkedHashMap<BTDigit, BTMove> moves = new LinkedHashMap<>();
    private final ArrayList<BTDigit> digits;

    int maxN;
    int totalN;
    int totalB;

    int nextDigitIndex = -1;
    BTDigit nextDigitToAddFrom;
    Integer specialPriority = null;

    boolean solutionFound = false;

    public BTSolution(ArrayList<BTDigit> digits) {
        this.digits = digits;
        this.nextDigitToAddFrom = digits.get(0);
        this.nextDigitIndex = 0;
        this.addNewMove();
    }

    public void addNewMove() {
        BTMove nextMove = null;

        if (nextDigitToAddFrom == null || solutionFound) {
            return;
        }
        nextMove = specialPriority == null ?
                nextDigitToAddFrom.getMoveByHierarchy(1) :
                nextDigitToAddFrom.getMoveByHierarchy(specialPriority);
        this.specialPriority = null;
        this.nextDigitIndex++;
        this.moves.put(nextDigitToAddFrom, nextMove);
        if (this.nextDigitIndex < this.digits.size()) {
            this.nextDigitToAddFrom = this.digits.get(this.nextDigitIndex);
            addNewMove();
        }
    }

    public void backTrack() {
        // Change last move to a higher priority
        // If there is no move with the next priority lvl, update the move of the previous digit

        // Get the last added move
        BTDigit lastMove = moves.keySet().toArray(new BTDigit[0])[moves.size() - 1];
        if (lastMove == null) throw new IllegalCallerException("Can not backtrack as there are no moves added.");

        BTMove oldMove = moves.get(lastMove);
        if (oldMove.getPriority() < lastMove.getMaxPriority()) {
            // Change the oldMove to the next move of lower priority, still regarding the same digit
            this.specialPriority = oldMove.getPriority() + 1;
        } else if (oldMove.getPriority() == lastMove.getMaxPriority()) {
            // Sets the next Digit to the previous one
            this.nextDigitToAddFrom = this.digits.get(this.nextDigitIndex - 1); // TODO: Check if + or -
            // Sets the specialPriority to a higher one, as the previously used did end up in a stuck situation
            this.specialPriority = oldMove.getPriority() + 1;

            // Get the second last added move
            BTDigit secondLastMove = moves.keySet().toArray(new BTDigit[0])[moves.size() - 2];
            this.moves.remove(secondLastMove);
        } else {
            throw new RuntimeException("New move could not be determined");
        }
        this.moves.remove(lastMove);
        this.addNewMove();
    }
}
