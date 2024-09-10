package com.portingdeadmods.modjam.utils;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.client.model.MJArmorModel;
import com.portingdeadmods.modjam.exampleCustom3DArmor.TestArmorModel;
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

public class ArmorModelsHandler {

	private static final Map<ModelLayerLocation, Layer> layers = new HashMap<>();
	private static final Map<Pair<ModelLayerLocation, EquipmentSlot>, MJArmorModel> cachedArmors = new HashMap<>();
	
	public static ModelLayerLocation test;

	private static boolean modelsInitted = false;

	private static void initModels() {
		if(modelsInitted)
			return;

		test = addArmorModel("test", TestArmorModel::createLayerDefinition);

		modelsInitted = true;
	}


	private static ModelLayerLocation addArmorModel(String name, Supplier<LayerDefinition> supplier) {
		return addLayer(name, new Layer(supplier, MJArmorModel::new));
	}

	private static ModelLayerLocation addLayer(String name, Layer layer) {
		ModelLayerLocation loc = new ModelLayerLocation(ResourceLocation.fromNamespaceAndPath(ModJam.MODID,name), "main");
		layers.put(loc, layer);
		return loc;
	}


	public static void registerLayers(EntityRenderersEvent.RegisterLayerDefinitions event){
		initModels();
		layers.forEach((loc,layer)->event.registerLayerDefinition(loc,layer.definition));
	}

	public static MJArmorModel armorModel(ModelLayerLocation location, EquipmentSlot slot) {
		Pair<ModelLayerLocation, EquipmentSlot> key = Pair.of(location, slot);
		if(cachedArmors.containsKey(key))
			return cachedArmors.get(key);

		initModels();

		Layer layer = layers.get(location);
		Minecraft mc = Minecraft.getInstance();
		MJArmorModel model = layer.armorModelConstructor.apply(mc.getEntityModels().bakeLayer(location), slot);
		cachedArmors.put(key, model);

		return model;
	}

	private static class Layer {

		final Supplier<LayerDefinition> definition;
		final BiFunction<ModelPart, EquipmentSlot, MJArmorModel> armorModelConstructor;
		
		public Layer(Supplier<LayerDefinition> definition, BiFunction<ModelPart, EquipmentSlot, MJArmorModel> armorModelConstructor) {
			this.definition = definition;
			this.armorModelConstructor = armorModelConstructor;
		}

	}

}
