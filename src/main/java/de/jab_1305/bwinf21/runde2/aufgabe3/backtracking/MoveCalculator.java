package de.jab_1305.bwinf21.runde2.aufgabe3.backtracking;

import de.jab_1305.bwinf21.runde2.aufgabe3.backtracking.objects.BTMove;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.Num;
import de.jab_1305.bwinf21.runde2.aufgabe3.model.formatting.SevenSegmetFormatter;

import javax.swing.*;

public class MoveCalculator {
    public static void main(String[] args) {
        JFrame f = new JFrame();//creating instance of JFrame

        JTextField num1 = new JTextField("N1");
        num1.setBounds(100, 100, 100, 30);

        JTextField num2 = new JTextField("N2");
        num2.setBounds(100, 200, 100, 30);


        JLabel out = new JLabel("OUT");
        out.setBounds(100, 400, 100, 30);

        JButton b = new JButton("calculate");//creating instance of JButton
        b.setBounds(100, 300, 100, 20);//x axis, y axis, width, height
        b.addActionListener(e -> {
            String sn1 = num1.getText();
            String sn2 = num2.getText();

            Num n1 = Num.valueOfString(sn1);
            Num n2 = Num.valueOfString(sn2);

            BTMove move = new BTMove(n1, n2, -1);
            out.setText("Moves: " + move.getN() + "; Bars: " + move.getB());
            System.out.println("move.getB() = " + move.getB());

            assert n1 != null;
            assert n2 != null;

            SevenSegmetFormatter.printNum(n1);
            System.out.println("\n\n\n");
            SevenSegmetFormatter.printNum(n2);
        });

        f.add(b);//adding button in JFrame
        f.add(num1);
        f.add(num2);
        f.add(out);

        f.setSize(400, 500);//400 width and 500 height
        f.setLayout(null);//using no layout managers
        f.setVisible(true);//making the frame visible
    }
}
