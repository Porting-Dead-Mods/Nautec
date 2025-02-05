package com.portingdeadmods.nautec.content.items;

import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.bacteria.IBacteriaStorage;
import com.portingdeadmods.nautec.data.NTDataMaps;
import com.portingdeadmods.nautec.data.maps.BacteriaObtainValue;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class GraftingToolItem extends Item {
    public GraftingToolItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        if (context.getHand() == InteractionHand.OFF_HAND) return InteractionResult.FAIL;

        BlockPos pos = context.getClickedPos();
        Level level = context.getLevel();
        BlockState blockState = level.getBlockState(pos);
        BacteriaObtainValue data = blockState.getBlockHolder().getData(NTDataMaps.BACTERIA_OBTAINING);
        Player player = context.getPlayer();
        if (player != null) {
            ItemStack offhandItem = player.getOffhandItem();
            if (data != null && offhandItem.is(NTItems.PETRI_DISH.get())) {
                IBacteriaStorage bacteriaStorage = offhandItem.getCapability(NTCapabilities.BacteriaStorage.ITEM);
                if (level.getRandom().nextFloat() <= data.chance()) {
                    bacteriaStorage.setBacteria(0, BacteriaInstance.roll(data.bacteria(), level.registryAccess()));
                }
                ItemStack itemInHand = context.getItemInHand();
                itemInHand.hurtAndBreak(1, player, player.getEquipmentSlotForItem(itemInHand));
                level.playSound(null, pos, SoundEvents.AXE_SCRAPE, SoundSource.PLAYERS);
                return InteractionResult.SUCCESS;
            }
        }
        return super.useOn(context);
    }
}
