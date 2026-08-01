package com.portingdeadmods.nautec.api.client.renderer.blockentities;

import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.core.Direction;

import java.util.ArrayList;
import java.util.List;

public class LaserRenderState extends BlockEntityRenderState {
    public final List<Beam> beams = new ArrayList<>();
    public float laserTime;
    public long gameTime;
    public float partialTick;

    public record Beam(Direction direction, int laserDistance, float shapeIndent) {
    }
}
