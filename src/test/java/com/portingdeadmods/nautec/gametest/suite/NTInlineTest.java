package com.portingdeadmods.nautec.gametest.suite;

import com.mojang.serialization.MapCodec;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestInstance;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.function.Consumer;

public final class NTInlineTest extends GameTestInstance {
    private static final MapCodec<NTInlineTest> CODEC = MapCodec.unit(() -> {
        throw new UnsupportedOperationException("NTInlineTest is registered programmatically, not decoded");
    });

    private final Consumer<GameTestHelper> body;

    public NTInlineTest(TestData<Holder<TestEnvironmentDefinition<?>>> info, Consumer<GameTestHelper> body) {
        super(info);
        this.body = body;
    }

    @Override
    public void run(GameTestHelper helper) {
        body.accept(helper);
    }

    @Override
    public MapCodec<? extends GameTestInstance> codec() {
        return CODEC;
    }

    @Override
    protected MutableComponent typeDescription() {
        return Component.literal("nautec:inline");
    }
}
