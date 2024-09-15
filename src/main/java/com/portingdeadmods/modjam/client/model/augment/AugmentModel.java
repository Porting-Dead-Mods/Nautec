package com.portingdeadmods.modjam.client.model.augment;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.player.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AugmentModel extends EntityModel<Player> {

    public final ModelPart body;

    public AugmentModel(){
        ModelPart.Cube bodyCube = new ModelPart.Cube(
                0, 0,           // Texture U and V coordinates
                -4.0F, -12.0F, -2.0F, // Origin (x, y, z) of the body armor
                8.0F, 12.0F, 4.0F,   // Dimensions of the cube (x, y, z)
                0.0F, 0.0F, 0.0F,    // Growth along the axes (optional)
                false,          // No mirroring of the texture
                1.0F, 1.0F,     // Texture scaling factors (U, V)
                Set.of(Direction.NORTH, Direction.SOUTH, Direction.EAST, Direction.WEST, Direction.UP, Direction.DOWN) // All faces visible
        );

        this.body = new ModelPart(List.of(bodyCube), Map.of());
    }

    @Override
    public void setupAnim(Player entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, int color) {
        this.body.render(poseStack,buffer,packedLight,packedOverlay);
    }
}
