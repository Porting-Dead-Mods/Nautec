package com.portingdeadmods.nautec;

import com.mojang.blaze3d.shaders.FogShape;
import com.mojang.blaze3d.systems.RenderSystem;
import com.portingdeadmods.nautec.api.bacteria.Bacteria;
import com.portingdeadmods.nautec.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.nautec.api.client.renderer.items.AnchorItemRenderer;
import com.portingdeadmods.nautec.api.client.renderer.items.PrismarineCrystalItemRenderer;
import com.portingdeadmods.nautec.api.fluids.BaseFluidType;
import com.portingdeadmods.nautec.client.hud.DivingSuitOverlay;
import com.portingdeadmods.nautec.client.hud.PrismMonocleOverlay;
import com.portingdeadmods.nautec.client.model.augment.DolphinFinModel;
import com.portingdeadmods.nautec.client.model.augment.GuardianEyeModel;
import com.portingdeadmods.nautec.client.model.block.*;
import com.portingdeadmods.nautec.client.renderer.augments.GuardianEyeRenderer;
import com.portingdeadmods.nautec.client.renderer.augments.SimpleAugmentRenderer;
import com.portingdeadmods.nautec.client.renderer.blockentities.*;
import com.portingdeadmods.nautec.client.renderer.robotArms.ClawRobotArmRenderer;
import com.portingdeadmods.nautec.client.screen.*;
import com.portingdeadmods.nautec.data.NTDataComponents;
import com.portingdeadmods.nautec.data.NTDataComponentsUtils;
import com.portingdeadmods.nautec.events.helper.AugmentLayerRenderer;
import com.portingdeadmods.nautec.events.helper.AugmentSlotsRenderer;
import com.portingdeadmods.nautec.registries.*;
import com.portingdeadmods.nautec.utils.ArmorModelsHandler;
import com.portingdeadmods.nautec.utils.BacteriaHelper;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.HumanoidModel;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.client.renderer.FogRenderer;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.FastColor;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.client.model.DynamicFluidContainerModel;
import net.neoforged.neoforge.fluids.FluidType;
import net.neoforged.neoforge.registries.NeoForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;
import org.joml.Vector4i;

@Mod(value = NautecClient.MODID, dist = Dist.CLIENT)
public final class NautecClient {
    public static final String MODID = "nautec";

    public NautecClient(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);

