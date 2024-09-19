package com.portingdeadmods.modjam.events;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.modjam.api.client.renderer.items.PrismarineCrystalItemRenderer;
import com.portingdeadmods.modjam.api.fluids.BaseFluidType;
import com.portingdeadmods.modjam.api.items.IPowerItem;
import com.portingdeadmods.modjam.capabilities.MJCapabilities;
import com.portingdeadmods.modjam.capabilities.power.IPowerStorage;
import com.portingdeadmods.modjam.client.hud.PrismMonocleOverlay;
import com.portingdeadmods.modjam.client.model.block.DrainTopModel;
import com.portingdeadmods.modjam.client.model.block.PrismarineCrystalModel;
import com.portingdeadmods.modjam.client.model.block.WhiskModel;
import com.portingdeadmods.modjam.client.renderer.augments.AugmentLayerRenderer;
import com.portingdeadmods.modjam.client.renderer.blockentities.DrainBERenderer;
import com.portingdeadmods.modjam.client.renderer.blockentities.LongDistanceLaserBERenderer;
import com.portingdeadmods.modjam.client.renderer.blockentities.MixerBERenderer;
import com.portingdeadmods.modjam.client.renderer.blockentities.PrismarineCrystalBERenderer;
import com.portingdeadmods.modjam.client.screen.CrateScreen;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import com.portingdeadmods.modjam.registries.MJBlocks;
import com.portingdeadmods.modjam.registries.MJItems;
import com.portingdeadmods.modjam.registries.MJMenuTypes;
import com.portingdeadmods.modjam.utils.ArmorModelsHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.entity.player.PlayerRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.common.util.Lazy;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector4i;
import org.lwjgl.glfw.GLFW;

public final class MJClientEvents {
    @EventBusSubscriber(modid = ModJam.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.MOD)
    public static final class ClientBus {
        public static final PrismarineCrystalItemRenderer PRISMARINE_CRYSTAL_RENDERER = new PrismarineCrystalItemRenderer();

        public static final Lazy<KeyMapping> AUGMENT_TEST_KEYMAP = Lazy.of(() -> new KeyMapping(
                "Test", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_N, "ModJam"));

        @SubscribeEvent
        public static void registerBindings(RegisterKeyMappingsEvent event) {
            event.register(AUGMENT_TEST_KEYMAP.get());
        }

        @SubscribeEvent
        public static void registerGuiOverlays(RegisterGuiLayersEvent event) {
            event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(ModJam.MODID, "scanner_info_overlay"), PrismMonocleOverlay.HUD);
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
            }, MJBlocks.PRISMARINE_CRYSTAL.asItem());

            event.registerItem(new IClientItemExtensions() {
                @Override
                public @NotNull HumanoidModel<?> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack
                        itemStack, EquipmentSlot equipmentSlot, HumanoidModel<?> original) {
                    return ArmorModelsHandler.armorModel(ArmorModelsHandler.divingSuit, equipmentSlot);
                }
            }, MJItems.DIVING_HELMET);
        }

        @SubscribeEvent
        public static void registerBERenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(MJBlockEntityTypes.AQUATIC_CATALYST.get(), LaserBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(MJBlockEntityTypes.PRISMARINE_LASER_RELAY.get(), LaserBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(MJBlockEntityTypes.CREATIVE_POWER_SOURCE.get(), LaserBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(MJBlockEntityTypes.LASER_JUNCTION.get(), LaserBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(MJBlockEntityTypes.LONG_DISTANCE_LASER.get(), LongDistanceLaserBERenderer::new);
            event.registerBlockEntityRenderer(MJBlockEntityTypes.PRISMARINE_CRYSTAL.get(), PrismarineCrystalBERenderer::new);
            event.registerBlockEntityRenderer(MJBlockEntityTypes.MIXER.get(), MixerBERenderer::new);
            event.registerBlockEntityRenderer(MJBlockEntityTypes.DRAIN_PART.get(), DrainBERenderer::new);
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(DrainTopModel.LAYER_LOCATION, DrainTopModel::createBodyLayer);
            event.registerLayerDefinition(PrismarineCrystalModel.LAYER_LOCATION, PrismarineCrystalModel::createBodyLayer);
            event.registerLayerDefinition(WhiskModel.LAYER_LOCATION, WhiskModel::createBodyLayer);
            ArmorModelsHandler.registerLayers(event);
        }

        @SubscribeEvent
        public static void registerMenus(RegisterMenuScreensEvent event) {
            event.register(MJMenuTypes.CRATE.get(), CrateScreen::new);
        }
    }


    @EventBusSubscriber(modid = ModJam.MODID, value = Dist.CLIENT, bus = EventBusSubscriber.Bus.GAME)
    public static final class ClientInGameBus {

        @SubscribeEvent
        public static void onRenderFog(ViewportEvent.RenderFog event) {
            Entity cameraEntity = Minecraft.getInstance().cameraEntity;
            if (cameraEntity instanceof Player player) {
                if (cameraEntity.isUnderWater() && player.getItemBySlot(EquipmentSlot.HEAD).is(MJItems.DIVING_HELMET.get())) {
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
    }
}
