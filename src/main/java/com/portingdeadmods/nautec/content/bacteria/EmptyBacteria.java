package com.portingdeadmods.nautec.content.bacteria;

import com.mojang.serialization.MapCodec;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.bacteria.BacteriaMutation;
import com.portingdeadmods.nautec.api.bacteria.BacteriaSerializer;
import com.portingdeadmods.nautec.api.bacteria.BacteriaStats;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Items;

import java.util.List;

public class EmptyBacteria implements Bacteria, Bacteria.Builder<EmptyBacteria> {
    public static final EmptyBacteria INSTANCE = new EmptyBacteria();
    public static final BacteriaSerializer<EmptyBacteria> SERIALIZER = new BacteriaSerializer<>() {
        @Override
        public MapCodec<EmptyBacteria> mapCodec() {
            return MapCodec.unit(EmptyBacteria.INSTANCE);
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, EmptyBacteria> streamCodec() {
            return StreamCodec.unit(EmptyBacteria.INSTANCE);
        }
    };
    public static final Resource.ItemResource RESOURCE = new Resource.ItemResource(Items.AIR);

    private EmptyBacteria() {
    }

    @Override
    public Resource resource() {
        return RESOURCE;
    }

    @Override
    public SimpleBacteriaStats stats() {
        return SimpleBacteriaStats.EMPTY;
    }

    @Override
    public List<BacteriaMutation> mutations() {
        return List.of();
    }

    @Override
    public BacteriaSerializer<EmptyBacteria> getSerializer() {
        return SERIALIZER;
    }

    @Override
    public EmptyBacteria build() {
        return EmptyBacteria.INSTANCE;
    }
}
