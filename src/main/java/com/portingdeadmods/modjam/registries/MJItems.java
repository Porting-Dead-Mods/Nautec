package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.items.AugmentDebugItem;
import com.portingdeadmods.modjam.content.items.PrismMonocleItem;
import com.portingdeadmods.modjam.content.items.AquarineWrenchItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public final class MJItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModJam.MODID);
    public static final List<ItemLike> CREATIVE_TAB_ITEMS = new ArrayList<>();
    public static final List<Supplier<BlockItem>> BLOCK_ITEMS = new ArrayList<>();

    public static final DeferredItem<Item> AQUARINE_STEEL_INGOT = registerItem("aquarine_steel_ingot",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> ATLANTIC_GOLD_INGOT = registerItem("atlantic_gold_ingot",
            Item::new, new Item.Properties());

    public static final DeferredItem<Item> RUSTY_GEAR = registerItem("rusty_gear",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> GEAR = registerItem("gear",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> ANCIENT_VALVE = registerItem("ancient_valve",
            Item::new, new Item.Properties());
    public static final DeferredItem<Item> VALVE = registerItem("valve",
            Item::new, new Item.Properties());

    public static final DeferredItem<Item> GLASS_VIAL = registerItem("glass_vial", Item::new, new Item.Properties());
    public static final DeferredItem<Item> ELECTROLYTE_ALGAE_SERUM_VIAL = registerItem("eas_vial", Item::new, new Item.Properties());

    public static final DeferredItem<PrismMonocleItem> PRISM_MONOCLE = registerItem("prism_monocle",
            PrismMonocleItem::new, new Item.Properties());

    public static final DeferredItem<BucketItem> SALT_WATER_BUCKET = registerItemBucket("salt_water_bucket",
            () -> new BucketItem(MJFluids.SALT_WATER_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final DeferredItem<AugmentDebugItem> AUGMENT_DEBUG_ITEM = registerItem("augment_debug_item", AugmentDebugItem::new, new Item.Properties());

    public static final DeferredItem<AquarineWrenchItem> AQUARINE_WRENCH = registerItem("aquarine_steel_wrench",
            AquarineWrenchItem::new, new Item.Properties());

    public static final DeferredItem<BucketItem> EAS_BUCKET = registerItemBucket("eas_bucket",
            () -> new BucketItem(MJFluids.EAS_SOURCE.get(), new Item.Properties().craftRemainder(Items.BUCKET).stacksTo(1)));

    public static final DeferredItem<Item> CROWBAR = registerItem("crowbar",Item::new,new Item.Properties().stacksTo(1));


    public static <T extends Item> DeferredItem<T> registerItem(String name, Function<Item.Properties, T> itemConstructor, Item.Properties properties) {
        return registerItem(name, itemConstructor, properties, true);
    }

    private static <T extends Item> DeferredItem<T> registerItemBucket(String name, Supplier<T> item) {
        return ITEMS.register(name, item);
    }

    public static <T extends Item> DeferredItem<T> registerItem(String name, Function<Item.Properties, T> itemConstructor, Item.Properties properties, boolean addToTab) {
        DeferredItem<T> toReturn = ITEMS.registerItem(name, itemConstructor, properties);
        if (addToTab) {
            CREATIVE_TAB_ITEMS.add(toReturn);
        }
        return toReturn;
    }
}
