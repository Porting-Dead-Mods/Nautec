package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.fluids.NTFluid;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTFluids;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ModelFile;
import net.neoforged.neoforge.client.model.generators.loaders.DynamicFluidContainerModelBuilder;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.function.Supplier;

public class ItemModelProvider extends net.neoforged.neoforge.client.model.generators.ItemModelProvider {
    public ItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, Nautec.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        // Register item models here
        basicItem(NTItems.AQUARINE_STEEL_INGOT.get());
        basicItem(NTItems.ATLANTIC_GOLD_INGOT.get());
        basicItem(NTItems.ATLANTIC_GOLD_NUGGET.get());
        basicItem(NTItems.PRISMARINE_CRYSTAL_SHARD.get());
        basicItem(NTItems.AQUARINE_STEEL_COMPOUND.get());
        basicItem(NTItems.SALT.get());
        basicItem(NTItems.AIR_BOTTLE.get());

        basicItem(NTItems.ELDRITCH_HEART.get());
        basicItem(NTItems.DROWNED_LUNGS.get());
        basicItem(NTItems.GUARDIAN_EYE.get());
        basicItem(NTItems.DOLPHIN_FIN.get());

        basicItem(NTItems.CLAW_ROBOT_ARM.get());
//        basicItem(NTItems.SYRINGE_ROBOT_ARM.get());

        basicItem(NTItems.CAST_IRON_INGOT.get());
        basicItem(NTItems.CAST_IRON_NUGGET.get());
        basicItem(NTItems.CAST_IRON_ROD.get());
        basicItem(NTItems.BROWN_POLYMER.get());

        basicItem(NTItems.RUSTY_GEAR.get());
        basicItem(NTItems.GEAR.get());
        basicItem(NTItems.BROKEN_WHISK.get());
        basicItem(NTItems.WHISK.get());
        basicItem(NTItems.BURNT_COIL.get());
        basicItem(NTItems.LASER_CHANNELING_COIL.get());

        basicItem(NTItems.GLASS_VIAL.get());
        basicItem(NTItems.ELECTROLYTE_ALGAE_SERUM_VIAL.get());

        petriDishItem(NTItems.PETRI_DISH.get());

        basicItem(NTItems.PRISM_MONOCLE.get());

        basicItem(NTItems.DIVING_HELMET.get());
        basicItem(NTItems.DIVING_CHESTPLATE.get());
        basicItem(NTItems.DIVING_LEGGINGS.get());
        basicItem(NTItems.DIVING_BOOTS.get());

        handHeldItem(NTItems.AQUARINE_WRENCH.get());
        handHeldItem(NTItems.CROWBAR.get());

        for (NTFluid fluid : NTFluids.HELPER.getFluids()) {
            bucket(fluid.getStillFluid());
        }

        aquarineSteelTool(NTItems.AQUARINE_AXE.get());
        aquarineSteelTool(NTItems.AQUARINE_HOE.get());
        aquarineSteelTool(NTItems.AQUARINE_PICKAXE.get());
        aquarineSteelTool(NTItems.AQUARINE_SHOVEL.get());
        aquarineSteelTool(NTItems.AQUARINE_SWORD.get());
        basicItem(NTItems.PRISMATIC_BATTERY.get());

        basicItem(NTItems.AQUARINE_HELMET.get());
        basicItem(NTItems.AQUARINE_CHESTPLATE.get());
        basicItem(NTItems.AQUARINE_LEGGINGS.get());
        basicItem(NTItems.AQUARINE_BOOTS.get());

        parentItemBlock(NTBlocks.LASER_JUNCTION.asItem(), "_base");

        blockItems();
    }

    private void bucket(Fluid f) {
        withExistingParent(key(f.getBucket()).getPath(), ResourceLocation.fromNamespaceAndPath("neoforge", "item/bucket"))
                .customLoader(DynamicFluidContainerModelBuilder::begin)
                .fluid(f);
    }

    private static @NotNull ResourceLocation key(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem());
    }

    private void blockItems() {
        for (Supplier<BlockItem> blockItem : NTItems.BLOCK_ITEMS) {
            parentItemBlock(blockItem.get());
        }
    }

    public ItemModelBuilder parentItemBlock(Item item, ResourceLocation loc) {
        ResourceLocation name = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item));
        return getBuilder(name.toString())
                .parent(new ModelFile.UncheckedModelFile(loc));
    }

    public ItemModelBuilder parentItemBlock(Item item) {
        return parentItemBlock(item, "");
    }

    public ItemModelBuilder parentItemBlock(Item item, String suffix) {
        ResourceLocation name = Objects.requireNonNull(BuiltInRegistries.ITEM.getKey(item));
        return getBuilder(name.toString())
                .parent(new ModelFile.UncheckedModelFile(ResourceLocation.fromNamespaceAndPath(name.getNamespace(), "block/" + name.getPath() + suffix)));
    }

    public void petriDishItem(Item item) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(item);
        ResourceLocation hasBacteria = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "has_bacteria");
        getBuilder(location.toString())
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .override()
                .model(basicItem(item))
                .predicate(hasBacteria, 0)
                .end()
                .override()
                .model(basicItem(item, "_bacteria")
                        .texture("layer1", itemTexture(item, "_overlay")))
                .predicate(hasBacteria, 1)
                .end()
                .texture("layer0", itemTexture(item, ""));
    }

    public void aquarineSteelTool(Item item) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(item);
        ResourceLocation enabled = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "enabled");
        getBuilder(location.toString())
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .override()
                .model(handHeldItem(item))
                .predicate(enabled, 0)
                .end()
                .override()
                .model(handHeldItem(item, "_enabled"))
                .predicate(enabled, 1)
                .end()
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "item/" + location.getPath()));
    }

    public ItemModelBuilder handHeldItem(Item item) {
        return handHeldItem(item, "");
    }

    public ItemModelBuilder handHeldItem(Item item, String suffix) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(item);
        return getBuilder(location + suffix)
                .parent(new ModelFile.UncheckedModelFile("item/handheld"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "item/" + location.getPath() + suffix));
    }

    private ResourceLocation itemTexture(Item item, String suffx) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(item);
        return ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "item/" + location.getPath() + suffx);
    }

    public ItemModelBuilder basicItem(Item item, String suffix) {
        ResourceLocation location = BuiltInRegistries.ITEM.getKey(item);
        return getBuilder(item.toString() + suffix)
                .parent(new ModelFile.UncheckedModelFile("item/generated"))
                .texture("layer0", ResourceLocation.fromNamespaceAndPath(location.getNamespace(), "item/" + location.getPath()));
    }
}
