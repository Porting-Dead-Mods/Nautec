package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.items.ExamplePowerItem;
import com.portingdeadmods.modjam.content.items.PrismMonocleItem;
import com.portingdeadmods.modjam.tiers.MJArmorMaterials;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class MJItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModJam.MODID);
    public static final List<ItemLike> CREATIVE_TAB_ITEMS = new ArrayList<>();

    public static final DeferredItem<Item> EXAMPLE_POWER_ITEM = registerItem("example_power_item",
            ExamplePowerItem::new, new Item.Properties());
    public static final DeferredItem<PrismMonocleItem> PRISM_MONOCLE = registerItem("prism_monocle",
            PrismMonocleItem::new, new Item.Properties());

    public static <T extends Item> DeferredItem<T> registerItem(String name, Function<Item.Properties, T> itemConstructor, Item.Properties properties) {
        return registerItem(name, itemConstructor, properties, true);
    }

    public static <T extends Item> DeferredItem<T> registerItem(String name, Function<Item.Properties, T> itemConstructor, Item.Properties properties, boolean addToTab) {
        DeferredItem<T> toReturn = ITEMS.registerItem(name, itemConstructor, properties);
        if (addToTab) {
            CREATIVE_TAB_ITEMS.add(toReturn);
        }
        return toReturn;
    }
}
