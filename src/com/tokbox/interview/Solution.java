package com.tokbox.interview;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * Created by gaopeng on 10/26/17.
 */
public class Solution {

    public static final String WELCOME = "Welcome to how-many-books calculator!";
    public static final String USAGE = "usage: initialPrice discount floorPrice budget";

    /**
     * Core computation method
     *
     * @param initialPrice
     * @param discount
     * @param floorPrice
     * @param budget
     * @return an array with 2 elements: first element is the number of books, second element is remaining money
     */
    public int[] howManyBooks(int initialPrice, int discount, int floorPrice, int budget) {

        if (initialPrice <= 0 || discount < 0 || floorPrice <= 0 || budget < 0) {
            throw new IllegalArgumentException("Could not have negative numbers or zero prices (will result in infinity)");
        }

        if (floorPrice > initialPrice) {
            throw new IllegalArgumentException("A valid floor price should be smaller than or equal to initial price");
        }

        int cnt = 0;


        while (budget >= initialPrice) {

            budget -= initialPrice;
            if (initialPrice - discount > floorPrice) {
                initialPrice -= discount;
            } else if (initialPrice > floorPrice) {
                initialPrice = floorPrice;
            }

            cnt++;


        }

        System.out.println(String.format("Purchasing Result: book=%d, remaining=%d", cnt, budget));

        return new int[]{cnt, budget};
    }

    /**
     * Some test cases
     */
    private void tests() {

        try {
            assert Arrays.equals(new int[]{8, 9}, new Solution().howManyBooks(50, 4, 25, 300));

            // corner case
            assert Arrays.equals(new int[]{0, 0}, new Solution().howManyBooks(1, 0, 1, 0));
            assert Arrays.equals(new int[]{1, 0}, new Solution().howManyBooks(100, 0, 1, 100));
            assert Arrays.equals(new int[]{1, 0}, new Solution().howManyBooks(100, 0, 100, 100));

            // large result
            assert Arrays.equals(new int[]{10000, 0}, new Solution().howManyBooks(1, 0, 1, 10000));
            assert Arrays.equals(new int[]{3333, 1}, new Solution().howManyBooks(3, 0, 3, 10000));

            System.out.println("Tests passed");
        } catch (AssertionError e) {
            System.err.println("Tests failed");
            throw e;
        }
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(WELCOME);
        System.out.println(USAGE);


        boolean loopFlag = true;
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (true) {
                String command = null;

                command = br.readLine().toLowerCase();

                switch (command) {
                    case "exit": {
                        // exit here
                        System.out.println("Goodbye!");
                        loopFlag = false;
                        break;
                    }

                    case "test": {
                        System.out.println("Running tests");
                        solution.tests();
                        break;
                    }
                    default: {
                        // unrecognised command

                        try {
                            int[] tokens = parseInput(command);
                            solution.howManyBooks(tokens[0], tokens[1], tokens[2], tokens[3]);
                        } catch (IllegalArgumentException e) {
                            e.printStackTrace();
                            System.out.println(USAGE);
                        }
                        break;
                    }
                }
                if(!loopFlag) break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Convert command into tokens
     * @param command
     * @return an array of size 4 containing the arguments for calculating howManyBooks
     */
    private static int[] parseInput(String command) {
        String[] split = command.split("\\s");

        if(split.length != 4) throw new IllegalArgumentException("Wrong input format");

        int[] res = new int[4];
        for(int i = 0; i < 4; i++) {
            res[i] = Integer.parseInt(split[i]);
        }

        System.out.println(String.format("input: initialPrice=%d discount=%d floorPrice=%d budget=%d", res[0], res[1], res[2], res[3]));

        return res;
    }
}
