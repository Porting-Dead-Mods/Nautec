package com.portingdeadmods.nautec.datagen;

import com.google.common.hash.Hashing;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.items.tiers.NTArmorMaterials;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.nio.file.Path;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

public class NTEquipmentAssetProvider extends EquipmentAssetProvider {
    private static final Map<String, String> TEXTURE_COPIES = Map.of(
            "/assets/minecraft/textures/models/armor/prismarine_layer_1.png", "humanoid/prismarine.png",
            "/assets/minecraft/textures/models/armor/prismarine_layer_2.png", "humanoid_leggings/prismarine.png",
            "/assets/minecraft/textures/models/armor/diving_suit_layer_1.png", "humanoid/diving_suit.png",
            "/assets/minecraft/textures/models/armor/diving_suit_layer_2.png", "humanoid_leggings/diving_suit.png",
            "/assets/minecraft/textures/models/armor/aquarine_steel_layer_1.png", "humanoid/aquarine_steel.png",
            "/assets/minecraft/textures/models/armor/aquarine_steel_layer_2.png", "humanoid_leggings/aquarine_steel.png",
            "/assets/nautec/textures/example/diving_suit.png", "humanoid/diving_suit_helmet.png"
    );

    private final Path textureOutput;

    public NTEquipmentAssetProvider(PackOutput output) {
        super(output);
        this.textureOutput = output.getOutputFolder(PackOutput.Target.RESOURCE_PACK)
                .resolve(Nautec.MODID).resolve("textures").resolve("entity").resolve("equipment");
    }

    @Override
    protected void registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        output.accept(NTArmorMaterials.PRISMARINE_ASSET, EquipmentClientInfo.builder()
                .addHumanoidLayers(Nautec.rl("prismarine"))
                .build());
        output.accept(NTArmorMaterials.DIVING_SUIT_ASSET, EquipmentClientInfo.builder()
                .addHumanoidLayers(Nautec.rl("diving_suit"))
                .build());
        output.accept(NTArmorMaterials.AQUARINE_STEEL_ASSET, EquipmentClientInfo.builder()
                .addHumanoidLayers(Nautec.rl("aquarine_steel"))
                .build());
        output.accept(ResourceKey.create(EquipmentAssets.ROOT_ID, Nautec.rl("diving_suit_helmet")), EquipmentClientInfo.builder()
                .addLayers(EquipmentClientInfo.LayerType.HUMANOID, new EquipmentClientInfo.Layer(Nautec.rl("diving_suit_helmet")))
                .build());
    }

    @Override
    public CompletableFuture<?> run(CachedOutput cache) {
        return CompletableFuture.allOf(super.run(cache), copyTextures(cache));
    }

    private CompletableFuture<?> copyTextures(CachedOutput cache) {
        return CompletableFuture.runAsync(() -> TEXTURE_COPIES.forEach((source, target) -> {
            try (InputStream stream = NTEquipmentAssetProvider.class.getResourceAsStream(source)) {
                if (stream == null) {
                    throw new IllegalStateException("Missing armor texture " + source);
                }
                byte[] data = stream.readAllBytes();
                cache.writeIfNeeded(textureOutput.resolve(target), data, Hashing.sha1().hashBytes(data));
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }));
    }

    @Override
    public String getName() {
        return "Nautec Equipment Asset Definitions";
    }
}
