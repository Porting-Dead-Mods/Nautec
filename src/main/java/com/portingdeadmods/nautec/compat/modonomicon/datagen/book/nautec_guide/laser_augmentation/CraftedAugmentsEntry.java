package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.laser_augmentation;

import com.klikli_dev.modonomicon.api.datagen.CategoryProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookCraftingRecipePageModel;
import com.klikli_dev.modonomicon.api.datagen.book.page.BookTextPageModel;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.BaseNautecEntry;
import com.portingdeadmods.nautec.registries.NTItems;

public class CraftedAugmentsEntry extends BaseNautecEntry {
    public CraftedAugmentsEntry(CategoryProviderBase parent) {
        super(parent, "crafted_augments", "Crafted Augments", "Build what nature forgot", BookIconModel.create(NTItems.MAGNETIC_COIL_ARM));
    }

    @Override
    protected void generatePages() {
        this.page("intro", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Crafted Augments");
        this.pageText("""
                Not every augment has to be cut out of something that
                used to be alive. With enough Aquatic Chips and steel
                you can build your own.
                \\
                \\
                Each part below is applied at the Augmentation Station
                exactly like an organ, and each one fits a specific set
                of slots.
                """);

        this.page("limbs", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Legs");
        this.pageText("""
                You only have two leg slots, so pick two:
                \\
                The Hydraulic Leg launches you where you look.
                \\
                The Servo Knee steps you up full blocks.
                \\
                The Shock Absorber cancels fall damage.
                \\
                The Tendon Weave makes you walk much faster.
                """);

        this.page("leg_recipes0", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Hydraulic Leg")
                .withRecipeId1("nautec:hydraulic_leg")
                .withTitle2("Servo Knee")
                .withRecipeId2("nautec:servo_knee"));

        this.page("leg_recipes1", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Shock Absorber")
                .withRecipeId1("nautec:shock_absorber")
                .withTitle2("Tendon Weave")
                .withRecipeId2("nautec:tendon_weave"));

        this.page("arms", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Arms");
        this.pageText("""
                Six arms compete for two arm slots.
                \\
                The Magnetic Coil Arm drags loose items to you, and the
                Ender Coil Arm skips the trip and puts them straight in
                your inventory. Crouch to hold either one back.
                \\
                The Hydro Drill Arm cancels the underwater mining
                penalty entirely.
                """);

        this.page("arm_recipes0", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Magnetic Coil Arm")
                .withRecipeId1("nautec:magnetic_coil_arm")
                .withTitle2("Ender Coil Arm")
                .withRecipeId2("nautec:ender_coil_arm"));

        this.page("arm_recipes1", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Hydro Drill Arm")
                .withRecipeId1("nautec:hydro_drill_arm")
                .withTitle2("Syringe Robot Arm")
                .withRecipeId2("nautec:syringe_robot_arm"));

        this.page("weapon_arms", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Armed Arms");
        this.pageText("""
                The Trident Launcher Arm throws a trident that bounces
                between whatever it hits, on 'Y' by default.
                \\
                The Volley Trident Arm throws a spread of them instead,
                on 'U' by default.
                \\
                The Syringe Robot Arm throws a random splash potion, on
                'G' by default. It is as helpful as it sounds.
                """);

        this.page("weapon_arm_recipes", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Trident Launcher Arm")
                .withRecipeId1("nautec:trident_launcher_arm")
                .withTitle2("Volley Trident Arm")
                .withRecipeId2("nautec:volley_trident_arm"));

        this.page("core", () -> BookTextPageModel.create()
                .withTitle(this.context().pageTitle())
                .withText(this.context().pageText()));
        this.pageTitle("Body and Heart");
        this.pageText("""
                The Buoyancy Tank takes your body slot and lets you fly,
                in water and out of it. It costs a Heart of the Sea.
                \\
                \\
                The Auxiliary Ventricle takes your heart slot and doubles
                your maximum health. It costs an Eldritch Heart, so you
                are choosing it over the regeneration one.
                """);

        this.page("core_recipes", () -> BookCraftingRecipePageModel.create()
                .withTitle1("Buoyancy Tank")
                .withRecipeId1("nautec:buoyancy_tank")
                .withTitle2("Auxiliary Ventricle")
                .withRecipeId2("nautec:auxiliary_ventricle"));
    }
}
