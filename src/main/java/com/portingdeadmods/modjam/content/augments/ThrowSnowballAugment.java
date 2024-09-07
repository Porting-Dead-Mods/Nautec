package com.portingdeadmods.modjam.content.augments;

import com.mojang.blaze3d.platform.InputConstants;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.utils.InputUtils;
import net.minecraft.world.entity.projectile.Snowball;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;

public class ThrowSnowballAugment implements StaticAugment {
    @Override
    public void breakBlock(BlockEvent.BreakEvent event) {

    }

    @Override
    public void tick(PlayerTickEvent.Post event) {
        if (InputUtils.isKeyDown(InputConstants.KEY_Y)) {
            ModJam.LOGGER.info("Snow");
            if (!event.getEntity().level().isClientSide) {
                Snowball snowball = new Snowball(event.getEntity().level(), event.getEntity());
                snowball.setItem(Items.SNOWBALL.getDefaultInstance());
                snowball.shootFromRotation(event.getEntity(), event.getEntity().getXRot(), event.getEntity().getYRot(), 0.0F, 1.5F, 1.0F);
                event.getEntity().level().addFreshEntity(snowball);
            }
        }
    }
}
