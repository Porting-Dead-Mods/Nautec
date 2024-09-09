package com.portingdeadmods.modjam.registries;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.structures.Ruins1;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class MJStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, ModJam.MODID);

    public static DeferredHolder<StructureType<?>, StructureType<Ruins1>> RUINS_1 = STRUCTURES.register("ruins_1", () -> explicitStructureTypeTyping(Ruins1.CODEC));

    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(MapCodec<T> structureCodec) {
        return () -> structureCodec;
    }
}
