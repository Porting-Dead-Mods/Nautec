package com.portingdeadmods.nautec.datagen;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.renderer.items.AnchorItemRenderer;
import com.portingdeadmods.nautec.api.client.renderer.items.PrismarineCrystalItemRenderer;
import com.portingdeadmods.nautec.api.fluids.NTFluid;
import com.portingdeadmods.nautec.client.item.AbilityEnabledProperty;
import com.portingdeadmods.nautec.client.item.BacteriaColorTintSource;
import com.portingdeadmods.nautec.client.item.HasBacteriaProperty;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTFluids;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.model.ItemModelUtils;
import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.client.data.models.model.TextureMapping;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Fluid;
import net.neoforged.neoforge.client.model.item.DynamicFluidContainerModel;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ItemModelProvider extends ModelProvider {
    private BlockModelGenerators blockModels;
    private ItemModelGenerators itemModels;

    public ItemModelProvider(PackOutput output) {
        super(output, Nautec.MODID);
    }

    @Override
    public String getName() {
        return "NauTec Item Model Definitions";
    }

    @Override
    protected Stream<? extends Holder<Block>> getKnownBlocks() {
        return Stream.empty();
    }

    @Override
    protected Stream<? extends Holder<Item>> getKnownItems() {
        return Stream.empty();
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        this.blockModels = blockModels;
        this.itemModels = itemModels;

        basicItem(NTItems.AQUARINE_STEEL_INGOT.get());
        basicItem(NTItems.ATLANTIC_GOLD_INGOT.get());
        basicItem(NTItems.ATLANTIC_GOLD_NUGGET.get());
        basicItem(NTItems.PRISMARINE_CRYSTAL_SHARD.get());
        basicItem(NTItems.PRISMARINE_LENS.get());
        basicItem(NTItems.AQUARINE_STEEL_COMPOUND.get());
        basicItem(NTItems.CAST_IRON_COMPOUND.get());
        basicItem(NTItems.SALT.get());
        basicItem(NTItems.AIR_BOTTLE.get());

        basicItem(NTItems.ELDRITCH_HEART.get());
        basicItem(NTItems.DROWNED_LUNGS.get());
        basicItem(NTItems.GUARDIAN_EYE.get());
        basicItem(NTItems.DOLPHIN_FIN.get());

        basicItem(NTItems.CLAW_ROBOT_ARM.get());

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
        basicItem(NTItems.AQUATIC_CHIP.get());
        basicItem(NTItems.DAMAGED_AQUATIC_CHIP.get());

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
        handHeldItem(NTItems.GRAFTING_TOOL.get());

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

        specialItemBlock(NTBlocks.ANCHOR.asItem(), new AnchorItemRenderer.Unbaked());
        specialItemBlock(NTBlocks.PRISMARINE_CRYSTAL.asItem(), new PrismarineCrystalItemRenderer.Unbaked());
        specialItemBlock(NTBlocks.DECORATIVE_PRISMARINE_CRYSTAL.asItem(), new PrismarineCrystalItemRenderer.Unbaked());

        blockItems();
    }

    private void specialItemBlock(Item item, SpecialModelRenderer.Unbaked<?> renderer) {
        Identifier name = key(item);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.specialModel(
                Identifier.fromNamespaceAndPath(name.getNamespace(), "block/" + name.getPath()), renderer));
    }

    private void bucket(Fluid f) {
        itemModels.itemModelOutput.accept(f.getBucket(), new DynamicFluidContainerModel.Unbaked(
                new DynamicFluidContainerModel.Textures(
                        Optional.empty(),
                        Optional.of(new Material(Identifier.withDefaultNamespace("item/bucket"))),
                        Optional.of(new Material(Identifier.fromNamespaceAndPath("neoforge", "item/mask/bucket_fluid"))),
                        Optional.empty()),
                f, false, true, true));
    }

    private static @NotNull Identifier key(ItemLike item) {
        return BuiltInRegistries.ITEM.getKey(item.asItem());
    }

    private void blockItems() {
        for (Supplier<BlockItem> blockItem : NTItems.BLOCK_ITEMS) {
            BlockItem item = blockItem.get();
            if (item == NTBlocks.LASER_JUNCTION.asItem()
                    || item == NTBlocks.ANCHOR.asItem()
                    || item == NTBlocks.PRISMARINE_CRYSTAL.asItem()
                    || item == NTBlocks.DECORATIVE_PRISMARINE_CRYSTAL.asItem()) {
                continue;
            }
            parentItemBlock(item);
        }
    }

    public void parentItemBlock(Item item) {
        parentItemBlock(item, "");
    }

    public void parentItemBlock(Item item, String suffix) {
        Identifier name = key(item);
        blockModels.registerSimpleItemModel(item, Identifier.fromNamespaceAndPath(name.getNamespace(), "block/" + name.getPath() + suffix));
    }

    public void petriDishItem(Item item) {
        Identifier base = itemModels.createFlatItemModel(item, ModelTemplates.FLAT_ITEM);
        Identifier bacteria = ModelTemplates.TWO_LAYERED_ITEM.create(ModelLocationUtils.getModelLocation(item, "_bacteria"),
                TextureMapping.layered(TextureMapping.getItemTexture(item), TextureMapping.getItemTexture(item, "_overlay")),
                itemModels.modelOutput);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.conditional(new HasBacteriaProperty(),
                ItemModelUtils.tintedModel(bacteria, ItemModelUtils.constantTint(-1), new BacteriaColorTintSource()),
                ItemModelUtils.plainModel(base)));
    }

    public void aquarineSteelTool(Item item) {
        Identifier base = itemModels.createFlatItemModel(item, ModelTemplates.FLAT_HANDHELD_ITEM);
        Identifier enabled = itemModels.createFlatItemModel(item, "_enabled", ModelTemplates.FLAT_HANDHELD_ITEM);
        itemModels.itemModelOutput.accept(item, ItemModelUtils.conditional(new AbilityEnabledProperty(),
                ItemModelUtils.plainModel(enabled),
                ItemModelUtils.plainModel(base)));
    }

    public void handHeldItem(Item item) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_HANDHELD_ITEM);
    }

    public void basicItem(Item item) {
        itemModels.generateFlatItem(item, ModelTemplates.FLAT_ITEM);
    }
}
