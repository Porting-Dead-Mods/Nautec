package com.portingdeadmods.modjam.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.modjam.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.modjam.api.blocks.blockentities.ContainerBlock;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.NotNull;

public class ExampleBEBlock extends ContainerBlock {
    public ExampleBEBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean tickingEnabled() {
        return false;
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return MJBlockEntityTypes.EXAMPLE_BE.get();
    }

    @Override
    protected @NotNull MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(ExampleBEBlock::new);
    }
}
