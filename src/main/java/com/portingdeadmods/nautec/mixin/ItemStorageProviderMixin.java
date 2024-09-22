package com.portingdeadmods.nautec.mixin;

import com.portingdeadmods.nautec.content.blockentities.AquaticCatalystBlockEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import snownee.jade.addon.universal.ItemStorageProvider;
import snownee.jade.api.Accessor;
import snownee.jade.api.ITooltip;
import snownee.jade.api.config.IPluginConfig;

@Mixin(ItemStorageProvider.class)
public class ItemStorageProviderMixin {
    @Inject(method = "append", at = @At("HEAD"), cancellable = true)
    private static void onAppend(ITooltip tooltip, Accessor<?> accessor, IPluginConfig config, CallbackInfo ci) {
        if (accessor.getTarget() instanceof AquaticCatalystBlockEntity) {
            ci.cancel();
        }
    }
}
