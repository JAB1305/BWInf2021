package de.jab_1305.bwinf21.runde2.aufgabe3.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MoveResult {
    private Integer n;
    private Integer b;

    @Override
    public String toString() {
        return "MoveResult{" +
                "n=" + n +
                ", b=" + b +
                '}';
    }
}
