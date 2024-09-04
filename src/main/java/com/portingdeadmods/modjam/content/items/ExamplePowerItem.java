package com.portingdeadmods.modjam.content.items;

import com.portingdeadmods.modjam.api.items.IPowerItem;
import com.portingdeadmods.modjam.data.MJDataComponents;
import com.portingdeadmods.modjam.data.components.ComponentPowerStorage;
import net.minecraft.world.item.Item;

public class ExamplePowerItem extends Item implements IPowerItem {
    public ExamplePowerItem(Properties properties) {
        super(properties.component(MJDataComponents.POWER, ComponentPowerStorage.EMPTY));
    }
}
