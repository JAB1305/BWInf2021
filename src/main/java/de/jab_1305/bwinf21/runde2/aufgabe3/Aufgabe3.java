package de.jab_1305.bwinf21.runde2.aufgabe3;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.Edit;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.Move;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;

import java.util.*;

public class Aufgabe3 {
    public static void main(String[] args) {

        Edit edit1 = new Edit(new ArrayList<>());
        Edit edit2 = edit1.clone();
        edit2.add(new Move(Num.ONE, Num.TWO));
        System.out.println(edit1.toString());




        String number = "D24";
        int nMax = 3;

        ArrayList<Num> numList = Num.valueOfMultipleDigitString(number);
        // This List is already sorted by digit, starting with the first one

        int count = 0;

        Map<Num, ArrayList<Move>> possibleMovesByN1 = new HashMap<>();
        for (Num num1 : Num.values()) {
            ArrayList<Move> possibilities = new ArrayList<>();
            for (Num num2 : num1.getAllBiggerOnes()) {
                Move move = new Move(num1, num2);
                if (move.getN() <= nMax) {
                    possibilities.add(move);
                }
            }
            possibilities.sort((o1, o2) -> (o1.getAbsoluteDiff() > o2.getAbsoluteDiff()) ? 1 : 0);
            possibleMovesByN1.put(num1, possibilities);
        }

        // PossibleMovesByN1 now contains all Moves that can be executed on the digits
        // Starting with the first digit, we can now start to calculate our optimal result

        List<Edit> paths = generateAllSubPathes(new Edit(numList), numList.get(0));

        System.out.println(paths);
    }

    public static List<Edit> generateAllSubPathes(Edit base, Num num) {
        System.out.println("Searching childs for node " + num.getHexSymbol());
        List<Edit> paths = new ArrayList<>();
        for (Move move : num.getAllPossibleMoves()) {
            Edit newEdit = base.clone();
            newEdit.add(move);
            paths.add(newEdit);
            paths.addAll(generateAllSubPathes(newEdit, move.getNum2()));
        }
        return paths;
    }
}
