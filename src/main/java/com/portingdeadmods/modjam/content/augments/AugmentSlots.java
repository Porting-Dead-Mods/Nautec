package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;

public enum AugmentSlots implements AugmentSlot {
    HEAD("head", 0),
    BODY("body", 1),
    ARMS("arms", 2),
    LEGS("legs",3),
    HEART("heart", 4),
    NONE("none", -1);

    private final ResourceLocation name;
    private final int slotId;

    AugmentSlots(String name, int id) {
        this.name = ResourceLocation.fromNamespaceAndPath(ModJam.MODID, name);
        this.slotId = id;
    }

    public static AugmentSlots getValue(int id) {
        AugmentSlots[] Slots = AugmentSlots.values();
        for (AugmentSlots slot : Slots) {
            if (slot.slotId == id)
                return slot;
        }
        return AugmentSlots.NONE;
    }

    @Override
    public @NotNull ResourceLocation getLocation() {
        return name;
    }

    @Override
    public int getSlotId() {
        return slotId;
    }
}
