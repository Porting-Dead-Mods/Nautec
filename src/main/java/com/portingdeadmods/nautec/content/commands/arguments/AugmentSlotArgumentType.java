package com.portingdeadmods.nautec.content.commands.arguments;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.portingdeadmods.nautec.NTRegistries;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.augments.AugmentSlot;
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
        Nautec.LOGGER.debug("Res: {}", read);
        AugmentSlot augmentSlot = NTRegistries.AUGMENT_SLOT.get(ResourceKey.create(NTRegistries.AUGMENT_SLOT_KEY, read));
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
