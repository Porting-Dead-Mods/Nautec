package com.portingdeadmods.nautec.utils;

public final class RNGUtils {
    public int uniformRandInt(int min, int max) {
        return (int) (Math.random() * (max - min + 1) + min);
    }

    public int uniformRandInt(int max) {
        return uniformRandInt(0, max);
    }

    public double uniformRandDouble(double min, double max) {
        return Math.random() * (max - min) + min;
    }

    public double uniformRandDouble(double max) {
        return uniformRandDouble(0, max);
    }

    public int intInRangeOf(int val, int range) {
        return (int) (uniformRandInt(-val, val) * Math.random() + range);
    }

    public int intInRangeOf(int val) {
        return intInRangeOf(val, 0);
    }

    public double doubleInRangeOf(double val, double range) {
        return uniformRandDouble(-val, val) * Math.random() + range;
    }

    public double doubleInRangeOf(double val) {
        return doubleInRangeOf(val, 0);
    }
}
