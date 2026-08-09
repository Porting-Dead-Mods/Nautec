package com.portingdeadmods.nautec.gametest.suite;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTEntities;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntitySpawnReason;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.AmethystClusterBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;

public final class ContentIntegrityTests {
    public static void register(NTTestRegistrar r) {
        r.add("content/every_item_has_a_model", 5, helper -> {
            List<String> missing = new ArrayList<>();
            for (Item item : BuiltInRegistries.ITEM) {
                Identifier id = BuiltInRegistries.ITEM.getKey(item);
                if (!id.getNamespace().equals(Nautec.MODID)) continue;
                if (item.getDefaultInstance().isEmpty()) {
                    missing.add(id.toString());
                }
            }
            if (!missing.isEmpty()) {
                helper.fail("Items that produce an empty stack: " + String.join(", ", missing));
            }
            helper.succeed();
        });

        r.add("content/mobs_can_spawn_in_water", 40, 1, helper -> {
            ServerLevel level = helper.getLevel();
            BlockPos pool = helper.absolutePos(new BlockPos(4, 2, 4));
            for (int x = -1; x <= 1; x++) {
                for (int z = -1; z <= 1; z++) {
                    for (int y = 0; y <= 2; y++) {
                        level.setBlockAndUpdate(pool.offset(x, y, z), Blocks.WATER.defaultBlockState());
                    }
                }
            }

            List<EntityType<?>> types = List.of(
                    NTEntities.SILT_SKIPPER.get(), NTEntities.LANTERN_JELLY.get(),
                    NTEntities.VENT_CRAWLER.get(), NTEntities.ABYSSAL_MAW.get());

            for (EntityType<?> type : types) {
                Entity entity = type.spawn(level, pool, EntitySpawnReason.SPAWN_ITEM_USE);
                if (entity == null) {
                    helper.fail("Could not spawn " + BuiltInRegistries.ENTITY_TYPE.getKey(type));
                    return;
                }
                entity.discard();
            }
            helper.succeed();
        });

        r.add("content/budding_prismarine_grows_clusters", 60, 1, helper -> {
            ServerLevel level = helper.getLevel();
            BlockPos budding = helper.absolutePos(new BlockPos(2, 2, 2));
            level.setBlockAndUpdate(budding, NTBlocks.BUDDING_PRISMARINE.get().defaultBlockState());
            for (Direction direction : Direction.values()) {
                level.setBlockAndUpdate(budding.relative(direction), Blocks.WATER.defaultBlockState());
            }

            BlockState budState = NTBlocks.SMALL_PRISMARINE_BUD.get().defaultBlockState()
                    .setValue(AmethystClusterBlock.FACING, Direction.UP);
            BlockPos budPos = budding.above();
            level.setBlockAndUpdate(budPos, budState);

            for (int attempt = 0; attempt < 400; attempt++) {
                NTBlocks.BUDDING_PRISMARINE.get().defaultBlockState()
                        .randomTick(level, budding, level.getRandom());
                if (level.getBlockState(budPos).is(NTBlocks.MEDIUM_PRISMARINE_BUD.get())) {
                    helper.succeed();
                    return;
                }
            }
            helper.fail("Budding Prismarine never advanced a Small Prismarine Bud to the next stage");
        });

        r.add("content/every_augment_is_installable", 5, helper -> {
            List<String> slotless = new ArrayList<>();
            for (com.portingdeadmods.nautec.api.augments.AugmentType<?> type : com.portingdeadmods.nautec.NTRegistries.AUGMENT_TYPE) {
                if (type.getAugmentSlots().isEmpty()) {
                    slotless.add(String.valueOf(com.portingdeadmods.nautec.NTRegistries.AUGMENT_TYPE.getKey(type)));
                }
            }
            if (!slotless.isEmpty()) {
                helper.fail("Augments with no compatible slot cannot ever be applied: " + String.join(", ", slotless));
            }
            helper.succeed();
        });

        r.add("content/vent_carapace_modifiers_are_reversible", 5, helper -> {
            ServerLevel level = helper.getLevel();
            net.minecraft.world.entity.player.Player player = helper.makeMockPlayer(net.minecraft.world.level.GameType.SURVIVAL);
            net.minecraft.world.entity.ai.attributes.AttributeInstance armor =
                    player.getAttribute(net.minecraft.world.entity.ai.attributes.Attributes.ARMOR);
            if (armor == null) {
                helper.fail("Player has no armor attribute");
                return;
            }

            double before = armor.getValue();
            com.portingdeadmods.nautec.content.augments.VentCarapaceAugment augment =
                    new com.portingdeadmods.nautec.content.augments.VentCarapaceAugment(
                            com.portingdeadmods.nautec.registries.NTAugmentSlots.BODY.get());

            augment.onAdded(player);
            double during = armor.getValue();
            augment.onAdded(player);
            if (armor.getValue() != during) {
                helper.fail("Applying Vent Carapace twice stacked its armour modifier");
            }
            if (during <= before) {
                helper.fail("Vent Carapace did not raise armour: " + before + " -> " + during);
            }

            augment.onRemoved(player);
            if (armor.getValue() != before) {
                helper.fail("Vent Carapace left its armour modifier behind after removal");
            }
            helper.succeed();
        });

        r.add("content/prismarine_cluster_drops_shards", 5, helper -> {
            Identifier lootTable = NTBlocks.PRISMARINE_CLUSTER.get().getLootTable()
                    .map(key -> key.identifier()).orElse(null);
            if (lootTable == null) {
                helper.fail("Prismarine Cluster has no loot table");
                return;
            }
            if (helper.getLevel().getServer().reloadableRegistries().getLootTable(
                    net.minecraft.resources.ResourceKey.create(net.minecraft.core.registries.Registries.LOOT_TABLE, lootTable)) == null) {
                helper.fail("Prismarine Cluster loot table " + lootTable + " does not resolve");
            }
            if (NTItems.PRISMARINE_CRYSTAL_SHARD.get().getDefaultInstance().isEmpty()) {
                helper.fail("Prismarine Crystal Shard is missing");
            }
            helper.succeed();
        });
    }

    private ContentIntegrityTests() {
    }
}
