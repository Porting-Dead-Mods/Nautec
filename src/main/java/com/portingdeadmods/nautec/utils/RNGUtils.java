package com.portingdeadmods.nautec.utils;

public final class RNGUtils {
    public static int uniformRandInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public static int uniformRandInt(int max) {
        return uniformRandInt(0, max);
    }

    public static double uniformRandDouble(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    public static double uniformRandDouble(double max) {
        return uniformRandDouble(0, max);
    }

    public static int intInRangeOf(int val, int range) {
        return (int) (uniformRandInt(-val, val) * Math.random() + range);
    }

    public static int intInRangeOf(int val) {
        return intInRangeOf(val, 0);
    }

    public static double doubleInRangeOf(double val, double range) {
        return uniformRandDouble(-val, val) * Math.random() + range;
    }

    public static double doubleInRangeOf(double val) {
        return doubleInRangeOf(val, 0);
    }

    /*
    * -1 -> always in the negative range
    * 1 -> always in the positive range
    * 0 -> neutral
     */
    public static double biasedInRangeOf(double val, double range, double bias) {
        double absBias = Math.abs(bias);

        if (bias < 0) {
            return uniformRandDouble(-range, range * (1 - absBias)) + val;
        } else if (bias > 0) {
            return uniformRandDouble(-range * (1 - absBias), range) + val;
        }
        return uniformRandDouble(-range, range) + val;
    }

    public static double biasedInRangeOf(double range, double bias) {
        return biasedInRangeOf(0, range, bias);
    }
}
