package com.portingdeadmods.modjam.api.blocks.blockentities;

public abstract class LaserBlock extends ContainerBlock {
    public LaserBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean tickingEnabled() {
        return true;
    }

}
