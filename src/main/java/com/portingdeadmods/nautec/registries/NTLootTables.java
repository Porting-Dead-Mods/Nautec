package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.storage.loot.LootTable;

public final class NTLootTables {

    public static final ResourceKey<LootTable> CRATE = register("chests/crate");
    public static final ResourceKey<LootTable> BURIED_TREASURE = register("chests/buried_treasure");
    public static final ResourceKey<LootTable> GUARDIAN = register("entities/guardian");
    public static final ResourceKey<LootTable> ELDER_GUARDIAN = register("entities/elder_guardian");
    public static final ResourceKey<LootTable> DROWNED = register("entities/drowned");
    public static final ResourceKey<LootTable> DOLPHIN = register("entities/dolphin");

    private static ResourceKey<LootTable> register(String name) {
        return ResourceKey.create(Registries.LOOT_TABLE, Nautec.rl(name));
    }

    private NTLootTables() {}
}
