package com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide;

import com.klikli_dev.modonomicon.api.datagen.CategoryProvider;
import com.klikli_dev.modonomicon.api.datagen.ModonomiconProviderBase;
import com.klikli_dev.modonomicon.api.datagen.book.BookIconModel;
import com.portingdeadmods.nautec.compat.modonomicon.datagen.book.nautec_guide.getting_started.*;
import com.portingdeadmods.nautec.registries.NTBlocks;
import com.portingdeadmods.nautec.registries.NTItems;
import net.minecraft.world.phys.Vec2;

public class GettingStartedCategory extends CategoryProvider {
    public GettingStartedCategory(ModonomiconProviderBase parent) {
        super(parent);
    }

    @Override
    protected String[] generateEntryMap() {
        return new String[0];
    }

    @Override
    protected void generateEntries() {
        add(new IntroductionEntry(this).generate(new Vec2(0, 0)));
        add(new StructuresEntry(this).generate(new Vec2(2, 0)));
        add(new LaserPowerEntry(this).generate(new Vec2(0, 2)));
        add(new ItemTransformationEntry(this).generate(new Vec2(4, 0)));
        add(new UtilsEntry(this).generate(new Vec2(6, 0)));
        add(new MonocleEntry(this).generate(new Vec2(4, 2)));
        add(new DivingGearEntry(this).generate(new Vec2(2, 2)));
    }

    @Override
    protected String categoryName() {
        return "Getting Started";
    }

    @Override
    protected BookIconModel categoryIcon() {
        return BookIconModel.create(NTBlocks.PRISMARINE_CRYSTAL.get());
    }

    @Override
    public String categoryId() {
        return "getting_started";
    }
}
