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
import com.portingdeadmods.nautec.client.model.block.DrainTopModel;
import com.portingdeadmods.nautec.client.model.block.PrismarineCrystalModel;
import com.portingdeadmods.nautec.client.model.block.RobotArmModel;
import com.portingdeadmods.nautec.client.model.block.WhiskModel;
import com.portingdeadmods.nautec.client.renderer.augments.GuardianEyeLaserRenderer;
import com.portingdeadmods.nautec.client.renderer.augments.SimpleAugmentRenderer;
import com.portingdeadmods.nautec.client.renderer.robotArms.ClawRobotArmRenderer;
import com.portingdeadmods.nautec.client.screen.AugmentScreen;
import com.portingdeadmods.nautec.client.screen.AugmentationStationExtensionScreen;
import com.portingdeadmods.nautec.client.screen.AugmentationStationScreen;
import com.portingdeadmods.nautec.content.augments.GuardianEyeAugment;
import com.portingdeadmods.nautec.events.helper.AugmentLayerRenderer;
import com.portingdeadmods.nautec.client.renderer.blockentities.*;
import com.portingdeadmods.nautec.client.screen.CrateScreen;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.network.AugmentationScreenPayload;
import com.portingdeadmods.nautec.registries.*;
import com.portingdeadmods.nautec.utils.ArmorModelsHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
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
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector4i;

