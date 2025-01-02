package com.portingdeadmods.nautec.content.blockentities;

import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;
import com.portingdeadmods.nautec.capabilities.IOActions;
import com.portingdeadmods.nautec.content.blocks.LaserJunctionBlock;
import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import it.unimi.dsi.fastutil.objects.ObjectSet;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.capabilities.BlockCapability;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static org.codehaus.plexus.util.StringUtils.capitalizeFirstLetter;

public class LaserJunctionBlockEntity extends LaserBlockEntity {
    private final Set<Direction> inputDirections;
    private final Set<Direction> outputDirections;

    public LaserJunctionBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(NTBlockEntityTypes.LASER_JUNCTION.get(), blockPos, blockState);
        this.inputDirections = new ObjectOpenHashSet<>();
        this.outputDirections = new ObjectOpenHashSet<>();
    }

    @Override
    public Set<Direction> getLaserInputs() {
        return inputDirections;
    }

    public String getLaserInputsAsString() {
        Set<Direction> inputs = getLaserInputs();
        if (inputs.isEmpty()) {
            return "No inputs";
        }

        // Join the direction names into a readable string with first letter capitalized
        return inputs.stream()
                .map(direction -> capitalizeFirstLetter(direction.getName()))
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }
    @Override
    public Set<Direction> getLaserOutputs() {
        return outputDirections;
    }

    public String getLaserOutputsAsString() {
        Set<Direction> outputs = getLaserOutputs();
        if (outputs.isEmpty()) {
            return "No outputs";
        }

        // Join the direction names into a readable string with first letter capitalized
        return outputs.stream()
                .map(direction -> capitalizeFirstLetter(direction.getName()))
                .reduce((a, b) -> a + ", " + b)
                .orElse("");
    }

    @Override
    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {
        return Map.of();
    }

    @Override
    public void commonTick() {
        super.commonTick();
        transmitPower(this.power);
    }
}
