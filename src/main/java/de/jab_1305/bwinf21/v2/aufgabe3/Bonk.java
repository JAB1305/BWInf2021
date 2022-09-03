package de.jab_1305.bwinf21.v2.aufgabe3;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;

import java.util.HashMap;

public class Bonk {

    Num[] num = {Num.D, Num.TWO};
    int moves = 3;
    HashMap<Tuple<Num, Num>, Integer> fCache = new HashMap<>();

    public static void main(String[] args) {
        new Bonk().start();
    }

    private void start() {
        int digit = 1;
        for (Num value : Num.values()) {
            int remaining = this.num[digit].remaining(value);
            System.out.println(remaining);
            System.out.println("this.f(digit, remaining) = " + this.f(digit, remaining));
        }
    }

    /*
        Calculate minimum required bars (changed positions) for a certain digit in order to have a certain amount of
        leftover "free" bars. (Obviously resulting in a valid hex number)
     */
    private Long f(int digit, int barsToBeLeftover) {
        if (digit + 1 == moves && barsToBeLeftover == 0) return 0L;
        if (digit + 1 == moves && barsToBeLeftover > 0) return Long.MAX_VALUE;
        long minF = Long.MAX_VALUE;
        for (Num num2 : Num.values()) {
            int newDigit = digit + 1;
            int remaining = this.num[newDigit].remaining(num2);
            int posDiff = this.num[newDigit].posDiff(num2);
            long l = f(newDigit, barsToBeLeftover - remaining) + posDiff;
            if (l < minF) minF = l;
        }
        return minF;
    }
}


