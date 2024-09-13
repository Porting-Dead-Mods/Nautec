package com.portingdeadmods.modjam.client.model.armor;

import com.portingdeadmods.modjam.api.client.model.MJArmorModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class DivingArmorModel {
    public static LayerDefinition createBodyLayer() {
        return MJArmorModel.createLayer(1, 1, parts -> {
            parts.getHead().addOrReplaceChild("bb_main", CubeListBuilder.create().texOffs(-21, -14).addBox(-8.0F, -2.0F, -8.0F, 16.0F, 2.0F, 16.0F, new CubeDeformation(0.0F))
                    .texOffs(-12, -8).addBox(-5.0F, -4.0F, -5.0F, 10.0F, 2.0F, 10.0F, new CubeDeformation(0.0F))
                    .texOffs(-18, -12).addBox(-7.0F, -16.0F, -7.0F, 14.0F, 12.0F, 14.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 24.0F, 0.0F));
        });
    }

}
