package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;

import java.util.*;

public class BTSolution {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    private final ArrayList<BTDigit> digits;
    private final ArrayList<BTMove> moves;

    private final int maxN;
    int totalN;
    int totalB;

    // FIXME: Numbers starting with F cause MP out of bounds

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
        // FIXME: Search for a valid digit, the index might lead to a digit already at F
        if (specialPriority == null) {
            while (nextMove == null) {
                if (this.nextDigitToAddFrom.num != Num.F) {
                    nextMove = this.nextDigitToAddFrom.getMoveByHierarchy(0);
                    continue;
                }
                this.nextDigitToAddFrom = this.digits.get(this.nextDigitIndex);
                this.nextDigitIndex++;
            }
            nextMove = nextDigitToAddFrom.getMoveByHierarchy(0);
        } else {
            // Atm it is assumed that the specialPriority is valid on the next digit
            nextMove = nextDigitToAddFrom.getMoveByHierarchy(specialPriority);
        }
        this.specialPriority = null;

        // Add new move, recalculate total B and N
        this.moves.add(nextMove);
        recalculate();


        // Check if either too much N or maxN but invalid
        // Bot cases -> BackTrack (reverse previous move)
        if (totalN > maxN || (this.totalN == maxN && this.totalB != 0) || (this.moves.size() == this.digits.size() && this.totalB != 0)) {
            if (this.nearestValidSolution != null) {
                System.out.println("Nearest solution was used");
            }
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

        if (this.totalB == 0) {
            recalculate();
            this.nearestValidSolution = new BTBasicSolution(this.moves, this.digits, this.totalN, this.totalB);
            System.out.println("Nearest solution found");
        }

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
                boolean isEditable =
                        (this.digits.get(indexToCheck).getMaxPriority()
                        != this.moves.get(indexToCheck).getPriority() + 1);
                // FIXME Looks like isEditable throws an exception when it should be false, inspect further
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
        } else {
            throw new RuntimeException("New move could not be determined");
        }
        this.moves.remove(oldMove);
        recalculate();
        this.addNewMove();
    }

    public String compile() {


        if (!solutionFound) {
            if (this.nearestValidSolution != null) return nearestValidSolution.compile();
            return ANSI_RED + "No solution was found. FIXME: There might be a solution not using every move";
        }

        String message = ANSI_BLUE;

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
