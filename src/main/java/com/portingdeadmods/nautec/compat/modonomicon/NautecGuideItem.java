package com.portingdeadmods.nautec.compat.modonomicon;

import com.klikli_dev.modonomicon.data.BookDataManager;
import com.klikli_dev.modonomicon.item.ModonomiconItem;
import com.klikli_dev.modonomicon.registry.DataComponentRegistry;
import com.portingdeadmods.nautec.Nautec;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.component.TooltipDisplay;
import net.minecraft.world.level.Level;

import java.util.function.Consumer;

public class NautecGuideItem extends ModonomiconItem {
    public NautecGuideItem(Properties pProperties) {
        super(pProperties.component(DataComponentRegistry.BOOK_ID.get(), Nautec.rl("nautec_guide")));
    }

    @Override
    public InteractionResult use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        try {
            return super.use(pLevel, pPlayer, pUsedHand);
        } catch (Exception e) {
            Nautec.LOGGER.error("Error opening book", e);
            return InteractionResult.FAIL;
        }
    }

    @Override
    public void onCraftedBy(ItemStack itemStack, Player player) {
        Identifier id = BookDataManager.get().getBook(Nautec.rl("nautec_guide")).getId();
        itemStack.set(DataComponentRegistry.BOOK_ID.get(), id);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, TooltipDisplay display, Consumer<Component> tooltip, TooltipFlag flag) {
        tooltip.accept(Component.translatable("nautec_guide.desc.0").withStyle(ChatFormatting.GRAY));
    }
}
