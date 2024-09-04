package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.items.ExamplePowerItem;
import com.portingdeadmods.modjam.content.items.PrismMonocleItem;
import com.portingdeadmods.modjam.tiers.MJArmorMaterials;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class MJItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModJam.MODID);

    public static final DeferredItem<Item> EXAMPLE_POWER_ITEM = ITEMS.register("example_power_item",
            () -> new ExamplePowerItem(new Item.Properties()));
    public static final DeferredItem<Item> PRISM_MONOCLE = ITEMS.register("prism_monocle",
            () -> new PrismMonocleItem(MJArmorMaterials.PRISMARINE, new Item.Properties()));
}
