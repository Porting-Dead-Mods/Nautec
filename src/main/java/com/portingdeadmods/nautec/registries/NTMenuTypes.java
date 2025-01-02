package com.portingdeadmods.nautec.registries;

import com.portingdeadmods.nautec.Nautec;
import com.portingdeadmods.nautec.content.menus.*;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public final class NTMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, Nautec.MODID);

    public static final Supplier<MenuType<CrateMenu>> CRATE = registerMenuType(CrateMenu::new, "crate");
    public static final Supplier<MenuType<MixerMenu>> MIXER = registerMenuType(MixerMenu::new, "mixer");
    public static final Supplier<MenuType<AugmentMenu>> AUGMENTS = registerMenuType(AugmentMenu::new, "augments");
    public static final Supplier<MenuType<AugmentationStationExtensionMenu>> AUGMENT_STATION_EXTENSION = registerMenuType(AugmentationStationExtensionMenu::new, "augment_station_extension");
    public static final Supplier<MenuType<BioReactorMenu>> BIO_REACTOR = registerMenuType(BioReactorMenu::new, "bio_reactor");
    public static final Supplier<MenuType<IncubatorMenu>> INCUBATOR = registerMenuType(IncubatorMenu::new, "incubator");
    public static final Supplier<MenuType<MutatorMenu>> MUTATOR = registerMenuType(MutatorMenu::new, "mutator");
    public static final Supplier<MenuType<BacterialAnalyzerMenu>> BACTERIAL_ANALYZER = registerMenuType(BacterialAnalyzerMenu::new, "bacterial_analyzer");


    private static <T extends AbstractContainerMenu> Supplier<MenuType<T>> registerMenuType(IContainerFactory<T> factory, String name) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }
}
