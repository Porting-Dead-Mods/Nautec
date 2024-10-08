/*
 * 3d armor rendering is mostly from quark
 * Thank you to Vazkii, VioletMoon and all
 * contributors of quark <3
 */

package com.portingdeadmods.nautec.utils;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.model.armor.NTArmorModel;
import com.portingdeadmods.nautec.client.model.armor.DivingArmorModel;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Supplier;

public final class ArmorModelsHandler {

    private static final Map<ModelLayerLocation, Layer> LAYERS = new HashMap<>();
    private static final Map<Pair<ModelLayerLocation, EquipmentSlot>, NTArmorModel> CACHED_ARMORS = new HashMap<>();

    public static ModelLayerLocation divingSuit;

    private static boolean modelsInitted = false;

    private static void initModels() {
        if (modelsInitted)
            return;

        divingSuit = addArmorModel("diving_suit", DivingArmorModel::createBodyLayer);

        modelsInitted = true;
    }

    private static ModelLayerLocation addArmorModel(String name, Supplier<LayerDefinition> supplier) {
        return addLayer(name, new Layer(supplier, NTArmorModel::new));
    }

    private static ModelLayerLocation addLayer(String name, Layer layer) {
        ModelLayerLocation loc = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, name), "main");
        LAYERS.put(loc, layer);
        return loc;
    }

    public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        initModels();
        LAYERS.forEach((loc, layer) -> event.registerLayerDefinition(loc, layer.definition));
    }

    public static NTArmorModel armorModel(ModelLayerLocation location, EquipmentSlot slot) {
        Pair<ModelLayerLocation, EquipmentSlot> key = Pair.of(location, slot);
        if (CACHED_ARMORS.containsKey(key))
            return CACHED_ARMORS.get(key);

        initModels();

        Layer layer = LAYERS.get(location);
        Minecraft mc = Minecraft.getInstance();
        NTArmorModel model = layer.armorModelConstructor.apply(mc.getEntityModels().bakeLayer(location), slot);
        CACHED_ARMORS.put(key, model);

        return model;
    }

    private record Layer(Supplier<LayerDefinition> definition,
                         BiFunction<ModelPart, EquipmentSlot, NTArmorModel> armorModelConstructor) {
    }

}
