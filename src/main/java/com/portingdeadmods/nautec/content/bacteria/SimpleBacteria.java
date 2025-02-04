package com.portingdeadmods.nautec.content.bacteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaMutation;
import com.portingdeadmods.nautec.api.bacteria.BacteriaSerializer;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStats;
import com.portingdeadmods.nautec.utils.ranges.FloatRange;
import com.portingdeadmods.nautec.utils.ranges.IntRange;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

import java.util.ArrayList;
import java.util.List;

public record SimpleBacteria(Resource.ItemResource resource, BacteriaStats<?> stats, List<BacteriaMutation> mutations) implements Bacteria {
    public static Builder of() {
        return new Builder();
    }

    @Override
    public BacteriaSerializer<SimpleBacteria> getSerializer() {
        return Serializer.INSTANCE;
    }

    public static final class Serializer implements BacteriaSerializer<SimpleBacteria> {
        public static final Serializer INSTANCE = new Serializer();
        public static final MapCodec<SimpleBacteria> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
                Resource.ItemResource.CODEC.fieldOf("bacteria").forGetter(SimpleBacteria::resource),
                BacteriaStats.CODEC.fieldOf("stats").forGetter(SimpleBacteria::stats),
                Codec.list(BacteriaMutation.CODEC).fieldOf("mutations").forGetter(SimpleBacteria::mutations)
        ).apply(instance, SimpleBacteria::new));
        public static final StreamCodec<RegistryFriendlyByteBuf, SimpleBacteria> STREAM_CODEC = StreamCodec.composite(
                Resource.ItemResource.STREAM_CODEC,
                SimpleBacteria::resource,
                BacteriaStats.STREAM_CODEC,
                SimpleBacteria::stats,
                BacteriaMutation.STREAM_CODEC.apply(ByteBufCodecs.list()),
                SimpleBacteria::mutations,
                SimpleBacteria::new
        );

        private Serializer() {
        }

        @Override
        public MapCodec<SimpleBacteria> mapCodec() {
            return MAP_CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, SimpleBacteria> streamCodec() {
            return STREAM_CODEC;
        }
    }

    public static class Builder implements Bacteria.Builder<SimpleBacteria> {
        private Resource.ItemResource resource = new Resource.ItemResource(Items.AIR);
        private FloatRange growthRate = FloatRange.of(0F, 0F);
        private FloatRange mutationResistance = FloatRange.of(0F, 0F);
        private FloatRange productionRate = FloatRange.of(0F, 0F);
        private IntRange lifespan = IntRange.of(0, 0);
        private int color;
        private final List<BacteriaMutation> mutations = new ArrayList<>();

        public Builder resource(Item resource) {
            this.resource = new Resource.ItemResource(resource);
            return this;
        }

        public Builder growthRate(FloatRange growthRate) {
            this.growthRate = growthRate;
            return this;
        }

        public Builder mutationResistance(FloatRange mutationResistance) {
            this.mutationResistance = mutationResistance;
            return this;
        }

        public Builder productionRate(FloatRange productionRate) {
            this.productionRate = productionRate;
            return this;
        }

        public Builder lifespan(IntRange lifespan) {
            this.lifespan = lifespan;
            return this;
        }

        public Builder color(int color) {
            this.color = color;
            return this;
        }

        /**
         * Adds a possible mutation to the bacteria<br>
         * TODO: Add conditions besides catalyst
         *
         * @param catalyst Item that triggers the mutation
         * @param mutation The resulting bacteria
         * @param chance The chance of the mutation happening - (chance%)
         * @return
         */
        public Builder mutation(Item catalyst, ResourceKey<Bacteria> mutation, int chance) {
            if (chance < 0 || chance > 100) {
                throw new IllegalArgumentException("Chance must be between 0 and 100");
            }
            mutations.add(new BacteriaMutation(catalyst, mutation, chance));
            return this;
        }

        public SimpleBacteria build() {
            return new SimpleBacteria(resource, new SimpleBacteriaStats(growthRate, mutationResistance, productionRate, lifespan, color), mutations);
        }
    }
}
