package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;

import java.util.*;

public class BTSolution implements Solution {

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

    BTBasicSolution nearestValidSolution = null;

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
        this.nextDigitToAddFrom = this.digits.get(this.nextDigitIndex);

        if (solutionFound) return;

        if (nextDigitToAddFrom == null) {
            throw new RuntimeException("Smoll PP");
        }

        // Load either the best or the specialPriority move
        if (specialPriority == null) {
            // Search for the next valid move, D ->
            while (nextMove == null) {
                // Checked if digit is maxed out
                if (this.nextDigitToAddFrom.num != Num.F) {
                    nextMove = this.nextDigitToAddFrom.getMoveByHierarchy(0);
                    continue;
                } else {
                    // As the digit is already maxed out, skip it and add the neutral move as a "placeholder"
                    this.moves.add(this.nextDigitToAddFrom.getNeutralMove());
                }
                this.nextDigitIndex++;

                // Check if iterated over every digit
                if (this.nextDigitIndex == this.digits.size()) {
                    System.out.println("Recursion should be canceled early");
                    this.nextDigitIndex--;
                    this.backTrack();
                    break;
                }
                this.nextDigitToAddFrom = this.digits.get(this.nextDigitIndex);
            }
        } else {
            // Atm it is assumed that the specialPriority is valid on the next digit
            if (nextDigitToAddFrom.getMaxPriority() >= specialPriority)
                nextMove = nextDigitToAddFrom.getMoveByHierarchy(specialPriority);
            else
                nextMove = nextDigitToAddFrom.getMoveByHierarchy(0);
        }
        this.specialPriority = null;

        // Add new move, recalculate total B and N
        if (nextMove != null)
        this.moves.add(nextMove);
        recalculate();


        if (this.totalB == 0) {
            this.nearestValidSolution = new BTBasicSolution(new ArrayList<>(this.moves), new ArrayList<>(this.digits), this.totalN, this.totalB);
        }

        // Check if either too much N or maxN but invalid
        // Bot cases -> BackTrack (reverse previous move)
        if (totalN > maxN || (this.totalN == maxN && this.totalB != 0) || (this.moves.size() == this.digits.size() && this.totalB != 0)) {
            if (this.nearestValidSolution != null) {
                System.out.println("Nearest solution was used");
            }
            this.simpleSolCompile();
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
        recalculate();

        // As long as moves can be added, add a new move
        if (this.nextDigitIndex < this.digits.size()) {
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

        //System.out.println("Supposed to rollback digit " + this.nextDigitIndex + " current prio: " + oldMove.getPriority());

        if (oldMove.getPriority() < digit.getMaxPriority() && (digit.getMaxPriority() - (oldMove.getPriority() + 1)) > 0) {
            // Change the oldMove to the next move of lower priority, still regarding the same digit
            this.specialPriority = oldMove.getPriority() + 1;
        } else if (oldMove.getPriority() + 1 >= digit.getMaxPriority()) {
            // Rollback to a point where the move can pe changed

            for (int indexToCheck = this.nextDigitIndex; indexToCheck >= 0; indexToCheck--) {
                boolean isSet = indexToCheck < this.moves.size();

                boolean isEditable = isSet && (this.digits.get(indexToCheck).getMaxPriority()
                        != this.moves.get(indexToCheck).getPriority() + 1);
                // FIXME Looks like isEditable throws an exception when it should be false, inspect further
                // FIXME More details: digits.indexToCheck is valid, moves.indexToCheck is outOfBounds
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
                } else if (isSet) {
                    BTMove moveToEdit = moves.get(indexToCheck);
                    this.moves.remove(moveToEdit);
                    recalculate();
                }
            }
        } else {
            throw new RuntimeException("New move could not be determined");
        }
        this.moves.remove(oldMove);
        recalculate();
        this.addNewMove();
    }

    @Override
    public void simpleSolCompile() {
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < this.digits.size(); i++) {
            if (i < this.moves.size()) {
                num.append(this.moves.get(i).getNum2().getHexSymbol());
                continue;
            }
            num.append(this.digits.get(i).num.getHexSymbol());
        }
        System.out.println(num.toString());
    }

    @Override
    public String compile() {

        System.out.println("DebugInformation:" +
                "\n    Moves done:" + this.moves.size() +
                "\n    Total N:" + this.totalN +
                "\n    Total B:" + this.totalB +
                ("\n    NearestSolution == null: " + String.valueOf(this.nearestValidSolution == null)));

        if (!solutionFound) {
            if (this.nearestValidSolution != null) System.out.println(nearestValidSolution.compile());
            System.out.println(ANSI_RED + "No solution was found. The number might already consist of only Fs");
        }

        String message = solutionFound ? ANSI_BLUE : ANSI_PURPLE;

        StringBuilder num = new StringBuilder();
        for (int i = 0; i < this.digits.size(); i++) {
            if (i < this.moves.size()) {
                num.append(this.moves.get(i).getNum2().getHexSymbol());
                continue;
            }
            num.append(this.digits.get(i).num.getHexSymbol());
        }
        message += num + "\n" + ANSI_YELLOW;

        StringBuilder moveInstructions = new StringBuilder("Züge: \n");
        for (BTMove move : this.moves) {
            moveInstructions.append("\n");
            moveInstructions.append(move.getNum1().getHexSymbol());
            moveInstructions.append(" ➔ ");
            moveInstructions.append(move.getNum2().getHexSymbol());
            moveInstructions.append(" | N ");
            moveInstructions.append(move.getN());
            moveInstructions.append(" B ");
            moveInstructions.append(move.getB());
        }

        message += moveInstructions;

        recalculate();
        System.out.println("\n");
        System.out.println("totalN = " + totalN);
        System.out.println("totalB = " + totalB);

        return message;
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

/*
EYYY


totalN = 13
totalB = 0
FFFF7D6B55
Züge:

5 ➔ F N: 1 B: 1
0 ➔ F N: 1 B: 2
9 ➔ F N: 1 B: 2
C ➔ F N: 1 B: 0
3 ➔ 7 N: 0 B: 2
1 ➔ D N: 3 B: -3
1 ➔ 6 N: 6 B: -4

1+2+2+2-3-4
 */
