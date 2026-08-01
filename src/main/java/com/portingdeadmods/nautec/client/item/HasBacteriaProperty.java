package com.portingdeadmods.nautec.client.item;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record HasBacteriaProperty() implements ConditionalItemModelProperty {
    public static final MapCodec<HasBacteriaProperty> MAP_CODEC = MapCodec.unit(new HasBacteriaProperty());

    @Override
    public boolean get(ItemStack stack, ClientLevel level, LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        return NTDataComponentsUtils.hasBacteria(stack) > 0;
    }

    @Override
    public MapCodec<HasBacteriaProperty> type() {
        return MAP_CODEC;
    }
}
