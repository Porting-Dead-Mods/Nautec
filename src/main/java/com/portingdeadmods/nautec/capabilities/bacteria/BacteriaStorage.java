package com.portingdeadmods.nautec.capabilities.bacteria;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.neoforged.neoforge.common.util.INBTSerializable;
import org.jetbrains.annotations.UnknownNullability;

import java.util.ArrayList;
import java.util.List;

public class BacteriaStorage implements IBacteriaStorage, INBTSerializable<Tag> {
    private static final Codec<BacteriaStorage> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            BacteriaInstance.CODEC.listOf().fieldOf("bacteria").forGetter(BacteriaStorage::getBacteria),
            Codec.INT.fieldOf("slots").forGetter(BacteriaStorage::getBacteriaSlots)
    ).apply(instance, BacteriaStorage::new));

    private NonNullList<BacteriaInstance> bacteria;
    private int slots;

    public BacteriaStorage(int slots) {
        this.bacteria = NonNullList.withSize(slots, BacteriaInstance.EMPTY);
        this.slots = slots;
    }

    private BacteriaStorage(List<BacteriaInstance> bacteria, int slots) {
        this(slots);
        for (int i = 0; i < bacteria.size(); i++) {
            BacteriaInstance instance = bacteria.get(i);
            this.bacteria.set(i, instance);
        }
    }

    @Override
    public @UnknownNullability Tag serializeNBT(HolderLookup.Provider provider) {
        DataResult<Tag> tagDataResult = CODEC.encodeStart(NbtOps.INSTANCE, this);
        if (tagDataResult.isSuccess()) {
            return tagDataResult.result().get();
        }
        Nautec.LOGGER.error("Error encoding BacteriaStorage: {}", tagDataResult.error().get().message());
        return null;
    }

    @Override
    public void deserializeNBT(HolderLookup.Provider provider, Tag nbt) {
        DataResult<Pair<BacteriaStorage, Tag>> dataResult = CODEC.decode(NbtOps.INSTANCE, nbt);
        if (dataResult.isSuccess()) {
            BacteriaStorage newThis = dataResult.getOrThrow().getFirst();
            this.bacteria = newThis.bacteria;
            this.slots = newThis.slots;
        } else {
            Nautec.LOGGER.error("Error decoding BacteriaStorage: {}", dataResult.error().get().message());
        }
    }

    public NonNullList<BacteriaInstance> getBacteria() {
        return bacteria;
    }

    @Override
    public void setBacteria(int slot, BacteriaInstance bacteriaInstance) {
        this.bacteria.set(slot, bacteriaInstance);
    }

    @Override
    public BacteriaInstance getBacteria(int slot) {
        return this.bacteria.get(slot);
    }

    @Override
    public int getBacteriaSlots() {
        return slots;
    }
}
