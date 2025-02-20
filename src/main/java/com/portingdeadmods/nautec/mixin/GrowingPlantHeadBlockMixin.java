package com.portingdeadmods.nautec.mixin;

import com.portingdeadmods.nautec.NTConfig;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.GrowingPlantHeadBlock;
import net.minecraft.world.level.block.KelpBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(GrowingPlantHeadBlock.class)
public class GrowingPlantHeadBlockMixin {
    @Inject(method = "getStateForPlacement", at = @At("RETURN"), cancellable = true)
    private void onGetStateForPlacement(LevelAccessor level, CallbackInfoReturnable<BlockState> cir) {
        BlockState state = cir.getReturnValue();
        if(state != null && state.getBlock() instanceof KelpBlock) {
            cir.setReturnValue(state.setValue(GrowingPlantHeadBlock.AGE, level.getRandom().nextInt(NTConfig.kelpHeight)));
        }
    }
}