package com.portingdeadmods.nautec.content.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.util.valueproviders.IntProviders;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class PrismarineSandBlock extends FallingBlock {
    public static final MapCodec<PrismarineSandBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            IntProviders.codec(0, 10).fieldOf("experience").forGetter(block -> block.xpRange),
            propertiesCodec()).apply(builder, PrismarineSandBlock::new)
    );

    private final IntProvider xpRange;

    public PrismarineSandBlock(IntProvider xpRange, Properties properties) {
        super(properties);
        this.xpRange = xpRange;
    }

    @Override
    protected void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
    }

    // Neo: Patch-in override for getExpDrop. Original vanilla logic passes this.xpRange to tryDropExperience.
    @Override
    public int getExpDrop(BlockState state, LevelAccessor level, BlockPos pos,
                          @Nullable BlockEntity blockEntity,
                          @Nullable Entity breaker, ItemStack tool) {
        return this.xpRange.sample(level.getRandom());
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }

    @Override
    public int getDustColor(BlockState state, BlockGetter level, BlockPos pos) {
        return -16777216;
    }
}
