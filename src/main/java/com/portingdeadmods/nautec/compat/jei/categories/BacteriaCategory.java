package com.portingdeadmods.nautec.compat.jei.categories;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import com.portingdeadmods.nautec.utils.GuiUtils;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BacteriaCategory<T> implements IRecipeCategory<T> {
    private static final ResourceLocation BACTERIA_SLOT_SPRITE = ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "container/bacteria_slot");
    private final Map<T, List<BacteriaSlot>> slots;

    public BacteriaCategory() {
        this.slots = new HashMap<>();
    }

    @Override
    public void draw(T recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        List<BacteriaSlot> slots = this.slots.get(recipe);
        if (slots != null) {
            for (BacteriaSlot slot : slots) {
                guiGraphics.blitSprite(BACTERIA_SLOT_SPRITE, slot.x, slot.y, 18, 18);
                GuiUtils.renderBacteria(guiGraphics, slot.bacteria, slot.x, slot.y);

                if (GuiUtils.isHovering(guiGraphics, slot.x, slot.y, 18, 18, (int) mouseX, (int) mouseY)) {
                    guiGraphics.renderComponentTooltip(Minecraft.getInstance().font, slot.bacteria.getTooltip(), (int) mouseX, (int) mouseY);
                }
            }
        }
    }

    public void addBacteriaSlot(T recipe, int x, int y, ResourceKey<Bacteria> bacteria) {
        BacteriaInstance instance = BacteriaInstance.withMaxStats(bacteria, Minecraft.getInstance().level.registryAccess());
        this.slots.computeIfAbsent(recipe, key -> new ArrayList<>()).add(new BacteriaSlot(instance, x, y));
    }

    public record BacteriaSlot(BacteriaInstance bacteria, int x, int y) {
    }

}
