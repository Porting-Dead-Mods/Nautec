package com.portingdeadmods.nautec.content.blocks;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.api.blockentities.ContainerBlockEntity;
import com.portingdeadmods.nautec.api.blocks.blockentities.LaserBlock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BioReactorBlock extends LaserBlock {
    public BioReactorBlock(Properties properties) {
        super(properties);
    }

    @Override
    public BlockEntityType<? extends ContainerBlockEntity> getBlockEntityType() {
        return NTBlockEntityTypes.BIO_REACTOR.get();
    }

    @Override
    protected MapCodec<? extends BaseEntityBlock> codec() {
        return simpleCodec(BioReactorBlock::new);
    }
}
