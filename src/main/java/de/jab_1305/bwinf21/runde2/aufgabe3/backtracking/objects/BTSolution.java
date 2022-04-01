package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects;

import java.util.*;

public class BTSolution {
    private final ArrayList<BTDigit> digits;
    private final ArrayList<BTMove> moves;

    private final int maxN;
    int totalN;
    int totalB;

    int nextDigitIndex;
    BTDigit nextDigitToAddFrom;
    Integer specialPriority = null;

    boolean solutionFound = false;

    public BTSolution(ArrayList<BTDigit> digits, int maxN) {
        this.digits = digits;
        this.moves = new ArrayList<>();
        this.maxN = maxN;
        this.nextDigitToAddFrom = digits.get(0);
        this.nextDigitIndex = 0;
        // Start recursion
        this.addNewMove();
    }

    public void addNewMove() throws RuntimeException {
        BTMove nextMove = null;

        if (nextDigitToAddFrom == null || solutionFound) {
            throw new RuntimeException("Smoll PP");
        }
        nextMove = specialPriority == null ?
                nextDigitToAddFrom.getMoveByHierarchy(1) :
                nextDigitToAddFrom.getMoveByHierarchy(specialPriority);
        this.specialPriority = null;
        this.nextDigitIndex++;

        this.moves.add(nextMove);
        this.totalB += nextMove.getB();
        this.totalN += nextMove.getN();

        System.out.println("Digit: " + nextDigitToAddFrom.num.toString());

        if (totalN >= maxN) {
            this.backTrack();
        }

        if (this.nextDigitIndex < this.digits.size()) {
            this.nextDigitToAddFrom = this.digits.get(this.nextDigitIndex);
            addNewMove();
        }
    }

    public void backTrack() {
        // Change last move to a higher priority
        // If there is no move with the next priority lvl, update the move of the previous digit

        // Get the last added move

        BTMove oldMove = moves.get(moves.size() - 1);
        this.totalN -= oldMove.getN();

        BTDigit digit = digits.get(moves.size() - 1);

        if (oldMove.getPriority() < digit.getMaxPriority() && (digit.getMaxPriority() - (oldMove.getPriority() + 1)) > 0) {
            // Change the oldMove to the next move of lower priority, still regarding the same digit
            this.specialPriority = oldMove.getPriority() + 1;
            this.nextDigitIndex--;
            System.out.println("specialPriority = " + specialPriority);
        } else if (oldMove.getPriority() + 1 >= digit.getMaxPriority()) {
            // Sets the next Digit to the previous one
            this.nextDigitIndex --;
            this.nextDigitToAddFrom = this.digits.get(this.nextDigitIndex);

            System.out.println(moves.size());

            // Get the second last added move
            BTMove secondLastMove = moves.get(moves.size() - 2);
            this.totalN -= secondLastMove.getN();
            // Sets the specialPriority to a higher one, as the previously used did end up in a stuck situation
            this.specialPriority = secondLastMove.getPriority() + 1;
            this.moves.remove(secondLastMove);

            System.out.println("Switched digit  from index " + (this.nextDigitIndex + 1) + " to " + this.nextDigitIndex);
        } else {
            throw new RuntimeException("New move could not be determined");
        }
        this.moves.remove(oldMove);
        this.addNewMove();
    }
}
