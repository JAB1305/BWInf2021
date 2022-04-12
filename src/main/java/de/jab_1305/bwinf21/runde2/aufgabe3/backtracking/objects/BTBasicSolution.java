package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BTBasicSolution implements Solution{
    private final List<BTMove> moves;
    private final List<BTDigit> digits;

    private FeedbackGen feedbackGen = new FeedbackGen(this, null);

    public void printSummary() {
        this.feedbackGen.printSummary(FeedbackGen.PURPLE);
    }

    public void printDetails() {
        this.feedbackGen.printSummary(FeedbackGen.YELLOW);
    }

    @Override
    public List<BTDigit> getDigits() {
        return this.digits;
    }

    @Override
    public List<BTMove> getMoves() {
        return this.moves;
    }
}
