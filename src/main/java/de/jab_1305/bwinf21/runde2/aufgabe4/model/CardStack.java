package de.jab_1305.bwinf21.runde2.aufgabe4.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CardStack {
    ArrayList<Card> cards;
    boolean[][] data;
    int cardLength;
    int targetCardCount;

    int[] ocTrueConditions = {2, 4, 6, 8};
    int[] ocFalseConditions = {0, 1, 3, 5, 7};
    int[] ocPerDigit;

    public CardStack(ArrayList<Card> cards, int cardLength, int targetCardCount) {

        this.targetCardCount = targetCardCount;
        this.cardLength = cardLength;
        this.cards = cards;

        System.out.println("Generated CardStack - " + cards.size());

        this.data = new boolean[cards.size()][cardLength];
        this.ocPerDigit = new int[cardLength];

        for (int row = 0; row < cards.size(); row++) {
            Card card = this.cards.get(row);
            System.arraycopy(card.getBoolArray(), 0, data[row], 0, card.getBoolArray().length);
        }
        int index = 0;
        for (int i = 0; i < cardLength; i++) {
            int oc = 0;
            for (boolean[] card : data) {
                if (card[i]) oc++;
            }
            ocPerDigit[index] = oc;
            index++;
        }
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (boolean[] datum : data) {
            for (boolean b : datum) {
                stringBuilder.append(b ? "1" : "0");
                stringBuilder.append(" ");
            }
            stringBuilder.append("\n");
        }
        return stringBuilder.toString();
    }

    public Card genXOR() {
        StringBuilder binaryString = new StringBuilder();

        for (int row = 0; row < this.cardLength; row++) {
            int oneCount = 0;
            for (int cardIndex = 0; cardIndex < data.length; cardIndex++) {
                if (data[cardIndex][row]) oneCount++;
            }
            // TODO: What bout 0 oneCount? 0 % 2 == 0 !!!
            if (oneCount == 0) {
                binaryString.append("0");
                continue;
            }
            binaryString.append(oneCount % 2 == 0 ? "1" : "0");
        }
        return new Card(binaryString.toString(), this.cardLength);
    }

    public Card genMissingCard(Card xorCard) {
        StringBuilder binaryString = new StringBuilder();

        for (int row = 0; row < this.cardLength; row++) {
            int oneCount = 0;
            boolean digitToAdd = false;
            for (boolean[] datum : data) {
                if (datum[row]) oneCount++;
            }
            if (xorCard.getBoolAt(row)) {
                if (oneCount % 2 != 0) {
                    digitToAdd = true;
                }
            }
            // FIXME: Adding a zero does not always avoid % 2 == 0
            binaryString.append(digitToAdd ? "1" : "0");
        }
        return new Card(binaryString.toString(), cardLength);
    }

    public void findXOR() {
        // FIXME: Es fehlt die Lösung???
        for (Card potXOR : cards) {
            boolean[] boolArray = potXOR.getBoolArray();
            // Checking if 'card' might be the XOR
            // Using declared conditions check possibilities for digit
            // e.g. First digit is a 1; A 1 can be formed by an OC of 2,4,6,8 but max is 3
            // -> 2 ones on the first digit
            List<DigitInfo> dInfos = this.generateDigitInformation(boolArray);
            // Generate all Subsets (4 in 5) and validate with the given DInformation


            List<List<Integer>> allPermutations = combination(this.cards.size(), this.targetCardCount);
            System.out.println("Permutation count: " + allPermutations.size());
            System.out.println(allPermutations);
            for (List<Integer> permutation : allPermutations) {
                int[] ocPerDigit = new int[8];
                for (int d = 0; d < this.cardLength; d++) { // Iterate over every digit
                    int oc = 0;
                    for (int i = 0; i < permutation.size(); i++) { // Count ones on every card at given digit
                        Card card = this.cards.get(permutation.get(i) - 1);
                        if (card.getBoolAt(d)) oc++;
                    }
                    ocPerDigit[d] = oc;
                }
                System.out.println("Permutation: " + permutation + "; " + " OneCounts: " + Arrays.toString(ocPerDigit));
                System.out.println("Valid: " + isValid(dInfos, ocPerDigit));
            }
            break;
        }
    }

    private List<DigitInfo> generateDigitInformation(boolean[] boolArray) {
        List<DigitInfo> dInfos = new ArrayList<>();
        int digit = 0;
        for (boolean b : boolArray) {
            int maxOC = -1;
            int[] conditions = b ? this.ocTrueConditions : this.ocFalseConditions;
            for (int i = conditions.length - 1; i >= 0; i--) {
                int potMax = conditions[i];
                if (potMax > ocPerDigit[digit]) {
                    maxOC = potMax;
                }
            }
            System.out.println("Digit " + digit + "; MAX oc " + maxOC + "; odd: " + b);
            dInfos.add(new DigitInfo(digit, maxOC, b));
            digit++;
        }

        return dInfos;
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

    private boolean isValid(List<DigitInfo> digits, int[] ocPerDigit) {
        for (DigitInfo digit : digits) {
            boolean isOdd = (ocPerDigit[digit.digit] % 2) == 0;
            if (ocPerDigit[digit.digit] != 0 && isOdd != digit.odd) return false;
        }
        return true;
    }

    public void test() {
        Card potXOR = this.cards.get(0);
        System.out.println("potXOR = " + potXOR.toString());

    }
}
