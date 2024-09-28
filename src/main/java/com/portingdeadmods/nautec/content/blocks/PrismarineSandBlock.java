package com.portingdeadmods.nautec.content.blocks;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.ColorRGBA;
import net.minecraft.util.valueproviders.IntProvider;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.ColoredFallingBlock;
import net.minecraft.world.level.block.FallingBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class PrismarineSandBlock extends FallingBlock {
    public static final MapCodec<PrismarineSandBlock> CODEC = RecordCodecBuilder.mapCodec(builder -> builder.group(
            IntProvider.codec(0, 10).fieldOf("experience").forGetter(block -> block.xpRange),
            propertiesCodec()).apply(builder, PrismarineSandBlock::new)
    );

    private final IntProvider xpRange;

    public PrismarineSandBlock(IntProvider xpRange, Properties properties) {
        super(properties);
        this.xpRange = xpRange;
    }

    /**
     * Perform side-effects from block dropping, such as creating silverfish
     */
    @Override
    protected void spawnAfterBreak(BlockState state, ServerLevel level, BlockPos pos, ItemStack stack, boolean dropExperience) {
        super.spawnAfterBreak(state, level, pos, stack, dropExperience);
    }

    // Neo: Patch-in override for getExpDrop. Original vanilla logic passes this.xpRange to tryDropExperience.
    @Override
    public int getExpDrop(BlockState state, net.minecraft.world.level.LevelAccessor level, BlockPos pos,
                          @org.jetbrains.annotations.Nullable net.minecraft.world.level.block.entity.BlockEntity blockEntity,
                          @org.jetbrains.annotations.Nullable net.minecraft.world.entity.Entity breaker, ItemStack tool) {
        return this.xpRange.sample(level.getRandom());
    }

    @Override
    protected MapCodec<? extends FallingBlock> codec() {
        return CODEC;
    }
}
