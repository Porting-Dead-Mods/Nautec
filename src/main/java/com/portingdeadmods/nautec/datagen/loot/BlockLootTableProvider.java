package com.portingdeadmods.nautec.datagen.loot;

import com.portingdeadmods.nautec.content.blocks.BacterialAnalyzerBlock;
import com.portingdeadmods.nautec.content.blocks.BacterialAnalyzerTopBlock;
import com.portingdeadmods.nautec.content.blocks.multiblock.part.DrainPartBlock;
import com.portingdeadmods.nautec.content.multiblocks.AugmentationStationMultiblock;
import com.portingdeadmods.nautec.content.multiblocks.BioReactorMultiblock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.registries.NTBlocks;
import it.unimi.dsi.fastutil.objects.ReferenceOpenHashSet;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class BlockLootTableProvider extends BlockLootSubProvider {

    private final Set<Block> knownBlocks = new ReferenceOpenHashSet<>();

    public BlockLootTableProvider(HolderLookup.Provider pRegistries) {
        super(Collections.emptySet(), FeatureFlags.VANILLA_SET, pRegistries);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        return knownBlocks;
    }

    @Override
    protected void add(@NotNull Block block, @NotNull LootTable.Builder table) {
        //Overwrite the core register method to add to our list of known blocks
        super.add(block, table);
        knownBlocks.add(block);
    }

    @Override
    protected void generate() {
        // Generate loot tables here
        prismarineSand(NTBlocks.PRISMARINE_SAND.get());
        dropSelf(NTBlocks.AQUATIC_CATALYST.get());
        dropSelf(NTBlocks.PRISMARINE_RELAY.get());
        dropSelf(NTBlocks.LASER_JUNCTION.get());
        dropSelf(NTBlocks.LONG_DISTANCE_LASER.get());
        dropSelf(NTBlocks.AQUARINE_STEEL_BLOCK.get());
        dropSelf(NTBlocks.DARK_PRISMARINE_PILLAR.get());
        dropSelf(NTBlocks.CHISELED_DARK_PRISMARINE.get());
        dropSelf(NTBlocks.POLISHED_PRISMARINE.get());
        dropSelf(NTBlocks.MIXER.get());
        dropSelf(NTBlocks.CHARGER.get());
        dropSelf(NTBlocks.AUGMENTATION_STATION_EXTENSION.get());
        dropSelf(NTBlocks.AUGMENTATION_STATION.get());
        dropSelf(NTBlocks.DRAIN.get());
        dropSelf(NTBlocks.DRAIN_WALL.get());
        add(NTBlocks.AUGMENTATION_STATION_PART.get(), multiblockPartDrop(NTBlocks.AUGMENTATION_STATION_PART.get(), AugmentationStationMultiblock.AS_PART, Map.of(
                0, NTBlocks.AQUARINE_STEEL_BLOCK.get(),
                1, NTBlocks.POLISHED_PRISMARINE.get(),
                2, NTBlocks.AQUARINE_STEEL_BLOCK.get(),
                3, NTBlocks.POLISHED_PRISMARINE.get(),
                4, NTBlocks.AQUARINE_STEEL_BLOCK.get(),
                5, NTBlocks.POLISHED_PRISMARINE.get(),
                6, NTBlocks.AQUARINE_STEEL_BLOCK.get(),
                7, NTBlocks.POLISHED_PRISMARINE.get(),
                8, NTBlocks.AQUARINE_STEEL_BLOCK.get()
        )));
        drainPartDrop(NTBlocks.DRAIN_PART.get());
        dropSelf(NTBlocks.BACTERIAL_CONTAINMENT_SHIELD.get());
        dropSelf(NTBlocks.INCUBATOR.get());
        dropSelf(NTBlocks.MUTATOR.get());
        dropSelf(NTBlocks.BIO_REACTOR.get());
        add(NTBlocks.BIO_REACTOR_PART.get(), createStateDrop(NTBlocks.BIO_REACTOR_PART.get(), BioReactorMultiblock.BIO_REACTOR_PART, Map.of(
                0, NTBlocks.DARK_PRISMARINE_PILLAR.get(),
                1, NTBlocks.BACTERIAL_CONTAINMENT_SHIELD.get(),
                2, NTBlocks.DARK_PRISMARINE_PILLAR.get(),
                3, NTBlocks.BACTERIAL_CONTAINMENT_SHIELD.get(),
                4, NTBlocks.POLISHED_PRISMARINE.get(),
                5, NTBlocks.BACTERIAL_CONTAINMENT_SHIELD.get(),
                6, NTBlocks.DARK_PRISMARINE_PILLAR.get(),
                7, NTBlocks.BACTERIAL_CONTAINMENT_SHIELD.get(),
                8, NTBlocks.DARK_PRISMARINE_PILLAR.get()
        )));
        dropSelf(NTBlocks.BACTERIAL_ANALYZER.get());
        dropSelf(NTBlocks.FISHING_STATION.get());
    }

    protected LootTable.Builder createStateDrop(
            Block block, IntegerProperty property, Map<Integer, Block> drops
    ) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F));

        for (Map.Entry<Integer, Block> entry : drops.entrySet()) {
            builder.add(LootItem.lootTableItem(entry.getValue())
                    .when(
                            LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, entry.getKey()))
                    ));
        }

        return LootTable.lootTable()
                .withPool(
                        this.applyExplosionCondition(
                                block,
                                builder
                        )
                );
    }


    protected <T extends Comparable<T> & StringRepresentable> LootTable.Builder createStateDrop(
            Block block, Property<T> property, Map<T, Block> drops
    ) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F));

        for (Map.Entry<T, Block> entry : drops.entrySet()) {
            builder.add(LootItem.lootTableItem(entry.getValue())
                    .when(
                            LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(property, entry.getKey()))
                    ));
        }

        return LootTable.lootTable()
                .withPool(
                        this.applyExplosionCondition(
                                block,
                                builder
                        )
                );
    }


    protected void prismarineSand(Block prismarineSandOre) {
        HolderLookup.RegistryLookup<Enchantment> registrylookup = this.registries.lookupOrThrow(Registries.ENCHANTMENT);
        add(prismarineSandOre, this.createSilkTouchDispatchTable(prismarineSandOre, this.applyExplosionDecay(prismarineSandOre, LootItem.lootTableItem(Items.PRISMARINE_CRYSTALS)
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(2.0F, 4.0F)))
                .apply(ApplyBonusCount.addUniformBonusCount(registrylookup.getOrThrow(Enchantments.FORTUNE)))
        )).withPool(LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1f))
                .add(LootItem.lootTableItem(Items.PRISMARINE_SHARD))
                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1.0F, 3.0F)))));
    }

    protected LootTable.Builder multiblockPartDrop(
            Block block, IntegerProperty partIndex, Map<Integer, Block> index2blocks
    ) {
        LootPool.Builder builder = LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F));
        Integer last = partIndex.getPossibleValues().stream().toList().getLast();
        for (int i = 0; i < last; i++) {
            builder.add(LootItem.lootTableItem(index2blocks.get(i))
                    .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                            .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(partIndex, i))));
        }
        return LootTable.lootTable()
                .withPool(
                        this.applyExplosionCondition(
                                block,
                                builder
                        )
                );
    }

    protected LootTable.Builder drainPartDrop(
            Block block
    ) {
        LootPool.lootPool()
                .setRolls(ConstantValue.exactly(1.0F));
        return LootTable.lootTable()
                .withPool(
                        this.applyExplosionCondition(
                                block,
                                LootPool.lootPool()
                                        .setRolls(ConstantValue.exactly(1.0F)).add(LootItem.lootTableItem(block)
                                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(DrainPartBlock.TOP, false))))
                        )
                );
    }

}
