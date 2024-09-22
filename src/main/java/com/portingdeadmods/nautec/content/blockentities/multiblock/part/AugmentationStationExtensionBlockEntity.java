package com.portingdeadmods.nautec.content.blockentities.multiblock.part;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockPartEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.content.items.RobotArmItem;
import com.portingdeadmods.nautec.content.menus.AugmentationStationExtensionMenu;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public class AugmentationStationExtensionBlockEntity extends LaserBlockEntity implements MultiblockPartEntity, MenuProvider {
    private float middleIndependentAngle;
    public float tipIndependentAngle;

    private int robotArmSpeed;

    private int animationTime;
    private int animationInterval;
    private Animation animation;

    private boolean hasRobotArm;
    private BlockPos controllerPos;

    public AugmentationStationExtensionBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.AUGMENTATION_STATION_EXTENSION.get(), blockPos, blockState);
        // Augment:0, Robot arm:1
        addItemHandler(2, 1, (slot, stack) -> (slot == 1 && stack.getItem() instanceof RobotArmItem) || (slot == 0));
        this.animation = Animation.IDLE;
    }

    public void equipAugment() {
        this.animationTime = 50;
        this.robotArmSpeed = 7;
        this.animation = Animation.FORWARD;
    }

    public void resetAnim() {
        this.animationTime = 0;
        this.robotArmSpeed = 0;
        this.middleIndependentAngle = 0;
        this.tipIndependentAngle = 0;
    }

    public Animation getAnimation() {
        return animation;
    }

    @Override
    public void commonTick() {
        super.commonTick();
        Nautec.LOGGER.debug("Slots: {}", getItemHandler().getSlots());
        if (animationInterval > 0) {
            animationInterval--;
            if (animation == Animation.BACKWARD && animationInterval == 35) {
                level.playSound(null, worldPosition, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS);
            }
        } else {
            if (animationTime > 0) {
                animationTime--;
                float oldMiddleAngle = middleIndependentAngle;
                middleIndependentAngle += (robotArmSpeed * 10 / 3f) * 0.25f;
                tipIndependentAngle += (middleIndependentAngle - oldMiddleAngle) * 1.5f;

                if (animation == Animation.FORWARD) {
                    if (animationTime == 0) {
                        this.animationInterval = 40;

                        this.animationTime = 50;
                        this.robotArmSpeed = -7;
                        this.animation = Animation.BACKWARD;
                    }
                }

                if (animation == Animation.BACKWARD) {
                    if (animationTime == 0) {
                        this.animation = Animation.IDLE;
                    }
                }
            }
        }
    }

    public float getMiddleIndependentAngle(float partialTicks) {
        return (middleIndependentAngle + partialTicks) / 360;
    }

    public float getTipIndependentAngle(float partialTicks) {
        return (tipIndependentAngle + partialTicks) / 360;
    }

    public boolean hasRobotArm() {
        return hasRobotArm;
    }

    public void setHasRobotArm(boolean hasRobotArm) {
        this.hasRobotArm = hasRobotArm;
    }

    @Override
    public ObjectSet<Direction> getLaserInputs() {
        return ObjectSet.of();
    }

    @Override
    public ObjectSet<Direction> getLaserOutputs() {
        return ObjectSet.of();
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    @Override
    public BlockPos getControllerPos() {
        return controllerPos;
    }

    @Override
    public void setControllerPos(BlockPos blockPos) {
        this.controllerPos = blockPos;
    }

    @Override
    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {
        super.loadData(tag, provider);
        this.controllerPos = BlockPos.of(tag.getLong("controllerPos"));
        this.hasRobotArm = tag.getBoolean("hasRobotArm");
    }

    @Override
    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {
        super.saveData(tag, provider);
        tag.putLong("controllerPos", controllerPos.asLong());
        tag.putBoolean("hasRobotArm", hasRobotArm);
    }

    @Override
    public @NotNull Component getDisplayName() {
        return Component.literal("Augmentation Station Extension");
    }

    @Override
    public @Nullable AbstractContainerMenu createMenu(int containerId, Inventory playerInventory, Player player) {
        return new AugmentationStationExtensionMenu(containerId, playerInventory, this);
    }

    public enum Animation {
        FORWARD,
        BACKWARD,
        IDLE
    }
}
