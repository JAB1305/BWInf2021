package de.jab_1305.bwinf21.runde2.aufgabe3.model;

import com.google.common.collect.Comparators;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
@Getter
public enum Num {

    F(15, "F", new boolean[]{true, false, false, false, true, true, true}),
    E(14, "E", new boolean[]{true, false, false, true, true, true, true}),
    D(13, "D", new boolean[]{false, true, true, true, true, false, true}),
    C(12, "C", new boolean[]{true, false, false, true, true, true, false}),
    B(11, "B", new boolean[]{false, false, true, true, true, true, true}),
    A(10, "A", new boolean[]{true, true, true, false, true, true, true}),
    NINE(9, "9", new boolean[]{true, true, true, true, false, true, true}),
    EIGHT(8, "8", new boolean[]{true, true, true, true, true, true, true}),
    SEVEN(7, "7", new boolean[]{true, true, true, false, false, false, false}),
    SIX(6, "6", new boolean[]{true, false, true, true, true, true, true}),
    FIVE(5, "5", new boolean[]{true, false, true, true, false, true, true}),
    FOUR(4, "4", new boolean[]{false, true, true, false, false, true, true}),
    THREE(3, "3", new boolean[]{true, true, true, true, false, false, true}),
    TWO(2, "2", new boolean[]{true, true, false, true, true, false, true}),
    ONE(1, "1", new boolean[]{false, true, true, false, false, false, false}),
    ZERO(0, "0", new boolean[]{true, true, true, true, true, true, false});

    private final int absolute;
    private final String hexSymbol;
    private final boolean[] bars;

    public int countBars() {
        int count = 0;
        for (boolean bar : bars) {
            if (bar) count++;
        }
        return count;
    }

    public static List<Num> getValuesDesc() {
        return Arrays.stream(Num.values()).sorted((o1, o2) -> o2.getAbsolute() - o1.getAbsolute()).collect(Collectors.toList());
    }

    public static Num[] parse(String numAsString) {
        Num[] value = new Num[numAsString.length()];
        String[] split = numAsString.split("");
        for (int i = 0; i < split.length; i++) {
            value[i] = valueOfString(split[i]);
        }
        return value;
    }

    public ArrayList<Num> getAllBiggerOnes() {
        ArrayList<Num> biggerNums = new ArrayList<>();
        for (Num potentialPossibility : Num.getValuesDesc()) {
            if (potentialPossibility.getAbsolute() >= this.getAbsolute()) {
                biggerNums.add(potentialPossibility);
            }
        }
        return biggerNums;
    }


    public static ArrayList<Num> valueOfMultipleDigitString(String s) {
        ArrayList<Num> value = new ArrayList<>();
        for (String sSeg : s.split("")) {
            value.add(valueOfString(sSeg));
        }
        return value;
    }


    public static Num valueOfString(String s) {
        for (Num potentialNum : Num.values()) {
            if (potentialNum.getHexSymbol().equalsIgnoreCase(s)) return potentialNum;
        }
        return null;
    }

    /**
     * Bars remaining after the current Num was changed to Num2. Might be negative if additional bars are needed
     * to change num1 to num 2
     *
     * @param num2 The number it should be formed into
     * @return A positive or negative amount of Bars remaining [Positive] / required [Negative]
     */
    public int remaining(Num num2) {
        return this.countBars() - num2.countBars();
    }

    /**
     * Positions of bars that need to be changed to form Num2 out of current Num
     * @param num2 The number it should be formed into
     * @return The amount of bars activated / deactivated in this process of changing Num-Value (Always positive)
     */
    public int posDiff(Num num2) {
        int diffCount = 0;
        for (int i = 0; i < 7; i++) {
            if (this.getBars()[i] != num2.getBars()[i]) {
                diffCount++;
            }
        }
        return diffCount;
    }


    public int movesNeces(Num num2) {
        int requiredMoves = 0;
        boolean[] num1Bars = this.getBars();

        for (int i = 0; i < num1Bars.length; i++) {
            if (num1Bars[i] != num2.getBars()[i]) requiredMoves++;
        }
        if (requiredMoves < Integer.MAX_VALUE - 1) return requiredMoves;

        int barCount1 = this.countBars();
        int barCount2 = num2.countBars();
        int B = barCount1 - barCount2;
        int N = requiredMoves;
        if (B > 0) {
            return N;
        }
        if (B == 0) {
            return requiredMoves;
        }
        return 0;
    }

    public Num getLower() {
        for (Num value : values()) {
            if (this.absolute - 1 == value.absolute) return value;
        }
        return this;
    }

    @Override
    public String toString() {
        return "{" + hexSymbol + "}";
    }
}
