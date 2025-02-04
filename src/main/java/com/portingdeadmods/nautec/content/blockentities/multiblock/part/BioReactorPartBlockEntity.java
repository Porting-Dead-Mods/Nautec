package com.portingdeadmods.nautec.content.blockentities.multiblock.part;import com.portingdeadmods.nautec.api.blockentities.LaserBlockEntity;import com.portingdeadmods.nautec.api.blockentities.multiblock.MultiblockPartEntity;import com.portingdeadmods.nautec.capabilities.IOActions;import com.portingdeadmods.nautec.content.multiblocks.BioReactorMultiblock;import com.portingdeadmods.nautec.registries.NTBlockEntityTypes;import com.portingdeadmods.nautec.utils.SidedCapUtils;import it.unimi.dsi.fastutil.Pair;import it.unimi.dsi.fastutil.objects.ObjectSet;import net.minecraft.core.BlockPos;import net.minecraft.core.Direction;import net.minecraft.core.HolderLookup;import net.minecraft.nbt.CompoundTag;import net.minecraft.world.level.block.entity.BlockEntity;import net.minecraft.world.level.block.state.BlockState;import net.neoforged.neoforge.capabilities.BlockCapability;import org.jetbrains.annotations.Nullable;import java.util.Collections;import java.util.HashSet;import java.util.Map;import java.util.Set;public class BioReactorPartBlockEntity extends LaserBlockEntity implements MultiblockPartEntity {    private final Set<Direction> laserInputs;    private final Set<Direction> laserOutputs = Collections.emptySet();    private final Map<Direction, Pair<IOActions, int[]>> sidedInteractions;    private BlockPos controllerPos;    public BioReactorPartBlockEntity(BlockPos pos, BlockState blockState) {        super(NTBlockEntityTypes.BIO_REACTOR_PART.get(), pos, blockState);        this.laserInputs = new HashSet<>();        this.sidedInteractions = Collections.emptyMap();    }    @Override    public BlockPos getControllerPos() {        return this.controllerPos;    }    @Override    public void setControllerPos(BlockPos blockPos) {        this.controllerPos = blockPos;    }    @Override    public Set<Direction> getLaserInputs() {        return laserInputs;    }    @Override    public Set<Direction> getLaserOutputs() {        return laserOutputs;    }    @Override    public <T> Map<Direction, Pair<IOActions, int[]>> getSidedInteractions(BlockCapability<T, @Nullable Direction> capability) {        return sidedInteractions;    }    public void setLaserInput(boolean hatch) {        if (hatch) {            this.laserInputs.add(Direction.UP);        } else {            this.laserInputs.remove(Direction.UP);        }    }    @Override    public void commonTick() {        super.commonTick();        if (getBlockState().getValue(BioReactorMultiblock.BIO_REACTOR_PART) % 2 != 0 && getBlockState().getValue(BioReactorMultiblock.TOP) && laserInputs.size() == 1) {            BlockPos controllerPos1 = getControllerPos();            if (controllerPos1 != null && level.getBlockEntity(controllerPos1) instanceof LaserBlockEntity laserBE) {                BlockPos diff = controllerPos1.subtract(worldPosition);                Direction dir = Direction.fromDelta(diff.getX(), diff.getY(), diff.getZ()).getOpposite();                laserBE.receivePower(getPower(), dir, worldPosition);                laserBE.receiveNewPurity(purity, dir, worldPosition);            }        }    }    @Override    protected void loadData(CompoundTag tag, HolderLookup.Provider provider) {        super.loadData(tag, provider);        setLaserInput(tag.getBoolean("input"));        this.controllerPos = BlockPos.of(tag.getLong("controllerPos"));    }    @Override    protected void saveData(CompoundTag tag, HolderLookup.Provider provider) {        super.saveData(tag, provider);        tag.putBoolean("input", this.laserInputs.size() == 1);        tag.putLong("controllerPos", this.controllerPos.asLong());    }}