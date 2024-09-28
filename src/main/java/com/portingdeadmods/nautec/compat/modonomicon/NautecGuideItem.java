package com.portingdeadmods.nautec.compat.modonomicon;

import com.klikli_dev.modonomicon.data.BookDataManager;
import com.klikli_dev.modonomicon.item.ModonomiconItem;
import com.klikli_dev.modonomicon.registry.DataComponentRegistry;
import com.portingdeadmods.nautec.Nautec;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import java.util.List;

public class NautecGuideItem extends ModonomiconItem {
    public NautecGuideItem(Properties pProperties) {
        super(pProperties.component(DataComponentRegistry.BOOK_ID, ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "nautec_guide")));
    }

    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {
        try {
            return super.use(pLevel, pPlayer, pUsedHand);
        } catch (Exception e) {
            Nautec.LOGGER.error("Error opening book", e);
            return InteractionResultHolder.fail(pPlayer.getItemInHand(pUsedHand));
        }
    }

    @Override
    public void onCraftedBy(ItemStack itemStack, Level level, Player player) {
        ResourceLocation id = BookDataManager.get().getBook(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "nautec_guide")).getId();
        itemStack.set(DataComponentRegistry.BOOK_ID, id);
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext worldIn, List<Component> tooltip, TooltipFlag flagIn) {
        tooltip.add(Component.translatable("nautec_guide.desc.0").withStyle(ChatFormatting.GRAY));
    }
}
