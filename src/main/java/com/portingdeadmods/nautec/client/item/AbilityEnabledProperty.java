package com.portingdeadmods.nautec.client.item;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.item.properties.conditional.ConditionalItemModelProperty;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;

public record AbilityEnabledProperty() implements ConditionalItemModelProperty {
    public static final MapCodec<AbilityEnabledProperty> MAP_CODEC = MapCodec.unit(new AbilityEnabledProperty());

    @Override
    public boolean get(ItemStack stack, ClientLevel level, LivingEntity entity, int seed, ItemDisplayContext displayContext) {
        return NTDataComponentsUtils.isAbilityEnabledNBT(stack) > 0;
    }

    @Override
    public MapCodec<AbilityEnabledProperty> type() {
        return MAP_CODEC;
    }
}
