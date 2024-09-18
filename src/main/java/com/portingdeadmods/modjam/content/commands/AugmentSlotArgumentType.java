package com.portingdeadmods.modjam.content.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.portingdeadmods.modjam.MJRegistries;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.AugmentSlot;
import com.portingdeadmods.modjam.api.augments.AugmentType;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class AugmentSlotArgumentType implements ArgumentType<AugmentSlot> {
    private static final AugmentSlotArgumentType INSTANCE = new AugmentSlotArgumentType();

    private static final DynamicCommandExceptionType UNKNOWN_TYPE = new DynamicCommandExceptionType(
            type -> Component.literal("Unknown augment slot: " + type));
    public static Set<String> suggestions = new HashSet<>();

    private AugmentSlotArgumentType() {
    }

    public static AugmentSlotArgumentType getInstance() {
        return INSTANCE;
    }

    @Override
    public AugmentSlot parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation read = ResourceLocation.read(reader);
        ModJam.LOGGER.debug("Res: {}", read);
        AugmentSlot augmentSlot = MJRegistries.AUGMENT_SLOT.get(ResourceKey.create(MJRegistries.AUGMENT_SLOT_KEY, read));
        if (augmentSlot != null) {
            return augmentSlot;
        }
        throw UNKNOWN_TYPE.create(read.toString());
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(suggestions, builder);
    }
}
