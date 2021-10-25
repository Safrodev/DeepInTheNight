package safro.deep.in.the.night.entity;

import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.passive.BatEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.Difficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;
import safro.deep.in.the.night.entity.goal.CustomAttackGoal;
import safro.deep.in.the.night.registry.EffectRegistry;

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
        this.goalSelector.add(2, new CustomAttackGoal(this, 1.0D, false));
        this.targetSelector.add(2, new FollowTargetGoal(this, PlayerEntity.class, true));
        this.targetSelector.add(3, new FollowTargetGoal(this, IronGolemEntity.class, true));
    }

    public static DefaultAttributeContainer.Builder createVampireAttributes() {
        return HostileEntity.createHostileAttributes().add(EntityAttributes.GENERIC_FOLLOW_RANGE, 15.0D).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25600000417232513D).add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0D);
    }

    public static boolean canSpawn(EntityType<VampireEntity> type, ServerWorldAccess world, SpawnReason spawnReason, BlockPos pos, Random random) {
        return isSpawnDark(world, pos, random);
    }

    public void remove(RemovalReason reason) {
        if (!world.isClient && this.isDead()) {
            BatEntity bat = EntityType.BAT.create(world);
            bat.refreshPositionAndAngles(this.getBlockPos(), 0.0F, 0.0F);
            world.spawnEntity(bat);
        }
        super.remove(reason);
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

    public boolean tryAttack(Entity target) {
        if (super.tryAttack(target)) {
            if (target instanceof LivingEntity) {
                int i = 0;
                if (this.world.getDifficulty() == Difficulty.NORMAL) {
                    i = 1;
                } else if (this.world.getDifficulty() == Difficulty.HARD) {
                    i = 2;
                } else if (this.world.getDifficulty() == Difficulty.EASY) {
                    i = 0;
                }
                ((LivingEntity)target).addStatusEffect(new StatusEffectInstance(EffectRegistry.BLEEDING, 1200, 0), this);
            }
            return true;
        } else {
            return false;
        }
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
