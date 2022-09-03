package de.jab_1305.bwinf21.runde2.aufgabe3;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.formatting.SevenSegmetFormatter;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.*;


import java.util.*;

public class Aufgabe3 {
    public static void main(String[] args) {
        String number = "EF50AA77ECAD25F5E11A307B713EAAEC55215E7E640FD263FA529BBB48DC8FAFE14D5B02EBF792B5CCBBE9FA1330B867E330A6412870DD2BA6ED0DBCAE553115C9A31FF350C5DF993824886DB5111A83E773F23AD7FA81A845C11E22C4C45005D192ADE68AA9AA57406EB0E7C9CA13AD03888F6ABEDF1475FE9832C66BFDC28964B7022BDD969E5533EA4F2E4EABA75B5DC11972824896786BD1E4A7A7748FDF1452A5079E0F9E6005F040594185EA03B5A869B109A283797AB31394941BFE4D38392AD12186FF6D233585D8C820F197FBA9F6F063A0877A912CCBDCB14BEECBAEC0ED061CFF60BD517B6879B72B9EFE977A9D3259632C718FBF45156A16576AA7F9A4FAD40AD8BC87EC569F9C1364A63B1623A5AD559AAF6252052782BF9A46104E443A3932D25AAE8F8C59F10875FAD3CBD885CE68665F2C826B1E1735EE2FDF0A1965149DF353EE0BE81F3EC133922EF43EBC09EF755FBD740C8E4D024B033F0E8F3449C94102902E143433262CDA1925A2B7FD01BEF26CD51A1FC22EDD49623EE9DEB14C138A7A6C47B677F033BDEB849738C3AE5935A2F54B99237912F2958FDFB82217C175448AA8230FDCB3B3869824A826635B538D47D847D8479A88F350E24B31787DFD60DE5E260B265829E036BE340FFC0D8C05555E75092226E7D54DEB42E1BB2CA9661A882FB718E7AA53F1E606";
        int nMax = 1369;

        ArrayList<Num> numList = Num.valueOfMultipleDigitString(number);
        // This List is already sorted by digit, starting with the first one we convert them
        // Digit just works as a wrapperclass here, so we can have the same Num at multiple positions
        ArrayList<Digit> digits = new ArrayList<>();
        int pos = numList.size() - 1;
        for (Num num : numList) {
            digits.add(new Digit(num, pos));
            pos--;
        }

        Move m = new Move(Num.D, Num.E, new Digit(Num.D, 2));/*
        System.out.println(m);

        System.out.println(digits);*/

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
