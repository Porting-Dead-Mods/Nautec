package com.portingdeadmods.nautec.gametest.suite;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.neoforge.event.RegisterGameTestsEvent;

public final class NTGameTestRegistration {
    private NTGameTestRegistration() {
    }

    public static void registerTests(RegisterGameTestsEvent event) {
        NTTestRegistrar r = new NTTestRegistrar(event);

        r.add("framework/arena_smoke", 20, helper -> {
            BlockPos pos = new BlockPos(4, 1, 4);
            helper.setBlock(pos, Blocks.STONE.defaultBlockState());
            helper.succeedWhenBlockPresent(Blocks.STONE, pos);
        });

        PowerAndLaserTests.register(r);
        MachineTests.register(r);
        MultiblockTests.register(r);
        CrateAndCapabilityTests.register(r);
        RecipeAndBacteriaTests.register(r);
        PersistenceTests.register(r);
        WorldgenInjectionTests.register(r);
        ContentIntegrityTests.register(r);
    }
}
