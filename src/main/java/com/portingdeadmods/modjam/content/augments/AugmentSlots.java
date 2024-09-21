package com.portingdeadmods.modjam.content.augments;

import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.api.client.renderer.entities.PartVisible;

public enum AugmentSlots implements AugmentSlot {
    HEAD((renderer, visible) -> renderer.getModel().head.visible = visible),
    EYES,
    BODY((renderer, visible) -> renderer.getModel().body.visible = visible),
    LUNG,
    LEFT_ARM((renderer, visible) -> renderer.getModel().leftArm.visible = visible),
    RIGHT_ARM((renderer, visible) -> renderer.getModel().rightArm.visible = visible),
    LEFT_LEG((renderer, visible) -> renderer.getModel().leftLeg.visible = visible),
    RIGHT_LEG((renderer, visible) -> renderer.getModel().rightLeg.visible = visible),
    HEART((renderer, visible) -> renderer.getModel().head.visible = visible);

    private final PartVisible partVisibleFunc;

    AugmentSlots(PartVisible partVisibleFunc) {
        this.partVisibleFunc = partVisibleFunc;
    }


    AugmentSlots() {
        this.partVisibleFunc = (ignored, ignored1) -> {
        };
    }

    public PartVisible getCancelRenderingFunc() {
        return partVisibleFunc;
    }
}
