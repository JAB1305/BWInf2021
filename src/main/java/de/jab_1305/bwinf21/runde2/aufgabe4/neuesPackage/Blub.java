package de.jab_1305.bwinf21.runde2.aufgabe4.neuesPackage;

import java.io.File;
import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Blub {

    public static void main(String[] args) throws FileNotFoundException {
        UUID[] u = loadUUIDFromFile("src/main/java/de/jab_1305/bwinf21/runde2/aufgabe4/examples/stapel0.txt");

        System.out.println(xorButDifferent(u, 0, 10, 0, 0));
    }

    private static ArrayList<Long> xor(long[] l, int startIndex, int remainingDepth, long xor) {
        if (remainingDepth == 0) {
            for (int i = startIndex; i < l.length; i++) {
                long result = xor ^ l[i];
                if (result == 0) return new ArrayList<>(List.of(l[i]));
            }
        } else {
            for (int i = startIndex; i < l.length - remainingDepth; i++) {
                long result = xor ^ l[i];
                ArrayList<Long> resultList = xor(l, i + 1, remainingDepth - 1, result);
                if (resultList != null) {
                    resultList.add(l[i]);
                    return resultList;
                }
            }
        }
        return null;
    }



    private static ArrayList<UUID> xorButDifferent(UUID[] l, int startIndex, int remainingDepth, long mostSigXOR, long leastSigXOR) {
        if (remainingDepth == 0) {
            for (int i = startIndex; i < l.length; i++) {
                long resultHigh = mostSigXOR ^ l[i].getMostSignificantBits();
                long resultLow = leastSigXOR ^ l[i].getLeastSignificantBits();
                if (resultHigh == 0 && resultLow == 0) {
                    return new ArrayList<>(List.of(l[i]));
                }
            }
        } else {
            for (int i = startIndex; i < l.length - remainingDepth; i++) {
                long resultHigh = mostSigXOR ^ l[i].getMostSignificantBits();
                long resultLow = leastSigXOR ^ l[i].getLeastSignificantBits();
                ArrayList<UUID> resultList = xorButDifferent(l, i + 1, remainingDepth - 1, resultHigh, resultLow);
                if (resultList != null) {
                    resultList.add(l[i]);
                    return resultList;
                }
            }
        }
        return null;
    }


    static long[] loadFromFile(String path) throws FileNotFoundException {
        Scanner s = new Scanner(new File(path));
        ArrayList<String> list = new ArrayList<>();
        while (s.hasNext()) {
            list.add(s.next());
        }
        s.close();

        int total = Integer.parseInt(list.get(0));
        int cardCount = Integer.parseInt(list.get(1));
        int cardLength = Integer.parseInt(list.get(2));

        System.out.println("cardCount = " + cardCount);
        System.out.println("cardLength = " + cardLength);
        System.out.println("total = " + total);

        long[] cards = new long[total];

        int index = 3;
        while (index < list.size()) {
            cards[index - 3] = Long.parseLong(list.get(index), 2);
            index++;
        }

        System.out.println(cards.length);
        return cards;
    }

    static UUID[] loadUUIDFromFile(String path) throws FileNotFoundException {
        Scanner s = new Scanner(new File(path));
        ArrayList<String> list = new ArrayList<>();
        while (s.hasNext()) {
            list.add(s.next());
        }
        s.close();

        int total = Integer.parseInt(list.get(0));
        int cardCount = Integer.parseInt(list.get(1));
        int cardLength = Integer.parseInt(list.get(2));

        System.out.println("cardCount = " + cardCount);
        System.out.println("cardLength = " + cardLength);
        System.out.println("total = " + total);

        UUID[] cards = new UUID[total];

        int index = 3;
        while (index < list.size()) {
            if (index == 3) System.out.println(list.get(index));
            String stringMostSig = list.get(index).substring(0, 64);
            long mostSigBits = new BigInteger(stringMostSig, 2).longValue();

            String stringLeastSig = list.get(index).substring(64, 128);
            long leastSigBits = new BigInteger(stringLeastSig, 2).longValue();

            cards[index - 3] = new UUID(mostSigBits, leastSigBits);
            index++;
        }

        System.out.println("Generated UUIDS, count: " + cards.length);
        return cards;
    }

    /*
        1
        0
        0
        1
        1

        m = 3
        [0,...] Wenn -> 0

        n = 4
        m = 11
        zeroCount -> 1, 3
        onesCount -> 2, 4

        1 -> 2,4,6...
        0 -> 1,3,5...
     */



    /*




     */
}
