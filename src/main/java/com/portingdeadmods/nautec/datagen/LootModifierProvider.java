package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.loot.AddItemModifier;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTLootTables;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.component.DataComponents;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.SeededContainerLoot;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.AddTableLootModifier;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class LootModifierProvider extends GlobalLootModifierProvider {
    public LootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, Nautec.MODID);
    }

    @Override
    protected void start() {
        ItemStack crate = NTBlocks.CRATE.toStack();
        crate.set(DataComponents.CONTAINER_LOOT,new SeededContainerLoot(NTLootTables.CRATE,0));
        add("elder_guardian_modifier",
                new AddItemModifier(new LootItemCondition[]{
                        LootTableIdCondition.builder(ResourceLocation.parse("entities/elder_guardian")).build(),
                        LootItemRandomChanceCondition.randomChance(1.0f).build()}
                        ,crate));
        add("guardian_modifier",
                new AddTableLootModifier(new LootItemCondition[]{
                        LootTableIdCondition.builder(ResourceLocation.parse("entities/guardian")).build(),
                        LootItemRandomChanceCondition.randomChance(0.20f).build()}
                        , NTLootTables.GUARDIAN));
        add("shipwreck_modifier",
                new AddItemModifier(new LootItemCondition[]{
                        LootTableIdCondition.builder(ResourceLocation.parse("chests/shipwreck_treasure")).build(),
                        LootItemRandomChanceCondition.randomChance(0.33f).build()}
                        ,crate));
        add("ocean_ruins_modifier",
                new AddItemModifier(new LootItemCondition[]{
                        LootTableIdCondition.builder(ResourceLocation.parse("chests/underwater_ruin_big")).or(LootTableIdCondition.builder(ResourceLocation.parse("chests/underwater_ruin_small"))).build(),
                        LootItemRandomChanceCondition.randomChance(0.33f).build()}
                        ,crate));
        add("suspicious_ruins_sand_modifier",
                new AddItemModifier(new LootItemCondition[]{
                        LootTableIdCondition.builder(ResourceLocation.parse("archaeology/ocean_ruin_warm")).build(),
                        LootItemRandomChanceCondition.randomChance(0.05f).build()}
                        ,crate));

    }
}
