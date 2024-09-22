package com.portingdeadmods.nautec.client.model.armor;

import com.portingdeadmods.nautec.api.client.model.armor.NTArmorModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class DivingArmorModel {
    public static LayerDefinition createBodyLayer() {
        return NTArmorModel.createLayer(64, 64, parts -> parts.getHead()
                .addOrReplaceChild("head", CubeListBuilder.create().texOffs(22, 15).addBox(-5.0F, -11.0F, -5.0F, 9.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
                                .texOffs(26, 2).addBox(2.0F, -11.0F, -3.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                                .texOffs(24, 21).addBox(-5.0F, -11.0F, -3.0F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F))
                                .texOffs(29, 18).addBox(-4.0F, -12.0F, -6.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                                .texOffs(16, 29).addBox(-3.0F, -17.0F, -6.0F, 5.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                                .texOffs(9, 19).addBox(-4.0F, -17.0F, -6.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                                .texOffs(0, 19).addBox(2.0F, -17.0F, -6.0F, 1.0F, 5.0F, 1.0F, new CubeDeformation(0.0F))
                                .texOffs(28, 27).addBox(-4.0F, -18.0F, -6.0F, 7.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                                .texOffs(4, 0).addBox(-5.0F, -18.0F, -5.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                                .texOffs(0, 0).addBox(3.0F, -18.0F, -5.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
                                .texOffs(26, 0).addBox(-5.0F, -11.0F, 2.0F, 9.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                                .texOffs(0, 9).addBox(-5.0F, -19.0F, 3.0F, 9.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
                                .texOffs(0, 0).addBox(-5.0F, -19.0F, -5.0F, 9.0F, 1.0F, 8.0F, new CubeDeformation(0.0F))
                                .texOffs(0, 19).addBox(-6.0F, -18.0F, -4.0F, 1.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                                .texOffs(13, 12).addBox(4.0F, -18.0F, -4.0F, 1.0F, 7.0F, 7.0F, new CubeDeformation(0.0F))
                                .texOffs(22, 9).addBox(-3.0F, -20.0F, -3.0F, 5.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)),
                        PartPose.offset(0.5F, 10.25F, 0.5F)));
    }

}
