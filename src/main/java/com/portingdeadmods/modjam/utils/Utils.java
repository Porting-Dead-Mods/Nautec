package com.portingdeadmods.modjam.utils;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;

public class Utils {
    public static IntList intArrayToList(int[] array) {
        IntList list = new IntArrayList();
        for (int i : array) {
            list.add(i);
        }
        return list;
    }
}
