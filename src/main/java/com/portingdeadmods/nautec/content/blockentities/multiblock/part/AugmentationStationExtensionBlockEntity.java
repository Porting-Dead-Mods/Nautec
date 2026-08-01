package com.portingdeadmods.nautec.content.blockentities.multiblock.part;

import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockEntity;
import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockPartEntity;
import com.portingdeadmods.nautec.api.multiblocks.Multiblock;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.content.blockentities.multiblock.controller.AugmentationStationBlockEntity;
import com.portingdeadmods.nautec.content.items.RobotArmItem;
import com.portingdeadmods.nautec.content.menus.AugmentationStationExtensionMenu;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import com.portingdeadmods.nautec.registries.NTItems;
import com.portingdeadmods.nautec.registries.NTMultiblocks;
import com.portingdeadmods.nautec.utils.MultiblockHelper;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import com.portingdeadmods.nautec.capabilities.item.ItemStackHandler;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Set;

public class AugmentationStationExtensionBlockEntity extends LaserBlockEntity implements MultiblockPartEntity, MenuProvider {
    public float prevMiddleIndependentAngle;
    public float prevTipIndependentAngle;
    public float middleIndependentAngle;
    public float tipIndependentAngle;

    private int robotArmSpeed;

    private boolean animationRunning;
    private int animationTime;
    private int animationInterval;
    private Animation animation;

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
        this.animationRunning = true;
    }

    public Animation getAnimation() {
        return animation;
    }

    @Override
    public void commonTick() {
        super.commonTick();
        if (animationInterval > 0) {
            animationInterval--;
            if (animation == Animation.BACKWARD && animationInterval == 35) {
                level.playSound(null, worldPosition, SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS);
            }
        } else {
            if (animationTime > 0) {
                animationTime--;
                prevMiddleIndependentAngle = middleIndependentAngle;
                prevTipIndependentAngle = tipIndependentAngle;

                middleIndependentAngle += (robotArmSpeed * 10 / 3f) * 0.25f;
                tipIndependentAngle += (middleIndependentAngle - prevMiddleIndependentAngle) * 1.5f;

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
                        this.animationRunning = false;
                    }
                }
            }
        }
    }

    @Override
    protected void onItemsChanged(int slot) {
        super.onItemsChanged(slot);
        ItemStackHandler handler = getItemStackHandler();
        if (isFormed()) {
            BlockPos controllerPos1 = getControllerPos();
            if (level.getBlockEntity(controllerPos1) instanceof AugmentationStationBlockEntity be) {
                if (!handler.getStackInSlot(1).isEmpty()) {
                    be.getAugmentItems().put(worldPosition, handler.getStackInSlot(0));
                } else if (be.getAugmentItems().containsKey(worldPosition)) {
                    be.getAugmentItems().remove(worldPosition);
                }
            }
        }
    }

    private @NotNull Boolean isFormed() {
        return getBlockState().getValue(Multiblock.FORMED);
    }

    public ItemStack getAugmentItem() {
        if (getItemStackHandler().getStackInSlot(1).is(NTItems.CLAW_ROBOT_ARM)) {
            return getItemStackHandler().getStackInSlot(0);
        }
        return ItemStack.EMPTY;
    }

    public float getPrevMiddleIndependentAngle(float partialTicks) {
        return animationRunning ? (prevMiddleIndependentAngle + partialTicks) / 360 : 0;
    }

    public float getPrevTipIndependentAngle(float partialTicks) {
        return animationRunning ? (prevTipIndependentAngle + partialTicks) / 360 : 0;
    }

    public float getMiddleIndependentAngle(float partialTicks) {
        return animationRunning ? (middleIndependentAngle + partialTicks) / 360 : 0;
    }

    public float getTipIndependentAngle(float partialTicks) {
        return animationRunning ? (tipIndependentAngle + partialTicks) / 360 : 0;
    }

    @Override
    public Set<Direction> getLaserInputs() {
        return ObjectSet.of(Direction.DOWN);
    }

    @Override
    public Set<Direction> getLaserOutputs() {
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
    public void onLoad() {
        super.onLoad();
        onItemsChanged(0);
    }

    @Override
    public void preRemoveSideEffects(BlockPos pos, BlockState state) {
        BlockPos controllerPos1 = getControllerPos();
        if (state.getValue(Multiblock.FORMED) && controllerPos1 != null && MultiblockEntity.UNFORMING.compareAndSet(false, true)) {
            try {
                MultiblockHelper.unform(NTMultiblocks.AUGMENTATION_STATION.get(), controllerPos1, level);
            } finally {
                MultiblockEntity.UNFORMING.set(false);
            }
        }
        super.preRemoveSideEffects(pos, state);
    }

    @Override
    protected void loadData(ValueInput in) {
        super.loadData(in);
        this.controllerPos = BlockPos.of(in.getLongOr("controllerPos", 0));
    }

    @Override
    protected void saveData(ValueOutput out) {
        super.saveData(out);
        if (controllerPos != null) {
            out.putLong("controllerPos", controllerPos.asLong());
        }
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
