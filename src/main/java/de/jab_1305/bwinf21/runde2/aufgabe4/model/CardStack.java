package de.jab_1305.bwinf21.runde2.aufgabe4.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
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
        // FIXME: Currently depth first reversal
        for (Card potXOR : cards) {
            boolean[] boolArray = potXOR.getBoolArray();
            List<DigitInfo> dInfos = this.generateDigitInformation(boolArray);

            LinkedList<LinkedList<Integer>> allPermutations = combination(this.cards.size(), this.targetCardCount);
            System.out.println("Permutation count: " + allPermutations.size());

            LinkedList<LinkedList<Integer>> allValidPermsD1 = allPermutations; // TODO: Big performance upgrade: Dont start with all Perms

            for (DigitInfo dInfo : dInfos) {
                allValidPermsD1 = this.validate(allValidPermsD1, dInfo);
                System.out.println("Remaining valid: " + allValidPermsD1.size());
            }
            System.out.println("Valid: " + allValidPermsD1.size());
            if (allValidPermsD1.size() == 1) {
                System.out.println(isActuallyValid(allValidPermsD1.get(0), potXOR) + " with potXOR " + (this.cards.indexOf(potXOR) + 1));
                System.out.println(allValidPermsD1.get(0));
            }
        }
    }

    private boolean isActuallyValid(List<Integer> shiftedIndexes, Card card) {
        // TODO: Gen XOR of given Cards and compare
        int[] ocPerDigit = new int[cardLength];
        int index = 0;
        for (int i = 0; i < cardLength; i++) {
            int oc = 0;
            for (Integer shiftedIndex : shiftedIndexes) {
                Card cardToAdd = this.cards.get(shiftedIndex - 1);
                if (cardToAdd.getBoolAt(i)) oc++;
            }
            ocPerDigit[index] = oc;
            index++;
        }
        for (int i = 0; i < card.getBoolArray().length; i++) {
            if (((ocPerDigit[i] % 2) == 0) != card.getBoolAt(i) && ocPerDigit[i] != 0) return false;
            if (ocPerDigit[i] == 0 && card.getBoolAt(i)) return false;
        }
        return true;
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
            //System.out.println("Digit " + digit + "; MAX oc " + maxOC + "; odd: " + b);
            dInfos.add(new DigitInfo(digit, maxOC, b));
            digit++;
        }

        return dInfos;
    }

    public LinkedList<LinkedList<Integer>> combination(int n, int k) {
        LinkedList<LinkedList<Integer>> result = new LinkedList<>();
        backtrack(n, k, 1, result, new LinkedList<>());
        return result;
    }

    public void backtrack(int n, int k, int startIndex, LinkedList<LinkedList<Integer>> result,
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

    private boolean isValid(List<DigitInfo> digits, int[] ocPerDigit) {
        for (DigitInfo digit : digits) {
        }
        return true;
    }

    public void test() {
        Card potXOR = this.cards.get(0);
        System.out.println("potXOR = " + potXOR.toString());

    }

    public LinkedList<LinkedList<Integer>> validate(LinkedList<LinkedList<Integer>> allPerms, DigitInfo digitInfo) {
        LinkedList<LinkedList<Integer>> validPerms = new LinkedList<>();
        for (LinkedList<Integer> allPerm : allPerms) {
            int oc = 0;
            for (Integer integer : allPerm) {
                Card card = this.cards.get(integer - 1);
                if (card.getBoolAt(digitInfo.digit)) oc++;
            }
            boolean isOdd = (oc % 2) == 0;
            if (oc != 0 && isOdd != digitInfo.odd) continue;
            validPerms.add(allPerm);
        }
        return validPerms;
    }
}
