package com.portingdeadmods.modjam.exampleCustom3DArmor;

import com.portingdeadmods.modjam.api.client.model.MJArmorModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeDeformation;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;

public class TestArmorModel {

    public static LayerDefinition createLayerDefinition(){
        return MJArmorModel.createLayer(1,1, parts ->{
            parts.getHead().addOrReplaceChild("test",
                    CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -9.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                            .texOffs(0, 0).addBox(1.0F, -9.0F, 0.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
                            .texOffs(0, 0).addBox(0.0F, -12.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));
            parts.getHead().addOrReplaceChild("curved", CubeListBuilder.create().texOffs(0, 0).addBox(-1.0F, -3.0F, 0.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -11.25F, 0.25F, 0.6981F, 0.0F, 0.0F));

            parts.getBody().addOrReplaceChild("slt",
                    CubeListBuilder.create().texOffs(0,0)
                            .addBox(-5,0,-3,10,12,6),
                    PartPose.ZERO);
        });
    }
}
