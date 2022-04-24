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

    String prev = "";
    int count = 0;

    BTBasicSolution nearestValidSolution = null;

    public BTSolution(ArrayList<BTDigit> digits, int maxN) {
        this.digits = digits;
        this.moves = new ArrayList<>();
        this.maxN = maxN;
        this.nextDigitToAddFrom = digits.get(0);
        this.nextDigitIndex = 0;
        // Start recursion
        this.addNewMove();
        FeedbackGen feedbackGen = new FeedbackGen(this, this.nearestValidSolution);
        feedbackGen.printDetails(FeedbackGen.YELLOW);
    }

    public void addNewMove() throws RuntimeException {

        System.out.println(currentNum().equals(prev) + " " + count);
        prev = currentNum();
        count++;

        BTMove nextMove = null;
        this.nextDigitToAddFrom = this.digits.get(this.nextDigitIndex);

        if (solutionFound) return;

        if (nextDigitToAddFrom == null) {
            throw new RuntimeException("Next digit null");
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
            // FIXME: Assumption: Digit is shifted while specialPriority adjusted on a certain digit remains the same
            // Resulting in stackOverflow as the digitIndex isn't shifting??? DEBUG
            // TODO: Validate existance, ei von Lars

            // FIXME: maxPriority illegal on double-shift? DEBUG

            if (nextDigitToAddFrom.getMaxPriority() > specialPriority)
                nextMove = nextDigitToAddFrom.getMoveByHierarchy(specialPriority);
            else {
                throw new RuntimeException("Invalid specialPriority");
                /*nextMove = nextDigitToAddFrom.getMoveByHierarchy(0);
                this.nextDigitIndex++;*/
            }
        }
        this.specialPriority = null;

        // Add new move, recalculate total B and N
        if (nextMove != null)
        this.moves.add(nextMove);
        recalculate();


        if (this.totalB == 0) {
            this.nearestValidSolution = new BTBasicSolution(new ArrayList<>(this.moves), new ArrayList<>(this.digits));
        }

        // Check if either too much N or maxN but invalid
        // Bot cases -> BackTrack (reverse previous move)
        if (totalN > maxN || (this.totalN == maxN && this.totalB != 0) || (this.moves.size() == this.digits.size() && this.totalB != 0)) {
            if (this.nearestValidSolution != null) {
                //System.out.println("Nearest solution was used");
            }
            FeedbackGen feedbackGen = new FeedbackGen(this, this.nearestValidSolution);
            //feedbackGen.printSummary(FeedbackGen.PURPLE);
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
                if (isEditable) {
                    // Sets the next Digit to the previous one
                    this.nextDigitIndex = indexToCheck;
                    this.nextDigitToAddFrom = this.digits.get(this.nextDigitIndex);

                    // Get the second last added move
                    BTMove moveToEdit = moves.get(indexToCheck);
                    // Sets the specialPriority to a higher one, as the previously used did end up in a stuck situation
                    this.specialPriority = moveToEdit.getPriority() + 1;
                    this.moves.remove(moveToEdit);
                    recalculate();

                    //System.out.println("Switched digit from index " + (this.nextDigitIndex + 1) + " to " + this.nextDigitIndex);
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

    private void recalculate() {
        this.totalN = 0;
        this.totalB = 0;

        for (BTMove move : this.moves) {
            this.totalN += move.getN();
            this.totalB += move.getB();
        }
    }

    private String currentNum() {
        StringBuilder num = new StringBuilder();
        for (int i = 0; i < digits.size(); i++) {
            if (i < moves.size()) {
                num.append(moves.get(i).getNum2().getHexSymbol());
                continue;
            }
            num.append(digits.get(i).num.getHexSymbol());
        }
        return num.toString();
    }

    @Override
    public List<BTDigit> getDigits() {
        return this.digits;
    }

    @Override
    public List<BTMove> getMoves() {
        return this.moves;
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
