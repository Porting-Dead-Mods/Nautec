package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.blockentities.ExampleBE;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MJBlockEntityTypes {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, ModJam.MODID);

    public static final Supplier<BlockEntityType<ExampleBE>> EXAMPLE_BE = BLOCK_ENTITIES.register("example_be",
            () -> BlockEntityType.Builder.of(ExampleBE::new,
                    MJBlocks.EXAMPLE_BE.get()).build(null));
}
