package de.jab_1305.bwinf21.v2.aufgabe3;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.Move;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.formatting.SevenSegmetFormatter;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class PreGenStuff {

    // 509C431B55
    //private Num[] num = {Num.D, Num.TWO, Num.FOUR};
    private Num[] num = Num.parse("0E9F1DB46B1E2C081B059EAF198FD491F477CE1CD37EBFB65F8D765055757C6F4796BB8B3DF7FCAC606DD0627D6B48C17C09");
    HashMap<Tuple<Integer, Integer>, Long> fCache = new HashMap<>();
    private int n = 121;

    public static void main(String[] args) {
        long startTime = System.nanoTime();
        new PreGenStuff().start();
        long endTime = System.nanoTime();
        long duration = (endTime - startTime);
        System.out.println("Duration:" + duration / 1_000_000_000.0);
    }

    public void start() {
        long m = 0; // benötigte Züge
        int b = 0; // verfügbare Stäbchen
        Num[] result = new Num[num.length];
        for (int i = 0; i < num.length; i++) {
            Num current = this.num[i];
            for (Num potentialNewNum : current.getAllBiggerOnes()) {
                long newM = m + current.posDiff(potentialNewNum);
                int newB = b + current.remaining(potentialNewNum);
                long integer = this.necessaryMovesToNextValidNum(i, newB);
                if (newM + integer <= 2L * n) {
                    result[i] = potentialNewNum;
                    m = newM;
                    b = newB;
                    break;
                }
            }
        }
    }

    /**
     * @param digit Stelle an der eine valide Zahl gebildet werden soll
     * @param barsAvailable Momentane "Anzahl" an verfügbaren Stäbchen, kann negativ sein, wenn für vorherige Umlegungen noch Stäbchen benötigt werden
     * @return Die mindestens nötigen Umlegungen um an einer bestimmten Stelle mit einer bestimmten Anzahl verfügbarer Stäbchen, eine valide Num zu bilden
     */
    private long necessaryMovesToNextValidNum(int digit, int barsAvailable) {
        Tuple<Integer, Integer> key = new Tuple<>(digit, barsAvailable);
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
        this.fCache.put(key, result);
        return result;
    }
}