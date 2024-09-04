package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.items.ExamplePowerItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class MJItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ModJam.MODID);

    public static final DeferredItem<Item> EXAMPLE_POWER_ITEM = ITEMS.register("example_power_item",
            () -> new ExamplePowerItem(new Item.Properties()));
}
