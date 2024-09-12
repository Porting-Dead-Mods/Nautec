package com.portingdeadmods.modjam.exampleCustom3DArmor;
import com.portingdeadmods.modjam.ModJam;
import com.portingdeadmods.modjam.content.items.tiers.MJArmorMaterials;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ArmorMaterial;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.Nullable;

public class TestItem extends ArmorItem {
    public TestItem(Properties properties) {
		super(MJArmorMaterials.PRISMARINE,Type.HELMET,properties);
    }

    @Override
    public @Nullable ResourceLocation getArmorTexture(ItemStack stack, Entity entity, EquipmentSlot slot, ArmorMaterial.Layer layer, boolean innerModel) {
        return ResourceLocation.fromNamespaceAndPath(ModJam.MODID,"textures/example/test.png");
    }
}
