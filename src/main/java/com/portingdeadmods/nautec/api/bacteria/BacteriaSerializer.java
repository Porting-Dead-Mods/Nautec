package com.portingdeadmods.nautec.api.bacteria;

import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

public interface BacteriaSerializer<T extends Bacteria> {
    MapCodec<T> mapCodec();

    StreamCodec<RegistryFriendlyByteBuf, T> streamCodec();
}
