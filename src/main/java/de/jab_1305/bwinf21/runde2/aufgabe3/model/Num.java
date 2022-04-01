package de.jab_1305.bwinf21.runde2.aufgabe3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Getter
public enum Num {

    //18.02.2022 15:41 - All valid, based on BWInf patterns
    //TODO: B and N are kinda sus

    ZERO(0, "0", new boolean[]{true, true, true, true, true, true, false}),
    ONE(1, "1", new boolean[]{false, true, true, false, false, false, false}),
    TWO(2, "2", new boolean[]{true, true, false, true, true, false, true}),
    THREE(3, "3", new boolean[]{true, true, true, true, false, false, true}),
    FOUR(4, "4", new boolean[]{false, true, true, false, false, true, true}),
    FIVE(5, "5", new boolean[]{true, false, true, true, false, true, true}),
    SIX(6, "6", new boolean[]{true, false, true, true, true, true, true}),
    SEVEN(7, "7", new boolean[]{true, true, true, false, false, false, false}),
    EIGHT(8, "8", new boolean[]{true, true, true, true, true, true, true}),
    NINE(9, "9", new boolean[]{true, true, true, true, false, true, true}),
    A(10, "A", new boolean[]{true, true, true, false, true, true, true}),
    B(11, "B", new boolean[]{false, false, true, true, true, true, true}),
    C(12, "C", new boolean[]{true, false, false, true, true, true, false}),
    D(13, "D", new boolean[]{false, true, true, true, true, false, true}),
    E(14, "E", new boolean[]{true, false, false, true, true, true, true}),
    F(15, "F", new boolean[]{true, false, false, false, true, true, true});

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

    public ArrayList<Num> getAllBiggerOnes() {
        ArrayList<Num> biggerNums = new ArrayList<>();
        for (Num potentialPossibility : Num.values()) {
            if (potentialPossibility.getAbsolute() > this.getAbsolute()) {
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

    @Override
    public String toString() {
        return "{" + hexSymbol + "}";
    }
}
