package com.portingdeadmods.nautec.content.entites;

import com.portingdeadmods.nautec.utils.MathUtils;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.ThrownTrident;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

public class ThrownSpreadingTrident extends ThrownTrident {
    int bouncesLeft = 0;

    public ThrownSpreadingTrident(EntityType<? extends ThrownTrident> entityType, Level level) {
        super(entityType, level);
    }

    public ThrownSpreadingTrident(Level world, LivingEntity owner, ItemStack stack, int bouncesLeft) {
        super(world, owner, stack);
        this.bouncesLeft = bouncesLeft;
    }

    public void createSpreadingTrident(double angleOffset, BlockHitResult result, Vec3 reflect, Level level, LivingEntity thrower, int bouncesLeft) {
        if (thrower == null)
            return;
        ThrownSpreadingTrident trident = new ThrownSpreadingTrident(level, thrower, ItemStack.EMPTY, bouncesLeft);
        reflect = MathUtils.rotateYawDegrees(reflect, angleOffset);
        trident.setPos(
                result.getLocation().x() + reflect.x() / 5.0D,
                result.getLocation().y() + reflect.y() / 5.0D,
                result.getLocation().z() + reflect.z() / 5.0D
        );

        trident.setDeltaMovement(reflect);

        double speed = reflect.length();

        trident.setXRot((float) Math.toDegrees(Mth.atan2(reflect.y, speed)));
        trident.setYRot((float) Math.toDegrees(Mth.atan2(reflect.x, reflect.z)));

        trident.reapplyPosition();
        trident.pickup = Pickup.DISALLOWED;
        trident.noPhysics = this.noPhysics;

        this.level().addFreshEntity(trident);
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        this.pickup = Pickup.DISALLOWED;
        if (result instanceof BlockHitResult && bouncesLeft > 0) {
            bouncesLeft--;
            bounce(((BlockHitResult) result));
            remove(RemovalReason.DISCARDED);
        }
    }

    private void bounce(BlockHitResult result) {
        Direction face = result.getDirection();

        Vec3 normal = new Vec3(face.getNormal().getX(), face.getNormal().getY(), face.getNormal().getZ());
        Vec3 motion = getDeltaMovement();
        double dot = motion.dot(normal) * 2.0D;
        Vec3 reflect = motion.subtract(normal.scale(dot));

        double verticalBoost = 0.3D;
        reflect = reflect.add(0.0D, verticalBoost, 0.0D);

        double forwardDamping = 0.5D;
        reflect = reflect.multiply(new Vec3(forwardDamping, 1.0D, forwardDamping));

        createSpreadingTrident(0, result, reflect, this.level(), (LivingEntity) getOwner(), bouncesLeft);
        createSpreadingTrident(8, result, reflect, this.level(), (LivingEntity) getOwner(), bouncesLeft);
        createSpreadingTrident(-8, result, reflect, this.level(), (LivingEntity) getOwner(), bouncesLeft);

    }

    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        bouncesLeft = 0;
    }

    @Override
    protected ItemStack getPickupItem() {
        return ItemStack.EMPTY;

    }

    @Override
    public void tickDespawn() {
        if (this.tickCount > 200) {
            this.remove(RemovalReason.DISCARDED);
        }
    }
}
