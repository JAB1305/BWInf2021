package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.Digit;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.Move;

import java.util.ArrayList;

public class BackTrackAnsatz {

    private ArrayList<Digit> digits = new ArrayList<>();

    public boolean backTrack(int N /*CURRENT SOLUTION AS FIELD*/) {

        Digit digit = digits.get(0); // Initial: Erster Digit
        int n = N; // Initial: Alle verfügbare Züge
        int b = 0;

        while (existOptions(digit, n)) {
            Move move = null; // Best possible VALID move, might be null

            // Add move to solution, update n and b
            if (b == 0 && n == 0) { // If solution is complete ! Best solution might not use EVERY N
                return true; // SOLUTION FOUND
            }
            if (backTrack(N - n /*CURRENT SOLUTION, MOVE TO NEXT DIGIT*/)) {
                return true;
            }
            // Remove move from solution, proceed with next one
            // Go to next digit
        }
        return false;
    }

    private boolean existOptions(Digit digit, int n) {
        // TODO
        return true;
    }

    private Move bestMove()
}
