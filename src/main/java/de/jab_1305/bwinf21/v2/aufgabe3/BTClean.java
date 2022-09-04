package de.jab_1305.bwinf21.v2.aufgabe3;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;

public class BTClean {

    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        try {
            BTClean btClean = new BTClean();
            btClean.addDigit();
            System.out.println(btClean.getNum());
        } catch (VirtualMachineError e) {
            System.out.println((System.currentTimeMillis() - startTime) / 1000.0);
        }
    }

    int maxMoves = 37;
    Num[] originalNum = Num.parse("632B29B38F11849015A3BCAEE2CDA0BD496919F8");
    int numLength = originalNum.length;
    Num[] nums = genFArray(numLength);

    public void addDigit() {
        if (valid(nums)) {
            return;
        }
        if (nums[nums.length - 1] == Num.ZERO && !valid(nums)) {
            for (int length = numLength - 1; length > 0; length--) {
                if (nums[length] == Num.ZERO && nums[length - 1] == Num.ZERO) { // F00 -> F0F
                    nums[length - 1] = Num.F;
                }
                if (nums[length] == Num.ZERO) { // F0F -> EFF
                    nums[length] = Num.F;
                    nums[length - 1] = nums[length - 1].getLower();
                }
            }
            addDigit();
            return;
        }
        nums[nums.length - 1] = nums[nums.length - 1].getLower();
        addDigit();
    }

    private boolean valid(Num[] potentialSolution) {
        int totalMoves = 0; // Benötigte Umlegungen
        int barsAvailable = 0; // Verfügbare Stäbchen

        for (int i = 0; i < numLength; i++) {
            // Einzelne Positionen "vergleichen" und insgesamt benötigte Züge zusammenrechnen
            totalMoves += originalNum[i].posDiff(potentialSolution[i]);
            barsAvailable += originalNum[i].remaining(potentialSolution[i]);
        }
        // Wenn die maxmalen Umlegungen nicht überschritten werden
        // und keine Stäbchen zu viel / zu wenig vorhanden sind, ist die Lösung valide
        return totalMoves <= 2 * maxMoves && barsAvailable == 0;
    }

    public String getNum() {
        StringBuilder num = new StringBuilder();
        for (Num num1 : this.nums) {
            num.append(num1.getHexSymbol());
        }
        return num.toString();
    }

    private Num[] genFArray(Integer length) {
        Num[] result = new Num[length];
        for (int integer = length - 1; integer >= 0; integer--) {
            result[integer] = Num.F;
        }
        return result;
    }
}
