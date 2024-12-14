package com.portingdeadmods.nautec.events;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.nautec.api.client.renderer.items.PrismarineCrystalItemRenderer;
import com.portingdeadmods.nautec.api.fluids.BaseFluidType;
import com.portingdeadmods.nautec.client.hud.DivingSuitOverlay;
import com.portingdeadmods.nautec.client.hud.PrismMonocleOverlay;
import com.portingdeadmods.nautec.client.model.augment.DolphinFinModel;
import com.portingdeadmods.nautec.client.model.augment.GuardianEyeModel;
import com.portingdeadmods.nautec.client.model.block.DrainTopModel;
import com.portingdeadmods.nautec.client.model.block.PrismarineCrystalModel;
import com.portingdeadmods.nautec.client.model.block.RobotArmModel;
import com.portingdeadmods.nautec.client.model.block.WhiskModel;
import com.portingdeadmods.nautec.client.renderer.augments.GuardianEyeRenderer;
import com.portingdeadmods.nautec.client.renderer.augments.SimpleAugmentRenderer;
import com.portingdeadmods.nautec.client.renderer.robotArms.ClawRobotArmRenderer;
import com.portingdeadmods.nautec.client.screen.AugmentationStationExtensionScreen;
import com.portingdeadmods.nautec.content.menus.AugmentationViewerScreen;
import com.portingdeadmods.nautec.events.helper.AugmentLayerRenderer;
import com.portingdeadmods.nautec.client.renderer.blockentities.*;
import com.portingdeadmods.nautec.client.screen.CrateScreen;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.events.helper.AugmentSlotsRenderer;
import com.portingdeadmods.nautec.registries.*;
import com.portingdeadmods.nautec.utils.ArmorModelsHandler;
import com.portingdeadmods.nautec.utils.AugmentHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector4i;

public final class NTClientEvents {
    @EventBusSubscriber(modid = Nautec.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
    public static final class ClientInGameBus {
        @SubscribeEvent
        public static void onRenderFog(ViewportEvent.RenderFog event) {
            Entity cameraEntity = Minecraft.getInstance().cameraEntity;
            if (cameraEntity instanceof Player player) {
                if (cameraEntity.isUnderWater() && player.getItemBySlot(EquipmentSlot.HEAD).is(NTItems.DIVING_HELMET.get())) {
                    event.setNearPlaneDistance(-8.0f);
                    event.setFarPlaneDistance(250.0f);
                    event.setFogShape(FogShape.CYLINDER);
                    event.setCanceled(true);
                }
            }
        }

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            Minecraft mc = Minecraft.getInstance();
            if (NTKeybinds.AUGMENT_SCREEN_KEYBIND.get().consumeClick()) {
                if (Minecraft.getInstance().screen == null || mc.player != null) {
                    if (!AugmentHelper.getAugments(mc.player).isEmpty())
                        Minecraft.getInstance().setScreen(new AugmentationViewerScreen(Component.literal("test"), Minecraft.getInstance().player));
                }
            }
        }
    }
}
