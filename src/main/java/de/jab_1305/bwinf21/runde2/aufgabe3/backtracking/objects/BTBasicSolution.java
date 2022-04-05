package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects;

import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class BTBasicSolution {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";


    private List<BTMove> moves;
    private List<BTDigit> digits;
    private Integer totalN;
    private Integer totalB;

    public String compile() {

            String message = ANSI_BLUE;

            StringBuilder num = new StringBuilder();
            for (int i = 0; i < this.digits.size(); i++) {
                if (i < this.moves.size()) {
                    num.append(this.moves.get(i).getNum2().getHexSymbol());
                    continue;
                }
                num.append(this.digits.get(i).num.getHexSymbol());
            }
            message += num + "\n" + ANSI_CYAN;

            StringBuilder moveInstructions = new StringBuilder("Züge: \n");
            for (BTMove move : this.moves) {
                moveInstructions.append("\n");
                moveInstructions.append(move.getNum1().getHexSymbol());
                moveInstructions.append(" ➔ ");
                moveInstructions.append(move.getNum2().getHexSymbol());
                moveInstructions.append(" N: ");
                moveInstructions.append(move.getN());
                moveInstructions.append(" B: ");
                moveInstructions.append(move.getB());
            }

            message += moveInstructions;

            System.out.println("\n");
            System.out.println("totalN = " + totalN);
            System.out.println("totalB = " + totalB);

            return message;
    }
}
