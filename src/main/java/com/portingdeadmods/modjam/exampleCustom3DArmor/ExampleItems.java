package com.portingdeadmods.modjam.exampleCustom3DArmor;

import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.portingdeadmods.modjam.registries.MJItems.registerItem;

public class ExampleItems {

    public static final DeferredItem<TestItem> TEST = registerItem("test",
            TestItem::new, new Item.Properties());
    public static final DeferredItem<TestChestItem> TEST_CHEST = registerItem("test_chest",
            TestChestItem::new, new Item.Properties());
}
