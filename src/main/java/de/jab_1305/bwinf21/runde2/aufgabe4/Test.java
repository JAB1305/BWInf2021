package de.jab_1305.bwinf21.runde2.aufgabe4;


import com.google.common.collect.Lists;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.*;

public class Test {

    private int cardLength = 4; //0: 32
    // IMPORTANT: targetCardCount has to be an ODD number
    private int targetCardCount = 3; //0:4
    private int totalCardCount = 4; //0:20

    public static void main(String[] args) throws FileNotFoundException {
        long start2 = System.currentTimeMillis();
        new Test().bonk();
        long end2 = System.currentTimeMillis();
        usage();
        System.out.println("Elapsed Time in seconds: " + (end2 - start2) / 1000.0);
    }

    private static void usage() {
        Runtime runtime = Runtime.getRuntime();

        NumberFormat format = NumberFormat.getInstance();

        StringBuilder sb = new StringBuilder();
        long maxMemory = runtime.maxMemory();
        long allocatedMemory = runtime.totalMemory();
        long freeMemory = runtime.freeMemory();

        sb.append("free memory: " + format.format(freeMemory / 1024) + "\n");
        sb.append("allocated memory: " + format.format(allocatedMemory / 1024) + "\n");
        sb.append("max memory: " + format.format(maxMemory / 1024) + "\n");
        sb.append("total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024) + "\n");
        System.out.println(sb.toString());
    }

    public void bonk() throws FileNotFoundException {
        boolean[][] data = loadFromFile("src/main/java/de/jab_1305/bwinf21/runde2/aufgabe4/examples/stapel1.txt");

        ArrayList<Integer> digits = generateDigitArray();
        int[] zcs = generateZeroCounts(data);
        digits.sort((o1, o2) -> zcs[o2] - zcs[o1]);
        // Index sorted by 0-count
        for (Integer digit : digits) {
            int zeroCount = zcs[digit];
            ArrayList<Integer> zeroIndices = genIndices(digit, data, false);
            ArrayList<Integer> oneIndices = genIndices(digit, data, true);
            List<List<Integer>> valid = generatePossibilities(zeroCount, this.targetCardCount + 1, zeroIndices, oneIndices);
        }
    }

    private List<List<Integer>> generatePossibilities(int zeroCount, int combinationLength, List<Integer> zeroIndices, List<Integer> oneIndices) {
        int maxZeroCount = Math.min(zeroCount, combinationLength);
        List<List<Integer>> result = new ArrayList<>();

        for (int validZeroCount : generateZeroCounts(maxZeroCount)) {

            // Liste mit "0-Indizes"
            List<List<Integer>> allZeroCombinations = combinationsInList(zeroIndices.size(), validZeroCount, zeroIndices);
            //System.out.print("Possibilities with " + validZeroCount + " zero: " + allZeroCombinations.size() + "   ");
            //System.out.println(allZeroCombinations.toString());

            // Liste mit "1-Indizes"
            if (combinationLength - validZeroCount != 0) {
                List<List<Integer>> allOneCombinations = combinationsInList(oneIndices.size(), combinationLength - validZeroCount, oneIndices);
                //System.out.print("Possiblities with " + (combinationLength - validZeroCount) + " one: " + allOneCombinations.size() + "   ");

                if (allZeroCombinations.size() != 0 && allOneCombinations.size() != 0) {
                    List<Integer> zeroCombs = makeSequence(0, allZeroCombinations.size());
                    List<Integer> oneCombs = makeSequence(0, allOneCombinations.size());

                    //result.addAll(Lists.cartesianProduct(zeroCombs, oneCombs));
                    // TODO: FIXME: Recursive call; Dont save all combs rather validate every single one
                }
            } else {
                // Valid combinations at digit = allZeroCombinations
                //result.addAll(allZeroCombinations);
                // TODO: FIXME: Recursive call; Dont save all combs rather validate every single one
            }
            //System.out.println("Current result " + result);
            // Kombinationen aus 'validZeroCount' '0-Indizes' und 'combinationLength' - 'validZerCount' '1-Indizes'
        }
        System.out.println("result = " + result.size());
        return result;
    }

    public List<List<Integer>> combinationsInList(int n, int k, List<Integer> list) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(n, k, 1, result, new ArrayList<>());
        List<List<Integer>> convertedResult = new ArrayList<>();
        for (List<Integer> combination : result) {
            List<Integer> newCombination = new ArrayList<>();
            combination.forEach(i -> newCombination.add(list.get(i - 1)));
            convertedResult.add(newCombination);
        }
        return convertedResult;
    }

