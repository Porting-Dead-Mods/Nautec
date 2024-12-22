package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.api.fluids.NTFluid;
import com.portingdeadmods.nautec.api.items.IBacteriaItem;
import com.portingdeadmods.nautec.api.items.IPowerItem;
import com.portingdeadmods.nautec.capabilities.NTCapabilities;
import com.portingdeadmods.nautec.capabilities.power.IPowerStorage;
import com.portingdeadmods.nautec.compat.modonomicon.ModonomiconCompat;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.components.ComponentBacteriaStorage;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.BucketItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class NTCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Nautec.MODID);

    public static final Supplier<CreativeModeTab> MAIN = CREATIVE_MODE_TABS.register("main", () -> CreativeModeTab.builder()
            .title(Component.translatable("nautec.creative_tab.main"))
            .icon(NTBlocks.AQUATIC_CATALYST::toStack)
            .displayItems((params, output) -> {
                for (ItemLike item : NTItems.CREATIVE_TAB_ITEMS) {
                    output.accept(item);
                    if (item.asItem() instanceof IPowerItem) {
                        addPowered(output, item.asItem());
                    }

                    if (item.asItem() instanceof IBacteriaItem) {
                        Optional<HolderLookup.RegistryLookup<Bacteria>> lookup = params.holders().lookup(NTRegistries.BACTERIA_KEY);
                        if (lookup.isPresent()) {
                            Stream<ResourceKey<Bacteria>> resourceKeyStream = lookup.get().listElementIds();
                            resourceKeyStream.forEach(elem -> {
                                addPetriDish(output, params.holders(), item, elem, false);
                                addPetriDish(output, params.holders(), item, elem, true);
                            });
                        }
                    }
                }

                if (ModList.get().isLoaded("modonomicon")) {
                    output.accept(ModonomiconCompat.getItemStack());
                }

                output.accept(NTBlocks.CREATIVE_POWER_SOURCE);

                for (NTFluid fluid : NTFluids.HELPER.getFluids()) {
                    DeferredItem<BucketItem> deferredBucket = fluid.getDeferredBucket();
                    Nautec.LOGGER.debug("Bucket: {}", deferredBucket);
                    output.accept(deferredBucket);
                }

            })
            .build());

    private static void addPetriDish(CreativeModeTab.Output output, HolderLookup.Provider lookup, ItemLike item, ResourceKey<Bacteria> elem, boolean analyzed) {
        if (elem != NTBacterias.EMPTY) {
            ItemStack stack = new ItemStack(item);
            stack.set(NTDataComponents.BACTERIA, new ComponentBacteriaStorage(new BacteriaInstance(elem, lookup)));
            stack.set(NTDataComponents.ANALYZED, analyzed);
            output.accept(stack);
        }
    }

    public static void addPowered(CreativeModeTab.Output output, Item item) {
        ItemStack itemStack = new ItemStack(item);
        IPowerStorage storage = itemStack.getCapability(NTCapabilities.PowerStorage.ITEM);
        if (storage != null) {
            storage.setPowerStored(storage.getPowerCapacity());
            output.accept(itemStack);
        }
    }
}
