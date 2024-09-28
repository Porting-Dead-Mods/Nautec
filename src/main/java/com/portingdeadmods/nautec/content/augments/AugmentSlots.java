package com.portingdeadmods.nautec.content.augments;

import com.portingdeadmods.nautec.api.augments.AugmentSlot;
import com.portingdeadmods.nautec.api.client.renderer.augments.ModelPartGetter;

public enum AugmentSlots implements AugmentSlot {
    HEAD(model -> model.head),
    EYES(model -> model.head),
    BODY(model -> model.body),
    LUNG,
    LEFT_ARM(model -> model.leftArm),
    RIGHT_ARM(model -> model.rightArm),
    LEFT_LEG(model -> model.leftLeg),
    RIGHT_LEG(model -> model.rightLeg),
    HEART;

    private final ModelPartGetter modelPartGetterFunc;

    AugmentSlots(ModelPartGetter modelPartGetterFunc) {
        this.modelPartGetterFunc = modelPartGetterFunc;
    }

    AugmentSlots() {
        this.modelPartGetterFunc = $ -> null;
    }

    @Override
    public ModelPartGetter getModelPart() {
        return modelPartGetterFunc;
    }
}
