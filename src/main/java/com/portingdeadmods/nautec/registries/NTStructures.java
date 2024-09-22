package com.portingdeadmods.nautec.registries;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.structures.Deepslate_crystal_geode;
import com.portingdeadmods.nautec.content.structures.Stone_crystal_geode;
import com.portingdeadmods.nautec.content.structures.Ruins1;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class NTStructures {
    public static final DeferredRegister<StructureType<?>> STRUCTURES = DeferredRegister.create(Registries.STRUCTURE_TYPE, Nautec.MODID);

    public static DeferredHolder<StructureType<?>, StructureType<Ruins1>> RUINS_1 = STRUCTURES.register("ruins_1", () -> explicitStructureTypeTyping(Ruins1.CODEC));
    public static DeferredHolder<StructureType<?>, StructureType<Stone_crystal_geode>> STONE_CRYSTAL_GEODE = STRUCTURES.register("stone_crystal_geode", () -> explicitStructureTypeTyping(Stone_crystal_geode.CODEC));
    public static DeferredHolder<StructureType<?>, StructureType<Deepslate_crystal_geode>> DEEPSLATE_CRYSTAL_GEODE = STRUCTURES.register("deepslate_crystal_geode", () -> explicitStructureTypeTyping(Deepslate_crystal_geode.CODEC));

    private static <T extends Structure> StructureType<T> explicitStructureTypeTyping(MapCodec<T> structureCodec) {
        return () -> structureCodec;
    }
}
