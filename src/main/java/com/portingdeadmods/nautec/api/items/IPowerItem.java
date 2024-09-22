package com.portingdeadmods.nautec.api.items;

/**
 * Implement this interface on your item to auto register the capability for it
 */
public interface IPowerItem {
    int getMaxInput();

    int getMaxOutput();

}
