package de.jab_1305.bwinf21.runde2.aufgabe3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum VisualizedNumber {
    N_1(1, new boolean[]{false, true, true, false, false, false, false}),
    N_2(2, new boolean[]{true, true, false, true, true, false, true}),
    N_3(3, new boolean[]{true, true, true, true, false, false, true});


    private final int decimalEquivalent;
    private final boolean[] activeBars;

    /**
     * Get the amount of "bars" you need to convert the first into the second number
     *
     * @param n1 The number that will be modified
     * @param n2 The number you want n2 to  be midified into
     * @return The amount of "bars" resulting from the conversion. If the number is positive, n2 consists of less bars than n1
     */
    public static Integer calculateBarDiff(VisualizedNumber n1, VisualizedNumber n2) {
        int count1 = n1.countActiveBars();
        int count2 = n2.countActiveBars();
        return count1 - count2;
    }

    /**
     * Get the amount of moves needed in the number itself to be converted
     *
     * @param n1 The number that will be modified
     * @param n2 The number you want n2 to  be midified into
     * @return The amount of moves needed. If bars are moved out of the number, its counted as a move. Bars moved into
     * the number are left out of the count
     */
    public static MoveResult calculateMovesNeeded(VisualizedNumber n1, VisualizedNumber n2) {
        MoveResult moveResult = new MoveResult();
        Integer barDiff = calculateBarDiff(n1, n2);
        moveResult.setB(barDiff);
        int overlap = 0;
        boolean[] activeBars1 = n1.getActiveBars();
        boolean[] activeBars2 = n2.getActiveBars();
        for (int a = 0; a < 7; a++) {
            if (activeBars1[a] && activeBars2[a]) {
                overlap++;
            }
        }
        moveResult.setN(n2.countActiveBars() - overlap - Math.abs(barDiff));
        return moveResult;
    }

    private int countActiveBars() {
        int count = 0;
        for (boolean bar : this.activeBars) {
            if (bar) count++;
        }
        return count;
    }

}
