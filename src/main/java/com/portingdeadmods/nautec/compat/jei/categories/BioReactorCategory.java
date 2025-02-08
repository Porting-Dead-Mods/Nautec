package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.content.recipes.BacteriaIncubationRecipe;
import com.portingdeadmods.nautec.registries.NTBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class BioReactorCategory extends BacteriaCategory<BioReactorCategory.BioReactorRecipe> {
    static final ResourceLocation RIGHT_ARROW_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "container/bio_reactor/progress_arrow_off");
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, BioReactorRecipe.NAME);
    public static final RecipeType<BioReactorRecipe> RECIPE_TYPE =
            new RecipeType<>(UID, BioReactorRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public BioReactorCategory(IGuiHelper helper) {
        this.background = helper.createBlankDrawable(96, 24);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(NTBlocks.BIO_REACTOR.get()));
    }

    @Override
    public RecipeType<BioReactorRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Bio Reactor");
    }

    @Override
    public @Nullable IDrawable getIcon() {
        return icon;
    }

    @Override
    public @Nullable IDrawable getBackground() {
        return background;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BioReactorRecipe recipe, IFocusGroup focuses) {
        if (recipe.resource() instanceof Bacteria.Resource.ItemResource(Item item)) {
            builder.addOutputSlot(getWidth() - 18, 3).addItemLike(item);
        }

        addBacteriaSlot(recipe, 0, 3, recipe.bacteria);
    }

    @Override
    public void draw(BioReactorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);

        guiGraphics.blitSprite(RIGHT_ARROW_SPRITE, getWidth() / 2 - 12, getHeight() / 2 - 5, 24, 10);
    }

    public record BioReactorRecipe(ResourceKey<Bacteria> bacteria, Bacteria.Resource resource) {
        public static final String NAME = "bio_reactor";
    }
}
