package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.network.KeyPressedPayload;
import com.portingdeadmods.nautec.registries.NTAugments;
import com.portingdeadmods.nautec.registries.NTKeybinds;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import net.neoforged.neoforge.network.PacketDistributor;
import org.jetbrains.annotations.Nullable;

@Deprecated
public class GiveDiamondAugment extends Augment {
    public GiveDiamondAugment(AugmentSlot augmentSlot) {
        super(NTAugments.GIVE_DIAMOND.get(), augmentSlot);
    }

    @Override
    public @Nullable AugmentSlot[] getCompatibleSlots() {
        return new AugmentSlot[0];
    }

    @Override
    public void clientTick(PlayerTickEvent.Post event) {
        if (NTKeybinds.GIVE_DIAMOND_KEYBIND.get().consumeClick()) {
            PacketDistributor.sendToServer(new KeyPressedPayload(augmentSlot));
        }
    }

    @Override
    public void handleKeybindPress() {
        player.addItem(Items.DIAMOND.getDefaultInstance());
    }
}
