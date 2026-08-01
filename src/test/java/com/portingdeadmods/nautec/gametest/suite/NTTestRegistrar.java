package com.portingdeadmods.nautec.gametest.suite;

import com.portingdeadmods.nautec.Nautec;
import net.minecraft.core.Holder;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.resources.Identifier;
import net.minecraft.world.level.block.Rotation;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;

import java.util.List;
import java.util.function.Consumer;

public final class NTTestRegistrar {
    private static final Identifier ARENA = Nautec.rl("empty_9x9x9");

    private final RegisterGameTestsEvent event;
    private final Holder<TestEnvironmentDefinition<?>> environment;

    public NTTestRegistrar(RegisterGameTestsEvent event) {
        this.event = event;
        this.environment = event.registerEnvironment(Nautec.rl("suite"), new TestEnvironmentDefinition.AllOf(List.of()));
    }

    public void add(String name, int maxTicks, Consumer<GameTestHelper> body) {
        add(name, maxTicks, 0, body);
    }

    public void add(String name, int maxTicks, int setupTicks, Consumer<GameTestHelper> body) {
        add(name, ARENA, maxTicks, setupTicks, body);
    }

    public void add(String name, Identifier structure, int maxTicks, int setupTicks, Consumer<GameTestHelper> body) {
        TestData<Holder<TestEnvironmentDefinition<?>>> info = new TestData<>(
                environment,
                structure,
                maxTicks,
                setupTicks,
                true,
                Rotation.NONE
        );
        try {
            event.registerTest(Nautec.rl(name), new NTInlineTest(info, body));
        } catch (Throwable t) {
            Nautec.LOGGER.error("Failed to register gametest {}", name, t);
        }
    }
}
