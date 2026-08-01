package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.types.IRecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class BioReactorCategory extends BacteriaCategory<BioReactorCategory.BioReactorRecipe> {
    static final Identifier RIGHT_ARROW_SPRITE = Nautec.rl("container/bio_reactor/progress_arrow_off");
    public static final Identifier UID = Nautec.rl(BioReactorRecipe.NAME);
    public static final IRecipeType<BioReactorRecipe> RECIPE_TYPE =
            IRecipeType.create(UID, BioReactorRecipe.class);

    public BioReactorCategory(IGuiHelper helper) {
        super(RECIPE_TYPE,
                Component.translatable("nautec.jei.category.bio_reactor"),
                helper.createDrawableItemStack(new ItemStack(NTBlocks.BIO_REACTOR.get())),
                96,
                24);
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, BioReactorRecipe recipe, IFocusGroup focuses) {
        if (recipe.resource() instanceof Bacteria.Resource.ItemResource(Item item)) {
            builder.addOutputSlot(getWidth() - 18, 3).add(item);
        }

        builder.addInvisibleIngredients(RecipeIngredientRole.INPUT).add(recipe.getInputDish());

        addBacteriaSlot(recipe, 0, 3, recipe.bacteria);
    }

    @Override
    public void draw(BioReactorRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphicsExtractor guiGraphics, double mouseX, double mouseY) {
        super.draw(recipe, recipeSlotsView, guiGraphics, mouseX, mouseY);

        NTJeiUtil.blitSprite(guiGraphics, RIGHT_ARROW_SPRITE, getWidth() / 2 - 12, getHeight() / 2 - 5, 24, 10);
    }

    public record BioReactorRecipe(ResourceKey<Bacteria> bacteria, Bacteria.Resource resource) {
        public static final String NAME = "bio_reactor";

        /**
         * <b><i>THIS METHOD SHOULD ONLY BE USED CLIENT SIDE :3</i></b>
         */
        public ItemStack getInputDish() {
            return BacteriaHelper.getMaxStatDish(bacteria, Minecraft.getInstance().level.registryAccess());
        }
    }
}
