package de.jab_1305.bwinf21.v2.aufgabe3;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.Move;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.formatting.SevenSegmetFormatter;

import java.util.HashMap;
import java.util.Objects;

public class PreGenStuff {

    // 509C431B55
    //private Num[] num = {Num.D, Num.TWO, Num.FOUR};
    private Num[] num = Num.parse("509C431B55");
    HashMap<Integer, Long> fCache = new HashMap<>();
    private int n = 8;

    public static void main(String[] args) {
        new PreGenStuff().start();
    }

    public void start() {
        long x = 0;
        int y = 0;

        for (int i = 0; i < num.length; i++) {
            System.out.println(i);
            System.out.println("y = " + y);
            for (Num num1 : num[i].getAllBiggerOnes()) {
                long newX = x + num[i].posDiff(num1);
                int newY = y + num[i].remaining(num1);
                long integer = this.necessaryMovesToNextValidNum(i, newY);
                if (newX + integer <= 2L * n) {
                    System.out.println("Found solution for digit " + i + ": " + this.num[i].toString() + " -> " + num1.toString());
                    x = newX;
                    y = newY;
                    break;
                }
            }
        }
    }

    // Bei digit = 1; barsAvailable = 1 -> result = 0. Mit welchem 2. Move???
    private long necessaryMovesToNextValidNum(int digit, int barsAvailable) {
        Integer key = Objects.hash(digit, barsAvailable);
        Long cacheValue = this.fCache.get(key);
        if (cacheValue != null) return cacheValue;
        if (digit == this.num.length && barsAvailable != 0)
            return Integer.MAX_VALUE; // Length-wise complete solution, but invalid
        if (digit == this.num.length)
            return 0;                 // Length-wise complete AND valid solution

        Num currentNum = this.num[digit];
        long result = Integer.MAX_VALUE;
        for (Num num : Num.values()) {
            int barsAvailable1 = barsAvailable + currentNum.remaining(num);
            long i = necessaryMovesToNextValidNum(digit + 1, barsAvailable1) + currentNum.posDiff(num);
            if (result == 0) return result;
            if (i < result) result = i;
        }
        this.fCache.put(Objects.hash(digit, barsAvailable), result);
        return result;
    }
}