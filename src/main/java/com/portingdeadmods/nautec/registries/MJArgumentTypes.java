package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.commands.arguments.AugmentSlotArgumentType;
import com.portingdeadmods.nautec.content.commands.arguments.AugmentTypeArgumentType;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.commands.synchronization.SingletonArgumentInfo;
import net.minecraft.core.registries.Registries;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class MJArgumentTypes {
    public static final DeferredRegister<ArgumentTypeInfo<?, ?>> ARGUMENT_TYPES =
            DeferredRegister.create(Registries.COMMAND_ARGUMENT_TYPE, Nautec.MODID);

    public static final Supplier<ArgumentTypeInfo<?, ?>> AUGMENT_SLOT_ARGUMENT =
            ARGUMENT_TYPES.register("augment_slot",
                    () -> ArgumentTypeInfos.registerByClass(AugmentSlotArgumentType.class,
                            SingletonArgumentInfo.contextFree(AugmentSlotArgumentType::getInstance)));
    public static final Supplier<ArgumentTypeInfo<?, ?>> AUGMENT_TYPE_ARGUMENT =
            ARGUMENT_TYPES.register("augment_type",
                    () -> ArgumentTypeInfos.registerByClass(AugmentTypeArgumentType.class,
                            SingletonArgumentInfo.contextFree(AugmentTypeArgumentType::getInstance)));
}
