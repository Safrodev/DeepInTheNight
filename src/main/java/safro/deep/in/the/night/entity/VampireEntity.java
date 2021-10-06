package safro.deep.in.the.night.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import safro.deep.in.the.night.entity.goal.BloodDrainAttackGoal;

import java.util.Random;

public class VampireEntity extends HostileEntity {

    public VampireEntity(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    public void initGoals() {
        this.goalSelector.add(7, new WanderAroundFarGoal(this, 1.0D));
        this.goalSelector.add(8, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        this.targetSelector.add(1, new RevengeGoal(this, new Class[0]));
        this.goalSelector.add(2, new BloodDrainAttackGoal(this, 1.0D, false));
        this.targetSelector.add(2, new FollowTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new FollowTargetGoal(this, IronGolemEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createVampireAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25600000417232513D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D);
    }

    public static boolean canSpawn(EntityType<VampireEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return isSpawnDark(world, pos, random);
    }

    public void tickMovement() {
        if (this.isAlive()) {
            if (this.isAffectedByDaylight()) {
                if (this.getEquippedStack(EquipmentSlot.HEAD).isEmpty()) {
                    this.setOnFireFor(8);
                }
            }
        }
        super.tickMovement();
    }

    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_PILLAGER_AMBIENT;
    }

    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_PILLAGER_HURT;
    }

    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_PILLAGER_DEATH;
    }
}
