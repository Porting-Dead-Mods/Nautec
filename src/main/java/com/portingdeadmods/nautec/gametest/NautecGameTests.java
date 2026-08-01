package com.portingdeadmods.nautec.gametest;

import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import com.portingdeadmods.nautec.Nautec;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.gametest.framework.GameTestHelper;
import net.minecraft.gametest.framework.GameTestInstance;
import net.minecraft.gametest.framework.TestData;
import net.minecraft.gametest.framework.TestEnvironmentDefinition;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModList;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

@EventBusSubscriber(modid = Nautec.MODID)
public class NautecGameTests {

    @SubscribeEvent
    public static void registerTestInstanceTypes(RegisterEvent event) {
        event.register(Registries.TEST_INSTANCE_TYPE, Nautec.rl("direct"), () -> DirectGameTestInstance.CODEC);
    }

    @SubscribeEvent
    public static void registerTests(RegisterGameTestsEvent event) {
        Holder<TestEnvironmentDefinition<?>> env = event.registerEnvironment(
                Nautec.rl("default"),
                new TestEnvironmentDefinition.AllOf());

        if (ModList.get().isLoaded("modonomicon")) {
            reg(event, "all_book_recipe_ids_resolve", BookRecipeIdGameTest::allBookRecipeIdsResolve, env, 100);
        }

        try {
            Class<?> suite = Class.forName("com.portingdeadmods.nautec.gametest.suite.NTGameTestRegistration");
            suite.getMethod("registerTests", RegisterGameTestsEvent.class).invoke(null, event);
        } catch (ClassNotFoundException ignored) {
        } catch (Throwable t) {
            Nautec.LOGGER.error("Failed to register nautec gametest suite", t);
        }
    }

    private static void reg(RegisterGameTestsEvent event, String name,
                            Consumer<GameTestHelper> function,
                            Holder<TestEnvironmentDefinition<?>> environment,
                            int timeoutTicks) {
        TestData<Holder<TestEnvironmentDefinition<?>>> testData = new TestData<>(
                environment, Nautec.rl("test_empty"), timeoutTicks, 0, true);
        GameTestInstance instance = new DirectGameTestInstance(name, function, testData);
        event.registerTest(Nautec.rl(name), instance);
    }

    public static class DirectGameTestInstance extends GameTestInstance {
        private static final Map<String, Consumer<GameTestHelper>> FUNCTIONS = new ConcurrentHashMap<>();
        private static final Consumer<GameTestHelper> NOOP = GameTestHelper::succeed;

        static final MapCodec<DirectGameTestInstance> CODEC = RecordCodecBuilder.mapCodec(i -> i.group(
                Codec.STRING.fieldOf("name").forGetter(d -> d.name),
                TestData.CODEC.forGetter(d -> d.testData)
        ).apply(i, DirectGameTestInstance::fromCodec));

        private final Consumer<GameTestHelper> testFunction;
        private final String name;
        private final TestData<Holder<TestEnvironmentDefinition<?>>> testData;

        public DirectGameTestInstance(String name, Consumer<GameTestHelper> testFunction,
                                      TestData<Holder<TestEnvironmentDefinition<?>>> info) {
            super(info);
            this.name = name;
            this.testFunction = testFunction;
            this.testData = info;
            FUNCTIONS.put(name, testFunction);
        }

        private static DirectGameTestInstance fromCodec(String name, TestData<Holder<TestEnvironmentDefinition<?>>> info) {
            return new DirectGameTestInstance(name, FUNCTIONS.getOrDefault(name, NOOP), info);
        }

        @Override
        public void run(GameTestHelper helper) {
            testFunction.accept(helper);
        }

        @Override
        public MapCodec<? extends GameTestInstance> codec() {
            return CODEC;
        }

        @Override
        protected MutableComponent typeDescription() {
            return Component.literal(name);
        }
    }
}
