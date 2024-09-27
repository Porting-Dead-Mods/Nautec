package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.AquaticCatalystChannelingRecipe;
import com.portingdeadmods.nautec.content.recipes.AugmentationRecipe;
import com.portingdeadmods.nautec.content.recipes.utils.IngredientWithCount;
import com.portingdeadmods.nautec.content.recipes.utils.RecipeUtils;
import com.portingdeadmods.nautec.datagen.recipeBuilder.AugmentationRecipeBuilder;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.utils.Utils;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.gui.widgets.IRecipeExtrasBuilder;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class AugmentationRecipeCategory implements IRecipeCategory<AugmentationRecipe> {
    static final ResourceLocation SINGLE_SLOT_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"container/furnace/empty_slot");
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "augmentation");
    public static final RecipeType<AugmentationRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, AugmentationRecipe.class);
    private final IDrawable icon;
    private final IDrawable background;


    public AugmentationRecipeCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(80, 64);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(NTItems.CLAW_ROBOT_ARM.get()));
    }

    @Override
    public RecipeType<AugmentationRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Augmentation Effects");
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
    public void setRecipe(IRecipeLayoutBuilder builder, AugmentationRecipe recipe, IFocusGroup focuses) {
        List<IngredientWithCount> ingredients = recipe.ingredients();
        int width = getWidth() / 2 - (ingredients.size() * 10);

        for (int i = 0; i < ingredients.size(); i++) {
            IngredientWithCount ingredient = ingredients.get(i);
            builder.addSlot(RecipeIngredientRole.INPUT, width + i * 20, 32)
                    .addIngredients(RecipeUtils.iWCToIngredientSaveCount(ingredient));
        }
    }

    @Override
    public void draw(AugmentationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;

        guiGraphics.drawCenteredString(font, Utils.registryTranslation(NTRegistries.AUGMENT_TYPE, recipe.resultAugment()), getWidth() / 2, 0, 0xFFFFFF);
        guiGraphics.renderFakeItem(recipe.augmentItem().getDefaultInstance(), getWidth() / 2 - 8, 12);

        List<IngredientWithCount> ingredients = recipe.ingredients();
        int width = getWidth() / 2 - (ingredients.size() * 10);

        for (int i = 0; i < ingredients.size(); i++) {
            guiGraphics.blitSprite(SINGLE_SLOT_SPRITE, width + i * 20, 31, 18, 18);
        }
    }
}
