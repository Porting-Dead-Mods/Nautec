package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.worldgen.NTBiomeKeys;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.TagAppender;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.BiomeTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.biome.Biome;
import net.neoforged.neoforge.common.Tags;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.CompletableFuture;

public class BiomeTagProvider extends TagsProvider<Biome> {
    public static final TagKey<Biome> ABYSSAL = modTag("abyssal");
    public static final TagKey<Biome> VENTS = modTag("vents");
    public static final TagKey<Biome> BIOLUMINESCENT = modTag("bioluminescent");
    public static final TagKey<Biome> REEF = modTag("reef");

    public BiomeTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, Registries.BIOME, registries, Nautec.MODID);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        tag(BiomeTags.IS_OCEAN).add(NTBiomeKeys.ABYSSAL_TRENCH, NTBiomeKeys.BIOLUMINESCENT_GROVE,
                NTBiomeKeys.HYDROTHERMAL_VENTS, NTBiomeKeys.PRISMARINE_REEF);
        tag(BiomeTags.IS_OVERWORLD).add(NTBiomeKeys.ABYSSAL_TRENCH, NTBiomeKeys.BIOLUMINESCENT_GROVE,
                NTBiomeKeys.HYDROTHERMAL_VENTS, NTBiomeKeys.PRISMARINE_REEF);
        tag(BiomeTags.IS_DEEP_OCEAN).add(NTBiomeKeys.ABYSSAL_TRENCH, NTBiomeKeys.BIOLUMINESCENT_GROVE,
                NTBiomeKeys.HYDROTHERMAL_VENTS);

        tag(Tags.Biomes.IS_OCEAN).add(NTBiomeKeys.ABYSSAL_TRENCH, NTBiomeKeys.BIOLUMINESCENT_GROVE,
                NTBiomeKeys.HYDROTHERMAL_VENTS, NTBiomeKeys.PRISMARINE_REEF);
        tag(Tags.Biomes.IS_AQUATIC).add(NTBiomeKeys.ABYSSAL_TRENCH, NTBiomeKeys.BIOLUMINESCENT_GROVE,
                NTBiomeKeys.HYDROTHERMAL_VENTS, NTBiomeKeys.PRISMARINE_REEF);
        tag(Tags.Biomes.IS_DEEP_OCEAN).add(NTBiomeKeys.ABYSSAL_TRENCH, NTBiomeKeys.BIOLUMINESCENT_GROVE,
                NTBiomeKeys.HYDROTHERMAL_VENTS);
        tag(Tags.Biomes.IS_SHALLOW_OCEAN).add(NTBiomeKeys.PRISMARINE_REEF);
        tag(Tags.Biomes.IS_COLD_OVERWORLD).add(NTBiomeKeys.ABYSSAL_TRENCH);
        tag(Tags.Biomes.IS_HOT_OVERWORLD).add(NTBiomeKeys.HYDROTHERMAL_VENTS, NTBiomeKeys.PRISMARINE_REEF);

        tag(BiomeTags.HAS_SHIPWRECK).add(NTBiomeKeys.ABYSSAL_TRENCH, NTBiomeKeys.BIOLUMINESCENT_GROVE,
                NTBiomeKeys.HYDROTHERMAL_VENTS, NTBiomeKeys.PRISMARINE_REEF);
        tag(BiomeTags.HAS_OCEAN_MONUMENT).add(NTBiomeKeys.BIOLUMINESCENT_GROVE, NTBiomeKeys.HYDROTHERMAL_VENTS);
        tag(BiomeTags.REQUIRED_OCEAN_MONUMENT_SURROUNDING).add(NTBiomeKeys.ABYSSAL_TRENCH, NTBiomeKeys.BIOLUMINESCENT_GROVE,
                NTBiomeKeys.HYDROTHERMAL_VENTS, NTBiomeKeys.PRISMARINE_REEF);
        tag(BiomeTags.HAS_OCEAN_RUIN_COLD).add(NTBiomeKeys.ABYSSAL_TRENCH, NTBiomeKeys.BIOLUMINESCENT_GROVE);
        tag(BiomeTags.HAS_OCEAN_RUIN_WARM).add(NTBiomeKeys.HYDROTHERMAL_VENTS, NTBiomeKeys.PRISMARINE_REEF);

        tag(ABYSSAL).add(NTBiomeKeys.ABYSSAL_TRENCH);
        tag(VENTS).add(NTBiomeKeys.HYDROTHERMAL_VENTS);
        tag(BIOLUMINESCENT).add(NTBiomeKeys.BIOLUMINESCENT_GROVE);
        tag(REEF).add(NTBiomeKeys.PRISMARINE_REEF);
    }

    private TagAppender<ResourceKey<Biome>, Biome> tag(TagKey<Biome> tag) {
        return TagAppender.forBuilder(getOrCreateRawBuilder(tag));
    }

    private static TagKey<Biome> modTag(String name) {
        return TagKey.create(Registries.BIOME, Nautec.rl(name));
    }
}
