package com.portingdeadmods.modjam.content.items;

import com.portingdeadmods.modjam.api.items.IFluidItem;
import com.portingdeadmods.modjam.data.MJDataComponents;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.fluids.SimpleFluidContent;

public class VialItem extends Item implements IFluidItem {
    public VialItem(Properties properties) {
        super(properties.component(MJDataComponents.FLUID.get(), SimpleFluidContent.EMPTY));
    }

    @Override
    public int getFluidCapacity() {
        return 100;
    }
}
