package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.compat.modonomicon.ModonomiconCompat;
import com.portingdeadmods.nautec.content.items.*;
import com.portingdeadmods.nautec.content.items.tiers.NTArmorMaterials;
import com.portingdeadmods.nautec.content.items.tools.*;
import com.portingdeadmods.nautec.data.NTDataComponents;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.item.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public final class NTItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(Nautec.MODID);
    public static final List<ItemLike> CREATIVE_TAB_ITEMS = new ArrayList<>();
    public static final List<Supplier<BlockItem>> BLOCK_ITEMS = new ArrayList<>();

    public static final Supplier<Item> NAUTEC_GUIDE;

    // MATERIALS
    public static final DeferredItem<Item> AQUARINE_STEEL_INGOT = registerItem("aquarine_steel_ingot",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> ATLANTIC_GOLD_INGOT = registerItem("atlantic_gold_ingot",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> ATLANTIC_GOLD_NUGGET = registerItem("atlantic_gold_nugget",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> PRISMARINE_CRYSTAL_SHARD = registerItem("prismarine_crystal_shard",
            Item::new, new Item.Properties());
    public static final DeferredItem<AirBottleItem> AIR_BOTTLE = registerItem("air_bottle",
            AirBottleItem::new, new Item.Properties().stacksTo(16));
    public static final DeferredItem<Item> AQUARINE_STEEL_COMPOUND = registerItem("aquarine_steel_compound",
            Item::new, new Item.Properties());

    // MACHINE PARTS
    public static final DeferredItem<Item> RUSTY_GEAR = registerItem("rusty_gear",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> GEAR = registerItem("gear",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> ANCIENT_VALVE = registerItem("ancient_valve",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> VALVE = registerItem("valve",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> BROKEN_WHISK = registerItem("broken_whisk",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> WHISK = registerItem("whisk",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> BURNT_COIL = registerItem("burnt_coil",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> LASER_CHANNELING_COIL = registerItem("laser_channeling_coil",
            Item::new, new Item.Properties());


    public static final DeferredItem<Item> CAST_IRON_INGOT = registerItem("cast_iron_ingot", Item::new, new Item.Properties());
    public static final DeferredItem<Item> CAST_IRON_NUGGET = registerItem("cast_iron_nugget", Item::new, new Item.Properties());
    public static final DeferredItem<Item> CAST_IRON_ROD = registerItem("cast_iron_rod", Item::new, new Item.Properties());
    public static final DeferredItem<Item> BROWN_POLYMER = registerItem("brown_polymer", Item::new, new Item.Properties());

    // MOB DROPS
    public static final DeferredItem<Item> DROWNED_LUNGS = registerItem("drowned_lungs",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> DOLPHIN_FIN = registerItem("dolphin_fin",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> GUARDIAN_EYE = registerItem("guardian_eye",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> ELDRITCH_HEART = registerItem("eldritch_heart",
            Item::new, new Item.Properties());

    // Robot Arms
    public static final DeferredItem<RobotArmItem> CLAW_ROBOT_ARM = registerItem("claw_robot_arm",
            RobotArmItem::new, new Item.Properties());
//    public static final DeferredItem<RobotArmItem> SYRINGE_ROBOT_ARM = registerItem("syringe_robot_arm",
//            RobotArmItem::new, new Item.Properties());

    // VIALS
    public static final DeferredItem<Item> GLASS_VIAL = registerItem("glass_vial", Item::new, new Item.Properties());
    public static final DeferredItem<Item> ELECTROLYTE_ALGAE_SERUM_VIAL = registerItem("eas_vial", Item::new, new Item.Properties());

    // ARMOR
    // CURIO ITEMS
    public static final DeferredItem<BatteryItem> PRISMATIC_BATTERY = registerItem("prismatic_battery",
            BatteryItem::new, new Item.Properties().stacksTo(1));
    public static final DeferredItem<PrismMonocleItem> PRISM_MONOCLE = registerItem("prism_monocle",
            PrismMonocleItem::new, new Item.Properties().stacksTo(1));

    public static final DeferredItem<DivingSuitArmorItem> DIVING_HELMET = registerItem("diving_helmet", () -> new DivingSuitArmorItem(NTArmorMaterials.DIVING_SUIT, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredItem<DivingSuitArmorItem> DIVING_CHESTPLATE = registerItem("diving_chestplate", () -> new DivingSuitArmorItem(NTArmorMaterials.DIVING_SUIT, ArmorItem.Type.CHESTPLATE, new Item.Properties().component(NTDataComponents.OXYGEN, 0)));
    public static final DeferredItem<DivingSuitArmorItem> DIVING_LEGGINGS = registerItem("diving_leggings", () -> new DivingSuitArmorItem(NTArmorMaterials.DIVING_SUIT, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredItem<DivingSuitArmorItem> DIVING_BOOTS = registerItem("diving_boots", () -> new DivingSuitArmorItem(NTArmorMaterials.DIVING_SUIT, ArmorItem.Type.BOOTS, new Item.Properties()));

    public static final DeferredItem<AquarineArmorItem> AQUARINE_HELMET = registerItem("aquarine_steel_helmet", () -> new AquarineArmorItem(NTArmorMaterials.AQUARINE_STEEL, ArmorItem.Type.HELMET, new Item.Properties()));
    public static final DeferredItem<AquarineArmorItem> AQUARINE_CHESTPLATE = registerItem("aquarine_steel_chestplate", () -> new AquarineArmorItem(NTArmorMaterials.AQUARINE_STEEL, ArmorItem.Type.CHESTPLATE, new Item.Properties()));
    public static final DeferredItem<AquarineArmorItem> AQUARINE_LEGGINGS = registerItem("aquarine_steel_leggings", () -> new AquarineArmorItem(NTArmorMaterials.AQUARINE_STEEL, ArmorItem.Type.LEGGINGS, new Item.Properties()));
    public static final DeferredItem<AquarineArmorItem> AQUARINE_BOOTS = registerItem("aquarine_steel_boots", () -> new AquarineArmorItem(NTArmorMaterials.AQUARINE_STEEL, ArmorItem.Type.BOOTS, new Item.Properties()));


    // TOOLS 'N WEAPONS
    public static final DeferredItem<NeptunesTridentItem> NEPTUNES_TRIDENT = registerItem("neptunes_trident",
            NeptunesTridentItem::new, new Item.Properties()
                    .attributes(NeptunesTridentItem.createAttributes())
                    .component(DataComponents.TOOL, NeptunesTridentItem.createToolProperties()));

    public static final DeferredItem<AquarineSwordItem> AQUARINE_SWORD = registerItem("aquarine_steel_sword", AquarineSwordItem::new);
    public static final DeferredItem<AquarineAxeItem> AQUARINE_AXE = registerItem("aquarine_steel_axe", AquarineAxeItem::new);
    public static final DeferredItem<AquarineHoeItem> AQUARINE_HOE = registerItem("aquarine_steel_hoe", AquarineHoeItem::new);
    public static final DeferredItem<AquarinePickaxeItem> AQUARINE_PICKAXE = registerItem("aquarine_steel_pickaxe", AquarinePickaxeItem::new);
    public static final DeferredItem<AquarineShovelItem> AQUARINE_SHOVEL = registerItem("aquarine_steel_shovel", AquarineShovelItem::new);

    // BUCKETS
    public static final DeferredItem<BucketItem> SALT_WATER_BUCKET = registerItemBucket("salt_water_bucket",
            () -> new BucketItem(NTFluids.SALT_WATER_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<BucketItem> EAS_BUCKET = registerItemBucket("eas_bucket",
            () -> new BucketItem(NTFluids.EAS_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));
    public static final DeferredItem<BucketItem> ETCHING_ACID_BUCKET = registerItemBucket("etching_acid_bucket",
            () -> new BucketItem(NTFluids.ETCHING_ACID_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    // TOOLS
    public static final DeferredItem<AquarineWrenchItem> AQUARINE_WRENCH = registerItem("aquarine_steel_wrench",
            AquarineWrenchItem::new, new Item.Properties());
    public static final DeferredItem<Item> CROWBAR = registerItem("crowbar",
            Item::new, new Item.Properties().stacksTo(1));

    public static <T extends Item> DeferredItem<T> registerItem(String name, Function<Item.Properties, T> itemConstructor, Item.Properties properties) {
        return registerItem(name, itemConstructor, properties, true);
    }

    private static <T extends Item> DeferredItem<T> registerItemBucket(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static <T extends Item> DeferredItem<T> registerItem(String name, Supplier<T> item) {
        DeferredItem<T> toReturn = ITEMS.register(name, item);
        CREATIVE_TAB_ITEMS.add(toReturn);
        return toReturn;
    }

    public static <T extends Item> DeferredItem<T> registerItem(String name, Function<Item.Properties, T> itemConstructor, Item.Properties properties, boolean addToTab) {
        DeferredItem<T> toReturn = ITEMS.registerItem(name, itemConstructor, properties);
        if (addToTab) {
            CREATIVE_TAB_ITEMS.add(toReturn);
        }
        return toReturn;
    }

    static {
        if (ModList.get().isLoaded("modonomicon")) {
            NAUTEC_GUIDE = ModonomiconCompat.registerItem();
        } else {
            NAUTEC_GUIDE = null;
        }
    }
}
