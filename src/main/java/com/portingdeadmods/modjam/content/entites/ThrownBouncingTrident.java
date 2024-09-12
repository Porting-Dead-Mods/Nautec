package com.portingdeadmods.modjam.content.entites;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
    boolean hasBounced = false;
    public ThrownBouncingTrident(EntityType<? extends ThrownTrident> entityType, Level level) {
        super(entityType, level);
    }
    public ThrownBouncingTrident(Level world, LivingEntity owner, ItemStack stack) {
        super(world, owner, stack);
    }
    public ThrownBouncingTrident createBouncingTrident(Level level, LivingEntity thrower) {
        if (thrower == null)
            return null;
        ThrownBouncingTrident trident = new ThrownBouncingTrident(level, thrower, ItemStack.EMPTY);
        trident.hasBounced = true;
        return trident;
    }

    @Override
    protected void onHit(HitResult result) {
        super.onHit(result);
        this.pickup = Pickup.DISALLOWED;
        if (result instanceof BlockHitResult && !hasBounced) {
            bounce(((BlockHitResult) result));
            hasBounced = true;
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

        ThrownBouncingTrident trident = createBouncingTrident(this.level(), (LivingEntity) getOwner());
        trident.setPos(
                result.getLocation().x() + reflect.x() / 5.0D,
                result.getLocation().y() + reflect.y() / 5.0D,
                result.getLocation().z() + reflect.z() / 5.0D
        );

        trident.setDeltaMovement(reflect);

        double speed = reflect.length();
        trident.setXRot((float)(Mth.atan2(reflect.y(), speed) * 57.2957763671875D));  // Convert radians to degrees for pitch (x rotation)
        trident.setYRot((float)(Mth.atan2(reflect.x(), reflect.z()) * 57.2957763671875D));  // Convert for yaw (y rotation)

        trident.reapplyPosition();
        trident.pickup = Pickup.DISALLOWED;
        trident.noPhysics = this.noPhysics;

        this.level().addFreshEntity(trident);
    }




    @Override
    protected void onHitEntity(EntityHitResult result) {
        super.onHitEntity(result);
        if (!hasBounced) {
            hasBounced = true;
        }
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
