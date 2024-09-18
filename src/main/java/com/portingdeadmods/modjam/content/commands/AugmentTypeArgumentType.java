package com.portingdeadmods.modjam.content.commands;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandExceptionType;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import com.portingdeadmods.modjam.MJRegistries;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.augments.AugmentType;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

public class AugmentTypeArgumentType implements ArgumentType<AugmentType<?>> {
    private static final AugmentTypeArgumentType INSTANCE = new AugmentTypeArgumentType();

    private static final DynamicCommandExceptionType UNKNOWN_TYPE = new DynamicCommandExceptionType(
            type -> Component.literal("Unknown augment type: " + type));
    public static Set<String> suggestions = new HashSet<>();

    private AugmentTypeArgumentType() {
    }

    public static AugmentTypeArgumentType getInstance() {
        return INSTANCE;
    }

    @Override
    public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
        return SharedSuggestionProvider.suggest(suggestions, builder);
    }

    @Override
    public AugmentType<?> parse(StringReader reader) throws CommandSyntaxException {
        ResourceLocation read = ResourceLocation.read(reader);
        AugmentType<?> augmentType = MJRegistries.AUGMENT_TYPE.get(read);
        if (augmentType != null) {
            return augmentType;
        }
        throw UNKNOWN_TYPE.create(read.toString());
    }
}
