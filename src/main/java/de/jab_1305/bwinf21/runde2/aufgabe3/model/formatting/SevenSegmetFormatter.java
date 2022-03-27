package de.jab_1305.bwinf21.runde2.aufgabe3.model.formatting;

import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;

public class SevenSegmetFormatter {

    public static void printNum(Num num) {
        boolean[] bars = num.getBars();

        String l1 = "", l2 = "", l3 = "", l4 = "", l5 = "";


        if (bars[0]) { // Top bar │ first line
            l1 += " ―";
        }

        // 2nd line
        if (bars[5] && bars[1]) l2 += "│  │";
        else if (bars[5]) l2 += "│";
        else if (bars[1]) l2 += "   │";

        if (bars[6]) { // Middle bar │ second line
            l3 += " ―";
        }

        // 4nth line
        if (bars[4] && bars[2]) l4 += "│  │";
        else if (bars[4]) l4 += "│";
        else if (bars[2]) l4 += "   │";

        if (bars[3]) { // Bottom bar │ third line
            l5 += " ―";
        }

        if (!l1.isEmpty()) System.out.println(l1);
        if (!l2.isEmpty()) System.out.println(l2);
        if (!l3.isEmpty()) System.out.println(l3);
        if (!l4.isEmpty()) System.out.println(l4);
        if (!l5.isEmpty())  System.out.println(l5);
    }


}
