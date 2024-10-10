package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.network.KeyPressedPayload;
import com.portingdeadmods.nautec.registries.NTAugments;
import com.portingdeadmods.nautec.registries.NTKeybinds;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.projectile.ThrownPotion;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potion;
import net.minecraft.world.item.alchemy.PotionContents;
import net.minecraft.world.item.alchemy.Potions;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;

import java.util.ArrayList;
import java.util.List;

public class ThrowRandomPotionAugments extends Augment {
    public ThrowRandomPotionAugments(AugmentSlot augmentSlot) {
        super(NTAugments.THROW_POTION_AUGMENT.get(), augmentSlot);
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        if (NTKeybinds.THROW_POTION_KEYBIND.get().consumeClick() && !isOnCooldown()) {
            PacketDistributor.sendToServer(new KeyPressedPayload(augmentSlot));
            handleKeybindPress();
        }
    }

    @Override
    public void handleKeybindPress() {
        List<Holder<Potion>> potions = new ArrayList<>();
        potions.add(Potions.HEALING);
        potions.add(Potions.HARMING);
        potions.add(Potions.REGENERATION);
        potions.add(Potions.SWIFTNESS);
        potions.add(Potions.SLOWNESS);

        Holder<Potion> randomPotion = potions.get(player.getRandom().nextInt(potions.size()));
        ItemStack stack = PotionContents.createItemStack(Items.SPLASH_POTION,randomPotion);

        ThrownPotion potion = new ThrownPotion(player.level(),player);
        potion.setItem(stack);
        potion.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
        player.level().addFreshEntity(potion);
        setCooldown(20); // Set the cooldown, which decrements by 1 every tick
    }
}
