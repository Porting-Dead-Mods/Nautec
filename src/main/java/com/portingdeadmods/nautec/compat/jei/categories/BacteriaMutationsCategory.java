package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.content.recipes.BacteriaMutationRecipe;
import com.portingdeadmods.nautec.content.recipes.MixingRecipe;
import com.portingdeadmods.nautec.content.recipes.utils.RecipeUtils;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.components.ComponentBacteriaStorage;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector2i;

public class BacteriaMutationsCategory implements IRecipeCategory<BacteriaMutationRecipe> {
    static final ResourceLocation SINGLE_SLOT_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"container/furnace/empty_slot");
    static final ResourceLocation DOWN_ARROW_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"container/furnace/down_arrow");
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, MixingRecipe.NAME);
    public static final RecipeType<BacteriaMutationRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, BacteriaMutationRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    private final int gap = 4;
    private final int slotSize = 18;
    private final int drawableWidth = 106;
    private final int drawableHeight = 66;

    public BacteriaMutationsCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(drawableWidth, drawableHeight);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(NTBlocks.MUTATOR.get()));
    }

    @Override
    public RecipeType<BacteriaMutationRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Bacteria Mutations");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BacteriaMutationRecipe recipe, IFocusGroup focuses) {
        // Petridish Slots
        builder.addSlot(RecipeIngredientRole.INPUT, gap, gap + slotSize / 2).addItemStack(recipe.INPUT_PETRIDISH);
        builder.addSlot(RecipeIngredientRole.OUTPUT, drawableWidth - gap - slotSize, gap + slotSize / 2).addItemStack(recipe.OUTPUT_PETRIDISH);

        // Catalyst Slot
        builder.addSlot(RecipeIngredientRole.INPUT, drawableWidth - slotSize / 2, drawableHeight / 2 - slotSize - gap * 2).addItemLike(recipe.catalyst);
    }

    @Override
    public void draw(@NotNull BacteriaMutationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        // Petridish Slots
        guiGraphics.blitSprite(SINGLE_SLOT_SPRITE, gap, gap + slotSize / 2, 18, 18);
        guiGraphics.blitSprite(SINGLE_SLOT_SPRITE, drawableWidth - gap - slotSize, gap + slotSize / 2, 18, 18);

        // Catalyst Slot
        guiGraphics.blitSprite(SINGLE_SLOT_SPRITE, drawableWidth - slotSize / 2, drawableHeight / 2 - slotSize - gap * 2, 18, 18);


        guiGraphics.blitSprite(DOWN_ARROW_SPRITE, 32, 22, 15, 23);
        guiGraphics.blitSprite(DOWN_ARROW_SPRITE, 88, 22, 15, 23);
    }
}
