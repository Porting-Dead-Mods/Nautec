package com.portingdeadmods.nautec.bacteria;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.utils.codec.CodecUtils;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.world.item.Item;

public interface Bacteria {
    /*
    Bacteria! We begin with only one!
    Bacteria! Two's what we then become!
    Bacteria! Each of us becomes two more!
    Bacteria! We are stronger than before!
    Bacteria! We keep growing at this rate!
    Bacteria! No longer shall we wait!
    Bacteria! The plan now unfolds!
    Bacteria! We will take over the world!
     */
    Item type();
    float growthRate();
    float mutationResistance();
    float productionRate();
    int lifespan();

    Codec<Bacteria> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(
                    CodecUtils.ITEM_CODEC.fieldOf("type").forGetter(Bacteria::type),
                    Codec.FLOAT.fieldOf("growth_rate").forGetter(Bacteria::growthRate),
                    Codec.FLOAT.fieldOf("mutation_resistance").forGetter(Bacteria::mutationResistance),
                    Codec.FLOAT.fieldOf("production_rate").forGetter(Bacteria::productionRate),
                    Codec.INT.fieldOf("lifespan").forGetter(Bacteria::lifespan)
            ).apply(instance, BacteriaImpl::new)
    );

    StreamCodec<RegistryFriendlyByteBuf, Bacteria> STREAM_CODEC = StreamCodec.composite(
            CodecUtils.ITEM_STREAM_CODEC,
            Bacteria::type,
            ByteBufCodecs.FLOAT,
            Bacteria::growthRate,
            ByteBufCodecs.FLOAT,
            Bacteria::mutationResistance,
            ByteBufCodecs.FLOAT,
            Bacteria::productionRate,
            ByteBufCodecs.INT,
            Bacteria::lifespan,
            BacteriaImpl::new
    );
}
