package com.portingdeadmods.nautec;

import com.portingdeadmods.nautec.api.client.renderer.blockentities.LaserBlockEntityRenderer;
import com.portingdeadmods.nautec.api.client.renderer.items.AnchorItemRenderer;
import com.portingdeadmods.nautec.api.client.renderer.items.PrismarineCrystalItemRenderer;
import com.portingdeadmods.nautec.api.fluids.BaseFluidType;
import com.portingdeadmods.nautec.api.fluids.NTFluid;
import com.portingdeadmods.nautec.client.hud.DivingSuitOverlay;
import com.portingdeadmods.nautec.client.hud.PrismMonocleOverlay;
import com.portingdeadmods.nautec.client.item.AbilityEnabledProperty;
import com.portingdeadmods.nautec.client.item.BacteriaColorTintSource;
import com.portingdeadmods.nautec.client.item.HasBacteriaProperty;
import com.portingdeadmods.nautec.client.model.augment.DolphinFinModel;
import com.portingdeadmods.nautec.client.model.augment.GuardianEyeModel;
import com.portingdeadmods.nautec.client.model.block.*;
import com.portingdeadmods.nautec.client.renderer.augments.GuardianEyeRenderer;
import com.portingdeadmods.nautec.client.renderer.augments.SimpleAugmentRenderer;
import com.portingdeadmods.nautec.client.renderer.blockentities.*;
import com.portingdeadmods.nautec.client.renderer.robotArms.ClawRobotArmRenderer;
import com.portingdeadmods.nautec.client.screen.*;
import com.portingdeadmods.nautec.events.helper.AugmentLayerRenderer;
import com.portingdeadmods.nautec.events.helper.AugmentSlotsRenderer;
import com.portingdeadmods.nautec.registries.*;
import com.portingdeadmods.nautec.utils.ArmorModelsHandler;
import net.minecraft.client.Camera;
import net.minecraft.client.model.Model;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.entity.ThrownTridentRenderer;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.client.renderer.fog.FogData;
import net.minecraft.client.renderer.fog.environment.FogEnvironment;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManagerReloadListener;
import net.minecraft.util.ARGB;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.PlayerModelType;
import net.minecraft.world.item.ItemStack;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.*;
import net.neoforged.neoforge.client.extensions.common.IClientFluidTypeExtensions;
import net.neoforged.neoforge.client.extensions.common.IClientItemExtensions;
import net.neoforged.neoforge.client.extensions.common.RegisterClientExtensionsEvent;
import net.neoforged.neoforge.client.fluid.FluidTintSources;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.fluids.FluidType;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector4f;
import org.joml.Vector4i;
import net.minecraft.client.renderer.block.FluidModel;

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
        modEventBus.addListener(this::registerColorHandlers);
        modEventBus.addListener(this::onLayersAdded);
        modEventBus.addListener(this::registerSpecialModelRenderers);
        modEventBus.addListener(this::registerConditionalItemModelProperties);
        modEventBus.addListener(this::registerFluidModels);
    }

    private void registerGuiOverlays(RegisterGuiLayersEvent event) {
        event.registerAboveAll(Nautec.rl("scanner_info_overlay"), PrismMonocleOverlay.HUD);
        event.registerAboveAll(Nautec.rl("diving_suit_overlay"), DivingSuitOverlay::render);
    }

    private void registerClientExtensions(RegisterClientExtensionsEvent event) {
        for (NTFluid fluid : NTFluids.HELPER.getFluids()) {
            FluidType fluidType = fluid.getFluidType().get();
            if (fluidType instanceof BaseFluidType baseFluidType) {
                event.registerFluidType(new IClientFluidTypeExtensions() {
                    @Override
                    public void modifyFogColor(Camera camera, float partialTick, ClientLevel level, int renderDistance, float darkenWorldAmount, Vector4f fluidFogColor) {
                        Vector4i color = baseFluidType.getColor();
                        fluidFogColor.set(color.x / 255f, color.y / 255f, color.z / 255f, fluidFogColor.w);
                    }

                    @Override
                    public void modifyFogRender(Camera camera, @Nullable FogEnvironment environment, float renderDistance, float partialTick, FogData fogData) {
                        fogData.environmentalStart = 1f;
                        fogData.environmentalEnd = 6f;
                    }
                }, fluidType);
            }
        }

        event.registerItem(new IClientItemExtensions() {
            @Override
            public Model getHumanoidArmorModel(ItemStack itemStack, EquipmentClientInfo.LayerType layerType, Model original) {
                return ArmorModelsHandler.armorModel(ArmorModelsHandler.divingSuit, EquipmentSlot.HEAD);
            }
        }, NTItems.DIVING_HELMET);
    }

    private void registerFluidModels(RegisterFluidModelsEvent event) {
        for (NTFluid fluid : NTFluids.HELPER.getFluids()) {
            FluidType fluidType = fluid.getFluidType().get();
            if (fluidType instanceof BaseFluidType baseFluidType) {
                Vector4i color = baseFluidType.getColor();
                int tint = ARGB.color(color.w, color.x, color.y, color.z);
                Identifier overlay = baseFluidType.getOverlayTexture();
                event.register(new FluidModel.Unbaked(
                        new Material(baseFluidType.getStillTexture()),
                        new Material(baseFluidType.getFlowingTexture()),
                        overlay != null ? new Material(overlay) : null,
                        FluidTintSources.constant(tint)
                ), fluid.stillFluid, fluid.flowingFluid);
            }
        }
    }

    private void registerSpecialModelRenderers(RegisterSpecialModelRendererEvent event) {
        event.register(Nautec.rl("prismarine_crystal"), PrismarineCrystalItemRenderer.Unbaked.MAP_CODEC);
        event.register(Nautec.rl("anchor"), AnchorItemRenderer.Unbaked.MAP_CODEC);
    }

    private void registerConditionalItemModelProperties(RegisterConditionalItemModelPropertyEvent event) {
        event.register(Nautec.rl("ability_enabled"), AbilityEnabledProperty.MAP_CODEC);
        event.register(Nautec.rl("has_bacteria"), HasBacteriaProperty.MAP_CODEC);
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
        event.registerBlockEntityRenderer(NTBlockEntityTypes.DECORATIVE_PRISMARINE_CRYSTAL.get(), DecorativePrismarineCrystalBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.MIXER.get(), MixerBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.CHARGER.get(), ChargerBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.DRAIN.get(), DrainBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.DRAIN_PART.get(), LaserBlockEntityRenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.AUGMENTATION_STATION_EXTENSION.get(), AugmentStationExtensionBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.ANCHOR.get(), AnchorBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.BACTERIAL_ANALYZER.get(), BacterialAnalyzerBERenderer::new);
        event.registerBlockEntityRenderer(NTBlockEntityTypes.FISHING_STATION.get(), FishingStationBERenderer::new);

        AugmentLayerRenderer.registerRenderer(NTAugments.DOLPHIN_FIN.get(),
                ctx -> new SimpleAugmentRenderer<>(DolphinFinModel::new, DolphinFinModel.LAYER_LOCATION, DolphinFinModel.RENDER_TYPE, true, ctx));
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

    private void registerClientReloadListeners(AddClientReloadListenersEvent event) {
        event.addListener(Nautec.rl("augment_renderers"), (ResourceManagerReloadListener) resourceManager -> {
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

    private void onLayersAdded(EntityRenderersEvent.AddLayers event) {
        for (PlayerModelType skin : event.getSkins()) {
            AvatarRenderer<?> renderer = event.getPlayerRenderer(skin);
            if (renderer != null) {
                renderer.addLayer(new AugmentLayerRenderer<>(renderer));
            }
        }
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

    private void registerColorHandlers(RegisterColorHandlersEvent.ItemTintSources event) {
        event.register(Nautec.rl("bacteria_color"), BacteriaColorTintSource.MAP_CODEC);
    }

}
