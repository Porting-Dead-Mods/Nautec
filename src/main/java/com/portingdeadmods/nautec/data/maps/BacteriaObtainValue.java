package com.portingdeadmods.nautec.data.maps;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;

public record BacteriaObtainValue(ResourceKey<Bacteria> bacteria, TagKey<Biome> biome, float chance) {
    public static final Codec<BacteriaObtainValue> CODEC = RecordCodecBuilder.create(inst -> inst.group(
            ResourceKey.codec(NTRegistries.BACTERIA_KEY).fieldOf("bacteria").forGetter(BacteriaObtainValue::bacteria),
            TagKey.codec(Registries.BIOME).fieldOf("biome").forGetter(BacteriaObtainValue::biome),
            Codec.FLOAT.fieldOf("chance").forGetter(BacteriaObtainValue::chance)
    ).apply(inst, BacteriaObtainValue::new));
}