        modEventBus.addListener(this::registerBERenderers);
        modEventBus.addListener(this::registerGuiOverlays);
        modEventBus.addListener(this::registerClientExtensions);
        modEventBus.addListener(this::registerClientReloadListeners);
        modEventBus.addListener(this::registerLayerDefinitions);
        modEventBus.addListener(this::registerMenus);
        modEventBus.addListener(this::onFMLClientSetupEvent);
        modEventBus.addListener(this::registerColorHandlers);
    }

    public static final PrismarineCrystalItemRenderer PRISMARINE_CRYSTAL_RENDERER = new PrismarineCrystalItemRenderer();
    public static final AnchorItemRenderer ANCHOR_RENDERER = new AnchorItemRenderer();

    private void registerGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "scanner_info_overlay"), PrismMonocleOverlay.HUD);
        event.registerAboveAll(ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "diving_suit_overlay"), DivingSuitOverlay::render);
    }

    private void registerClientExtensions(RegisterClientExtensionsEvent event) {
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
            public @NotNull BlockEntityWithoutLevelRenderer getCustomRenderer() {
                return ANCHOR_RENDERER;
            }
        }, NTBlocks.ANCHOR.asItem());

        event.registerItem(new IClientItemExtensions() {
            @Override
            public @NotNull HumanoidModel<?> getHumanoidArmorModel(@NotNull LivingEntity livingEntity, @NotNull ItemStack
                    itemStack, @NotNull EquipmentSlot equipmentSlot, @NotNull HumanoidModel<?> original) {
                return ArmorModelsHandler.armorModel(ArmorModelsHandler.divingSuit, equipmentSlot);
            }
        }, NTItems.DIVING_HELMET);
    }

    private void registerBERenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(NTEntities.THROWN_BOUNCING_TRIDENT.get(), ThrownTridentRenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.AQUATIC_CATALYST.get(), LaserBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.PRISMARINE_LASER_RELAY.get(), LaserBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.CREATIVE_POWER_SOURCE.get(), LaserBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.LASER_JUNCTION.get(), LaserBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.LONG_DISTANCE_LASER.get(), LongDistanceLaserBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.PRISMARINE_CRYSTAL.get(), PrismarineCrystalBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.PRISMARINE_CRYSTAL_PART.get(), LaserBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.MIXER.get(), MixerBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.CHARGER.get(), ChargerBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.DRAIN.get(), DrainBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.DRAIN_PART.get(), LaserBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.AUGMENTATION_STATION_EXTENSION.get(), AugmentStationExtensionBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.ANCHOR.get(), AnchorBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.BACTERIAL_ANALYZER.get(), BacterialAnalyzerBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.FISHING_STATION.get(), FishingStationBERenderer::new);

        AugmentLayerRenderer.registerRenderer(NTAugments.DOLPHIN_FIN.get(),
                ctx -> new SimpleAugmentRenderer<>(DolphinFinModel::new, DolphinFinModel.LAYER_LOCATION, DolphinFinModel.MATERIAL, true, ctx));
        AugmentLayerRenderer.registerRenderer(NTAugments.GUARDIAN_EYE.get(), GuardianEyeRenderer::new);
        AugmentStationExtensionBERenderer.registerRenderer(NTItems.CLAW_ROBOT_ARM.get(), ClawRobotArmRenderer::new);

        AugmentSlotsRenderer.registerAugmentSlotModelPart(NTAugmentSlots.HEAD, model -> model.head);
        AugmentSlotsRenderer.registerAugmentSlotModelPart(NTAugmentSlots.EYES, model -> model.head);
        AugmentSlotsRenderer.registerAugmentSlotModelPart(NTAugmentSlots.LEFT_ARM, model -> model.leftArm);
        AugmentSlotsRenderer.registerAugmentSlotModelPart(NTAugmentSlots.RIGHT_ARM, model -> model.rightArm);
        AugmentSlotsRenderer.registerAugmentSlotModelPart(NTAugmentSlots.LEFT_LEG, model -> model.leftLeg);
        AugmentSlotsRenderer.registerAugmentSlotModelPart(NTAugmentSlots.RIGHT_LEG, model -> model.rightLeg);
        AugmentSlotsRenderer.registerAugmentSlotModelPart(NTAugmentSlots.BODY, model -> model.body);
    }

    private void registerClientReloadListeners(RegisterClientReloadListenersEvent event) {
        event.registerReloadListener((ResourceManagerReloadListener) resourceManager -> {
            AugmentLayerRenderer.createRenderers();
            AugmentStationExtensionBERenderer.createRenderers();
        });
    }

    private void registerLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(DrainTopModel.LAYER_LOCATION, DrainTopModel::createBodyLayer);
        event.registerLayerDefinition(PrismarineCrystalModel.LAYER_LOCATION, PrismarineCrystalModel::createBodyLayer);
        event.registerLayerDefinition(AnchorModel.LAYER_LOCATION, AnchorModel::createBodyLayer);
        event.registerLayerDefinition(FishingNetModel.LAYER_LOCATION, FishingNetModel::createBodyLayer);
        event.registerLayerDefinition(WhiskModel.LAYER_LOCATION, WhiskModel::createBodyLayer);
        event.registerLayerDefinition(RobotArmModel.LAYER_LOCATION, RobotArmModel::createBodyLayer);
        event.registerLayerDefinition(DolphinFinModel.LAYER_LOCATION, DolphinFinModel::createBodyLayer);
        event.registerLayerDefinition(GuardianEyeModel.LAYER_LOCATION, GuardianEyeModel::createBodyLayer);
        ArmorModelsHandler.registerLayers(event);
    }

    private void registerMenus(RegisterMenuScreensEvent event) {
        event.register(NTMenuTypes.CRATE.get(), CrateScreen::new);
        event.register(NTMenuTypes.AUGMENT_STATION_EXTENSION.get(), AugmentationStationExtensionScreen::new);

        event.register(NTMenuTypes.FISHING_STATION.get(), FishingStationScreen::new);
        event.register(NTMenuTypes.INCUBATOR.get(), IncubatorScreen::new);
        event.register(NTMenuTypes.MUTATOR.get(), MutatorScreen::new);
        event.register(NTMenuTypes.BIO_REACTOR.get(), BioReactorScreen::new);
        event.register(NTMenuTypes.MIXER.get(), MixerScreen::new);
        event.register(NTMenuTypes.BACTERIAL_ANALYZER.get(), BacterialAnalyzerScreen::new);
    }

    private void onFMLClientSetupEvent(final FMLClientSetupEvent event) {
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
            ItemProperties.register(NTItems.PETRI_DISH.get(), ResourceLocation.fromNamespaceAndPath(Nautec.MODID, "has_bacteria"),
                    (stack, level, living, id) -> NTDataComponentsUtils.hasBacteria(stack));
        });
    }

    private void registerColorHandlers(RegisterColorHandlersEvent.Item event) {
        event.register((stack, layer) -> {
            ResourceKey<Bacteria> bacteriaType = stack.get(NTDataComponents.BACTERIA).bacteriaInstance().getBacteria();
            Bacteria bacteria = BacteriaHelper.getBacteria(Minecraft.getInstance().level.registryAccess(), bacteriaType);
            return layer == 1 ? bacteria.stats().color() : -1;
        }, NTItems.PETRI_DISH);
        event.register(new DynamicFluidContainerModel.Colors(), NTFluids.SALT_WATER.getBucket());
    }
}
