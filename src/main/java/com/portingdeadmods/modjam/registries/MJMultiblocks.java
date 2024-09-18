package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.MJRegistries;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.multiblocks.Multiblock;
import com.portingdeadmods.modjam.content.multiblocks.AugmentationStationMultiblock;
import com.portingdeadmods.modjam.content.multiblocks.DrainMultiblock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MJMultiblocks {
    public static final DeferredRegister<Multiblock> MULTIBLOCKS = DeferredRegister.create(MJRegistries.MULTIBLOCK, ModJam.MODID);

    public static final Supplier<DrainMultiblock> DRAIN = MULTIBLOCKS.register("drain",
            DrainMultiblock::new);
    public static final Supplier<AugmentationStationMultiblock> AUGMENTATION_STATION = MULTIBLOCKS.register("augmentation_station",
            AugmentationStationMultiblock::new);
}
