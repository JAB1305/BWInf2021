package de.jab_1305.bwinf21.runde2.aufgabe3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
public class Edit implements Cloneable {

    //TODO: Besseren Klassennamen für den Chonker

    private List<Num> startNum;
    private final Stack<Move> movesDone;
    private final List<Integer> diff;

    public Edit(List<Num> startNum) {
        this.startNum = startNum;
        this.movesDone = new Stack<>();
        this.diff = new ArrayList<>();
    }

    public void add(Move move) {
        this.movesDone.add(move);
        this.diff.add(move.getAbsoluteDiff());
    }

    @Override
    public Edit clone() {
        try {
            Edit clone = (Edit) super.clone();
            List<Num> startNum = clone.getStartNum();
            Stack<Move> movesDone = (Stack<Move>) clone.getMovesDone().clone();
            List<Integer> diff = clone.getDiff();
            return new Edit(new ArrayList<>(startNum), movesDone, new ArrayList<>(diff));
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "\nPath:" +
                "\n   -> startNum=" + startNum +
                "\n   -> movesDone=" + movesDone.toString() +
                "\n   -> diff=" + diff;
    }
}
