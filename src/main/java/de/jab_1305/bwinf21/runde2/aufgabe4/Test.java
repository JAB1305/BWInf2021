package de.jab_1305.bwinf21.runde2.aufgabe4;


import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Test {

    private static int cardLength = 4; //0: 32
    // IMPORTANT: targetCardCount has to be an ODD number
    private static int targetCardCount = 3; //0:4
    private static int totalCardCount = 4; //0:20

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println(Arrays.toString(generateZeroCounts(11)));

        boolean[][] data = loadFromFile("src/main/java/de/jab_1305/bwinf21/runde2/aufgabe4/examples/stapeltest.txt");

        ArrayList<Integer> digits = generateDigitArray();
        int[] zcs = generateZeroCounts(data);
        digits.sort((o1, o2) -> zcs[o2] - zcs[o1]);
        // Index sorted by 0-count
        for (Integer digit : digits) {
            System.out.println("Digit: " + digit);
            int zeroCount = zcs[digit];
            ArrayList<Integer> zeroIndices = genIndices(digit, data, false);
            ArrayList<Integer> oneIndices = genIndices(digit, data, true);
            generatePossibilities(zeroCount, targetCardCount, zeroIndices, oneIndices);
            break;
        }

    }

    private static List<List<Integer>> generatePossibilities(int zeroCount, int combinationLength, List<Integer> zeroIndices, List<Integer> oneIndices) {
        int maxZeroCount = Math.min(zeroCount, combinationLength);
        for (int validZeroCount : generateZeroCounts(maxZeroCount)) {

            // Liste mit "0-Indizes"
            LinkedList<LinkedList<Integer>> allZeroCombinations = combinationsInList(zeroIndices.size(), validZeroCount, zeroIndices);
            System.out.print("Possiblities with " + validZeroCount + " zero: " + allZeroCombinations.size() + "   ");
            System.out.println(allZeroCombinations.toString());


            // Liste mit "1-Indizes"
            if (combinationLength - validZeroCount != 0) {
                LinkedList<LinkedList<Integer>> allOneCombinations = combinationsInList(oneIndices.size(), combinationLength - validZeroCount, oneIndices);
                System.out.print("Possiblities with " + (combinationLength - validZeroCount) + " one: " + allOneCombinations.size() + "   ");
                System.out.print(allOneCombinations.toString());

            } else {
                // Valid combinations at digit = allZeroCombinations
            }

            System.out.println("\n\n");
            // Kombinationen aus 'validZeroCount' '0-Indizes' und 'combinationLength' - 'validZerCount' '1-Indizes'
        }
        return null;
    }

    public static LinkedList<LinkedList<Integer>> combinationsInList(int n, int k, List<Integer> list) {
        LinkedList<LinkedList<Integer>> result = new LinkedList<>();
        backtrack(n, k, 1, result, new LinkedList<>());
        LinkedList<LinkedList<Integer>> convertedResult = new LinkedList<>();
        for (LinkedList<Integer> combination : result) {
            LinkedList<Integer> newCombination = new LinkedList<>();
            combination.forEach(i -> newCombination.add(list.get(i - 1)));
            convertedResult.add(newCombination);
        }
        return convertedResult;
    }

    public static LinkedList<LinkedList<Integer>> combination(int n, int k) {
        LinkedList<LinkedList<Integer>> result = new LinkedList<>();
        backtrack(n, k, 1, result, new LinkedList<>());
        return result;
    }

    public static void backtrack(int n, int k, int startIndex, LinkedList<LinkedList<Integer>> result,
                          LinkedList<Integer> partialList) {
        if (k == partialList.size()) {
            result.add(new LinkedList<>(partialList));
            return;
        }
        for (int i = startIndex; i <= n; i++) {
            partialList.add(i);
            backtrack(n, k, i + 1, result, partialList);
            partialList.remove(partialList.size() - 1);
        }
    }

    void generatePermutations(List<List<Integer>> lists, List<List<Integer>> result, int depth, List<Integer> current) {
        if (depth == lists.size()) {
            result.add(current);
            return;
        }

        for (int i = 0; i < lists.get(depth).size(); i++) {
            Integer integer = lists.get(depth).get(i);
            current.add(integer);
            generatePermutations(lists, result, depth + 1, current);
        }
    }

    private static ArrayList<Integer> generateDigitArray() {
        ArrayList<Integer> digits = new ArrayList<>();
        for (int i = 0; i < cardLength; i++) {
            digits.add(i);
        }
        return digits;
    }

    private static int[] generateZeroCounts(boolean[][] data) {
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

    static boolean[][] loadFromFile(String path) throws FileNotFoundException {
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

        StringBuilder stringBuilder = new StringBuilder();
        for (boolean[] datum : cards) {
            for (boolean b : datum) {
                stringBuilder.append(b ? "1" : "0");
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        System.out.println(stringBuilder);

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
}
