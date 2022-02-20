import de.jab_1305.bwinf21.runde2.aufgabe3.model.Move;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;

public class BilanzBerechnungsTest {

    public void test() {
        for (Num num1 : Num.values()) {
            for (Num num2 : num1.getAllBiggerOnes()) {
            //    Move move = new Move(num1, num2);
             //   Move moveReversed = new Move(num2, num1);
               // assert move.getB() + moveReversed.getB() != 0;
            }
        }
    }


}
