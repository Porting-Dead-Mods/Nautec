package com.portingdeadmods.modjam.api.augments;

import com.portingdeadmods.modjam.MJRegistries;
import com.portingdeadmods.modjam.data.MJDataAttachments;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.neoforged.neoforge.common.util.INBTSerializable;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.event.tick.PlayerTickEvent;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnknownNullability;

public abstract class Augment implements INBTSerializable<CompoundTag> {
    protected final AugmentType<?> augmentType;
    protected Player player;
    protected final AugmentSlot augmentSlot;

    // Serialized
    private int cooldown;

    public Augment(AugmentType<?> augmentType, AugmentSlot augmentSlot) {
        this.augmentType = augmentType;
        this.augmentSlot = augmentSlot;
    }

    public boolean replaceBodyPart() {
        return false;
    }

    // Return null if all slots are compatible
    public abstract @Nullable AugmentSlot[] getCompatibleSlots();

    public void setPlayer(Player player) {
        this.player = player;
    }

    public AugmentType<?> getAugmentType() {
        return augmentType;
    }

    public Player getPlayer() {
        return player;
    }

    public AugmentSlot getAugmentSlot() {
        return augmentSlot;
    }

    public int getCooldown() {
        return cooldown;
    }

    public void setCooldown(int cooldown) {
        this.cooldown = cooldown;
        setChanged();
    }

    public void breakBlock(BlockEvent.BreakEvent event) {

    }

    public void commonTick(PlayerTickEvent.Post event) {
        if (player == null) return;

        if (player.level().isClientSide) {
            clientTick(event);
        } else serverTick(event);
    }

    @Deprecated
    public void clientTick(PlayerTickEvent.Post event) {

    }

    @Deprecated
    public void serverTick(PlayerTickEvent.Post event) {

    }

    public void handleKeybindPress() {
    }

    public boolean isOnCooldown() {
        return getCooldown() > 0;
    }

    // Call this, whenever NBT should be saved
    protected final void setChanged() {
        player.setData(MJDataAttachments.AUGMENT_DATA_CHANGED, MJRegistries.AUGMENT_SLOT.getId(augmentSlot));
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putInt("cooldown", cooldown);
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.cooldown = nbt.getInt("cooldown");
    }
}
