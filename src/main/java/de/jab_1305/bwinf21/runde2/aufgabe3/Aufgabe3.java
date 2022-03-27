package de.jab_1305.bwinf21.runde2.aufgabe3;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.formatting.SevenSegmetFormatter;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.*;


import java.util.*;

public class Aufgabe3 {
    public static void main(String[] args) {
        String number = "D24";
        int nMax = 3;

        ArrayList<Num> numList = Num.valueOfMultipleDigitString(number);
        // This List is already sorted by digit, starting with the first one we convert them
        // Digit just works as a wrapperclass here, so we can have the same Num at multiple positions
        ArrayList<Digit> digits = new ArrayList<>();
        int pos = numList.size() - 1;
        for (Num num : numList) {
            digits.add(new Digit(num, pos));
            pos--;
        }

        Move m = new Move(Num.D, Num.E, new Digit(Num.D, 2));
        System.out.println(m);

        System.out.println(digits);

        // Starting with the first digit, we can now start to calculate our optimal result

        // TODO: Wofür Edits? -> Eigentlich nur Moves mit extraSchritten

        System.out.println(findSolution(digits, nMax));

        SevenSegmetFormatter.printNum(Num.D);
        SevenSegmetFormatter.printNum(Num.E);
    }

    public static Solution findSolution(ArrayList<Digit> digits, int maxN) {

        int remainingN = maxN;
        int currentB = 0;

        /*Solution optimizedSolution = new Solution(new HashMap<>(), 0, 0, maxN);

        List<Move> allPossibleMoves = new ArrayList<>();
        for (Digit digit : digits) {
            allPossibleMoves.addAll(digit.getAllPossibleMoves(maxN));
        }

        ArrayList<Edit> allEdits = generateAllEdits(new Edit(), allPossibleMoves, 0, maxN);
        ArrayList<Edit> allValidEdits = new ArrayList<>(allEdits.stream().filter(edit -> edit.isValid(maxN)).toList());
        allValidEdits.sort(Comparator.comparingDouble(Edit::getTotalDiff));

        System.out.println(allValidEdits);

        return optimizedSolution;*/
        return null;
    }

    public static Move findBestEdit(ArrayList<Move> options) {
        Move optimalEdit = null;
        for (Move option : options) {
            if ((optimalEdit == null || option.getAbsoluteDiff() > optimalEdit.getAbsoluteDiff())) {
                optimalEdit = option;
            }
        }
        return optimalEdit;
    }

    public static ArrayList<Edit> generateAllEdits(Edit base, List<Move> moves, int currentB, int remainingN) {
        ArrayList<Edit> paths = new ArrayList<>();
        for (Move move : moves) {
            if (move.getN() > remainingN) continue;
            if (base.getMovesDone().contains(move)) continue;
            Edit newEdit = base.clone();
            int newN = remainingN - move.getN();
            newEdit.add(move);
            paths.add(newEdit);
            paths.addAll(generateAllEdits(newEdit, moves, currentB + move.getB(), newN));
        }
        return paths;
    }
}
