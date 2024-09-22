package com.portingdeadmods.nautec.content.items.tiers;

import com.portingdeadmods.nautec.registries.MJItems;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.crafting.Ingredient;
import net.neoforged.neoforge.common.SimpleTier;
import net.neoforged.neoforge.common.Tags;

public class MJToolMaterials {
    // We place copper somewhere between stone and iron.
    public static final Tier AQUARINE = new SimpleTier(
            // The tag that determines what blocks this tool cannot break. See below for more information.
            Tags.Blocks.NEEDS_GOLD_TOOL,
            // Determines the durability of the tier.
            // Stone is 131, iron is 250.
            350,
            // Determines the mining speed of the tier. Unused by swords.
            // Stone uses 4, iron uses 6.
            7f,
            // Determines the attack damage bonus. Different tools use this differently. For example, swords do (getAttackDamageBonus() + 4) damage.
            // Stone uses 1, iron uses 2, corresponding to 5 and 6 attack damage for swords, respectively; our sword does 5.5 damage now.
            3f,
            // Determines the enchantability of the tier. This represents how good the enchantments on this tool will be.
            // Gold uses 22, we put copper slightly below that.
            20,
            // Determines the repair ingredient of the tier. Use a supplier for lazy initializing.
            () -> Ingredient.of(MJItems.AQUARINE_STEEL_INGOT)
    );
}
