package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.blockentities.AquaticCatalystBlockEntity;
import com.portingdeadmods.modjam.content.blockentities.PrismarineCrucibleBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MJBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ModJam.MODID);

    public static final Supplier<BlockEntityType<AquaticCatalystBlockEntity>> AQUATIC_CATALYST = BLOCK_ENTITIES.register("aquatic_catalyst",
            () -> BlockEntityType.Builder.of(AquaticCatalystBlockEntity::new,
                    MJBlocks.AQUATIC_CATALYST.get()).build(null));
    public static final Supplier<BlockEntityType<PrismarineCrucibleBlockEntity>> PRISMARINE_CRUCIBLE = BLOCK_ENTITIES.register("prismarine_crucible",
            () -> BlockEntityType.Builder.of(PrismarineCrucibleBlockEntity::new,
                    MJBlocks.PRISMARINE_CRUCIBLE.get()).build(null));
}