    public List<List<Integer>> combination(int n, int k) {
        List<List<Integer>> result = new ArrayList<>();
        backtrack(n, k, 1, result, new ArrayList<>());
        return result;
    }

    public void backtrack(int n, int k, int startIndex, List<List<Integer>> result,
                          List<Integer> partialList) {
        if (k == partialList.size()) {
            result.add(new ArrayList<>(partialList));
            return;
        }
        for (int i = startIndex; i <= n; i++) {
            partialList.add(i);
            backtrack(n, k, i + 1, result, partialList);
            partialList.remove(partialList.size() - 1);
        }
    }

    private ArrayList<Integer> generateDigitArray() {
        ArrayList<Integer> digits = new ArrayList<>();
        for (int i = 0; i < this.cardLength; i++) {
            digits.add(i);
        }
        return digits;
    }

    private int[] generateZeroCounts(boolean[][] data) {
        int[] ocs = new int[cardLength];

        for (int digit = 0; digit < cardLength; digit++) {
            int zc = 0;
            for (boolean[] row : data) {
                if (!row[digit]) zc++;
            }
            ocs[digit] = zc;
        }
        return ocs;
    }

    private boolean[][] loadFromFile(String path) throws FileNotFoundException {
        Scanner s = new Scanner(new File(path));
        ArrayList<String> list = new ArrayList<>();
        while (s.hasNext()) {
            list.add(s.next());
        }
        s.close();

        int total = Integer.parseInt(list.get(0));
        this.totalCardCount = total;
        int cardCount = Integer.parseInt(list.get(1));
        this.targetCardCount = cardCount;
        int cardLength = Integer.parseInt(list.get(2));
        this.cardLength = cardLength;

        System.out.println("cardCount = " + cardCount);
        System.out.println("cardLength = " + cardLength);
        System.out.println("total = " + total);
        boolean[][] cards = new boolean[total][cardLength];
        int index = 3;
        while (index < list.size()) {
            boolean[] boolArray = new boolean[cardLength];
            String cardString = list.get(index);
            for (int i = 0; i < boolArray.length; i++) {
                boolArray[i] = cardString.charAt(i) == '1';
            }
            cards[index - 3] = boolArray;
            index++;
        }

        /*StringBuilder stringBuilder = new StringBuilder();
        for (boolean[] datum : cards) {
            for (boolean b : datum) {
                stringBuilder.append(b ? "1" : "0");
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);*/

        return cards;
    }

    private static int[] generateZeroCounts(int limit) {
        int[] zcs = (limit % 2 == 0) ? new int[limit / 2] : new int[limit / 2 + 1];
        int index = 0;
        for (int i = 1; i <= limit; i++) {
            // Have to be odd
            if (i % 2 != 0) {
                zcs[index] = i;
                index++;
            }
        }
        return zcs;
    }

    private static ArrayList<Integer> genIndices(int digit, boolean[][] data, boolean value) {
        ArrayList<Integer> indices = new ArrayList<>();
        for (int index = 0; index < data.length; index++) {
            if (value == data[index][digit]) indices.add(index);
        }
        return indices;
    }

    static List<Integer> makeSequence(int begin, int end) {
        List<Integer> ret = new ArrayList(end - begin);
        for (int i = begin; i < end; i++, ret.add(i - 1)) ;
        return ret;
    }
}
