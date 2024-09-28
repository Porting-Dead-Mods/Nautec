package com.portingdeadmods.nautec.compat.jade;

import com.portingdeadmods.nautec.content.blocks.AquaticCatalystBlock;
import com.portingdeadmods.nautec.content.blocks.LaserJunctionBlock;
import com.portingdeadmods.nautec.content.blocks.MixerBlock;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;
import snownee.jade.api.WailaPlugin;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

    @Override
    public void register(IWailaCommonRegistration registration) {
        //TODO register data providers
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(AquaticCatalystComponentProvider.INSTANCE, AquaticCatalystBlock.class);
        registration.registerBlockComponent(LaserJunctionComponentProvider.INSTANCE, LaserJunctionBlock.class);
        registration.registerBlockComponent(MixerComponentProvider.INSTANCE, MixerBlock.class);
    }
}