public final class NTClientEvents {
    @EventBusSubscriber(modid = Nautec.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static final class ClientBus {
        public static final PrismarineCrystalItemRenderer PRISMARINE_CRYSTAL_RENDERER = new PrismarineCrystalItemRenderer();

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
            event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "scanner_info_overlay"), PrismMonocleOverlay.HUD);
            event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "diving_suit_overlay"), DivingSuitOverlay.HUD);
        }

        @SubscribeEvent
        public static void registerClientExtensions(RegisterClientExtensionsEvent event) {
            // Fluid renderers
            for (FluidType fluidType : NeoForgeRegistries.FLUID_TYPES) {
                if (fluidType instanceof BaseFluidType baseFluidType) {
                    event.registerFluidType(new IClientFluidTypeExtensions() {
                        @Override
                        public @NotNull ResourceLocation getStillTexture() {
                            return baseFluidType.getStillTexture();
                        }

                        @Override
                        public @NotNull ResourceLocation getFlowingTexture() {
                            return baseFluidType.getFlowingTexture();
                        }

                        @Override
                        public @Nullable ResourceLocation getOverlayTexture() {
                            return baseFluidType.getOverlayTexture();
                        }

                        @Override
                        public int getTintColor() {
                            Vector4i color = baseFluidType.getColor();
                            return FastColor.ARGB32.color(color.w, color.x, color.y, color.z);
                        }

                        @Override
                        public @NotNull Vector3f modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector3f fluidFogColor) {
                            Vector4i color = baseFluidType.getColor();
                            return new Vector3f(color.x / 255f, color.y / 255f, color.z / 255f);
                        }

                        @Override
                        public void modifyFogRender(Camera camera, FogRenderer.FogMode mode, float renderDistance, float partialTick, float nearDistance, float farDistance, FogShape shape) {
                            RenderSystem.setShaderFogStart(1f);
                            RenderSystem.setShaderFogEnd(6f); // distance when the fog starts
                        }
                    }, baseFluidType);
                }
            }

            event.registerItem(new IClientItemExtensions() {
                @Override
                public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                    return PRISMARINE_CRYSTAL_RENDERER;
                }
            }, NTBlocks.PRISMARINE_CRYSTAL.asItem());

            event.registerItem(new IClientItemExtensions() {
                @Override
                public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack
                        itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                    return ArmorModelsHandler.armorModel(ArmorModelsHandler.divingSuit, equipmentSlot);
                }
            }, NTItems.DIVING_HELMET);
        }

        @SubscribeEvent
        public static void registerBERenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerEntityRenderer(NTEntites.THROWN_BOUNCING_TRIDENT.get(), ThrownTridentRenderer::new);
            event.registerBlockEntityRenderer(NTBlockEntityTypes.AQUATIC_CATALYST.get(), LaserBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(NTBlockEntityTypes.PRISMARINE_LASER_RELAY.get(), LaserBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(NTBlockEntityTypes.CREATIVE_POWER_SOURCE.get(), LaserBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(NTBlockEntityTypes.LASER_JUNCTION.get(), LaserBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(NTBlockEntityTypes.LONG_DISTANCE_LASER.get(), LongDistanceLaserBERenderer::new);
            event.registerBlockEntityRenderer(NTBlockEntityTypes.PRISMARINE_CRYSTAL.get(), PrismarineCrystalBERenderer::new);
            event.registerBlockEntityRenderer(NTBlockEntityTypes.MIXER.get(), MixerBERenderer::new);
            event.registerBlockEntityRenderer(NTBlockEntityTypes.DRAIN_PART.get(), DrainBERenderer::new);
            event.registerBlockEntityRenderer(NTBlockEntityTypes.AUGMENTATION_STATION_EXTENSION.get(), AugmentStationExtensionBERenderer::new);
            AugmentLayerRenderer.registerRenderer(NTAugments.DOLPHIN_FIN.get(),
                    ctx -> new SimpleAugmentRenderer<>(DolphinFinModel::new, DolphinFinModel.LAYER_LOCATION, DolphinFinModel.MATERIAL, ctx));
            AugmentStationExtensionBERenderer.registerRenderer(NTItems.CLAW_ROBOT_ARM.get(), ClawRobotArmRenderer::new);
        }

        @SubscribeEvent
        public static void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
            event.registerReloadListener((ResourceManagerReloadListener) resourceManager -> {
                AugmentLayerRenderer.createRenderers();
                AugmentStationExtensionBERenderer.createRenderers();
            });
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(DrainTopModel.LAYER_LOCATION, DrainTopModel::createBodyLayer);
            event.registerLayerDefinition(PrismarineCrystalModel.LAYER_LOCATION, PrismarineCrystalModel::createBodyLayer);
            event.registerLayerDefinition(WhiskModel.LAYER_LOCATION, WhiskModel::createBodyLayer);
            event.registerLayerDefinition(RobotArmModel.LAYER_LOCATION, RobotArmModel::createBodyLayer);
            event.registerLayerDefinition(DolphinFinModel.LAYER_LOCATION, DolphinFinModel::createBodyLayer);
            ArmorModelsHandler.registerLayers(event);
        }

        @SubscribeEvent
        public static void registerMenus(RegisterMenuScreensEvent event) {
            event.register(NTMenuTypes.CRATE.get(), CrateScreen::new);
            event.register(NTMenuTypes.AUGMENT_STATION_EXTENSION.get(), AugmentationStationExtensionScreen::new);
            event.register(NTMenuTypes.AUGMENTS.get(), AugmentScreen::new);
        }

        @SubscribeEvent
        public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemProperties.register(NTItems.AQUARINE_SWORD.get(), ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "enabled"),
                        (stack, level, living, id) -> NTDataComponentsUtils.isAbilityEnabledNBT(stack));
                ItemProperties.register(NTItems.AQUARINE_PICKAXE.get(), ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "enabled"),
                        (stack, level, living, id) -> NTDataComponentsUtils.isAbilityEnabledNBT(stack));
                ItemProperties.register(NTItems.AQUARINE_AXE.get(), ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "enabled"),
                        (stack, level, living, id) -> NTDataComponentsUtils.isAbilityEnabledNBT(stack));
                ItemProperties.register(NTItems.AQUARINE_SHOVEL.get(), ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "enabled"),
                        (stack, level, living, id) -> NTDataComponentsUtils.isAbilityEnabledNBT(stack));
                ItemProperties.register(NTItems.AQUARINE_HOE.get(), ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "enabled"),
                        (stack, level, living, id) -> NTDataComponentsUtils.isAbilityEnabledNBT(stack));
            });
        }
    }


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
        public static void onRenderPlayer(RenderPlayerEvent.Post event) {
            PlayerRenderer renderer = event.getRenderer();
            renderer.addLayer(new AugmentLayerRenderer(renderer));
        }

        @SubscribeEvent
        public static void onClientTick(ClientTickEvent.Post event) {
            while (NTKeybinds.AUGMENT_SCREEN_KEYBIND.get().consumeClick()) {
                PacketDistributor.sendToServer(new AugmentationScreenPayload((byte) 0));
            }
        }

        @SubscribeEvent
        public static void renderGuardianEye(RenderLevelStageEvent event){
            // Nautec.LOGGER.info("render");
            MultiBufferSource buf = Minecraft.getInstance().renderBuffers().bufferSource();
            if (GuardianEyeAugment.laserFiredPos != null && GuardianEyeAugment.timeLeft > 0) {
                GuardianEyeAugment.timeLeft --;
                Nautec.LOGGER.info("RENDER LASER");
                GuardianEyeLaserRenderer.renderLaser(Minecraft.getInstance().player.position(), GuardianEyeAugment.laserFiredPos, event.getPoseStack(), buf);
            }
        }
    }
}
