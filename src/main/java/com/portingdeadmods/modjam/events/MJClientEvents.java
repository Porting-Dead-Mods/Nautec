package com.portingdeadmods.modjam.events;

import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.modjam.api.fluids.BaseFluidType;
import com.portingdeadmods.modjam.client.hud.PrismMonocleOverlay;
import com.portingdeadmods.modjam.client.model.DrainTopModel;
import com.portingdeadmods.modjam.client.renderer.blockentities.DrainBERenderer;
import com.portingdeadmods.modjam.registries.MJBlockEntityTypes;
import net.minecraft.client.Camera;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.FastColor;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.neoforged.neoforge.client.event.RegisterGuiLayersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
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
                            return FastColor.ARGB32.color(color.x, color.y, color.z, color.w);
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
        }

        @SubscribeEvent
        public static void registerBERenderers(EntityRenderersEvent.RegisterRenderers event) {
            event.registerBlockEntityRenderer(MJBlockEntityTypes.AQUATIC_CATALYST.get(), LaserBlockEntityRenderer::new);
            event.registerBlockEntityRenderer(MJBlockEntityTypes.DRAIN.get(), DrainBERenderer::new);
            event.registerBlockEntityRenderer(MJBlockEntityTypes.DRAIN_PART.get(), LaserBlockEntityRenderer::new);
        }

        @SubscribeEvent
        public static void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
            event.registerLayerDefinition(DrainTopModel.LAYER_LOCATION, DrainTopModel::createBodyLayer);
        }
    }
}
