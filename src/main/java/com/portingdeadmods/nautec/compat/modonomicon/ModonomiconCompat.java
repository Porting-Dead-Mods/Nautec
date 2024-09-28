package com.portingdeadmods.nautec.compat.modonomicon;

import com.klikli_dev.modonomicon.data.BookDataManager;
import com.klikli_dev.modonomicon.registry.DataComponentRegistry;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

public class ModonomiconCompat {
    public static ItemStack getItemStack() {
        ResourceLocation id = BookDataManager.get().getBook(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "nautec_guide")).getId();
        ItemStack itemStack = new ItemStack(NTItems.NAUTEC_GUIDE.get());
        itemStack.set(DataComponentRegistry.BOOK_ID, id);
        return itemStack;
    }

    public static Supplier<Item> registerItem() {
        return NTItems.registerItem("nautec_guide", () -> new NautecGuideItem(new Item.Properties().stacksTo(1)));
    }
}
