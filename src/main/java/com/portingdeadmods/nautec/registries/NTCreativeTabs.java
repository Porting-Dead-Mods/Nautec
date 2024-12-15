package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.items.IBacteriaItem;
import com.portingdeadmods.nautec.api.items.IPowerItem;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.power.IPowerStorage;
import com.portingdeadmods.nautec.compat.modonomicon.ModonomiconCompat;
import com.portingdeadmods.nautec.data.NTDataAttachments;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.components.ComponentBacteriaStorage;
import com.portingdeadmods.nautec.data.components.ComponentPowerStorage;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;

public final class NTCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Nautec.MODID);

    public static final Supplier<CreativeModeTab> MAIN = CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("nautec.creative_tab.main"))
            .icon(() -> NTBlocks.AQUATIC_CATALYST.asItem().getDefaultInstance())
            .displayItems((params, output) -> {
                for (ItemLike item : NTItems.CREATIVE_TAB_ITEMS) {
                    output.accept(item);
                    if (item.asItem() instanceof IPowerItem) {
                        addPowered(output, item.asItem());
                    }

                    if (item.asItem() instanceof IBacteriaItem) {
                        ItemStack stack = new ItemStack(item);
                        stack.set(NTDataComponents.BACTERIA, Optional.of(ComponentBacteriaStorage.EMPTY));
                        output.accept(item);
                        output.accept(stack);
                    }
                }

                if (ModList.get().isLoaded("modonomicon")) {
                    output.accept(ModonomiconCompat.getItemStack());
                }

                output.accept(NTItems.SALT_WATER_BUCKET);
                output.accept(NTItems.EAS_BUCKET);
                output.accept(NTItems.ETCHING_ACID_BUCKET);

                output.accept(NTBlocks.CREATIVE_POWER_SOURCE);
            })
            .build());

    public static void addPowered(CreativeModeTab.Output output, Item item) {
        ItemStack itemStack = new ItemStack(item);
        IPowerStorage storage = itemStack.getCapability(NTCapabilities.PowerStorage.ITEM);
        if (storage != null) {
            storage.setPowerStored(storage.getPowerCapacity());
            output.accept(itemStack);
        }
    }
}
