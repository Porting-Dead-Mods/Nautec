package com.portingdeadmods.modjam.mixin;


import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.StructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(StructurePiece.class)
public class StructurePieceMixin {

    @ModifyVariable(
            method = "<init>(Lnet/minecraft/world/level/levelgen/structure/pieces/StructurePieceType;ILnet/minecraft/world/level/levelgen/structure/BoundingBox;)V",
            at = @At("HEAD"),
            ordinal = 0,
            argsOnly = true
    ) // Yes this code was taking from Deeper Oceans. Thanks to them.
    private static BoundingBox deeperoceans$lowerOceanMonuments(BoundingBox boundingBox, StructurePieceType type) {
        if (type == StructurePieceType.OCEAN_MONUMENT_BUILDING){
            return boundingBox.moved(0, -30, 0);
        }
        return boundingBox;
    }
}
