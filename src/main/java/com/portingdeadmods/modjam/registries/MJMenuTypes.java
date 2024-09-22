package com.portingdeadmods.modjam.registries;

import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.menus.CrateMenu;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class MJMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(BuiltInRegistries.MENU, ModJam.MODID);

    public static final DeferredHolder<MenuType<?>,MenuType<CrateMenu>> CRATE = registerMenuType(CrateMenu::new,"crate");



    private static <T extends AbstractContainerMenu> DeferredHolder<MenuType<?>,MenuType<T>> registerMenuType(IContainerFactory<T> factory,
                                                                                                              String name) {
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }
}
