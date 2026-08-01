package com.portingdeadmods.nautec.content.items.tiers;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.tags.NTTags;
import net.minecraft.util.Util;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.equipment.ArmorMaterial;
import net.minecraft.world.item.equipment.ArmorType;
import net.minecraft.world.item.equipment.EquipmentAsset;
import net.minecraft.world.item.equipment.EquipmentAssets;

import java.util.EnumMap;

public final class NTArmorMaterials {
    public static final ResourceKey<EquipmentAsset> PRISMARINE_ASSET = assetKey("prismarine");
    public static final ResourceKey<EquipmentAsset> DIVING_SUIT_ASSET = assetKey("diving_suit");
    public static final ResourceKey<EquipmentAsset> DIVING_SUIT_HELMET_ASSET = assetKey("diving_suit_helmet");
    public static final ResourceKey<EquipmentAsset> AQUARINE_STEEL_ASSET = assetKey("aquarine_steel");

    public static final ArmorMaterial PRISMARINE = new ArmorMaterial(
            5,
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 0);
                map.put(ArmorType.LEGGINGS, 0);
                map.put(ArmorType.CHESTPLATE, 0);
                map.put(ArmorType.HELMET, 2);
                map.put(ArmorType.BODY, 2);
            }),
            10,
            SoundEvents.ARMOR_EQUIP_ELYTRA,
            0,
            0,
            NTTags.Items.REPAIRS_PRISMARINE_ARMOR,
            PRISMARINE_ASSET
    );

    public static final ArmorMaterial DIVING_SUIT = new ArmorMaterial(
            20,
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 2);
                map.put(ArmorType.LEGGINGS, 4);
                map.put(ArmorType.CHESTPLATE, 5);
                map.put(ArmorType.HELMET, 3);
                map.put(ArmorType.BODY, 4);
            }),
            10,
            SoundEvents.ARMOR_EQUIP_LEATHER,
            1,
            0.05f,
            NTTags.Items.REPAIRS_DIVING_SUIT,
            DIVING_SUIT_ASSET
    );

    public static final ArmorMaterial AQUARINE_STEEL = new ArmorMaterial(
            5,
            Util.make(new EnumMap<>(ArmorType.class), map -> {
                map.put(ArmorType.BOOTS, 3);
                map.put(ArmorType.LEGGINGS, 6);
                map.put(ArmorType.CHESTPLATE, 7);
                map.put(ArmorType.HELMET, 3);
                map.put(ArmorType.BODY, 4);
            }),
            10,
            SoundEvents.ARMOR_EQUIP_IRON,
            1,
            0.05f,
            NTTags.Items.REPAIRS_AQUARINE_ARMOR,
            AQUARINE_STEEL_ASSET
    );

    private static ResourceKey<EquipmentAsset> assetKey(String name) {
        return ResourceKey.create(EquipmentAssets.ROOT_ID, Nautec.rl(name));
    }
}
