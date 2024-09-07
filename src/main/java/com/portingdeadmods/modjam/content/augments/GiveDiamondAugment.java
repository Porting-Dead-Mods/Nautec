package com.portingdeadmods.modjam.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.modjam.utils.InputUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.lwjgl.system.linux.Stat;

public class GiveDiamondAugment implements StaticAugment {
    @Override
    public void breakBlock(BlockEvent.BreakEvent event) {

    }

    @Override
    public void tick(PlayerTickEvent.Post event) {
        if (InputUtils.isKeyDown(InputConstants.KEY_Y)) {
            event.getEntity().addItem(Items.DIAMOND.getDefaultInstance());
        }

    }
}
