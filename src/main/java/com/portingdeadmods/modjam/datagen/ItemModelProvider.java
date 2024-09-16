package com.portingdeadmods.modjam.datagen;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.registries.MJBlocks;
import com.portingdeadmods.modjam.registries.MJItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

import java.util.Objects;
import java.util.function.Supplier;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ModJam.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Register item models here
        basicItem(MJItems.AQUARINE_STEEL_INGOT.get());
        basicItem(MJItems.ATLANTIC_GOLD_INGOT.get());
        basicItem(MJItems.ATLANTIC_GOLD_NUGGET.get());
        basicItem(MJItems.PRISMARINE_CRYSTAL_SHARD.get());

        basicItem(MJItems.DROWNED_LUNGS.get());
        basicItem(MJItems.DOLPHIN_FIN.get());

        basicItem(MJItems.RUSTY_GEAR.get());
        basicItem(MJItems.GEAR.get());

        basicItem(MJItems.GLASS_VIAL.get());
        basicItem(MJItems.ELECTROLYTE_ALGAE_SERUM_VIAL.get());

        basicItem(MJItems.PRISM_MONOCLE.get());

        basicItem(MJItems.DIVING_HELMET.get());
        basicItem(MJItems.DIVING_CHESTPLATE.get());
        basicItem(MJItems.DIVING_LEGGINGS.get());
        basicItem(MJItems.DIVING_BOOTS.get());

        handHeldItem(MJItems.AQUARINE_WRENCH.get());
        handHeldItem(MJItems.CROWBAR.get());

        basicItem(MJItems.SALT_WATER_BUCKET.get());
        basicItem(MJItems.EAS_BUCKET.get());
        basicItem(MJItems.ETCHING_ACID_BUCKET.get());

        parentItemBlock(MJBlocks.LASER_JUNCTION.asItem(), "_base");

        blockItems();
    }

    private void blockItems() {
        for (Supplier<BlockItem> blockItem : MJItems.BLOCK_ITEMS) {
            parentItemBlock(blockItem.get());
        }
    }

    public ItemModelBuilder parentItemBlock(Item item) {
        return parentItemBlock(item, "");
    }

    public ItemModelBuilder parentItemBlock(Item item, String suffix) {
        ResourceLocation name = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item));
        return getBuilder(name.toString())
                .parent(new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(name.getNamespace(), "block/" + name.getPath() + suffix)));
    }

    public ItemModelBuilder handHeldItem(Item item) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(item);
        return getBuilder(location.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "item/" + location.getPath()));
    }
}
