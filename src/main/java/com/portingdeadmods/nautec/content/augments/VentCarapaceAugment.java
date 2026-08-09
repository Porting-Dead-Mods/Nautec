package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.registries.NTAugments;
import net.minecraft.core.Holder;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

public class VentCarapaceAugment extends Augment {
    private static final Identifier ARMOR_ID = Nautec.rl("vent_carapace_armor");
    private static final Identifier KNOCKBACK_ID = Nautec.rl("vent_carapace_knockback");
    private static final Identifier BURNING_ID = Nautec.rl("vent_carapace_burning");

    public VentCarapaceAugment(AugmentSlot augmentSlot) {
        super(NTAugments.VENT_CARAPACE.get(), augmentSlot);
    }

    @Override
    public void onAdded(Player player) {
        apply(player, Attributes.ARMOR, ARMOR_ID, 4.0, AttributeModifier.Operation.ADD_VALUE);
        apply(player, Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_ID, 0.5, AttributeModifier.Operation.ADD_VALUE);
        apply(player, Attributes.BURNING_TIME, BURNING_ID, -0.5, AttributeModifier.Operation.ADD_MULTIPLIED_BASE);
    }

    @Override
    public void onRemoved(Player player) {
        remove(player, Attributes.ARMOR, ARMOR_ID);
        remove(player, Attributes.KNOCKBACK_RESISTANCE, KNOCKBACK_ID);
        remove(player, Attributes.BURNING_TIME, BURNING_ID);
    }

    private static void apply(Player player, Holder<Attribute> attribute, Identifier id, double amount, AttributeModifier.Operation operation) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance != null) {
            instance.addOrUpdateTransientModifier(new AttributeModifier(id, amount, operation));
        }
    }

    private static void remove(Player player, Holder<Attribute> attribute, Identifier id) {
        AttributeInstance instance = player.getAttribute(attribute);
        if (instance != null) {
            instance.removeModifier(id);
        }
    }
}
