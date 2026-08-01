package com.portingdeadmods.nautec.client.item;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import net.minecraft.client.color.item.ItemTintSource;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public record BacteriaColorTintSource() implements ItemTintSource {
    public static final MapCodec<BacteriaColorTintSource> MAP_CODEC = MapCodec.unit(new BacteriaColorTintSource());

    @Override
    public int calculate(ItemStack stack, ClientLevel level, LivingEntity entity) {
        var component = stack.get(NTDataComponents.BACTERIA);
        if (component == null || level == null) {
            return -1;
        }
        ResourceKey<Bacteria> bacteriaType = component.bacteriaInstance().getBacteria();
        Bacteria bacteria = BacteriaHelper.getBacteria(level.registryAccess(), bacteriaType);
        return bacteria != null ? ARGB.opaque(bacteria.stats().color()) : -1;
    }

    @Override
    public MapCodec<BacteriaColorTintSource> type() {
        return MAP_CODEC;
    }
}
