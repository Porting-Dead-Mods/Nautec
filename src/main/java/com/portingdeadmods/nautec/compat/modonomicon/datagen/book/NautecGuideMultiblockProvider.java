package com.portingdeadmods.nautec.compat.modonomicon.datagen.book;

import com.klikli_dev.modonomicon.api.datagen.MultiblockProvider;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.registries.NTBlocks;
import net.minecraft.data.PackOutput;

public class NautecGuideMultiblockProvider extends MultiblockProvider {
    public NautecGuideMultiblockProvider(PackOutput packOutput) {
        super(packOutput, Nautec.MODID);
    }

    @Override
    public void buildMultiblocks() {
        this.add(modLoc("drain"), new MultiblockProvider.DenseMultiblockBuilder()
                .layer(
                        "WWW",
                        "W0W",
                        "WWW"
                )
                .block('W', NTBlocks.DRAIN_WALL)
                .block('0', NTBlocks.DRAIN).build(false));
    }
}
