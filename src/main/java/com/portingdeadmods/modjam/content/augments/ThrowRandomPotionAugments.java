package com.portingdeadmods.modjam.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.capabilities.augmentation.Slot;
import com.portingdeadmods.modjam.network.KeyPressedPayload;
import com.portingdeadmods.modjam.utils.InputUtils;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Player;
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

public class ThrowRandomPotionAugments extends Augment{
    @Override
    public int getId() {
        return 4;
    }
    @Override
    public void clientTick(Slot slot, PlayerTickEvent.Post event) {
        if (InputUtils.isKeyDown(InputConstants.KEY_Y) && !onCooldown(slot, event.getEntity())) {
            PacketDistributor.sendToServer(new KeyPressedPayload(getId(), slot.slotId));
        }
    }

    @Override
    public void handleKeybindPress(Slot slot, Player player) {
        List<Holder<Potion>> potions = new ArrayList<>();
        potions.add(Potions.HEALING);
        potions.add(Potions.HARMING);
        potions.add(Potions.REGENERATION);
        potions.add(Potions.SWIFTNESS);
        potions.add(Potions.SLOWNESS);

        Holder<Potion> randomPotion = potions.get(ModJam.random.nextInt(potions.size()));
        ItemStack stack = PotionContents.createItemStack(Items.SPLASH_POTION,randomPotion);

        ThrownPotion potion = new ThrownPotion(player.level(),player);
        potion.setItem(stack);
        potion.shootFromRotation(player, player.getXRot(), player.getYRot(), 0.0F, 1.5F, 1.0F);
        player.level().addFreshEntity(potion);
        AugmentHelper.setCooldownAndUpdate(player, slot, 20); // Set the cooldown, which decrements by 1 every tick
    }
}
