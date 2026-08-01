package com.portingdeadmods.nautec.api.client.renderer.robotArms;

import com.portingdeadmods.nautec.content.items.RobotArmItem;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.item.ItemStackRenderState;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;

public class RobotArmRenderState extends BlockEntityRenderState {
    @Nullable
    public RobotArmItem armItem;
    public Direction facing = Direction.NORTH;
    public float partialTick;
    public float middleAngle;
    public float prevMiddleAngle;
    public float tipAngle;
    public float prevTipAngle;
    public int lightAbove;
    public final ItemStackRenderState heldItem = new ItemStackRenderState();
}
