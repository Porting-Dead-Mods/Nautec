package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.recipes.AquaticCatalystChannelingRecipe;
import com.portingdeadmods.nautec.content.recipes.AugmentationRecipe;
import com.portingdeadmods.nautec.datagen.recipeBuilder.AugmentationRecipeBuilder;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTItems;
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
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class AugmentationRecipeCategory implements IRecipeCategory<AugmentationRecipe> {
    static final ResourceLocation SINGLE_SLOT_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID,"container/furnace/empty_slot");
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "augmentation");
    public static final RecipeType<AugmentationRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, AugmentationRecipe.class);
    private final IDrawable icon;
    private final IDrawable background;


    public AugmentationRecipeCategory(IGuiHelper helper) {
        Font font = Minecraft.getInstance().font;
        this.background = helper.createBlankDrawable(136, 24 + 4 * font.lineHeight);
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
        // Add the catalyst slot in the middle, at the top
        builder.addSlot(RecipeIngredientRole.CATALYST, getWidth() / 2 - 8, 0)
                .addItemStack(recipe.augmentItem().getDefaultInstance());

        // Hardcode 3 input slots at specific positions at the bottom
        // Slot 1
        builder.addSlot(RecipeIngredientRole.INPUT, 40, 60)
                .addItemStack(recipe.getIngredients().get(1).getItems()[0]);

        // Slot 2 (centered)
        builder.addSlot(RecipeIngredientRole.INPUT, 58, 60)
                .addItemStack(recipe.getIngredients().get(2).getItems()[0]);

        // Slot 3
        builder.addSlot(RecipeIngredientRole.INPUT, 76, 60)
                .addItemStack(recipe.getIngredients().get(3).getItems()[0]);
    }

    @Override
    public void draw(AugmentationRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Font font = Minecraft.getInstance().font;
        int fontSize = font.lineHeight;

        // Drawing augment result text
        ResourceLocation loc = NTRegistries.AUGMENT_TYPE.getKey(recipe.resultAugment());
        String translationKey = "augment" + "." + loc.getNamespace() + "." + loc.getPath();
        String resultText = Component.translatable(translationKey).getString();
        int resultTextWidth = font.width(resultText);
        int resultX = (getWidth() / 2) - (resultTextWidth / 2); // Center horizontally
        int resultY = 16 + 2; // Positioned right under the item slot with some padding

        guiGraphics.drawString(font, resultText, resultX, resultY, 0xFFFFFF);

        // Drawing the description below the augment result
        String descText = recipe.desc();
        int descTextWidth = font.width(descText);
        int descX = (getWidth() / 2) - (descTextWidth / 2); // Center horizontally
        int descY = resultY + fontSize + 2; // Positioned right under resultText with padding

        guiGraphics.drawString(font, descText, descX, descY, 0xFFFFFF);

        // Draw the main catalyst slot sprite
        guiGraphics.blitSprite(SINGLE_SLOT_SPRITE, getWidth() / 2 - 9, -1, 18, 18);

        // Draw hardcoded slot sprites for the 3 input slots at the bottom
        // Slot 1 sprite
        guiGraphics.blitSprite(SINGLE_SLOT_SPRITE, 40 - 1, 60 - 1, 18, 18);

        // Slot 2 sprite (centered)
        guiGraphics.blitSprite(SINGLE_SLOT_SPRITE, 58 - 1, 60 - 1, 18, 18);

        // Slot 3 sprite
        guiGraphics.blitSprite(SINGLE_SLOT_SPRITE, 76 - 1, 60 - 1, 18, 18);
    }
}
