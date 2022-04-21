package de.jab_1305.bwinf21.runde2.aufgabe4.model;

import java.util.LinkedList;

public class DepthFristBruteForce {

    Card[] cards;

    public static void main(String[] args) {
        System.out.println(combination(4, 3));
    }

    void genRandomComb(int length) {

    }

    public static LinkedList<LinkedList<Integer>> combination(int n, int k) {
        LinkedList<LinkedList<Integer>> result = new LinkedList<>();
        backtrack(n, k, 1, result, new LinkedList<>());
        return result;
    }

    public static void backtrack(int n, int k, int startIndex, LinkedList<LinkedList<Integer>> result,
                          LinkedList<Integer> partialList) {
        if (k == partialList.size()) {
            System.out.println("Result found: " + partialList);
            result.add(new LinkedList<>(partialList));
            return;
        }
        for (int i = startIndex; i <= n; i++) {
            partialList.add(i);
            backtrack(n, k, i + 1, result, partialList);
            partialList.remove(partialList.size() - 1);
        }
    }
}
