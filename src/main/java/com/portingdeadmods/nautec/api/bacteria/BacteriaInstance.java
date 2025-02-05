package com.portingdeadmods.nautec.api.bacteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.content.bacteria.SimpleBacteriaStats;
import com.portingdeadmods.nautec.registries.NTBacterias;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;

import java.util.Objects;

public final class BacteriaInstance {
    public static final BacteriaInstance EMPTY = new BacteriaInstance(NTBacterias.EMPTY, SimpleBacteriaStats.EMPTY);
    public static final Codec<BacteriaInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Bacteria.BACTERIA_TYPE_CODEC.fieldOf("bacteria").forGetter(BacteriaInstance::getBacteria),
            Codec.LONG.fieldOf("amount").forGetter(BacteriaInstance::getAmount),
            CollapsedBacteriaStats.CODEC.fieldOf("stats").forGetter(BacteriaInstance::getStats)
    ).apply(instance, BacteriaInstance::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BacteriaInstance> STREAM_CODEC = StreamCodec.composite(
            Bacteria.BACTERIA_TYPE_STREAM_CODEC,
            BacteriaInstance::getBacteria,
            ByteBufCodecs.VAR_LONG,
            BacteriaInstance::getAmount,
            CollapsedBacteriaStats.STREAM_CODEC,
            BacteriaInstance::getStats,
            BacteriaInstance::new
    );

    private final ResourceKey<Bacteria> bacteria;
    private long amount;
    private CollapsedBacteriaStats stats;

    public BacteriaInstance(ResourceKey<Bacteria> bacteria, HolderLookup.Provider lookup) {
        this(bacteria, 1, BacteriaHelper.getBacteria(lookup, bacteria).stats());
    }

    public BacteriaInstance(ResourceKey<Bacteria> bacteria, HolderLookup.Provider lookup, long amount) {
        this(bacteria, amount, BacteriaHelper.getBacteria(lookup, bacteria).stats());
    }

    public BacteriaInstance(ResourceKey<Bacteria> bacteria, BacteriaStats<?> stats) {
        this(bacteria, 1, stats);
    }

    public BacteriaInstance(ResourceKey<Bacteria> bacteria, long amount, BacteriaStats<?> stats) {
        this.bacteria = bacteria;
        this.amount = amount;
        this.stats = stats.collapse();
    }

    public BacteriaInstance(ResourceKey<Bacteria> bacteria, long amount, CollapsedBacteriaStats stats) {
        this.bacteria = bacteria;
        this.amount = amount;
        this.stats = stats;
    }

    public void setAmount(long amount) {
        this.amount = amount;
    }

    public long getAmount() {
        return this.amount;
    }

    public ResourceKey<Bacteria> getBacteria() {
        return bacteria;
    }

    public void setStats(CollapsedBacteriaStats stats) {
        this.stats = stats;
    }

    public CollapsedBacteriaStats getStats() {
        return this.stats;
    }

    public BacteriaInstance rollStats() {
        this.stats = this.stats.rollStats();
        return this;
    }

    public BacteriaInstance copy() {
        return new BacteriaInstance(this.bacteria, amount, this.stats.copy());
    }

    public BacteriaInstance copyWithAmount(long amount) {
        if (amount > 0) {
            return new BacteriaInstance(this.bacteria, amount, this.stats.copy());
        }
        return BacteriaInstance.EMPTY;
    }

    public boolean is(ResourceKey<Bacteria> bacteria) {
        return this.bacteria.equals(bacteria);
    }

    public boolean isEmpty() {
        return bacteria == NTBacterias.EMPTY;
    }

    public static boolean isSameBacteriaAndStats(BacteriaInstance a, BacteriaInstance b) {
        return a.bacteria.equals(b.bacteria) && a.stats.equals(b.stats);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BacteriaInstance that)) return false;
        return amount == that.amount && Objects.equals(bacteria, that.bacteria) && Objects.equals(stats, that.stats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bacteria, amount, stats);
    }
}
