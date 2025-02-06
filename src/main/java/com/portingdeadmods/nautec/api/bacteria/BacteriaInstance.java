package com.portingdeadmods.nautec.api.bacteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.content.bacteria.SimpleCollapsedStats;
import com.portingdeadmods.nautec.registries.NTBacterias;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import com.portingdeadmods.nautec.utils.Utils;
import net.minecraft.ChatFormatting;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;

import javax.swing.plaf.basic.ComboPopup;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class BacteriaInstance {
    public static final BacteriaInstance EMPTY = new BacteriaInstance(NTBacterias.EMPTY, 0, SimpleCollapsedStats.EMPTY, false);
    public static final Codec<BacteriaInstance> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Bacteria.BACTERIA_TYPE_CODEC.fieldOf("bacteria").forGetter(BacteriaInstance::getBacteria),
            Codec.LONG.fieldOf("amount").forGetter(BacteriaInstance::getSize),
            CollapsedBacteriaStats.CODEC.fieldOf("stats").forGetter(BacteriaInstance::getStats),
            Codec.BOOL.fieldOf("analyzed").forGetter(BacteriaInstance::isAnalyzed)
    ).apply(instance, BacteriaInstance::new));
    public static final StreamCodec<RegistryFriendlyByteBuf, BacteriaInstance> STREAM_CODEC = StreamCodec.composite(
            Bacteria.BACTERIA_TYPE_STREAM_CODEC,
            BacteriaInstance::getBacteria,
            ByteBufCodecs.VAR_LONG,
            BacteriaInstance::getSize,
            CollapsedBacteriaStats.STREAM_CODEC,
            BacteriaInstance::getStats,
            ByteBufCodecs.BOOL,
            BacteriaInstance::isAnalyzed,
            BacteriaInstance::new
    );

    private final ResourceKey<Bacteria> bacteria;
    private long size;
    private CollapsedBacteriaStats stats;
    private boolean analyzed;

    public BacteriaInstance(ResourceKey<Bacteria> bacteria, long size, CollapsedBacteriaStats stats, boolean analyzed) {
        this.bacteria = bacteria;
        this.size = size;
        this.stats = stats;
        this.analyzed = analyzed;
    }

    public static BacteriaInstance roll(ResourceKey<Bacteria> bacteria, HolderLookup.Provider lookupProvider) {
        Bacteria bacteria1 = BacteriaHelper.getBacteria(lookupProvider, bacteria);
        return new BacteriaInstance(bacteria, bacteria1.rollSize(), bacteria1.stats().collapse(), false);
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getSize() {
        return this.size;
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

    public void setAnalyzed(boolean analyzed) {
        this.analyzed = analyzed;
    }

    public boolean isAnalyzed() {
        return analyzed;
    }

    public BacteriaInstance copy() {
        return new BacteriaInstance(this.bacteria, size, this.stats.copy(), this.analyzed);
    }

    public BacteriaInstance copyWithSize(long size) {
        if (size > 0) {
            return new BacteriaInstance(this.bacteria, size, this.stats.copy(), this.analyzed);
        }
        return BacteriaInstance.EMPTY;
    }

    public void shrink(int amount) {
        setSize(getSize() - amount);
    }

    public void grow(int amount) {
        setSize(getSize() + amount);
    }

    public boolean is(ResourceKey<Bacteria> bacteria) {
        return this.bacteria.equals(bacteria);
    }

    public boolean isEmpty() {
        return bacteria == NTBacterias.EMPTY;
    }

    public Component getName() {
        return Utils.registryTranslation(this.bacteria);
    }

    public List<Component> getTooltip() {
        return getTooltip(false, this.analyzed);
    }

    public List<Component> getTooltip(boolean showAnalyzedValues) {
        return getTooltip(showAnalyzedValues, this.analyzed);
    }

    public List<Component> getTooltip(boolean showMutatorValues, boolean analyzed) {
        List<Component> tooltip = new ArrayList<>();
        tooltip.add(getName());
        MutableComponent statsCaption = Component.literal("Stats: ").withStyle(ChatFormatting.WHITE);
        if (analyzed) {
            tooltip.add(Component.literal("Size: " + this.size));
            tooltip.add(statsCaption);
            tooltip.addAll(showMutatorValues ? this.stats.statsTooltipWithMutatorValues() : this.stats.statsTooltip());
        } else {
            statsCaption.append(Component.literal("???").withStyle(ChatFormatting.YELLOW));
            tooltip.add(statsCaption);
        }
        return tooltip;
    }

    public List<Component> getExpandableTooltip(boolean hasShiftDown, boolean hasControlDown) {
        List<Component> tooltipComponents = new ArrayList<>();
        tooltipComponents.add(Component.literal("Name: ").append(Utils.registryTranslation(bacteria)).withStyle(ChatFormatting.WHITE));
        if (bacteria != NTBacterias.EMPTY) {
            MutableComponent statsCaption = Component.literal("Stats: ").withStyle(ChatFormatting.WHITE);
            if (isAnalyzed()) {
                tooltipComponents.add(Component.literal("Size: " + this.size));
                if (!hasShiftDown) {
                    statsCaption
                            .append(Component.literal("<").withStyle(ChatFormatting.WHITE))
                            .append(Component.literal("Shift").withStyle(ChatFormatting.YELLOW))
                            .append(Component.literal(">").withStyle(ChatFormatting.WHITE))
                            .append(Component.literal(" and ").withStyle(ChatFormatting.WHITE))
                            .append(Component.literal("<").withStyle(ChatFormatting.WHITE))
                            .append(Component.literal("Control").withStyle(ChatFormatting.YELLOW))
                            .append(Component.literal(">").withStyle(ChatFormatting.WHITE));
                }
                tooltipComponents.add(statsCaption);
                if (hasShiftDown && hasControlDown) {
                    for (Component tooltipComponent : getStats().statsTooltipWithMutatorValues()) {
                        tooltipComponents.add(Component.literal(" ".repeat(2)).append(tooltipComponent));
                    }
                }
                if (hasShiftDown && !hasControlDown) {
                    for (Component tooltipComponent : getStats().statsTooltip()) {
                        tooltipComponents.add(Component.literal(" ".repeat(2)).append(tooltipComponent));
                    }
                }
            } else {
                statsCaption.append(Component.literal("???").withStyle(ChatFormatting.YELLOW));
                tooltipComponents.add(statsCaption);
            }
        }

        return tooltipComponents;
    }

    public static boolean isSameBacteriaAndStats(BacteriaInstance a, BacteriaInstance b) {
        return a.bacteria.equals(b.bacteria) && a.stats.equals(b.stats);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof BacteriaInstance instance)) return false;
        return size == instance.size && analyzed == instance.analyzed && Objects.equals(bacteria, instance.bacteria) && Objects.equals(stats, instance.stats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bacteria, size, stats, analyzed);
    }
}
