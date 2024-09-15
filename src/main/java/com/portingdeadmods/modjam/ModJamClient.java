package com.portingdeadmods.modjam;

import com.portingdeadmods.modjam.client.renderer.curios.PrismMonocleCuriosRenderer;
import com.portingdeadmods.modjam.registries.MJItems;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.gui.ConfigurationScreen;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import top.theillusivec4.curios.api.client.CuriosRendererRegistry;

@Mod(value = ModJamClient.MODID, dist = Dist.CLIENT)
public final class ModJamClient {
    public static final String MODID = "modjam";

    public ModJamClient(IEventBus modEventBus, ModContainer container) {
        container.registerExtensionPoint(IConfigScreenFactory.class, ConfigurationScreen::new);
    }
}