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

    // A valid solution was found on the path, if necessary fall back
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
        BTMove nextMove;

        if (solutionFound) return;

        if (nextDigitToAddFrom == null) {
            throw new RuntimeException("Smoll PP");
        }

        // Load either the best or the specialPriority move
        if (specialPriority == null) {
            nextMove = nextDigitToAddFrom.getMoveByHierarchy(0);
        } else {
            nextMove = nextDigitToAddFrom.getMoveByHierarchy(specialPriority);
        }
        this.specialPriority = null;

        // Add new move, recalculate total B and N
        this.moves.add(nextMove);
        this.totalB += nextMove.getB();
        this.totalN += nextMove.getN();

        // Check if either too much N or maxN but invalid
        // Bot cases -> BackTrack (reverse previous move)
        if (totalN > maxN || (this.totalN == maxN && this.totalB != 0)) {
            this.backTrack();
            return;
        }

        // Proceed with digit
        this.nextDigitIndex++;

        // Check if a solution was found, N doesn't matter as it was
        // checked to be less than maxN previously and is allowed to be less than maxN
        if (this.totalB == 0 && this.totalN == this.maxN) {
            this.solutionFound = true;
        }

        // As long as moves can be added, add a new move
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


        // As the index is not increased already, this is the CURRENT digit
        // That the "oldMove" is referring to
        BTDigit digit = digits.get(this.nextDigitIndex);

        System.out.println("Supposed to rollback digit " + this.nextDigitIndex + " current prio: " + oldMove.getPriority());

        if (oldMove.getPriority() < digit.getMaxPriority() && (digit.getMaxPriority() - (oldMove.getPriority() + 1)) > 0) {
            // Change the oldMove to the next move of lower priority, still regarding the same digit
            this.specialPriority = oldMove.getPriority() + 1;
        } else if (oldMove.getPriority() + 1 >= digit.getMaxPriority()) {
            // Rollback to a point where the move can pe changed

            for (int indexToCheck = this.nextDigitIndex; indexToCheck >= 0; indexToCheck--) {
                boolean isEditable = (this.digits.get(indexToCheck).getMaxPriority()
                        != this.moves.get(indexToCheck).getPriority() + 1);
                if (isEditable) {
                    // Sets the next Digit to the previous one
                    this.nextDigitIndex--;
                    this.nextDigitToAddFrom = this.digits.get(this.nextDigitIndex);

                    // Get the second last added move
                    BTMove moveToEdit = moves.get(indexToCheck);
                    // Sets the specialPriority to a higher one, as the previously used did end up in a stuck situation
                    this.specialPriority = moveToEdit.getPriority() + 1;
                    this.moves.remove(moveToEdit);
                    recalculate();

                    System.out.println("Switched digit from index " + (this.nextDigitIndex + 1) + " to " + this.nextDigitIndex);
                    break;
                } else {
                    BTMove moveToEdit = moves.get(indexToCheck);
                    this.moves.remove(moveToEdit);
                    recalculate();
                }
            }

            // FIXME: If F24 with D24 being the starting num is rollbacked, the
            //  code will try to add priority to the second digit, leading to MovePriorityOutOfBounds
        } else {
            throw new RuntimeException("New move could not be determined");
        }
        this.moves.remove(oldMove);
        this.addNewMove();
    }

    public String compile() {
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < this.digits.size(); i++) {
            if (i < this.moves.size()) {
                num.append(this.moves.get(i).getNum2().getHexSymbol());
                continue;
            }
            num.append(this.digits.get(i).num.getHexSymbol());
        }
        return num.toString();
    }

    private void recalculate() {
        this.totalN = 0;
        this.totalB = 0;

        for (BTMove move : this.moves) {
            this.totalN += move.getN();
            this.totalB += move.getB();
        }
    }
}
