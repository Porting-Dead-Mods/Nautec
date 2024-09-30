package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public final class NTLootTables {

    private static final Set<ResourceKey<LootTable>> LOCATIONS = new HashSet<>();
    private static final Set<ResourceKey<LootTable>> IMMUTABLE_LOCATIONS = Collections.unmodifiableSet(LOCATIONS);

    public static final ResourceKey<LootTable> CRATE = register("chests/crate");
    public static final ResourceKey<LootTable> RUSTY_CRATE = register("chests/rusty_crate");
    public static final ResourceKey<LootTable> GUARDIAN = register("entities/guardian");
    public static final ResourceKey<LootTable> ELDER_GUARDIAN = register("entities/elder_guardian");
    public static final ResourceKey<LootTable> DROWNED = register("entities/drowned");
    public static final ResourceKey<LootTable> DOLPHIN = register("entities/dolphin");

    private static ResourceKey<LootTable> register(String name) {
        return register(ResourceKey.create(Registries.LOOT_TABLE, ResourceLocation.fromNamespaceAndPath(Nautec.MODID,name)));
    }

    private static ResourceKey<LootTable> register(ResourceKey<LootTable> name) {
        if (LOCATIONS.add(name)) {
            return name;
        } else {
            throw new IllegalArgumentException(name.location() + " is already a registered built-in loot table");
        }
    }
}
