package com.portingdeadmods.modjam.datagen;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.registries.MJBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockModelProvider extends BlockStateProvider {
    public BlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ModJam.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        axisBlock(MJBlocks.DARK_PRISMARINE_PILLAR.get());
        simpleBlock(MJBlocks.CHISELED_DARK_PRISMARINE.get());
        simpleBlock(MJBlocks.AQUATIC_CATALYST.get());
    }
}