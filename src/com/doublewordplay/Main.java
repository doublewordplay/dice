package com.doublewordplay;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

public class Main {

    public static void main(String[] args) {
        List<Integer> rolls = new ArrayList<>();

        System.out.println("roll 1 die 1 time");
        rolls = roll(20);
        System.out.println("\nroll 1 die multiple times");
        rolls = roll(asList(20), 5);
        System.out.println("\nroll multiple dice 1 time");
        rolls = roll(20, 8, 4);
        System.out.println("\nroll 3 different sided dice 3 different times");
        rolls = roll(asList(20, 10, 6), 3, 2, 1);
        System.out.println("\nbad number of args case");
        rolls = roll(asList(20, 10, 8), 1, 2);
    }

    /**
     * Recursively rolls dice based on sides and times.
     * @param sides specifies an n-sided die to roll.
     * @param times specifies how many times to roll each die.
     * @return a list of rolls.
     */
    private static List<Integer> roll(final List<Integer> sides, final int... times) {
        List<Integer> result = new ArrayList<Integer>();

        Random seed = new Random();

        if (times.length == 1) {
            if (times[0] == 1) {
                for (int side : sides) {
                    seed.ints(1, side + 1).findFirst().ifPresent(num -> {
                        result.add(num);
                        System.out.println("d" + side + ": " + num);
                    });
                }
            } else {
                for (int side : sides) {
                    int[] die = seed.ints(times[0], 1, side + 1).toArray();
                    result.addAll(Arrays.stream(die).boxed().collect(Collectors.toList()));
                    System.out.print(times[0] + "d" + side + ": ");
                    Iterator it = Arrays.stream(die).iterator();
                    System.out.print(it.next());
                    while (it.hasNext()){
                        System.out.print(", " + it.next());
                    }
                    System.out.println();
                }
            }
        } else {
            if (times.length != sides.size()) {
                System.err.println("Not enough parameters to match dice.");
                System.exit(1);
            } else {
                for (int i = 0; i < sides.size(); i ++) {
                    result.addAll(roll(asList(sides.get(i)), times[i]));
                }
            }
        }

        return result;
    }

    /**
     * Overloaded method for convenience if you want to roll however many dice, but only once.
     * @param sides specifies an n-sided die to roll.
     * @return a list of rolls.
     */
    private static List<Integer> roll(final int... sides) {
        return roll(Arrays.stream(sides).boxed().collect(Collectors.toList()), 1);
    }
}
