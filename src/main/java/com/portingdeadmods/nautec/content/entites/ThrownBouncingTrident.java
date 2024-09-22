package com.portingdeadmods.nautec.content.entites;

import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
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

public class ThrownBouncingTrident extends ThrownTrident {
    int bouncesLeft = 1;
    public ThrownBouncingTrident(EntityType<? extends ThrownTrident> entityType, Level level) {
        super(entityType, level);
    }
    public ThrownBouncingTrident(Level world, LivingEntity owner, ItemStack stack, int bouncesLeft) {
        super(world, owner, stack);
        this.bouncesLeft = bouncesLeft;
    }

    public ThrownBouncingTrident createBouncingTrident(Level level, LivingEntity thrower, int bouncesLeft) {
        if (thrower == null)
            return null;
        ThrownBouncingTrident trident = new ThrownBouncingTrident(level, thrower, ItemStack.EMPTY, bouncesLeft);
        return trident;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        this.pickup = Pickup.DISALLOWED;
        if (result instanceof BlockHitResult && bouncesLeft>0) {
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

        ThrownBouncingTrident trident = createBouncingTrident(this.level(), (LivingEntity) getOwner(), bouncesLeft);
        if (trident == null ) return;
        trident.setPos(
                result.getLocation().x() + reflect.x() / 5.0D,
                result.getLocation().y() + reflect.y() / 5.0D,
                result.getLocation().z() + reflect.z() / 5.0D
        );

        trident.setDeltaMovement(reflect);

        double speed = reflect.length();

        /*
            // Can just use Math.toDegrees, big dumdum but the decimal is approx 180 / PI , i guess using toDegrees removes the "magic" number from the function
        trident.setXRot((float)(Mth.atan2(reflect.y(), speed) * 57.2957763671875D));  // Convert radians to degrees for pitch (x rotation)
        trident.setYRot((float)(Mth.atan2(reflect.x(), reflect.z()) * 57.2957763671875D));  // Convert for yaw (y rotation)
        */

        trident.setXRot((float) Math.toDegrees(Mth.atan2(reflect.y, speed)));
        trident.setYRot((float) Math.toDegrees(Mth.atan2(reflect.x, reflect.z)));

        trident.reapplyPosition();
        trident.pickup = Pickup.DISALLOWED;
        trident.noPhysics = this.noPhysics;

        this.level().addFreshEntity(trident);
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
        if (this.tickCount > 200){
            this.remove(RemovalReason.DISCARDED);
        }
    }
}
