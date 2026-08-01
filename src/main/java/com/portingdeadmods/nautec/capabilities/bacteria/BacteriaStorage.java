package com.portingdeadmods.nautec.capabilities.bacteria;

import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.bacteria.BacteriaInstance;
import net.minecraft.core.NonNullList;
import net.minecraft.world.level.storage.ValueInput;
import net.minecraft.world.level.storage.ValueOutput;
import net.neoforged.neoforge.common.util.ValueIOSerializable;

import java.util.ArrayList;
import java.util.List;

public class BacteriaStorage implements IBacteriaStorage, ValueIOSerializable {
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
    public void serialize(ValueOutput out) {
        out.store("data", CODEC, this);
    }

    @Override
    public void deserialize(ValueInput in) {
        in.read("data", CODEC).ifPresent(newThis -> {
            this.bacteria = newThis.bacteria;
            this.slots = newThis.slots;
        });
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
