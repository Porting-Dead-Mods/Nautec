package com.portingdeadmods.nautec.capabilities.bacteria;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.DataResult;
import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import net.minecraft.core.HolderLookup;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceKey;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

public class BacteriaStorage implements IBacteriaStorage, INBTSerializable<CompoundTag> {
    private ResourceKey<Bacteria> bacteria;
    private long bacteriaAmount;

    @Override
    public void setBacteria(ResourceKey<Bacteria> bacteria) {
        this.bacteria = bacteria;
    }

    @Override
    public ResourceKey<Bacteria> getBacteria() {
        return this.bacteria;
    }

    @Override
    public void setBacteriaAmount(long bacteriaAmount) {
        this.bacteriaAmount = bacteriaAmount;
    }

    @Override
    public long getBacteriaAmount() {
        return this.bacteriaAmount;
    }

    @Override
    public @UnknownNullability CompoundTag serializeNBT(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        tag.putLong("bacteriaAmount", this.bacteriaAmount);
        DataResult<Tag> tagDataResult = ResourceKey.codec(NTRegistries.BACTERIA_KEY).encodeStart(NbtOps.INSTANCE, this.bacteria);
        if (tagDataResult.isSuccess()) {
            tag.put("bacteriaType", tagDataResult.getOrThrow());
        } else {
            Nautec.LOGGER.error("Failed to encode bacteria, {}", tagDataResult.error().get().message());
        }
        return tag;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, CompoundTag nbt) {
        this.bacteriaAmount = nbt.getLong("bacteriaAmount");
        DataResult<Pair<ResourceKey<Bacteria>, Tag>> decodedBacteria = ResourceKey.codec(NTRegistries.BACTERIA_KEY).decode(NbtOps.INSTANCE, nbt.get("bacteriaType"));
        if (decodedBacteria.isSuccess()) {
            this.bacteria = decodedBacteria.result().get().getFirst();
        } else {
            Nautec.LOGGER.error("Failed to decode bacteria, {}", decodedBacteria.error().get().message());
        }
    }
}
