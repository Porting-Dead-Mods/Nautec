package com.portingdeadmods.nautec.events.helper;

import com.portingdeadmods.nautec.api.augments.Augment;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.client.renderer.augments.ModelPartGetter;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.model.geom.ModelPart;
import net.neoforged.neoforge.client.event.RenderPlayerEvent;

import java.util.Map;
import java.util.function.Supplier;

public class AugmentSlotsRenderer {
    private static final Object2ObjectMap<AugmentSlot, ModelPartGetter> MODEL_PART_GETTERS = new Object2ObjectOpenHashMap<>();

    public static void registerAugmentSlotModelPart(AugmentSlot slot, ModelPartGetter getter) {
        MODEL_PART_GETTERS.put(slot, getter);
    }

    public static void registerAugmentSlotModelPart(Supplier<AugmentSlot> slot, ModelPartGetter getter) {
        MODEL_PART_GETTERS.put(slot.get(), getter);
    }

    public static ModelPartGetter modelPartBySlot(AugmentSlot slot) {
        return MODEL_PART_GETTERS.get(slot);
    }

    public static void render(RenderPlayerEvent.Pre event) {

        Map<AugmentSlot, Augment> augments = AugmentLayerRenderer.AUGMENTS_CACHE;
        for (AugmentSlot slot : augments.keySet()) {
            Augment augment = augments.get(slot);
            if (augment != null && augment.replaceBodyPart()) {
                ModelPart modelPart = MODEL_PART_GETTERS.get(slot).getModelPart(event.getRenderer().getModel());
                if (modelPart != null) {
                    modelPart.visible = false;
                }
            }
        }
    }
}
