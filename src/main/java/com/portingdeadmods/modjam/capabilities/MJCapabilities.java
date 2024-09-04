package com.portingdeadmods.modjam.capabilities;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.power.IPowerStorage;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.EntityCapability;
import net.neoforged.neoforge.capabilities.ItemCapability;
import org.jetbrains.annotations.Nullable;

public final class MJCapabilities {
    public static final class PowerStorage {
        public static final BlockCapability<IPowerStorage, @Nullable Direction> BLOCK = BlockCapability.createSided(create("power"), IPowerStorage.class);
        public static final ItemCapability<IPowerStorage, Void> ITEM = ItemCapability.createVoid(create("power"), IPowerStorage.class);
        public static final EntityCapability<IPowerStorage, @Nullable Direction> ENTITY = EntityCapability.createSided(create("power"), IPowerStorage.class);
    }

    private static ResourceLocation create(String path) {
        return ResourceLocation.fromNamespaceAndPath(ModJam.MODID, path);
    }
}
