package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class FeedbackGen {

    private static final String RESET = "\u001B[0m";
    public static final String BLACK = "\u001B[30m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";
    public static final String BLUE = "\u001B[34m";
    public static final String PURPLE = "\u001B[35m";
    public static final String CYAN = "\u001B[36m";
    public static final String WHITE = "\u001B[37m";

    private Solution solution;
    private Solution nearestSolution;

    public void printSummary(String baseColor) {

    }

    public void printDetails(String baseColor) {

        int totalN = 0;
        int totalB = 0;

        List<BTMove> moves = this.solution.getMoves();
        List<BTDigit> digits = this.solution.getDigits();
        if (this.nearestSolution != null) {
            moves = this.nearestSolution.getMoves();
            digits = this.nearestSolution.getDigits();
        }


        for (BTMove move : moves) {
            totalN += move.getN();
            totalB += move.getB();
        }

        System.out.println("DebugInformation:" +
                "\n    Moves done:" + moves.size() +
                "\n    Total N:" + totalN +
                "\n    Total B:" + totalB);

        if (moves.size() == 0) {
            System.out.println("No solution was found. The number might already consist of only Fs");
        }

        String message = baseColor;

        StringBuilder num = new StringBuilder();
        for (int i = 0; i < digits.size(); i++) {
            if (i < moves.size()) {
                num.append(moves.get(i).getNum2().getHexSymbol());
                continue;
            }
            num.append(digits.get(i).num.getHexSymbol());
        }
        message += num + "\n" + YELLOW;

        StringBuilder moveInstructions = new StringBuilder("Züge: \n");
        for (BTMove move : moves) {
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

        System.out.println("\n");
        System.out.println("totalN = " + totalN);
        System.out.println("totalB = " + totalB);

        System.out.println(message);
    }

}
