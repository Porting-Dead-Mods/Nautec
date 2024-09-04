package com.portingdeadmods.modjam.datagen;

import com.portingdeadmods.modjam.ModJam;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class BlockModelProvider extends BlockStateProvider {
    public BlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ModJam.MODID, existingFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        // Register block models here
    }
}